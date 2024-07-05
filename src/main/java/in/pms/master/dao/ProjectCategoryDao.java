package in.pms.master.dao;

import java.util.List;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.transaction.domain.CategoryMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectCategoryDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	public ProjectCategoryMaster mergeProjectCategory(
			ProjectCategoryMaster projectCategoryMaster){
		return daoHelper.merge(ProjectCategoryMaster.class, projectCategoryMaster);
	}
	
	public List<ProjectCategoryMaster> getAllProjectCategory(){
		String query = "from ProjectCategoryMaster where numIsValid<>2";
		return daoHelper.findByQuery(query);	
	}
	
	public List<ProjectCategoryMaster> getActiveProjectCategory(){
		String query = "from ProjectCategoryMaster p where p.numIsValid=1 order by p.categoryName";
		return daoHelper.findByQuery(query);
	}
	
	public ProjectCategoryMaster getProjectCategoryById(int id){
		return daoHelper.findById(ProjectCategoryMaster.class, id);
	}
	
	public List<CategoryMaster> getProjectCategoryByCatId(long categoryId){
		String query = "from CategoryMaster p where   p.numCategoryId="+categoryId+" and  p.numIsValid=1";
		return daoHelper.findByQuery(query);
	}
	
	//Function added by devesh on 06-05-24 to get base cost details
	public List<Object[]> getBaseCostDetails(int categoryId, int designationId){
		StringBuffer buffer = new StringBuffer("SELECT p.num_designation_id, p.cost, p.dt_from_date, p.dt_to_date, p.num_designation_id_fk, p.num_deputed_at,");
		buffer.append(" p.num_category_id_fk, d.designation_name, c.str_category_name FROM pms_log.project_category_designation_mapping_log p, pms.pms_designation_for_client d,");
		buffer.append(" pms.pms_project_category_master c WHERE p.num_isvalid=1 and d.num_isvalid=1 and c.num_isvalid=1 and p.num_designation_id_fk=d.num_designation_id");
		buffer.append(" and p.num_category_id_fk=c.num_project_category_id and p.num_designation_id_fk = "+designationId+"and p.num_category_id_fk = "+categoryId);
		buffer.append(" order by p.dt_from_date desc");
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return obj;
	}
	//End of function

}
