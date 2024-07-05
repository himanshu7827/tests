package in.pms.login.controller;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.RandomGenerator;
import in.pms.login.model.ResetPasswordModel;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.OtpInfoDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.OtpInfo;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.OtpInfoModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.OtpInfoService;
import in.pms.transaction.model.BudgetHeadMasterForm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//sun.misc.BASE64Decoder() deprecated for upgraded Java versions (jdk 11+)
//import sun.misc.BASE64Decoder;
import java.util.Base64;

@Controller
public class LoginController {
	
	@Autowired
	 OtpInfoService otpInfoService;
	 
	 @Autowired
	 OtpInfoDao otpInfoDao;
	
	@Autowired
	EmployeeMasterService employeeMasterService;
	
	 @Autowired
	 private MessageSource messages;

	 @Autowired
	 EmployeeRoleMasterService employeeRoleMasterService;
	 
	 @Autowired
	 SessionRegistry sessionRegistry;
	 
	 @Autowired
	 EncryptionService encryptionService;
	 
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	    public String showChangePasswordPage(final ResetPasswordModel resetPasswordModel,HttpServletRequest request) {
		 HttpSession httpSession = request.getSession(true);
		 
		 String prefixNew = RandomGenerator.generateRandom(5, 3);
		 String suffixNew = RandomGenerator.generateRandom(6, 5);

		 String prefixOld = RandomGenerator.generateRandom(6, 5);
		 String suffixOld = RandomGenerator.generateRandom(5, 4);
		 
		 httpSession.setAttribute("prefixNew",prefixNew );
		 httpSession.setAttribute("suffixNew",suffixNew );
		 httpSession.setAttribute("prefixOld",prefixOld);
		 httpSession.setAttribute("suffixOld",suffixOld );
		
			return "changePassword";
	    }
	 

