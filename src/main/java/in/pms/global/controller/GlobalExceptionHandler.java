package in.pms.global.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public String runTimeHandler(HttpServletRequest request, Exception e){
		log.error(e.getCause().getMessage());
		log.error(e.getMessage());		
		e.printStackTrace();
		return  "error";
	}

}
