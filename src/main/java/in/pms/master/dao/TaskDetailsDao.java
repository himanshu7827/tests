package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.domain.TaskDocumentDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDetailsDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long mergeTaskDetailsMaster(TaskDetailsDomain taskDetailsDomain){
		taskDetailsDomain = daoHelper.merge(TaskDetailsDomain.class, taskDetailsDomain);
		return taskDetailsDomain.getNumId();
	}

	public TaskDetailsDomain getTaskDetailsById(long numId){
		TaskDetailsDomain taskDetailsDomain =null;
		taskDetailsDomain = daoHelper.findById(TaskDetailsDomain.class, numId);
		return taskDetailsDomain;	
	}
	
	public List<Object[]>  getAllTaskDetailsData(){
		StringBuffer buffer = new StringBuffer("select t.num_task_id,t.dt_tr_date,t.num_isvalid,t.num_tr_user_id,t.num_expected_time,t.str_priority,");
		buffer.append("t.num_project_id,t.str_task_description,t.str_task_name ,p.str_project_name, '' as str_milestone_name, t.num_with_milestone,t.num_milestone_id,");
		buffer.append( "t.milestone_activity_id,'' str_description,t.num_document_id  from pms_task_details_mst t,pms_project_master p where t.num_project_id=p.num_project_id and t.num_milestone_id=0 ");
		buffer.append("union select t.num_task_id,t.dt_tr_date,t.num_isvalid,t.num_tr_user_id,t.num_expected_time,t.str_priority,");
		buffer.append( " t.num_project_id,t.str_task_description,t.str_task_name ,p.str_project_name,m.str_milestone_name,");
		buffer.append( "t.num_with_milestone,t.num_milestone_id,t.milestone_activity_id,a.str_description,t.num_document_id from pms_task_details_mst t,");
		buffer.append( " pms_project_master p , pms_project_milestone_mst m,pms_milestone_activity_mapping a where t.num_project_id=p.num_project_id and t.milestone_activity_id=a.num_id ");
		buffer.append( "and t.num_milestone_id=m.num_milestone_id and t.NUM_ISVALID <> 2 order by 2");
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		return 	obj;
	}	
	public List<TaskDetailsDomain> getActiveTaskDetailsData(){
		String query = "from TaskDetailsDomain where numIsValid=1 ";
		return  daoHelper.findByQuery(query);	
	}
	
	public TaskDocumentDomain getTaskDocumentDetailsById(long id){
		return daoHelper.findById(TaskDocumentDomain.class, id);
	}
	
	public TaskDocumentDomain getProjectDocumentDetail(long parentId,long documentFormatId){
		String query= "from TaskDocumentDomain where taskDetailsDomain.numId="+parentId+" and documentFormatMaster.numId="+documentFormatId;
		List<TaskDocumentDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<TaskDetailsDomain> getAllActiveTaskDetailsData(long empid,String taskStatus){
		StringBuffer buffer = new StringBuffer("select t.* from pms_task_details_mst t,pms_project_team_details_mst p where t.num_project_id=p.num_application_id");
        buffer.append(" and p.dt_effective_from<=CURRENT_DATE and p.dt_effective_upto>=CURRENT_DATE and p.num_emp_id="+empid);
        buffer.append( " and t.str_task_status = any(string_to_array('"+taskStatus+"', ','))");
	    List<TaskDetailsDomain>taskDetailsDomain = daoHelper.runNative(buffer.toString(), TaskDetailsDomain.class);	
		return taskDetailsDomain;
	}
	
	public Object[]  getTaskDetailsData(long taskId){
		StringBuffer buffer = new StringBuffer("select t.num_task_id,t.str_task_name,t.num_tr_user_id,t.dt_tr_date,");
		buffer.append(" t.num_project_id,t.str_task_description,p.str_project_name,e.str_emp_name,t.num_document_id,t.num_expected_time,t.str_priority");
		buffer.append(" from pms_task_details_mst t,pms_project_master p,pms_employee_master e where t.num_project_id=p.num_project_id and t.num_tr_user_id=e.emp_id and t.num_task_id="+taskId );
		List<Object[]> obj = daoHelper.runNative(buffer.toString());
		if(obj.size()>0){
			return obj.get(0);
		}
		return 	null;
	}	
	
}
