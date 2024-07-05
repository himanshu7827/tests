/**********************************************************
 Project:	   DWH_TB
 File:         FormFieldValidationFilter.java
 Created:      Aug 9, 2017
 Last Changed: Aug 9, 2017
 Author:       Shivam Bhatnagar

This code is copyright (c) 2017 C-DAC Noida.

 ***********************************************************/
package in.pms.global.misc;



import in.pms.global.service.DataEncoderService;
import in.pms.global.service.DataEncoderServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;


public class FormFieldValidationFilter implements Filter {

	
	private static Set<String> keys = new HashSet<String>();

	static {
		keys.add("varssoticketgrantingticket");
		keys.add("go.x");
		keys.add("go.y");
	}

	private static final String TOKEN_KEY = "SUGAMLAB_TOKEN_KEY";

	/**
	 * Default constructor.
	 */
	public FormFieldValidationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
	

		String method = ((HttpServletRequest) request).getMethod();	
		if(method==null)
			method="";
		
		//System.out.println("check method :: "	+ method);
		
	
		if( !method.equalsIgnoreCase("POST") ){
			chain.doFilter(request, response);
		}
		else{
			String strURI = ((HttpServletRequest) request).getRequestURI();	
			
			String fHTokenVal = "";

			List<ParamObject> paramMap = null;
			UploadMultipartRequestWrapper multipartRequest = null;

			boolean isMultiPart = ServletFileUpload
					.isMultipartContent((HttpServletRequest) request);

	

			if (paramMap == null) {

					/*System.out.println("above getParamsFromSimpleForm :: "
							+ request.getParameter("hmode"));*/

					paramMap = getParamsFromSimpleForm((HttpServletRequest) request);

					/*System.out.println("below getParamsFromSimpleForm :: "
							+ request.getParameter("hmode"));*/

			}

	

			StringBuffer sb = new StringBuffer("");

			if (paramMap != null && paramMap.size() > 0) {

				Collections.sort(paramMap, new Comparator<ParamObject>() {

					public int compare(ParamObject s1, ParamObject s2) {

						return s1.getName().toLowerCase().toString()
								.compareTo(s2.getName().toLowerCase().toString());
					}

				});

				int x=0;
				
				for (ParamObject paramObject : paramMap) {
					
					//if(!paramObject.getName().equals("serb_token"))
					//System.out.println("key : =" + paramObject.getName() + "=  value :  " + paramObject.getValue());

					if (TOKEN_KEY.equalsIgnoreCase(paramObject.getName() )) {
						
						if(paramObject.getValue()!=null && !paramObject.getValue().equals(""))
							fHTokenVal = paramObject.getValue();						
						
					} 

					else 
						{

						String val = paramObject.getValue().replaceAll(" ", "_");
						val = val.replaceAll("\\r?\\n", "_");	
						
						sb.append(val);

					}

				}

			}


			System.out.println("string :: " + sb.toString());

			String fToken = "";
			if(!fHTokenVal.equals(""))
				fToken=	sb.toString().isEmpty() ? "" : SecurityUtil
					.getMd5Hash(sb.toString());

			System.out.println("fToken :: " + fToken);

			System.out.println("fHTokenVal :: " + fHTokenVal);

			if (sb.toString().equals(fHTokenVal) || fToken.equals("")   ) {
				

				/*if (isMultiPart) {
					
					System.out.println("multipartRequest hmode before invoke next filter :: "
							+ multipartRequest.getParameter("hmode"));

					chain.doFilter(multipartRequest, response);

					
				} else {*/


					System.out.println("request hmode before invoke next filter :: "
							+ request.getParameter("hmode"));
					
					chain.doFilter(request, response);

				//}

			} else {

				HttpServletResponse res = (HttpServletResponse) response;

				res.setContentType("text/html");
				PrintWriter pw = res.getWriter();
				pw.write("<html lang='en'><meta http-equiv='content-type' content='text/html;charset=UTF-8'/><head><link href='/pms/resources/app_srv/NMD/global/sugam-dashboard/vendors/base/vendors.bundle.css' rel='stylesheet' type='text/css'/><link href='/pms/resources/app_srv/NMD/global/sugam-dashboard/demo/default/base/style.bundle.css' rel='stylesheet' type='text/css' /><link rel='icon' href='/pms/resources/app_srv/NMD/global/landingPage_assets/img/sugamLabs-ico.ico'></head><body  class='m--skin- m-header--fixed m-header--fixed-mobile m-aside-left--enabled m-aside-left--skin-dark m-aside-left--fixed m-aside-left--offcanvas m-footer--push m-aside--offcanvas-default'  ><div class='m-grid m-grid--hor m-grid--root m-page'><div class='m-grid__item m-grid__item--fluid m-grid  m-error-6' style='background-image: url(/pms/resources/app_srv/NMD/global/sugam-dashboard/app/media/img/error/bg6.jpg);'><div class='m-error_container'><div class='m-error_subtitle m--font-light'><h1>Oops... </h1></div><p class='m-error_description m--font-light'>Looks like someome is trying to tamper the actual data<br>We're working on it<br><a href='/pms/Logout' class='btn btn-large btn-warning'><i class='icon-home icon-white'></i> Take Me Home</a></p></div></div></div><script src='/pms/resources/app_srv/NMD/global/sugam-dashboard/vendors/base/vendors.bundle.js' type='text/javascript'></script><script src='/pms/resources/app_srv/NMD/global/sugam-dashboard/demo/default/base/scripts.bundle.js' type='text/javascript'></script></body></html>");

			}
			
		
		
		}

		// pass the request along the filter chain

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private static List<ParamObject> getParamsFromSimpleForm(
			HttpServletRequest request) {

		Enumeration<String> paramNames = request.getParameterNames();

		List<ParamObject> paramMap = new ArrayList<ParamObject>();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
		//	System.out.println(" param name is " + paramName);
			if (!keys.contains(paramName.toLowerCase())) {

				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() > 0)
						// paramValue= "108";
						if(paramName.equals("SUGAMLAB_TOKEN_KEY"))
						{
							paramValue=DataEncoderServiceImpl.decodeNew(paramValue);
						}
						paramMap.add(new ParamObject(paramName.toLowerCase(),
								paramValue));

				} else {
					for (int i = 0; i < paramValues.length; i++) {
						if (!("hmode".equalsIgnoreCase(paramName) && i == 0))
							paramMap.add(new ParamObject(paramName
									.toLowerCase(), paramValues[i]));
					}

				}

			}

		}

		return paramMap;
	}

	private static List<ParamObject> getParamsFromMultipartForm(
			HttpServletRequest request) {

		Enumeration<String> paramNames = request.getParameterNames();

		List<ParamObject> paramMap = new ArrayList<ParamObject>();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			//System.out.println(" param name is " + paramName);
			if (!keys.contains(paramName.toLowerCase())) {

				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() > 0)
						// paramValue= "108";
						System.out.println(paramName +"  >>  "+paramValue);
						paramMap.add(new ParamObject(paramName.toLowerCase(),
								paramValue));

				} else {
					for (int i = 0; i < paramValues.length; i++) {
						if (!("hmode".equalsIgnoreCase(paramName) && i == 0))
							paramMap.add(new ParamObject(paramName
									.toLowerCase(), paramValues[i]));
						
						
						System.out.println(paramName +"  >>  "+paramValues[i]);
						
					}

				}

			}

		}

		return paramMap;
	}

}
