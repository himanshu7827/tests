package in.pms.login.util;

import in.pms.login.domain.TransactionActivity;
import in.pms.login.service.TransactionActivityService;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomRequestInterceptor implements HandlerInterceptor {

	@Autowired
	TransactionActivityService transactionActivityService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("User-Agent");           
       String requestURI = request.getRequestURI();
      
       if (SecurityContextHolder.getContext().getAuthentication() != null &&
               SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
               //when Anonymous Authentication is enabled
               !(SecurityContextHolder.getContext().getAuthentication()
                       instanceof AnonymousAuthenticationToken)) {
				   	 if (!requestURI.contains("/resources/app_srv") && !requestURI.contains("/PMS/popUpRoles")){
				   	   TransactionActivity activity = new TransactionActivity();
				       
				       activity.setRequestMethod(request.getMethod());
				       activity.setUrl(requestURI);
				       activity.setTransactionDate(new Date());
				       UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();				         
				       activity.setUserId(user.getEmployeeId());
				       activity.setSessionId(request.getSession().getId());
				       activity.setUserAgent(userAgent);
				       transactionActivityService.saveTransactionActivity(activity);
				   	 }
       		}
       return true;
    }

	
}
