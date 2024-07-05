package in.pms.timesheet.model;

import java.util.List;

public class WeekSubmitModel {
	
	private String yearSelectDB; 
	private List<String> weekEffort;
	private String todaysDate;
	private String selectedDate;
	private Integer employeeIdDB;
	private String employeeNameDB;
	private long groupIdDB;
	private String weekNumbers;
	private String projectIds;
	private String miscActivityIds;
	private String projectTaskDetails;
	private String taskProjectDetails;
	private String taskProjectTimesheetTypeDetails;
	private String miscOtherActivityDesc;
	private String miscOtherActivityPreviousWeek;
	private String miscOtherActivityActiveWeek;
	private String activeWeekNumber;
	
	public String getYearSelectDB() {
		return yearSelectDB;
	}
	public void setYearSelectDB(String yearSelectDB) {
		this.yearSelectDB = yearSelectDB;
	}
	public List<String> getWeekEffort() {
		return weekEffort;
	}
	public void setWeekEffort(List<String> weekEffort) {
		this.weekEffort = weekEffort;
	}
	public String getTodaysDate() {
		return todaysDate;
	}
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public Integer getEmployeeIdDB() {
		return employeeIdDB;
	}
	public void setEmployeeIdDB(Integer employeeIdDB) {
		this.employeeIdDB = employeeIdDB;
	}
	public String getEmployeeNameDB() {
		return employeeNameDB;
	}
	public void setEmployeeNameDB(String employeeNameDB) {
		this.employeeNameDB = employeeNameDB;
	}
	public long getGroupIdDB() {
		return groupIdDB;
	}
	public void setGroupIdDB(long groupIdDB) {
		this.groupIdDB = groupIdDB;
	}
	public String getWeekNumbers() {
		return weekNumbers;
	}
	public void setWeekNumbers(String weekNumbers) {
		this.weekNumbers = weekNumbers;
	}
	public String getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}
	public String getMiscActivityIds() {
		return miscActivityIds;
	}
	public void setMiscActivityIds(String miscActivityIds) {
		this.miscActivityIds = miscActivityIds;
	}
	
	public String getProjectTaskDetails() {
		return projectTaskDetails;
	}
	public void setProjectTaskDetails(String projectTaskDetails) {
		this.projectTaskDetails = projectTaskDetails;
	}
	
	public String getTaskProjectDetails() {
		return taskProjectDetails;
	}
	public void setTaskProjectDetails(String taskProjectDetails) {
		this.taskProjectDetails = taskProjectDetails;
	}
	public String getTaskProjectTimesheetTypeDetails() {
		return taskProjectTimesheetTypeDetails;
	}
	public void setTaskProjectTimesheetTypeDetails(String taskProjectTimesheetTypeDetails) {
		this.taskProjectTimesheetTypeDetails = taskProjectTimesheetTypeDetails;
	}
	public String getMiscOtherActivityDesc() {
		return miscOtherActivityDesc;
	}
	public void setMiscOtherActivityDesc(String miscOtherActivityDesc) {
		this.miscOtherActivityDesc = miscOtherActivityDesc;
	}
	
	public String getMiscOtherActivityPreviousWeek() {
		return miscOtherActivityPreviousWeek;
	}
	public void setMiscOtherActivityPreviousWeek(
			String miscOtherActivityPreviousWeek) {
		this.miscOtherActivityPreviousWeek = miscOtherActivityPreviousWeek;
	}
	public String getMiscOtherActivityActiveWeek() {
		return miscOtherActivityActiveWeek;
	}
	public void setMiscOtherActivityActiveWeek(String miscOtherActivityActiveWeek) {
		this.miscOtherActivityActiveWeek = miscOtherActivityActiveWeek;
	}
	
	public String getActiveWeekNumber() {
		return activeWeekNumber;
	}
	public void setActiveWeekNumber(String activeWeekNumber) {
		this.activeWeekNumber = activeWeekNumber;
	}
	@Override
	public String toString() {
		return "WeekSubmitModel [yearSelectDB=" + yearSelectDB + ", weekEffort=" + weekEffort + ", todaysDate="
				+ todaysDate + ", selectedDate=" + selectedDate + ", employeeIdDB=" + employeeIdDB + ", employeeNameDB="
				+ employeeNameDB + ", groupIdDB=" + groupIdDB + ", weekNumbers=" + weekNumbers + ", projectIds="
				+ projectIds + ", miscActivityIds=" + miscActivityIds + ", projectTaskDetails=" + projectTaskDetails
				+ ", taskProjectDetails=" + taskProjectDetails + ", taskProjectTimesheetTypeDetails="
				+ taskProjectTimesheetTypeDetails + ", miscOtherActivityDesc=" + miscOtherActivityDesc
				+ ", miscOtherActivityPreviousWeek=" + miscOtherActivityPreviousWeek + ", miscOtherActivityActiveWeek="
				+ miscOtherActivityActiveWeek + ", activeWeekNumber=" + activeWeekNumber + "]";
	}
	
}
