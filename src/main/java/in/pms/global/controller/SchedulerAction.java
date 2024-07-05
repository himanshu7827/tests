package in.pms.global.controller;


import in.pms.global.misc.ResourceBundleFile;
import in.pms.master.service.NewsLetterService;
import in.pms.transaction.service.MonthlyProgressService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
@SessionAttributes("msg")

@EnableScheduling
@Controller
public class SchedulerAction{
	
	static Logger logger = LoggerFactory.getLogger(SchedulerAction.class);

    @Autowired
    public NewsLetterService NewsletterService;
    
    
    @Autowired
    public MonthlyProgressService monthlyProgressService;

    
    /*Global variables for Proposal Flow progress bar*/
    public  int numProposalSeq;
    
    
    String schedule_run_check=ResourceBundleFile.getValueFromKey("SCHEDULER_RUN_CONDITION") ;   
    
    public String link;
    /***************?

    
    // Variable used for doc upload form    
    
	/*LinkedList<FileMeta> uplaodfiles = new LinkedList<FileMeta>();
	FileMeta fileMeta = null;
	*/
	public List<String> listOfDocuments = new ArrayList<String>();
	public List<String> listOfDocumentsIds = new ArrayList<String>();
    Long setproposalId=0l;
    int setpseq=0;
    //String crontime="0 09 16 ? * *";
	
	@ExceptionHandler(Exception.class)
	public String exc(HttpServletRequest request,Exception e) {
		//System.out.println("exception occured is........."+e);
		e.printStackTrace();
		return "ErrorPage";			 	
	}
	

	
	@Scheduled(cron="0 0 14 ? * *")
	public void runNewsLetterNightScheduler() {
	
	        try {
	        	if(schedule_run_check.equals("YES"))
	        	NewsletterService.getNewsLettersToBeTransfer();	
			} catch (Exception e) {
							// TODO: handle exception
			}
	
        System.out.println("Time's up!");
       
    }
	
	
	 @Scheduled(cron="0 34 17 * * ?") 
	 public void runNewsLetterPeriodicScheduler() {
		 System.out.println("Periodic scheduler is about to run!!"); 
		 try {
		 	  if(schedule_run_check.equals("YES"))
		 		NewsletterService.getNewsLettersToBeTransferredPeriodically(); 
		  } 
		  catch
		  (Exception e) {
			  System.out.println("Some exception in periodic scheduler"+e.getStackTrace());
		  }
		  
		  System.out.println("Periodic Scheduler has run!");	  
	  }
	 

	 @Scheduled(cron="0 20 16 28 * ?")
		public void runSchedulerForMPRPendingAtGC() {
		
		        try {
		        	if(schedule_run_check.equals("YES"))
		        	monthlyProgressService.getPendingMonthlyProgReportAtGC();	
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	        System.out.println("Time's up!");
	       
	    }
	
	

	
	
	

}