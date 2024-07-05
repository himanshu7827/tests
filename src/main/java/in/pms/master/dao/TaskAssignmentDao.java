package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.TaskAssignmentDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskAssignmentDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long mergeTaskAssignmentMaster(TaskAssignmentDomain taskAssignmentDomain){
		taskAssignmentDomain = daoHelper.merge(TaskAssignmentDomain.class, taskAssignmentDomain);
		return taskAssignmentDomain.getNumId();
	}
	
	public TaskAssignmentDomain getAllTaskAssignmentById(long numId){
		/*TaskAssignmentDomain taskAssignmentDomain =null;
		String query = "from TaskAssignmentDomain where numId="+numId;
		List<TaskAssignmentDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			taskAssignmentDomain =list.get(0);
		}
		return taskAssignmentDomain;*/	
		return daoHelper.findById(TaskAssignmentDomain.class, numId);

	}
	
	public List<TaskAssignmentDomain> getAllTaskAssignmentData(){
		String query = "from TaskAssignmentDomain where numIsValid in(0,1)";
		return  daoHelper.findByQuery(query);	
	}
	
	
	public List<TaskAssignmentDomain> getActiveTaskAssignmentData(){
		String query = "from TaskAssignmentDomain where numIsValid=1 ";
		return  daoHelper.findByQuery(query);	
	}
	
	
	
	
}
