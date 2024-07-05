package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.DescriptionMasterDomain;
import in.pms.master.domain.SubTaskMasterDomain;
import in.pms.master.domain.TaskMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;



@Repository
public class TimeSheetMasterDao {

	@Autowired
	DaoHelper daoHelper;
	
	
	//To save/update record by Himanshu on 10-05-2024
	public long saveUpdateDescMaster(DescriptionMasterDomain descriptionMasterDomain) {
		descriptionMasterDomain = daoHelper.merge(DescriptionMasterDomain.class,
				descriptionMasterDomain);
		return descriptionMasterDomain.getDescId();
	}
	
	
	//To get Subtask by Task Id by Himanshu on 10-05-2024
	public List<SubTaskMasterDomain> getSubTaskListMasterById(long id) {
		String query = "from SubTaskMasterDomain where numIsValid IN (0,1) and taskMasterDomain.taskId="+id;
		return daoHelper.findByQuery(query);
	}
	
	public SubTaskMasterDomain getSubTaskMasterById(long id) {		
		return daoHelper.findById(SubTaskMasterDomain.class, id);
	}
	
	
	public TaskMasterDomain getAllTaskMaster(long id) {		
		return daoHelper.findById(TaskMasterDomain.class, id);
	}
	
	
	public DescriptionMasterDomain getDescriptionMaster(long id) {		
		return daoHelper.findById(DescriptionMasterDomain.class, id);
	}
	
	//To get Task List by Himanshu on 10-05-2024
	public List<TaskMasterDomain> getAllTaskMasterDomain() {
		String query = "from TaskMasterDomain where numIsValid IN (0,1)";
		return daoHelper.findByQuery(query);
	}
	
	
	//To get all details by Himanshu on 10-05-2024
	public List<DescriptionMasterDomain> getAllSubTaskDescriptionMasterDomain() {
		String query = "from DescriptionMasterDomain s where s.numIsValid IN (0,1) ORDER BY s.subtaskMasterDomain.taskMasterDomain.taskName, s.subtaskMasterDomain.subtaskName";
		return daoHelper.findByQuery(query);
	}
	
	
}
