package in.pms.transaction.dao;
import in.pms.global.domain.TransactionMasterDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.transaction.domain.MonthlyProgressDomain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MonthlyProgressDao extends JpaRepository<MonthlyProgressDomain, Integer>
{
	@Query(value ="SELECT * from pms_monthly_progress_status b where b.num_month=:month and b.num_year=:year and b.num_group_id_fk=:groupId and b.num_isvalid=1 and b.num_project_id_fk=:projectId", nativeQuery=true)
	public MonthlyProgressDomain getdetail(@Param("year") int year,@Param("month") int month,@Param("groupId") long groupId,@Param("projectId") long projectId);

	@Query(value ="SELECT a.num_category_id,a.num_parent_category,a.num_category_sequence from pms_category_master a where a.num_isvalid=1 and a.str_project_group_flag=:type", nativeQuery=true)
	public List<Object[]> getCateoryDetail(@Param("type") String type);
	
	@Query(value ="SELECT * from pms_monthly_progress_status b where b.num_month=:month and b.num_year=:year and b.num_group_id_fk=:groupId and b.num_isvalid=1 and b.num_project_id_fk=0", nativeQuery=true)
	public MonthlyProgressDomain getdetailByGroup(@Param("year") int year,@Param("month") int month,@Param("groupId") long groupId);
	
	@Query(value ="SELECT b.* from pms_monthly_progress_status b,pms_transaction_master t,pms_project_master p where b.num_month=:month and b.num_year=:year and b.num_project_id_fk in (:projectIds) and b.num_progress_status_id=t.num_monthly_progress_id_fk and b.num_progress_status_id = T .num_monthly_progress_id_fk AND b.num_project_id_fk=p.num_project_id and p.num_corp_monthly_sharing in (:selectedProjectType)and t.num_action_id_fk=6", nativeQuery=true)
	public List<MonthlyProgressDomain> getProjectIdsForReportWithSelectedType(@Param("year") int year,@Param("month") int month,@Param("projectIds") List<Long> projectIds,@Param("selectedProjectType") int selectedProjectType);


	@Query(value="select a.num_category_id,b.str_category_name,b.str_category_controller from pms_monthly_progress_status_dtl a,pms_category_master b where a.num_isvalid=1 and num_progress_id_fk=:parentId and a.num_category_id= b.num_category_id and b.num_parent_category=0 order by a.num_cateory_sequence",nativeQuery=true)
	public List<Object[]> getCategoryByParentId(@Param("parentId") long parentId);
	
	@Query(value="select a.num_category_id,b.str_category_name,b.str_category_controller from pms_monthly_progress_status_dtl a,pms_category_master b where a.num_isvalid=1 and num_progress_id_fk=:parentId and a.num_category_id= b.num_category_id and b.num_parent_category=:categoryId order by a.num_cateory_sequence",nativeQuery=true)
	public List<Object[]> getChildCategoryByParentId(@Param("parentId") long parentId,@Param("categoryId") long categoryId);
	

	@Query(value="select count(*) from TransactionMasterDomain a where a.numIsValid=1 and a.actionMasterDomain.numActionId=:actionId and a.monthlyProgressDomain.groupMaster.numId=:groupId")
	public int activeProgressReportsWithGCCount(@Param("groupId") long groupId,@Param("actionId")long actionId);
	
	@Query(value="from TransactionMasterDomain a where a.numIsValid=1 and a.actionMasterDomain.numActionId=:actionId and a.monthlyProgressDomain.groupMaster.numId=:groupId")
	public List<TransactionMasterDomain> activeProgressReportsWithGCDetails(@Param("groupId") long groupId,@Param("actionId")long actionId);
	
	@Query(value="select count(*) from pms_transaction_master t, pms_monthly_progress_status p where t.num_action_id_fk=:actionId and p.num_progress_status_id=t.num_monthly_progress_id_fk and p.num_month=:month and p.num_year=:year and t.num_isvalid=1",nativeQuery=true)
	public int allActiveProgressReportsbyGCCount(@Param("year") int year,@Param("month") int month,@Param("actionId")long actionId);
	
	@Query(value="select t.num_monthly_progress_id_fk,p.num_project_id_fk,p.num_month,num_year,t.dt_tr_date,p.num_group_id_fk,r.str_action_performed from pms_transaction_master t, pms_monthly_progress_status p,pms_action_master r where t.num_action_id_fk=:actionId and p.num_progress_status_id=t.num_monthly_progress_id_fk AND r.num_action_id=T.num_action_id_fk and p.num_month=:month and p.num_year=:year and t.num_isvalid=1 order by p.num_group_id_fk",nativeQuery=true)
	public List<Object[]> activeProgressReportsDetailsbyGCforCurrentMonth(@Param("year") int year,@Param("month") int month,@Param("actionId")long actionId);
	
	@Query(value="select count(*) from pms_transaction_master t, pms_monthly_progress_status p where t.num_action_id_fk=:actionId and p.num_project_id_fk in (:assignedProjects) and p.num_progress_status_id=t.num_monthly_progress_id_fk and t.num_isvalid=1",nativeQuery=true)
	public int sentForRevisionCount(@Param("actionId")long actionId,@Param("assignedProjects")String assignedProjects);
	
	@Query(value ="SELECT b.* from pms_monthly_progress_status b,pms_transaction_master t where b.num_month=:month and b.num_year=:year and b.num_project_id_fk in (:projectIds) and b.num_progress_status_id=t.num_monthly_progress_id_fk and t.num_action_id_fk=6", nativeQuery=true)
	public List<MonthlyProgressDomain> getProjectIdsForReport(@Param("year") int year,@Param("month") int month,@Param("projectIds") List<Long> projectIds);
	
	@Query(value ="SELECT G.group_id,G .str_group_name,A .num_project_id FROM pms_project_master A,trans_application b,application_project C,pms_transaction_master T,pms_monthly_progress_status P,pms_action_master r,pms_group_master G WHERE A .num_project_id = C .project_id AND b.num_application_id = C .application_id AND b.num_group_id_fk = G .group_id AND A .num_project_id = P .num_project_id_fk AND P .num_progress_status_id = T .num_monthly_progress_id_fk AND T .num_action_id_fk IN (4, 7) AND P .num_month = :currentMonth AND P .num_year = :currentYear AND T .num_isvalid = 1 AND A .str_project_status = 'Ongoing' AND A .dt_project_start <= :dtLastdate AND T .num_action_id_fk = r.num_action_id UNION SELECT ta.num_group_id_fk ,G .str_group_name,ppm.num_project_id FROM trans_application ta, application_project ap,pms_project_master ppm,pms_group_master G WHERE ta.num_isvalid = 1 AND ta.num_group_id_fk = G .group_id and ta.num_application_id = ap.application_id AND ppm.num_project_id = ap.project_id AND ppm.num_isvalid = 1 AND ppm.str_project_status = 'Ongoing' AND ppm .dt_project_start <= :dtLastdate AND ppm.num_project_id NOT IN (SELECT P .num_project_id_fk FROM pms_transaction_master T, pms_monthly_progress_status P WHERE P .num_progress_status_id = T .num_monthly_progress_id_fk AND T .num_isvalid = 1 and P.num_year=:currentYear and P.num_month=:currentMonth)", nativeQuery=true)
	public List<Object[]> getPendingMonthlyProgReportAtGC(@Param("currentMonth") String currentMonth,@Param("currentYear") String currentYear,@Param("dtLastdate") Date dtLastdate);
	
	@Query(value ="select str_project_name from pms_project_master where num_project_id in :projectIds", nativeQuery=true)
	public List<String> getProjectNameById(@Param("projectIds") List<Long> projectIds);
	
	@Query(value="select distinct p from TempProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList  where p.documentTypeMasterDomain.numId in :docTypeIds and p.numIsValid=1 and  p.projectId=:projectId  order by p.documentDate desc")
	public List<TempProjectDocumentMasterDomain> uploadedTempDocumentByProjectId(@Param("projectId") long projectId,@Param("docTypeIds") List<Long> docTypeIds);
	
}
