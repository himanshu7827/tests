package in.pms.master.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.login.util.UserInfo;
import in.pms.master.domain.ReportMaster;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ReportMasterModel;
import in.pms.master.service.GlobalService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ReportMasterService;
import in.pms.transaction.service.MonthlyProgressService;

@Controller
public class ReportMasterController 
{

	@Autowired
	ReportMasterService reportMasterService;
	
	
	@Autowired
	public GlobalService globalService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	
	public int userId = 0;
	
		
	@RequestMapping(value="/AddNewReport",method=RequestMethod.POST)
	public String AddNewReport(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request){
		
		
		List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
		request.setAttribute("interfaceDetails", interfaceDetails);
				       		
		return "AddNewReport";
		}
		

	

	@RequestMapping(value="/SaveReportDetails",method=RequestMethod.POST)
	public String SaveReportDetails(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request)
	{		
		
		
		
		reportMasterService.SaveNewReport(reportMasterModel,userId);
		
		List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
		request.setAttribute("interfaceDetails", interfaceDetails);
				
        List<ReportMasterModel> reportType=reportMasterService.getReportType();
    	request.setAttribute("reportTypelist", reportType);
        
		
		return "ReportEditorMaster";
		}
		
	
	
	@RequestMapping(value="/EditReport",method = { RequestMethod.GET, RequestMethod.POST })
	public String EditReport(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel, HttpServletRequest request)
	{
		reportMasterService.readReportAuthorityCheck();
		List<ReportMasterModel> reportType=reportMasterService.getReportType();
		request.setAttribute("reportTypelist", reportType);

		List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
		request.setAttribute("interfaceDetails", interfaceDetails);
		
		return "ReportEditorMaster";
		 }
		
		
	
	
	@RequestMapping(value="/DeleteReport",method=RequestMethod.POST)
	public String DeleteReport(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request,@RequestParam("schemeId") int schemeId,RedirectAttributes redirect){
		
		if(reportMasterModel.getNumReportId()==0 ){
			
			List<ReportMasterModel> reportType=reportMasterService.getReportType();
			request.setAttribute("reportTypelist", reportType);

			List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
			request.setAttribute("interfaceDetails", interfaceDetails);
	        request.setAttribute("Message","Please select Report.");
		}
		else{
		
		
	if(schemeId==0){
		reportMasterService.DeleteReport(reportMasterModel,userId);
	}
	
	redirect.addFlashAttribute("Message", "Delete");
	List<ReportMasterModel> reportType=reportMasterService.getReportType();
	request.setAttribute("reportTypelist", reportType);

	List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
	request.setAttribute("interfaceDetails", interfaceDetails);
		
		}
		
		return "redirect:/EditReport";
		}
		

	
	
	@RequestMapping(value="/ShowSchemeReport/{value}/{scheme}",method=RequestMethod.GET)
	public @ResponseBody String ShowSchemeReport(@PathVariable String value,@PathVariable String scheme, @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request){
				
		int reportId = Integer.parseInt(value);
		int schId= Integer.parseInt(scheme);
		String query=null;
		if(schId== 0)
		{
			Map<String,Object> mapData=reportMasterService.getQueryData(reportId);
			ReportMaster editor=(ReportMaster)mapData.get("txtEditor");
			query =editor.getStrQuery();
			
		}
		
		
		return query;	
		
	}
	
