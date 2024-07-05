package in.pms.transaction.dao;

import in.pms.global.dao.DaoHelper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManpowerExpensesDao {

	@Autowired
	DaoHelper daoHelper;
	
	//Query for Expenditure 
		public Double getBaseCostFromDesignationName(String designationName, int numDeputedAt, long projectId){
		
			StringBuilder query = new StringBuilder("Select a.cost from pms.project_category_designation_mapping a, pms.pms_designation_for_client c, pms.application_project ap, pms.trans_application t");
			query.append(" where a.num_designation_id_fk = c.num_designation_id and c.designation_name=?0 and a.num_deputed_at=?1");
			query.append(" and a.num_category_id_fk= t.num_project_category_fk and t.num_application_id = ap.application_id and ap.project_id= ?2");
			
//			StringBuffer query = new StringBuffer("Select a.cost from DesignationProjectCategoryMapping a JOIN ProjectMasterDomain p ON a.projectCategoryMaster.numId = p.application.categoryMaster.numId");
//			query.append("  where a.numIsValid =1 and a.designationForClient.designationName ='?0' and p.numId=?1");
			
//			ArrayList<Object> paraList= new ArrayList<>();
//			paraList.add(designationName);
//			paraList.add(projectId);

			List<Object> params = new ArrayList<>();
		    params.add(designationName);
		    params.add(numDeputedAt);
		    params.add(projectId);

	    	List<Double> list = daoHelper.runNativeQueryWithParams(query.toString(), params);
			
			if(!list.isEmpty()){
				return list.get(0);
			}
			return null;
			
		}
		
		public Double getBaseCostFromDesignationId(int designationId, int numDeputedAt, long projectId){
		
			StringBuilder query = new StringBuilder("Select a.cost from pms.project_category_designation_mapping a, pms.pms_designation_for_client c, pms.application_project ap, pms.trans_application t");
			query.append(" where a.num_designation_id_fk = c.num_designation_id and c.num_designation_id=?0 and a.num_deputed_at=?1");
			query.append(" and a.num_category_id_fk= t.num_project_category_fk and t.num_application_id = ap.application_id and ap.project_id= ?2");

			List<Object> params = new ArrayList<>();
		    params.add(designationId);
		    params.add(numDeputedAt);
		    params.add(projectId);

	    	List<Double> list = daoHelper.runNativeQueryWithParams(query.toString(), params);
			
			if(!list.isEmpty()){
				return list.get(0);
			}
			return null;
			
		}
	
	public Double calculateEffectiveBaseCostByDesgId(int designationId, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted) {
        String query = new StringBuilder()
                .append("WITH params AS (SELECT ")
                .append("TIMESTAMP '"+overlapStartFormatted+"' AS given_start_date, ")
                .append("TIMESTAMP '"+overlapEndFormatted+"' AS given_end_date ")
                .append("), overlapping_data AS (SELECT *, CASE WHEN dt_to_date IS NULL THEN ")
                .append("(EXTRACT(EPOCH FROM (SELECT given_end_date FROM params) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400 + 1) * (cost) ")
                .append("ELSE (GREATEST(0, EXTRACT(EPOCH FROM LEAST((SELECT given_end_date FROM params), dt_to_date) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400) + 1) * (cost) ")
                .append("END AS overlapping_cost, CASE WHEN dt_to_date IS NULL THEN ")
                .append("(EXTRACT(EPOCH FROM (SELECT given_end_date FROM params) - GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400 + 1) ")
                .append("ELSE (GREATEST(0, EXTRACT(EPOCH FROM LEAST((SELECT given_end_date FROM params), dt_to_date) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400) + 1) ")
                .append("END AS overlapping_days FROM ( SELECT *, CASE WHEN dt_from_date IS NULL THEN ")
                .append("CASE WHEN (SELECT given_start_date FROM params) > dt_to_date THEN dt_to_date ELSE (SELECT given_start_date FROM params) ")
                .append("END ELSE dt_from_date END AS adjusted_from_date FROM pms_log.project_category_designation_mapping_log ) AS adjusted_dates, ")
                .append("pms.application_project ap, pms.trans_application t where adjusted_dates.num_designation_id_fk = ?0 ")
                .append("and adjusted_dates.num_deputed_at = ?1 and adjusted_dates.num_category_id_fk= t.num_project_category_fk and t.num_application_id = ap.application_id ")
                .append("and ap.project_id=?2 AND ( (dt_to_date IS NULL AND (SELECT given_end_date FROM params) >= adjusted_from_date) ")
                .append("OR (dt_to_date IS NOT NULL AND (SELECT given_start_date FROM params) <= dt_to_date AND ")
                .append("(SELECT given_end_date FROM params) >= adjusted_from_date) ) ) SELECT ")
                .append("CASE WHEN SUM(overlapping_days) = 0 THEN 0 ELSE SUM(overlapping_cost) / SUM(overlapping_days) END AS total_cost FROM overlapping_data")
                .toString();

		        List<Object> params = new ArrayList<>();
			    params.add(designationId);
			    params.add(numDeputedAt);
			    params.add(projectId);
		
		    	List<Double> list = daoHelper.runNativeQueryWithParams(query, params);
				
				if(!list.isEmpty()){
					return list.get(0);
				}
				return null;
    }
	
	public Double calculateEffectiveBaseCostByDesgName(String designationName, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted) {
        String query = new StringBuilder()
                .append("WITH params AS (SELECT ")
                .append("TIMESTAMP '"+overlapStartFormatted+"' AS given_start_date, ")
                .append("TIMESTAMP '"+overlapEndFormatted+"' AS given_end_date ")
                .append("), overlapping_data AS (SELECT *, CASE WHEN dt_to_date IS NULL THEN ")
                .append("(EXTRACT(EPOCH FROM (SELECT given_end_date FROM params) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400 + 1) * (cost) ")
                .append("ELSE (GREATEST(0, EXTRACT(EPOCH FROM LEAST((SELECT given_end_date FROM params), dt_to_date) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400) + 1) * (cost) ")
                .append("END AS overlapping_cost, CASE WHEN dt_to_date IS NULL THEN ")
                .append("(EXTRACT(EPOCH FROM (SELECT given_end_date FROM params) - GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400 + 1) ")
                .append("ELSE (GREATEST(0, EXTRACT(EPOCH FROM LEAST((SELECT given_end_date FROM params), dt_to_date) - ")
                .append("GREATEST((SELECT given_start_date FROM params), adjusted_from_date)) / 86400) + 1) ")
                .append("END AS overlapping_days FROM ( SELECT *, CASE WHEN dt_from_date IS NULL THEN ")
                .append("CASE WHEN (SELECT given_start_date FROM params) > dt_to_date THEN dt_to_date ELSE (SELECT given_start_date FROM params) ")
                .append("END ELSE dt_from_date END AS adjusted_from_date FROM pms_log.project_category_designation_mapping_log ) AS adjusted_dates, pms.pms_designation_for_client c, ")
                .append("pms.application_project ap, pms.trans_application t where adjusted_dates.num_designation_id_fk = c.num_designation_id and c.designation_name = ?0 ")
                .append("and adjusted_dates.num_deputed_at = ?1 and adjusted_dates.num_category_id_fk= t.num_project_category_fk and t.num_application_id = ap.application_id ")
                .append("and ap.project_id=?2 AND ( (dt_to_date IS NULL AND (SELECT given_end_date FROM params) >= adjusted_from_date) ")
                .append("OR (dt_to_date IS NOT NULL AND (SELECT given_start_date FROM params) <= dt_to_date AND ")
                .append("(SELECT given_end_date FROM params) >= adjusted_from_date) ) ) SELECT ")
                .append("CASE WHEN SUM(overlapping_days) = 0 THEN 0 ELSE SUM(overlapping_cost) / SUM(overlapping_days) END AS total_cost FROM overlapping_data")
                .toString();
        
		        List<Object> params = new ArrayList<>();
			    params.add(designationName);
			    params.add(numDeputedAt);
			    params.add(projectId);

	        	List<Double> list = daoHelper.runNativeQueryWithParams(query, params);
				
				if(!list.isEmpty()){
					return list.get(0);
				}
				return null;
    }
	
	//Function added by devesh on 06-05-24 to get base cost details
	public List<Object[]> getBaseCostDetailsFromDesignationName(String categoryName, String designationName, int numDeputedAt){
		StringBuilder buffer = new StringBuilder("SELECT p.num_designation_id, p.cost, p.dt_from_date, p.dt_to_date, p.num_designation_id_fk, p.num_deputed_at,");
		buffer.append(" p.num_category_id_fk, d.designation_name, c.str_category_name FROM pms_log.project_category_designation_mapping_log p, pms.pms_designation_for_client d,");
		buffer.append(" pms.pms_project_category_master c WHERE p.num_isvalid=1 and d.num_isvalid=1 and c.num_isvalid=1 and p.num_designation_id_fk=d.num_designation_id");
		buffer.append(" and p.num_category_id_fk=c.num_project_category_id and d.designation_name = ?0 and c.str_category_name = ?1 ");
		buffer.append(" and p.num_deputed_at = ?2 order by p.dt_from_date desc");
		
		String query = buffer.toString();
	    List<Object> params = new ArrayList<>();
	    params.add(designationName);
	    params.add(categoryName);
	    params.add(numDeputedAt);
	    
		return daoHelper.runNativeQueryWithParams(query, params);
	}
	//End of function
	
}
