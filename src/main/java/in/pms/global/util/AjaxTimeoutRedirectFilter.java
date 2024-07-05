package in.pms.global.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Servlet Filter implementation class AjaxTimeoutRedirectFilter
 */
@Slf4j
public class AjaxTimeoutRedirectFilter extends GenericFilterBean implements Filter {
    
   
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException{

    try {      
        HttpServletRequest request = (HttpServletRequest) req;
    	
        HttpSession session = request.getSession(false);

        SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
	
        if (isAjax(request) && null == sci) {
            log.error("Session expiration during ajax request, partial redirect to login page");
            HttpServletResponse response = (HttpServletResponse) resp;      
            response.getWriter().print("Unauthorised Access");        
            response.flushBuffer();
        }
        else {
            chain.doFilter(req, resp);
        }
        
    } catch (Exception e) {
    	chain.doFilter(req, resp);     
    }
}

private boolean isAjax(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
}
}
