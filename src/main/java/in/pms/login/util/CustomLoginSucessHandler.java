package in.pms.login.util;

import in.pms.login.domain.LoginHistoryDomain;
import in.pms.login.service.LoginHistoryService;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginSucessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	LoginHistoryService loginHistoryService;
	
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request,
	            HttpServletResponse response, Authentication authentication)
	            throws ServletException, IOException {
	        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
	        LoginHistoryDomain  loginHistoryDomain = createLoginHistoryDomain(request,userInfo);
	        loginHistoryService.saveLoginHistoryDomain(loginHistoryDomain);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        request.getSession().setAttribute("loginSuccess", true);  // after user login add variable loginSuccess value true [28-02-2024]
	        super.onAuthenticationSuccess(request, response, authentication);
	    }
	 
	public LoginHistoryDomain createLoginHistoryDomain(HttpServletRequest request,UserInfo userInfo){
		LoginHistoryDomain loginHistory = new LoginHistoryDomain();
		loginHistory.setUserId(userInfo.getEmployeeId());
		loginHistory.setUserName(userInfo.getEmployeeName());
		loginHistory.setUserEmail(userInfo.getOfficeEmail());
		loginHistory.setLoginDate(new Date());
		loginHistory.setNumTrUserId(userInfo.getEmployeeId());
		
		loginHistory.setNumIsValid(1);
		
		String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();

        String os = "";
        String browser = "";

       
        //=================OS=======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             os = "Windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
             os = "Mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
             os = "Unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
             os = "Android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
             os = "IPhone";
         }else{
             os = "UnKnown, More-Info: "+userAgent;
         }
         //===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else
        {
            browser = "UnKnown, More-Info: "+userAgent;
        }
      loginHistory.setOsType(os);
      loginHistory.setBrowserName(browser);
      
      String ipAddress = request.getRemoteAddr();
      loginHistory.setIpAddress(ipAddress);      
      loginHistory.setSessionId(request.getSession().getId());				
		return loginHistory;
	}

}
