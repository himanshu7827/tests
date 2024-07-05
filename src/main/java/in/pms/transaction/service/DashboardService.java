package in.pms.transaction.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;

import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.transaction.model.DashboardModel;

public interface DashboardService {
	
	JSONArray getgroupBusinessTypeProjectCount();

	JSONArray getgroupEmployeeTypeEmployeeCount();
	JSONArray getgroupProjectCount();
	
	JSONArray getProjectCountByBusinessType();
	JSONArray getProjectCostByBusinessType();
	JSONArray getProjectCountByGroupandBusinessType();
	
	/*Gender wise Employee Distribution for GC Dashboard*/
	JSONArray getGenderWiseEmployeeCount();
	
	/*Project Detail Graph for GC Dashboard*/
	JSONArray getProjectDetailGraph();
	

	
	@Transactional
	public long  getTotalActiveEmployees();
	
    @Transactional
	List<DashboardModel> getAllActiveProjectName();
    
    
    /// Count total ongoing projects based on organization
    @Transactional
   	public long getAllOngoingProjectCount();
    
    
    /// Count total ongoing project cost based on organization
    @Transactional
    public double getTotalOngoingProjectsCost();
    
    /// Count total ongoing projects and their cost based on group and organization
    
    @Transactional
    List<DashboardModel> getProjectCountandCostByGroup();
   
    public String getExpenditure(Date startDate);

  ///Expenditure Groupwise
	  JSONArray getExpenditureByGroup(Date startDate);
	   
  ///Groupwsie and Projectwise Expenditure
	public List<DashboardModel> getExpenditureGroupwiseProjectwise();
	
	//Group-wise Proposals Submitted in last 3 years
	JSONArray getgroupProposalCount();
	
	  @Transactional
	    List<DashboardModel> getOngoinProjectlist(ProjectMasterModel projectMastermodel);
	
	/* @Transactional
	   	public long getAllNewProjectCount();*/
	  @Transactional
	   public long  getActiveEmployeesAtCDAC();
	  
	  JSONArray getGroupGenderWiseEmployeeCount(String groupName);
	  
	// Get Total outlay share on 22/05/2023
	  public double getCDACoutlay();

	public long getActiveEmployeesAtCLIENT();

	public List<Object[]> getLastUpdatedAtDateForEmployees();//Added by devesh to get latest transaction date from employee master
}