	// change user password
	    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)	    
	    public String changeUserPassword(@Valid ResetPasswordModel resetPasswordModel,RedirectAttributes redirectAttributes) {
	        final EmployeeMasterDomain user = employeeMasterService.findByEmail(((UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOfficeEmail());
	        String oldPassword ="";
	        String newPassword ="";
	        try{
	        	 //byte[] decodedPass = new BASE64Decoder().decodeBuffer(resetPasswordModel.getOldPassword());
	        	byte[] decodedPass = Base64.getDecoder().decode(resetPasswordModel.toString());
	 			
	 			String value =new String(decodedPass);
	 			String[] temp = value.split(" ### ");
	 			if(temp.length==3){
					String suffixOld = temp[2];
					String prefixOld = temp[0];
					RequestAttributes requestAttributes = RequestContextHolder
				            .currentRequestAttributes();
				    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
				    HttpServletRequest request = attributes.getRequest();
				    HttpSession httpSession = request.getSession(true);
				    String prefixOldSession = (String) httpSession.getAttribute("prefixOld");
				    String suffixOldSession = (String) httpSession.getAttribute("suffixOld");
				    if(!prefixOld.equals(prefixOldSession) || !suffixOld.equals(suffixOldSession)) {
				    	throw new Exception("Something went wrong!");
				    }
				    oldPassword = temp[1];				
				}
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        if (!employeeMasterService.checkIfValidOldPassword(user, oldPassword)) {
	           // throw new InvalidOldPasswordException();
	        	redirectAttributes.addFlashAttribute("message", "Invalid Old password");
	        	redirectAttributes.addFlashAttribute("status", "error");
	        	 return "redirect:/changePassword";
	        }
	        
	        try{
	        	 //byte[] decodedPass = new BASE64Decoder().decodeBuffer(resetPasswordModel.getNewPassword());
	        	byte[] decodedPass = Base64.getDecoder().decode(resetPasswordModel.toString());
	 			
	 			String value =new String(decodedPass);
	 			String[] temp = value.split(" ### ");
	 			if(temp.length==3){
					String suffixNew = temp[2];
					String prefixNew = temp[0];
					RequestAttributes requestAttributes = RequestContextHolder
				            .currentRequestAttributes();
				    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
				    HttpServletRequest request = attributes.getRequest();
				    HttpSession httpSession = request.getSession(true);
				    String prefixNewSession = (String) httpSession.getAttribute("prefixNew");
				    String suffixNewSession = (String) httpSession.getAttribute("suffixNew");
				    if(!prefixNew.equals(prefixNewSession) || !suffixNew.equals(suffixNewSession)) {
				    	throw new Exception("Something went wrong!");
				    }
				    newPassword = temp[1];				
				}
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
	        employeeMasterService.changeUserPassword(user, newPassword);
        	redirectAttributes.addFlashAttribute("message", "Password Updated Successfully");
	        redirectAttributes.addFlashAttribute("status", "success");
	        logoutAllSessions();
        	return "redirect:/changePassword";
	    }

	    @RequestMapping(value = "/popUpRoles", method = RequestMethod.GET)
	    public String showRolespopUp(HttpServletRequest request) {
	        String txt = "";
	        String returnString2 = "";

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo usrInfo = (UserInfo) authentication.getPrincipal();
			
	      
	        //request.setAttribute("lastLogin", loginService.getLastSessionDetail(usrInfo.getNumUserId()));

			EmployeeRoleMasterModel defaultEmpRole = employeeRoleMasterService.getDefaultEmployeeRoleByEmpId(usrInfo.getEmployeeId());
			
			List<EmployeeRoleMasterModel> employeeRoles = employeeRoleMasterService
					.getActiveEmployeeRoleByEmpId(usrInfo.getEmployeeId());
			
			if(defaultEmpRole!=null)
			returnString2=defaultEmpRole.getStrRoleName()+"("+defaultEmpRole.getStrProjectName()+")";
	        
			
	        txt = returnString2 + "#|@|#" + txt;
	        request.setAttribute("Roles", employeeRoles);
	        request.setAttribute("TEXT", txt);
	        return "showRolespopup";
	    }
	    
	    
	    
		
		 
		 @RequestMapping(value="/GetEmailId", method=RequestMethod.POST)
			public @ResponseBody String GetEmailId(EmployeeMasterModel model, HttpServletRequest request)
			{
				int id = model.getCaptcha();
				/*-----------  GET Captcha Values [ 02/08/2023 ] ----------------------*/
				String dataLoadedBasedOn = model.getDataLoadedBasedOn();
				String[] captchaValues = dataLoadedBasedOn.split(",");
				/*---------------------------------------------------------------------*/
				if (id >= 0) {
					// HttpSession session = request.getSession();
					// int rand1 = Integer.parseInt((String)
					// session.getAttribute("rand1"));
					// int rand2 = Integer.parseInt((String)
					// session.getAttribute("rand2"));
					/*-----------  Change Captcha values to int Values [ 02/08/2023 ] ----------------------*/
					int rand1 = Integer.parseInt((String) captchaValues[0]);
					int rand2 = Integer.parseInt((String) captchaValues[1]);
					/*--------------------------------------------------------------------------------------*/
					if (id != rand1 + rand2) {
						String str = "false";
						str += ";Incorrect captcha value!";
						return str;
					}
					// request.getSession().setAttribute("captcha", id);
				} else {
					String str = "false";
					str += ";Incorrect captcha value!";
					return str;
				}
		
				final EmployeeMasterDomain user = employeeMasterService
						.findByEmail(model.getOfficeEmail());
		
				if (user != null)
					return "true";
				else
					return "false;Email id not registered";
			}
		 
		 @RequestMapping(value="/EmailSend", method=RequestMethod.POST)
			public String viewForgotPasswords(EmployeeMasterModel model, HttpServletRequest request)
			{ 
			 	boolean flag =employeeMasterService.sendMail(model);
			if(flag){
				/*----------  Add for Reset Password ------*/
				request.setAttribute("Message","Login Credentials Details Sent to Email ID Successfully !");
				//request.setAttribute("Message1","WindowClose");
			//request.setAttribute("Message","OTP has been sent to your registered Email Id");
			//request.setAttribute("Message1","WindowClose");
			}
			
	        return "ForgotPasswordForm";
			}
		 
			
		 	@RequestMapping("/sessionsCountForUser") 	
		 	public @ResponseBody int totalSessionForUser(){
		 		int count =0;	 		
		 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 		UserInfo loginInfo = (UserInfo) authentication.getPrincipal();	
				List<SessionInformation> sessions = sessionRegistry.getAllSessions(loginInfo, false);
				//List<Object> sessions = sessionRegistry.getAllPrincipals();
				count = sessions.size();		    
		 		return count;
		 	}
		 	
		 	@RequestMapping("/logoutAllSessions") 	
		 	public String logoutAllSessions(){
		 		 		
		 		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				UserInfo loginInfo = (UserInfo) authentication.getPrincipal();					
				List<SessionInformation> sessions = sessionRegistry.getAllSessions(loginInfo, false);		
		    	 for (SessionInformation information : sessions) {             	
		    		 information.expireNow();	    		
	             }		    	
		    	 return "redirect:/Homepage";
		 	}
		 	
		 	@RequestMapping("/allSessions") 	
		 	public String allSessions(HttpServletRequest request){		 		
		 		List<User> userlist = new ArrayList<User>();
		 		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		 		for(Object obj : allPrincipals){
		 			if(obj instanceof User){
		 				userlist.add((User) obj);
		 			}
		 		}
		 		request.setAttribute("users",userlist);
		 		return "allSessions";
		 	}
		 	
		 	
		 	@RequestMapping(value="/ForgotPassword", method=RequestMethod.GET)
			public String viewForgotPassword(EmployeeMasterModel employeeMasterModel,HttpServletRequest request)
			{
			 
			// System.out.println("in forgot password");
				      //return "redirect:/ForgotPasswordForm";
		 		return "ForgotPasswordForm";
				      
			}
		 
		 	@RequestMapping(value="/GenerateOTP", method=RequestMethod.GET) 
		 	public String viewgenerateOTP(OtpInfoModel otpInfoModel,HttpServletRequest request) {
			  
			   System.out.println("in generate OTP Form"); 
		 		return "GenerateOTPForm";
			  
			  }
		 	
				/*
				 * @RequestMapping(value="/GenOTP",method=RequestMethod.POST) public
				 * ModelAndView GenOTP(OtpInfoModel otpInfoModel,HttpServletRequest
				 * request,RedirectAttributes redirectAttributes, BindingResult bindingResult){
				 * ModelAndView mav = new ModelAndView(); System.out.println("Controller"); long
				 * id=otpInfoService.GenOTP(otpInfoModel);
				 * System.out.println("Call for service class done");
				 * redirectAttributes.addFlashAttribute("message",
				 * "record saved Successfully with Id "+id);
				 * redirectAttributes.addFlashAttribute("status", "success");
				 * 
				 * 
				 * List<OtpInfoModel> list = otpInfoService.getAllGenOtpDomain();
				 * request.setAttribute("otpInfoModel",new OtpInfoModel());
				 * request.setAttribute("data", list);
				 * 
				 * 
				 * mav.setViewName("redirect:/PMS/GenerateOTP"); return mav; }
				 */
		 	 
		 	@RequestMapping(value="/GetEmailIdOTP", method=RequestMethod.POST)
			public @ResponseBody String GetEmailIdOTP(OtpInfoModel otpInfoModel, HttpServletRequest request)
			{

			 int id = otpInfoModel.getCaptcha();
			 System.out.println("in get email id");
			 if(id>0){
		        HttpSession session = request.getSession();
		        int rand1 = Integer.parseInt((String) session.getAttribute("rand1"));
		        int rand2 = Integer.parseInt((String) session.getAttribute("rand2"));
		        if (id != rand1 + rand2) {
		        String str="false";
		           str +=";Incorrect calculated value!";
		           return str;
		        }
		        //request.getSession().setAttribute("captcha", id);
			 }
			 
		        EmployeeMasterDomain user = employeeMasterService.findByEmail(otpInfoModel.getStremail());

				if(user != null)
				{
					
					System.out.println("Email id is registered");		
					return "True";
					//boolean isSuccess=otpInfoService.sendOTPMail(otpInfoModel);
				}
					//if(isSuccess)
					//{
						//System.out.println("OTP sent successfully");
						//return "OTP Sent Successfully";
					//}
					else
					{
						//return "Something went wrong in sending OTP. Try again";
						System.out.println("Email id not registered");
						return "false;Email id not registered";
					}
					
					//otpInfoService.GenOTP(otpInfoModel);
				}
		        
				//else
					//String str="false";
				//return "Email id not registered";
				

		 
		 	@RequestMapping(value="/EmailSendOTP", method=RequestMethod.POST)
			public String EmailSendOTP(OtpInfoModel otpInfoModel, HttpServletRequest request)
			{ 
		 		System.out.println("call for sendOTPMail");
			 	boolean flag = otpInfoService.sendOTPMail(otpInfoModel);
			 	if(flag){
				System.out.println("displayed message");
				request.setAttribute("Message","OTP has been sent to your registered Email Id. The OTP will be valid for only 10 mins");
			//request.setAttribute("Message1","WindowClose");
			}
			 	HttpSession session = request.getSession();
			 	session.setAttribute("flag","1");
	        return "GenerateOTPForm";
			}
		 	 //viewOTPEmails
		 	
		 	@RequestMapping(value="/VerifyOTP",method=RequestMethod.POST)	
			public String VerifyOTP(OtpInfoModel otpInfoModel,HttpServletRequest request){		
				
				
				System.out.println("In controller of verify OTP");
				//OtpInfo otpInfoDomain = new OtpInfo();
				boolean strOtpCheck = otpInfoService.checkSameOtp(otpInfoModel);
				if(strOtpCheck)
				{
					System.out.println("Verified");
					//redirectAttributes.addFlashAttribute("message","OTP Not Verified !! Try Again !!");
					//int count=0;
					
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					System.out.println(authentication.getPrincipal());
					UserInfo loginInfo = (UserInfo) authentication.getPrincipal();
					System.out.println(authentication.getPrincipal());
					String username=loginInfo.getUsername();
					System.out.println(username);
				}
				else
				{
					
					throw new AuthenticationServiceException("Wrong OTP" );
					
					
					
					//redirectAttributes.addFlashAttribute("message","OTP Verified");
					//System.out.println("Not Verified");
				}
				/*
				 * List<BudgetHeadMasterForm> list = otpInfoService.getAllOtpInfoDomain();
				 * request.setAttribute("budgetHeadMasterForm",new BudgetHeadMasterForm());
				 * request.setAttribute("data", list); System.out.println(list);
				 */
				
				return "GenerateOTPForm";
			}
			
}
