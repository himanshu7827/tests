package in.pms.transaction.controller;

import java.util.List;

import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.model.ProjectInnovationsModel;
import in.pms.transaction.model.ProjectOthersModel;
import in.pms.transaction.model.PublicationDetailsModel;
import in.pms.transaction.service.MonthlyProgressService;
import in.pms.transaction.service.ProjectOthersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
	public class ProjectOthersController {
 
		@Autowired 
		ProjectOthersService projectOthersService;
		@Autowired
		EncryptionService encryptionService;
		
		@Autowired
		MonthlyProgressService monthlyProgressService;
		
		
		@RequestMapping(value="/Others",method=RequestMethod.GET)
		public String ProjectOthers(HttpServletRequest request,@ModelAttribute ProjectOthersModel projectOthersModel ) {
			monthlyProgressService.writeProgressReportAuthorityCheck();
			String encMonthlyProgressId = projectOthersModel.getEncMonthlyProgressId();
			
			if(null != encMonthlyProgressId){
				int monthlyProgressId = Integer.parseInt(encryptionService.dcrypt(encMonthlyProgressId));
				System.out.println(" decrypted monthly progressid"+monthlyProgressId);
				//Load Group, Project Id based on encMonthlyProgressId
				MonthlyProgressModel monthlyProgressModel= monthlyProgressService.getById(monthlyProgressId);
				request.setAttribute("monthlyProgressModel", monthlyProgressModel);
				long categoryId =Long.parseLong(encryptionService.dcrypt(projectOthersModel.getEncCategoryId()));
				String descr= projectOthersService.loadOthersDetail(monthlyProgressId,categoryId);
				if(descr!=null){
					String news=descr.replaceAll("\"", "(?*");
					
				request.setAttribute("descrip",news);
				}
				request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
				request.setAttribute("encCategoryId", projectOthersModel.getEncCategoryId());
			}
			return "ProjectOthers";
		}
		
		@RequestMapping(value = "/SaveProjectOthers", method = RequestMethod.POST)
	    public @ResponseBody
	    String SaveProjectOthers(ProjectOthersModel projectOthersModel, HttpServletRequest request,HttpServletResponse response) {
	       monthlyProgressService.writeProgressReportAuthorityCheck();
	       return projectOthersService.SaveProjectOthers(projectOthersModel);
	        
	 }
		}