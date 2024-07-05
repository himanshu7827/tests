 package in.pms.transaction.service;

import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.AnswerMasterDao;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.EmployeeRoleMasterDao;
import in.pms.master.dao.QuestionMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.EmployeeRoleMasterDomain;
import in.pms.master.domain.ExitInterviewDomain;
import in.pms.master.domain.ExitInterviewMainDomain;
import in.pms.master.domain.TaskDetailsDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.transaction.dao.ExitInterviewDao;
import in.pms.transaction.model.ExitInterviewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class ExitInterviewServiceImpl implements ExitInterviewService{
	
	@Autowired
	QuestionMasterDao questionMasterDao;
	
	@Autowired
	AnswerMasterDao answerMasterDao;
	
	@Autowired
	ExitInterviewDao exitInterviewDao;
	
	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	@Autowired
	EmployeeRoleMasterDao employeeRoleMasterDao;
	
	
	@Override
    public long saveExitInterviewData(ExitInterviewModel exitInterviewModel,String flaEmail,String slaEmail,String empRemarks){
	ExitInterviewMainDomain exitInterviewMainDomain = convertExitInterviewModelToDomain(exitInterviewModel);
	exitInterviewMainDomain.setStrFlaEmail(flaEmail);
	exitInterviewMainDomain.setStrSlaEmail(slaEmail);
	exitInterviewMainDomain.setStrEmployeeRemarks(empRemarks);	
	return exitInterviewDao.save(exitInterviewMainDomain).getNumId();
}
	
	public ExitInterviewMainDomain convertExitInterviewModelToDomain(ExitInterviewModel exitInterviewModel){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ExitInterviewMainDomain exitInterviewMainDomain = new ExitInterviewMainDomain();
		if(exitInterviewModel.getNumId()!=0){		
			exitInterviewMainDomain =  exitInterviewDao.getOne(exitInterviewModel.getNumId());
		}
		exitInterviewMainDomain.setEmployeeMasterDomain(employeeMasterDao.getEmployeeMasterById(userInfo.getEmployeeId()));;
		exitInterviewMainDomain.setStrStatus("SUB");
		exitInterviewMainDomain.setDtTrDate(new Date());
		exitInterviewMainDomain.setNumTrUserId(userInfo.getEmployeeId());		
		exitInterviewMainDomain.setNumIsValid(1);
	
		exitInterviewMainDomain.getExitInterviewChildDomain();
		
		String exitInterviewDetails = exitInterviewModel.getExitInterviewDetails();
		
		JSONArray jsonArray = new JSONArray(exitInterviewDetails);
		List<ExitInterviewDomain> exitInterviewChildDomainList = new ArrayList<ExitInterviewDomain>();
		for (int j = 0; j < jsonArray.length(); j++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(j);
		    ExitInterviewDomain exitInterviewDomain= new ExitInterviewDomain();
		    exitInterviewDomain.setQuestionMasterDomain(questionMasterDao.getAllQusestionTypeById(jsonObject.getLong("questionValue")));
			exitInterviewDomain.setAnswerMasterDomain(answerMasterDao.getAnswerDataById(jsonObject.getLong("answerValue")));
			exitInterviewDomain.setDtTrDate(new Date());
			exitInterviewDomain.setNumTrUserId(userInfo.getEmployeeId());		
			exitInterviewDomain.setNumIsValid(1);
			
			exitInterviewDomain.setExitInterviewMainDomain(exitInterviewMainDomain);
			
			
			exitInterviewChildDomainList.add(exitInterviewDomain);
		}
		exitInterviewMainDomain.setExitInterviewChildDomain(exitInterviewChildDomainList);
		
	
		                                                                                                                                                                                                                                                                                                                        
		return exitInterviewMainDomain;
	}
	
	
	@Override
    public ExitInterviewMainDomain getExitInterviewMainDomainDataByEmpId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		long empId=userInfo.getEmployeeId();
		return exitInterviewDao.getExitInterviewMainDomainDataByEmpId(empId);
}
	
	
	@Override
	public List<ExitInterviewModel> getActiveExitInterviewDataByFla(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		long empId=userInfo.getEmployeeId();
		String officeEmail=userInfo.getOfficeEmail();
		return convertExitInterviewDomainToModel(exitInterviewDao.getExitInterviewMainDomainDataByFlaEmail(officeEmail));
	}
	
	public List<ExitInterviewModel>convertExitInterviewDomainToModel(List<ExitInterviewMainDomain> ExitInterviewList){
		List<ExitInterviewModel> list=new ArrayList<ExitInterviewModel>();
		for(int i=0;i<ExitInterviewList.size();i++){
			ExitInterviewMainDomain exitInterviewMainDomain=ExitInterviewList.get(i);
			ExitInterviewModel exitInterviewModel=new ExitInterviewModel();
			exitInterviewModel.setNumId(exitInterviewMainDomain.getNumId());
			exitInterviewModel.setEmployeeRemarks(exitInterviewMainDomain.getStrEmployeeRemarks());
			exitInterviewModel.setFlaRemarks(exitInterviewMainDomain.getStrFlaRemarks());
			exitInterviewModel.setFlaEmail(exitInterviewMainDomain.getStrFlaEmail());
			exitInterviewModel.setStatus(exitInterviewMainDomain.getStrStatus());
			exitInterviewModel.setEmpId(exitInterviewMainDomain.getEmployeeMasterDomain().getNumId());
			exitInterviewModel.setEmpName(exitInterviewMainDomain.getEmployeeMasterDomain().getEmployeeName());
			list.add(exitInterviewModel);
		}
		return list;
	}
	
	@Override
	public List<ExitInterviewModel> getActiveExitInterviewDataBySla(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		long empId=userInfo.getEmployeeId();
		String officeEmail=userInfo.getOfficeEmail();
		return convertExitInterviewDomainToModel(exitInterviewDao.getExitInterviewMainDomainDataBySlaEmail(officeEmail));
	}
	
	@Override
	public boolean updateFlaNdSlaStatus(long exitInterviewId,String status,int approvalId,String remarks){
		//ExitInterviewModel exitInterviewModel=new ExitInterviewModel();
		boolean result = false;
		try{
			ExitInterviewMainDomain domain = exitInterviewDao.getExitInterviewMainDomainDataById(exitInterviewId);
			EmployeeMasterDomain employeeMasterDomain= employeeMasterDao.getEmployeeDetails(domain.getEmployeeMasterDomain().getNumId());
			if(status.equals("")){
				domain.setStrStatus("Rejected");
				if(approvalId ==1){
				domain.setStrFlaRemarks(remarks);
				}
				if(approvalId ==2){
					domain.setStrSlaRemarks(remarks);
					}
				domain.setNumIsValid(0);

			}
			else{
			domain.setStrStatus(status);
			if(approvalId ==1){
				domain.setStrFlaRemarks(remarks);
				}
				if(approvalId ==2){
					domain.setStrSlaRemarks(remarks);
					employeeMasterDomain.setEmploymentStatus("Notice Period");
					employeeMasterDomain.setDateOfResignation(domain.getDtTrDate());
					employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
					}
			}
			exitInterviewDao.save(domain);
		result=true;
		 }catch(Exception e){
			
		}
		return result;
	}

	@Override
	public List<ExitInterviewModel> getActiveExitInterviewDataApprovedBySla(){
		return convertExitInterviewDomainToModel(exitInterviewDao.getExitInterviewDataApprovedBySla());
	}
	
	@Override
    public long saveExitInterviewByHr(ExitInterviewModel exitInterviewModel,long employeeId){
		convertExitInterviewModelToEmpRoleMasterDomain(exitInterviewModel,employeeId);			
		return 1;
}
	
	public void convertExitInterviewModelToEmpRoleMasterDomain(ExitInterviewModel exitInterviewModel,long employeeId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ExitInterviewMainDomain exitInterviewMainDomain = exitInterviewDao.getExitInterviewMainDomainDataByEmpId(employeeId);
		EmployeeMasterDomain employeeMasterDomain= employeeMasterDao.getEmployeeDetails(employeeId);
		String endDateDetails=exitInterviewModel.getEndDateDetails();
		JSONArray jsonArray = new JSONArray(endDateDetails);
		List<EmployeeRoleMasterDomain> employeeRoleMasterDomainList = employeeRoleMasterDao.getEmployeeRoleMasterDomain(employeeId);
		for(int i=0;i<employeeRoleMasterDomainList.size();i++){
		for (int j = 0; j < jsonArray.length(); j++) {
		    JSONObject jsonObject = jsonArray.getJSONObject(j);
		   //long projectId = 1;
		  long roleId = jsonObject.getLong("roleId");
		    EmployeeRoleMasterDomain roleDomain = employeeRoleMasterDomainList.stream()
	                .filter(x -> roleId == x.getNumId())
	                .findAny()
	                .orElse(null);
		    
		    if(null != roleDomain){
		    	try {
					Date date = DateUtils.dateStrToDate(jsonObject.getString("endDate"));
					roleDomain.setDtEndDate(date);
					roleDomain.setNumTrUserId(userInfo.getEmployeeId());
					roleDomain.setDtTrDate(new Date());
					employeeRoleMasterDao.saveUpdateEmployeeRoleMaster(roleDomain);
				} catch (JSONException | ParseException e) {					
					e.printStackTrace();
				}
		    }
		    
		
		   
			exitInterviewMainDomain.setStrStatus("HR Approved");
			exitInterviewDao.save(exitInterviewMainDomain);
			employeeMasterDomain.setEmploymentStatus("Relieved");
			employeeMasterDomain.setDateOfResignation(new Date());
			employeeMasterDao.saveUpdateEmployeeMaster(employeeMasterDomain);
			
		}
		}
		
	}
}