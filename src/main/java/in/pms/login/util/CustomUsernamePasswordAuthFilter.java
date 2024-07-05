package in.pms.login.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import in.pms.master.dao.OtpInfoDao;
import in.pms.master.domain.OtpInfo;
import in.pms.master.service.OtpInfoService;
//sun.misc.BASE64Decoder() deprecated for upgraded Java versions (jdk 11+)
//import sun.misc.BASE64Decoder;
import java.util.Base64;

/*import com.octo.captcha.service.image.ImageCaptchaService;*/

public class CustomUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	OtpInfoService otpInfoService;
/*	@Autowired
	ImageCaptchaService imageCaptchaService;*/
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response,FilterChain chain,Authentication authResult) throws ServletException,IOException {
		System.out.println("Successful login");
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,HttpServletResponse response,AuthenticationException failed) throws ServletException,IOException {
		System.out.println("not redirected");
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException{
		System.out.println("attempt authentication");
		if(request.getParameter("otpenter")==null)
		{
		 String id = request.getParameter("captcha");
		 System.out.println(id);
		 if(id!=null){
	        HttpSession session = request.getSession();
	        String random1 = (String) session.getAttribute("rand1");
	        System.out.println(random1);
	        String random2 = (String) session.getAttribute("rand2");
	        System.out.println(random2);
	        if(null != random1 && null != random2){
	        	int rand1 = Integer.parseInt(random1);
		        int rand2 = Integer.parseInt(random2);
		        if (Integer.valueOf(id) != rand1 + rand2) {
		            throw new AuthenticationServiceException("Incorrect captcha value!");
		        }
	        }else{
	        	throw new AuthenticationServiceException("Session Expired");
	        }	        
	        request.getSession().setAttribute("captcha", id);
		 }
		}
		 
		
	        return super.attemptAuthentication(request, response);
	    }
	
	
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		
		 String userName=request.getParameter("stremail");
		 //System.out.println(userName);
	
		 if(request.getParameter("otpenter")!=null)
		 {
			 userName=request.getParameter("stremail").toString();
				 return userName;
		 }
	return  super.obtainUsername(request);
		
	}
	
	
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		if(request.getParameter("otpenter")!=null)
		{
		 String strOTP=request.getParameter("otpenter");
		 //System.out.println(strOTP);
		 
		 
		 
		 String decodedOTP="";
			try{
				 //byte[] decodedPass = new BASE64Decoder().decodeBuffer((strOTP.toString()));
				 byte[] decodedPass = Base64.getDecoder().decode(strOTP.toString());
					String value =new String(decodedPass);
					String[] temp = value.split(" ### ");
					
						String random1 = temp[0];
						String random2 = temp[2];
					    //System.out.println("----decoded-----"+temp[1]);
					    decodedOTP=temp[1];
				}catch(Exception e){
					e.printStackTrace();
				}
			
		 //System.out.println("+++++++++"+decodedOTP);
		 boolean flag1 = otpInfoService.secOTPCheck(decodedOTP);

		 if(flag1)
		 {
			 strOTP=decodedOTP;
		 }
		 else
		 {
			 //System.out.println("catch");
			 //strOTP=null;
	         throw new AuthenticationServiceException("Incorrect OTP!");


		 }
		 //System.out.println("[[[[[[[["+strOTP);
		 
		 return strOTP;
		 
		}
		 return  super.obtainPassword(request);
		
	}
	
	
	
	
}
