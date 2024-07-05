package in.pms.transaction.dao;

import in.pms.transaction.domain.ApprovedJobDomain;
import in.pms.transaction.domain.EmployeeApprovedJobMapping;
import in.pms.transaction.model.ApprovedJobModel;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedJobDao extends JpaRepository<ApprovedJobDomain, Integer>  { 
	
	
	@Query("select distinct a.designationMaster.numDesignationId, a.approvedOn,a.durationInMonths from ApprovedJobDomain a where a.projectMasterDomain.numId= :projectId and a.numIsValid=1")
	public List<Object[]> getApprovedJobDetails(@Param("projectId") long projectId);
	
	@Query("from ApprovedJobDomain a where a.projectMasterDomain.numId= :projectId and  a.designationMaster.numDesignationId= :designationId  and a.approvedOn=:approvedOn and a.numIsValid=1")
	public List<ApprovedJobDomain> getJobCodeDetails(@Param("projectId") long projectId,@Param("designationId") int designationId,@Param("approvedOn") Date approvedOn);
		
	@Query(nativeQuery=true,value= "select a.num_group_id_fk, a.num_designation_id_fk, b.str_group_name, c.designation_name,c.num_hierarchy, count(*) from pms_approved_job_detail a,pms_group_master b , pms_designation_master c where a.num_isvalid= 1  and a.num_designation_id_fk = c.num_designation_id and a.num_group_id_fk = b.group_id and a.num_group_id_fk in (:groups) and a.dt_approved_on >= :startDate and a.dt_approved_on <= :endDate  group by 1,2,3,4,5 order by 3,5")
	public List<Object[]>  getAllApprovedJobsCount(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("groups") List<Long> groups);

	@Query(nativeQuery=true,value= "select a.num_group_id_fk, a.num_designation_id_fk, b.str_group_name, c.designation_name,c.num_hierarchy, count(*) from pms_approved_job_detail a,pms_group_master b , pms_designation_master c where a.num_isvalid= 1  and a.num_designation_id_fk = c.num_designation_id and a.num_group_id_fk = b.group_id and a.num_group_id_fk in (:groups)  and a.str_status='Created' group by 1,2,3,4,5 order by 3,5")
	public List<Object[]>  getAllFreshJobsCount(@Param("groups") List<Long> groups);

	@Query(nativeQuery=true,value= "select a.num_group_id_fk, a.num_designation_id_fk, b.str_group_name, c.designation_name,c.num_hierarchy, count(*) from pms_approved_job_detail a,pms_group_master b , pms_designation_master c where a.num_isvalid= 1  and a.num_designation_id_fk = c.num_designation_id and a.num_group_id_fk = b.group_id and a.num_group_id_fk in (:groups) and a.dt_tr_date >= :startDate and  a.dt_tr_date <= :endDate  and a.str_status=:status group by 1,2,3,4,5 order by 3,5")
	public List<Object[]>  getJobsCountByStatus(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("groups") List<Long> groups,@Param("status") String status);

	@Query(nativeQuery=true,value= "select a.num_group_id_fk, a.num_designation_id_fk, b.str_group_name, c.designation_name,c.num_hierarchy, count(*) from pms_approved_job_detail a,pms_group_master b , pms_designation_master c where a.num_isvalid= 1  and a.num_designation_id_fk = c.num_designation_id and a.num_group_id_fk = b.group_id and a.num_group_id_fk in (:groups) and a.dt_closed_on between :startDate and :endDate  and a.str_status='Closed' group by 1,2,3,4,5 order by 3,5")
	public List<Object[]>  getAllClosedJobsCount(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("groups") List<Long> groups);

	@Query("from ApprovedJobDomain where numIsValid=1 and status='Created' and projectMasterDomain.numId= :projectId")
	public List<ApprovedJobDomain> getApprovedJobDetailsCreated(@Param("projectId") long projectId);
	
	@Query("select a from EmployeeApprovedJobMapping a join fetch a.employeeMasterDomain where a.numIsValid=1 and a.approvedJob.numId=:jobId")
	public List<EmployeeApprovedJobMapping> getApprovedJobDetails(@Param("jobId") int jobId);

}