	@RequestMapping(value="/ShowReport2/{value}",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> ShowReport2(@PathVariable String value, @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request){
		
		int reportId = Integer.parseInt(value);
		Map<String,Object> dataMap=new HashMap<String, Object>();
		Map<String,Object> mapData= reportMasterService.getQueryData(reportId);
		ReportMaster reportMaster=(ReportMaster)mapData.get("txtEditor");
		
		dataMap.put("flag",reportMaster.getIsSchemeSpecific());
		dataMap.put("txtData",reportMaster.getStrQuery());
		return dataMap;	
		
	}
	
	@RequestMapping(value="/ShowReports/{value}",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> ShowReports(@PathVariable String value, @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request){
		
		int reportId = Integer.parseInt(value);
		Map<String,Object> dataMap=new HashMap<String, Object>();
		
		Map<String,Object> mapData= reportMasterService.getQueryData(reportId);
		ReportMaster reportMaster=(ReportMaster)mapData.get("txtEditor");
		String interfaceName=(String)mapData.get("InterfaceName");
		int interfaceId=(int)mapData.get("InterfaceId");
		//List<SchemeForm> schemeList=(List<SchemeForm>)mapData.get("schemeList");
		dataMap.put("flag",reportMaster.getIsSchemeSpecific());
		dataMap.put("txtData",reportMaster.getStrQuery());
		dataMap.put("Description",reportMaster.getStrDesc());
		/**/
		//dataMap.put("schemeList",schemeList);
		dataMap.put("InterfaceName",interfaceName);
		dataMap.put("InterfaceId",interfaceId);
		dataMap.put("InterfaceName",interfaceName);
		dataMap.put("InterfaceId",interfaceId);
		dataMap.put("SheetNames",reportMaster.getStrSheetNames());
		return dataMap;	
		
	}
	
	
	@RequestMapping(value="/AddSchemeSpecificReport",method=RequestMethod.POST)
	public String AddSchemeSpecificReport(@RequestParam("schemeId") int schemeId,@Valid @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel, BindingResult result,HttpServletRequest request,RedirectAttributes redirect){
				
		if (result.hasErrors()) {
			List<ReportMasterModel> reportType=reportMasterService.getReportType();
			request.setAttribute("reportTypelist", reportType);

			List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
			request.setAttribute("interfaceDetails", interfaceDetails);
			
		} else {
				
			
			List<ReportMasterModel> reportType=reportMasterService.getReportType();
			request.setAttribute("reportTypelist", reportType);

			List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
			request.setAttribute("interfaceDetails", interfaceDetails);
		}
		return "redirect:/EditReport";
		}
		
	
	@RequestMapping(value="/SaveReport",method=RequestMethod.POST)
	public String SaveReport(@Valid @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel, BindingResult result,HttpServletRequest request,RedirectAttributes redirect){
		
		if (result.hasErrors()) {
			
			
			List<ReportMasterModel> reportType=reportMasterService.getReportType();
			request.setAttribute("reportTypelist", reportType);

			List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
			request.setAttribute("interfaceDetails", interfaceDetails);
			
		} else {
	
			reportMasterService.SaveEdittedReport(reportMasterModel,userId);
			redirect.addFlashAttribute("Message", "SavedForReport");
		List<ReportMasterModel> reportType=reportMasterService.getReportType();
		request.setAttribute("reportTypelist", reportType);

		List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
		request.setAttribute("interfaceDetails", interfaceDetails);
		}
		
		return "redirect:/EditReport";
		}
		

	
	@RequestMapping(value="/SaveSchemeSpecificReport",method=RequestMethod.POST)
	public String SaveSchemeSpecificReport(@RequestParam("schemeId") int schemeId,@Valid @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel, BindingResult result,HttpServletRequest request,RedirectAttributes redirect){
		
		if (result.hasErrors()) {
			List<ReportMasterModel> reportType=reportMasterService.getReportType();
			request.setAttribute("reportTypelist", reportType);

			List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
			request.setAttribute("interfaceDetails", interfaceDetails);
		} else {
			
		
		
		List<ReportMasterModel> reportType=reportMasterService.getReportType();
		request.setAttribute("reportTypelist", reportType);

		List<ReportMasterModel> interfaceDetails=reportMasterService.getInterfaceDetails();
		request.setAttribute("interfaceDetails", interfaceDetails);
		}
		
		return "redirect:/EditReport";
		}
		
			
	
	/*@RequestMapping(value="/DetailsForInterface2", method=RequestMethod.GET)
	public String DetailsForInterface2(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request)
	{		
		
        
        List<ReportMasterModel> reportType=reportMasterService.getReportTypeForInterface(1);
		request.setAttribute("reportTypelist", reportType);
	
		return "DetailsForInterface2";
		
	
	}*/
	
	@RequestMapping(value="/ReportDetails",method=RequestMethod.POST)
	public @ResponseBody String ReportDetails( @ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,@RequestParam("reportId") int reportId,HttpServletRequest request){
		
		String description="";
		List<ReportMaster> data=reportMasterService.getReportData(reportId);
		if(data.size()>0)
		{
			description=data.get(0).getStrDesc();
			return description;
		}
		return description;
	}
	
	
	
	@RequestMapping(value="/isDuplicateRecord",method=RequestMethod.POST)
	public @ResponseBody boolean isDuplicateRecord(@ModelAttribute("reportMasterModel")ReportMasterModel reportMasterModel,HttpServletRequest request){
		boolean duplicateFlag=false;
		duplicateFlag=reportMasterService.isDuplicateReport(reportMasterModel.getStrEcode());
		if(duplicateFlag){
			duplicateFlag=true;
		}
		else{
			duplicateFlag=false;
		}
		return duplicateFlag;
		}


@RequestMapping(value = "/generateReportForInterface3", method = RequestMethod.POST)
	public  String generateReportForInterface3(@RequestParam("eCode") String eCode,HttpServletRequest request, HttpServletResponse reponse) {	

		String result="Done";
		String fileName="";
		String query="";
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		String groups= userInfo.getAssignedGroups();		
			try{
				
				 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
		    	//if(query!=null && !query.equals("")){
				//query= query.replaceAll("@1",pIds+"");
				if(query!=null && !query.equals("")){
				//result=reportMasterService.generateReportForInterface(query,fileName,request,reponse);
				return result; 
				}
		    	//}	
		    	else{
		    		//result=reportMasterService.generateReportForInterface(query,fileName,request,reponse);
		    	}
		    	
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return result; 
		
	}
		@RequestMapping(value="/generateMonthlyProgressReport",method = { RequestMethod.GET, RequestMethod.POST })
		public String generateReport(HttpServletRequest request, HttpServletResponse reponse){
			List<GroupMasterModel> groupList =groupMasterService.getAllActiveGroupMasterDomain();
			request.setAttribute("groupList", groupList);
		return "GenerateReport";	
			}
		
		
		
		@RequestMapping(value = "/generateReportForBirthDayDetails", method = RequestMethod.POST)
		public  String generateReportForBirthDayDetails(@RequestParam("month") String month,@RequestParam("year") String year,@RequestParam("eCode") String eCode,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="EmptyPage";
			String fileName="";
			String query="";
			String sheetName="";

				try{
				
					List<ReportMaster> details=reportMasterService.getReportDetailByEcode(eCode);
					if(details.size()>0){
						fileName=details.get(0).getStrName();
						
					}			
					 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
			    	if(query!=null && !query.equals("")){
			    		String[] queryNSheet=query.split("%@12345@%");
			    		query=queryNSheet[0];
			    		sheetName=queryNSheet[1];
			    		
			    		query= query.replaceAll("@1",month+"");
    		
					if(query!=null && !query.equals("")){
					result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
					return result; 
					}
			    	}	
			    	else{
			    		result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
			    	}
			    	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return result; 
			
		}

		@RequestMapping(value = "/generateReportForInterface", method = RequestMethod.POST)
		public  String generateReportForInterface(@RequestParam("month") String month,@RequestParam("year") String year,@RequestParam("groupIds") String groupIds,@RequestParam("projectIds") String projectIds,@RequestParam("eCode") String eCode,@RequestParam("selectedProjectType") String selectedProjectType,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="EmptyPage";
			String fileName="";
			String query="";
			String sheetName="";
			String flag="0,1";
				try{
				
					List<ReportMaster> details=reportMasterService.getReportDetailByEcode(eCode);
					if(details.size()>0){
						fileName=details.get(0).getStrName();
						
					}			
					 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
			    	if(query!=null && !query.equals("")){
			    		String[] queryNSheet=query.split("%@12345@%");
			    		query=queryNSheet[0];
			    		sheetName=queryNSheet[1];
			    		
			    		query= query.replaceAll("@1",month+"");
			    		query= query.replaceAll("@2",year+"");
			    		query= query.replaceAll("@3",groupIds+"");
			    		query= query.replaceAll("@4",projectIds+"");
			    		if(selectedProjectType.equals("2")){
			    		query= query.replaceAll("@5",flag+"");	
			    		}
			    		else{
			    		query= query.replaceAll("@5",selectedProjectType+"");	
			    		}
					if(query!=null && !query.equals("")){
					result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
					return result; 
					}
			    	}	
			    	else{
			    		result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
			    	}
			    	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return result; 
			
		}
		
		@RequestMapping(value = "/getProjectsByGroup", method = RequestMethod.POST)
		public @ResponseBody List<ProjectMasterModel> getProjectsUnderGroup(String groupIds,int month,int year,int selectedProjectType,HttpServletRequest request) {
			List<ProjectMasterModel> tempProjectList=projectMasterService.getProjectDataByGroupIds(groupIds);
			String projectids= new String();
			List<ProjectMasterModel> finalIds=null;
			if(tempProjectList.size()>0){
			for(int i=0;i<tempProjectList.size();i++){
				if(tempProjectList.size()-1==i){
					projectids+=tempProjectList.get(i).getNumId();
				}
				else{
					projectids+=tempProjectList.get(i).getNumId()+",";
				}
			}
			finalIds=monthlyProgressService.getProjectIdsForReport(month, year, projectids,selectedProjectType);
			}
			return finalIds;
		}
		
		/*@RequestMapping(value = "/generateReportForInterfaceForPreview", method = RequestMethod.POST)
		public  void generateReportForInterfaceForPreview(@RequestParam("month") String month,@RequestParam("year") String year,@RequestParam("groupIds") String groupIds,@RequestParam("projectIds") String projectIds,@RequestParam("eCode") String eCode,@RequestParam("encMonthlyProgressId") String encMonthlyProgressId,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="Done";
			String fileName="";
			String query="";
			String sheetName="";

				try{
				
					List<ReportMaster> details=reportMasterService.getReportDetailByEcode(eCode);
					if(details.size()>0){
						fileName=details.get(0).getStrName();
						
					}
					
					 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
			    	if(query!=null && !query.equals("")){
			    		String[] queryNSheet=query.split("%@12345@%");
			    		query=queryNSheet[0];
			    		sheetName=queryNSheet[1];
			    		
			    		query= query.replaceAll("@1",month+"");
			    		query= query.replaceAll("@2",year+"");
			    		query= query.replaceAll("@3",groupIds+"");
			    		query= query.replaceAll("@4",projectIds+"");
					if(query!=null && !query.equals("")){
					result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
					
					}
			    	}	
			    	else{
			    		result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
			    	}
			    	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				return "redirect:/getPreviewDetails?&encMonthlyProgressId=" +encMonthlyProgressId;
		}*/
		
		@RequestMapping(value="/generateMonthlyBirthDayReport",method = { RequestMethod.GET, RequestMethod.POST })
		public String generateMonthlyBirthDayReport(HttpServletRequest request, HttpServletResponse reponse){
		return "generateMonthlyBirthDayReport";	
			}
		
		@RequestMapping(value="/generateDateWiseReports",method = { RequestMethod.GET, RequestMethod.POST })
		public String generateClientSiteEmpReport(HttpServletRequest request, HttpServletResponse reponse){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			String assignedOrganisation = userInfo.getAssignedOrganisation();
			 if(null != assignedOrganisation && !assignedOrganisation.equals("")){
					Long orgId = Long.valueOf(assignedOrganisation).longValue();		     
				    List<GroupMasterModel> groupList =groupMasterService.getAllActiveGrpMasterDomain(orgId);
					request.setAttribute("groupList", groupList);
				}
			 List<ReportMasterModel> reportType=reportMasterService.getReportsByRoleInterface(3);
			 request.setAttribute("reportTypelist", reportType);
			 return "generateClientSiteEmpReport";	
		}
		
		@RequestMapping(value = "/generateReportForClientSitEmpDetails", method = RequestMethod.POST)
		public  String generateReportForClientSitEmpDetails(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("groupIds") String groupIds,@RequestParam("reportId") int reportId,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="EmptyPage";
			String fileName="";
			String query="";
			String sheetName="";

				try{
					List<ReportMaster> data=reportMasterService.getReportData(reportId);
					if(data.size()>0){
					List<ReportMaster> details=reportMasterService.getReportDetailByEcode(data.get(0).getStrECode());
					if(details.size()>0){
						fileName=details.get(0).getStrName();
						
					}			
					 query=reportMasterService.isReportSchemeSpecificQuery(data.get(0).getStrECode());
			    	if(query!=null && !query.equals("")){
			    		String[] queryNSheet=query.split("%@12345@%");
			    		query=queryNSheet[0];
			    		sheetName=queryNSheet[1];
			    		
			    		query= query.replaceAll("@1",startDate+"");
			    		query= query.replaceAll("@2",endDate+"");
			    		query= query.replaceAll("@3",groupIds+"");
    		
					if(query!=null && !query.equals("")){
					result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
					return result; 
					}
			    	}	
			    	else{
			    		result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
			    	}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return result; 
			
		}

	/*	@RequestMapping(value="/getContractExpEmployess",method=RequestMethod.POST)
		public @ResponseBody List<EmployeeMasterModel> getContractExpEmployess(@RequestParam("interval") int interval ,HttpServletRequest request){		
			List<EmployeeMasterModel> details=reportMasterService.getContractExpEmp(interval);
			return details;
		}*/
		
		@RequestMapping(value = "/generateReportForContractExpEmp", method = RequestMethod.POST)
		public  String generateReportForContractExpEmp(@RequestParam("eCode") String eCode,@RequestParam("interval") int interval,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="Done";
			String fileName="";
			String query="";
			String sheetName="";
			
			/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
				*/
				try{
					
					 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
					 List<ReportMaster> details=reportMasterService.getReportDetailByEcode(eCode);
						if(details.size()>0){
							fileName=details.get(0).getStrName();
							
						}
						if(query!=null && !query.equals("")){
				    		String[] queryNSheet=query.split("%@12345@%");
				    		query=queryNSheet[0];
				    		sheetName=queryNSheet[1];
				    		
				    		query= query.replaceAll("@1",interval+"");
			
	    		
						if(query!=null && !query.equals("")){
						result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
						return result; 
						}
				    	}
			    	//}	
			    	else{
			    		//result=reportMasterService.generateReportForInterface(query,fileName,request,reponse);
			    	}
			    	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return result; 
			
		}
		
		@RequestMapping(value="/generateEmployeeDetails",method = { RequestMethod.GET, RequestMethod.POST })
		public String generateEmployeeDetails(HttpServletRequest request, HttpServletResponse reponse){
			List<GroupMasterModel> groupList =groupMasterService.getAllActiveGroupMasterDomain();
			request.setAttribute("groupList", groupList);
		return "generateEmployeeDetails";	
			}
		
		@RequestMapping(value = "/generateReportForEmplDetails", method = RequestMethod.POST)
		public  String generateReportForEmplDetails(@RequestParam("groupIds") String groupIds,@RequestParam("selectYear") String selectYear,@RequestParam("eCode") String eCode,HttpServletRequest request, HttpServletResponse reponse) {	

			String result="EmptyPage";
			String fileName="";
			String query="";
			String sheetName="";
		
				try{
				
					List<ReportMaster> details=reportMasterService.getReportDetailByEcode(eCode);
					if(details.size()>0){
						fileName=details.get(0).getStrName();
						
					}			
					 query=reportMasterService.isReportSchemeSpecificQuery(eCode);
			    	if(query!=null && !query.equals("")){
			    		String[] queryNSheet=query.split("%@12345@%");
			    		query=queryNSheet[0];
			    		sheetName=queryNSheet[1];
			    		
			    	
			    		query= query.replaceAll("@1",groupIds+"");
			    		query= query.replaceAll("@2",selectYear+"");
			    	
					if(query!=null && !query.equals("")){
					result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
					return result; 
					}
			    	}	
			    	else{
			    		result=reportMasterService.generateReportForInterface(query,fileName,sheetName,request,reponse);
			    	}
			    	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return result; 
			
		}
		
}
