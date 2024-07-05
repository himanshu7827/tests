package in.pms.timesheet.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import in.pms.global.dao.DaoHelper;
import in.pms.timesheet.model.ProjectTaskWeekEffortTransactionModel;
import in.pms.timesheet.service.ProjectSubTaskDescMasterService;
import in.pms.timesheet.service.ProjectSubTaskMasterService;
import in.pms.timesheet.service.ProjectTaskMasterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TSProjectTaskWeekEffortTransactionDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	@Autowired
	ProjectTaskMasterService projectTaskMasterService;
	
	@Autowired
	ProjectSubTaskMasterService projectSubTaskMasterService;
	
	@Autowired
	ProjectSubTaskDescMasterService projectSubTaskDescMasterService;
	
	//Purpose - To save data in pms_date_use_project table
		@Transactional
		public Integer entryInTimesheetProjectTaskWeekEffortTrn(ProjectTaskWeekEffortTransactionModel projectTaskWeekEffortTransactionModel){

			projectTaskWeekEffortTransactionModel = daoHelper.merge(ProjectTaskWeekEffortTransactionModel.class, projectTaskWeekEffortTransactionModel);

	        return projectTaskWeekEffortTransactionModel.getId().getParentTransactionModel().getTransactionId();
		}
		
		//Purpose - To get Effort to show on popup UI for basic projects
		public HashMap<Long, String> getTaskAndSubTaskDescEffortByProjectIdBasic(List<Integer> transactionId, Long projectId){
			
			HashMap<Long, String> resultMap = new HashMap<>();
			if(transactionId.isEmpty()) {
				List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
		  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
				for(Long ids: projectTaskIds) {
					resultMap.put(ids, "0");
				}
				return resultMap;
			}
			String query = "SELECT e.id.projectTaskId, e.totalEffortProjectSubTaskDesc FROM ProjectTaskWeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.id.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.id.parentTransactionModel.transactionId = (?0) AND e.id.projectId = ?1" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(transactionId);
			paraList.add(projectId);
			List<Object> result = (daoHelper.findByQuery(query, paraList));
			for (Object row : result) {
		        Object[] rowData = (Object[]) row;
		        Long projectTaskId = (Long) rowData[0];
		        String totalEffort = (String) rowData[1];
		        resultMap.put(projectTaskId, totalEffort);
		    }
			if(resultMap.isEmpty()) {
				List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
		  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
				for(Long ids: projectTaskIds) {
					resultMap.put(ids, "0");
				}
						
			}
			return resultMap;
		}
		
		//Purpose - To get Effort to show on popup UI for detailed projects
		public HashMap<Long, String> getTaskAndSubTaskDescEffortByProjectIdDetailed(List<Integer> transactionId, Long projectId){
			HashMap<Long, String> resultMap = new HashMap<>();
			if(transactionId.isEmpty()) {
				List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
		  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
				List<List<Long>> projectSubTaskIds = this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) != null ?
						this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) : new ArrayList<>();
				List<List<List<Long>>> projectSubTaskDescIds = this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) != null ?
						this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) : new ArrayList<>();
				
				List<Long> projectSubTaskDescIdsPlain = projectSubTaskDescIds.stream()
					    .flatMap(List::stream)
					    .flatMap(List::stream)
					    .collect(Collectors.toList());
				
				for(Long ids: projectSubTaskDescIdsPlain) {
					resultMap.put(ids, "0");
				}
				return resultMap;
			}
			String query = "SELECT e.id.projectSubTaskDescId, e.totalEffortProjectSubTaskDesc FROM ProjectTaskWeekEffortTransactionModel e JOIN FETCH ParentTransactionModel f ON e.id.parentTransactionModel.transactionId = f.transactionId WHERE e.isValid=1 AND e.id.parentTransactionModel.transactionId = (?0) AND e.id.projectId = ?1" ;
			ArrayList<Object> paraList = new ArrayList<>();
			paraList.add(transactionId);
			paraList.add(projectId);
			List<Object> result = (daoHelper.findByQuery(query, paraList));
			for (Object row : result) {
		        Object[] rowData = (Object[]) row;
		        Long projectSubTaskDescId = (Long) rowData[0];
		        String totalEffort = (String) rowData[1];
		        resultMap.put(projectSubTaskDescId, totalEffort);
		    }
			
			if(resultMap.isEmpty()) {
				List<Long> projectTaskIds = this.projectTaskMasterService.getProjectTaskIds() != null ?
		  				this.projectTaskMasterService.getProjectTaskIds() : new ArrayList<>();
				List<List<Long>> projectSubTaskIds = this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) != null ?
						this.projectSubTaskMasterService.getProjectSubTaskIds(projectTaskIds) : new ArrayList<>();
				List<List<List<Long>>> projectSubTaskDescIds = this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) != null ?
						this.projectSubTaskDescMasterService.getProjectSubTaskDescIds(projectSubTaskIds) : new ArrayList<>();
				
				List<Long> projectSubTaskDescIdsPlain = projectSubTaskDescIds.stream()
					    .flatMap(List::stream)
					    .flatMap(List::stream)
					    .collect(Collectors.toList());
				
				for(Long ids: projectSubTaskDescIdsPlain) {
					resultMap.put(ids, "0");
				}
						
			}

			return resultMap;
		}
}
