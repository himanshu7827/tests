 package in.pms.mail.dao;

 import in.pms.global.dao.DaoHelper;
import in.pms.global.misc.SMTPPropertiesReader;
import in.pms.mail.domain.SendMailHistory;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;


public  class SendMail
{ 
	
	@Autowired
	SendMailDao sendMailDao;
	
	@Autowired
	DaoHelper daoHelper;
	
	/*@Autowired
	DaoHelper daoHelper;*/
	
	String SMTP_HOST_NAME = "";
	String SMTP_AUTH_USER = "";
	String SMTP_AUTH_PWD  = "";
	
	String emailMsgTxt      = "";
	String emailSubjectTxt  = "";
	
	String emailFromAddress = "";
	String fileName = "";
          String strCCMail = "";
        

          public void TransferToMailServer(String mail,String subj,String emailMsgTxt)throws MessagingException
      	{	    
        	  //System.out.println("------------------------------Inside TransferToMailServer function.");
      			 
        	  			

             
                      String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                      String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                      String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                      String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                      String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                     
                      SMTP_HOST_NAME = hostname.trim();  
                      SMTP_AUTH_USER = username1.trim();  
                      SMTP_AUTH_PWD=password1.trim() ;      
                      emailFromAddress=from.trim();
                      strCCMail=ccMail.trim();
      	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                      emailSubjectTxt = subj;
                    
                      List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1);   
                     
      		    postMailBulk( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail,allrow);
      		    
      		  //  postMail(strCCMail,emailSubjectTxt,"Recepients : "+mail+"\n"+"Body : \n"+emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",ccrow);
      		 // updateValidFlag(allrow);
      	}
          
          public void TransferToMailServerwithCC(String to,String subj,String emailMsgTxt,String cc,int projectId)throws MessagingException
        	{	    
          	  //System.out.println("------------------------------Inside TransferToMailServer function.");
        			 
          	  			

               
                        String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                        String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                        String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                        String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                       // String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                       
                        SMTP_HOST_NAME = hostname.trim();  
                        SMTP_AUTH_USER = username1.trim();  
                        SMTP_AUTH_PWD=password1.trim() ;      
                        emailFromAddress=from.trim();
                        strCCMail=cc;
        	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                        emailSubjectTxt = subj;
                       // emailMsgTxt = addHeader(emailMsgTxt);
                        emailMsgTxt = emailMsgTxt + 
                      "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
                		  
              		+"This is a system generated information and does not require any signature."
              		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
              		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
              		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
              		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
              		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
              		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
              		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
              		" by any recipient as a result of this e-mail.<br/><br/>" +
              		" *******************************************************************<br/>";
              		
             
                        List<Integer> allrow =  mailhistoryNew(to, subj, emailMsgTxt,1,strCCMail,projectId);   
                       
        		    postMailBulk( to, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail,allrow);
        		    
        		  //  postMail(strCCMail,emailSubjectTxt,"Recepients : "+mail+"\n"+"Body : \n"+emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",ccrow);
        		 // updateValidFlag(allrow);
        	}
          
          public void TransferToMailServerWithoutDB(String mail,String subj,String emailMsgTxt)throws MessagingException
        	{	    

                        String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                        String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                        String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                        String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                        String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                        SMTP_HOST_NAME = hostname.trim();  
                        SMTP_AUTH_USER = username1.trim();  
                        SMTP_AUTH_PWD=password1.trim() ;      
                        emailFromAddress=from.trim();
        	         strCCMail=ccMail.trim();
        	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                        emailSubjectTxt = subj;
                        emailMsgTxt = addHeader(emailMsgTxt);
                        emailMsgTxt = emailMsgTxt + 
                      "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
                  +"This is a system generated information and does not require any signature."
              		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
              		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
              		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
              		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
              		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
              		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
              		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
              		" by any recipient as a result of this e-mail.<br/><br/>" +
              		" *******************************************************************<br/>" +
              		"'SAVE PAPER - THINK BEFORE YOU PRINT!'"+
        	  		"<br/> <span class='red'>Please do not reply to this mail </span>"+
			  		"<hr align='left'/><div>* Don�t want to receive such notification anymore?<a href='mailto:pms-noida@cdac.in?subject=Unsubscribe' target='_blank'>Click here to send a mail to unsubscribe</a></div>";
             
        		    postMailBulkDB( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail);
        		    
        	}

		public boolean TransferToMailServerFromMail(String FromMail,String FromName,String mail,String subj,String emailMsgTxt)throws MessagingException
        	{	    
          	  //System.out.println("------------------------------Inside TransferToMailServer function.");
        			 
          	  boolean flag = false;		
          	  try{
                        String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                        String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                        String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                        //String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                        String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                        SMTP_HOST_NAME = hostname.trim();  
                        SMTP_AUTH_USER = username1.trim();  
                        SMTP_AUTH_PWD=password1.trim() ;      
                        emailFromAddress=FromMail.trim();
        	         strCCMail=ccMail.trim();
        	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                        emailSubjectTxt = subj;
                        emailMsgTxt = addHeader(emailMsgTxt);
                        emailMsgTxt = emailMsgTxt + 
                      "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
                 +"This is a system generated information and does not require any signature."
              		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
              		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
              		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
              		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
              		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
              		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
              		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
              		" by any recipient as a result of this e-mail.<br/><br/>" +
              		" *******************************************************************<br/>" +
              		"'SAVE PAPER - THINK BEFORE YOU PRINT!'"+
        	  		"<br/> <span class='red'>Please do not reply to this mail </span>"+
			  		"<hr align='left'/><div>* Don�t want to receive such notification anymore?<a href='mailto:pms-noida@cdac.in?subject=Unsubscribe' target='_blank'>Click here to send a mail to unsubscribe</a></div>";
             
                        List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1);   
                        List<Integer> ccrow =  mailhistory(strCCMail, subj, emailMsgTxt,1);
                        System.out.println("madi in send mail to  mail...."+mail);
                        System.out.println("madi in send Mail from mail "+emailFromAddress);
        		    postMailBulkSenderName( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail,allrow,FromName.trim());
        		    postMailSenderName(strCCMail,emailSubjectTxt,"Recepients : "+mail+"\n"+"Body : \n"+emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",ccrow,FromName.trim());
        		 // updateValidFlag(allrow);
          	  }
          	  catch (Exception e) {
				// TODO: handle exception
          		  flag= false;
          	  }
          	  return flag;
          	  }  
          
          
          
	
          public boolean TransferToMailServerBoolean(String mail,String subj,String emailMsgTxt)throws MessagingException
      	{	   System.out.println("------------------------------Inside TransferToMailServer function.");
      			 
                

      			boolean flag=false;
      			
      		
      		//System.out.println("this is isnerted no ...."+allrow.size());	
      		try
      	
      			{
      				
      					 
      				
                      String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;  
                      //System.out.println("this is the host name of mailing personn ......"+hostname);
                      String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                      String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                      String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                      String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                      SMTP_HOST_NAME = hostname.trim();  
                      SMTP_AUTH_USER = username1.trim();  
                      SMTP_AUTH_PWD=password1.trim() ;      
                      emailFromAddress=from.trim();
      	         strCCMail=ccMail.trim();
                       //System.out.println("Anshu jain ==== SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                   
      	      
      	         emailSubjectTxt = subj;
      	         emailMsgTxt = addHeader(emailMsgTxt);
                      emailMsgTxt = emailMsgTxt +
                    		 
                              
                      		"<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
                      	+"This is a system generated information and does not require any signature."
                      		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
                      		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
                      		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
                      		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
                      		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
                      		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
                      		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
                      		" by any recipient as a result of this e-mail.<br/><br/>" +
                      		" *******************************************************************<br/>" +
                      		"'SAVE PAPER - THINK BEFORE YOU PRINT!'"+
                	  		"<br/> <span class='red'>Please do not reply to this mail </span>"+
    				  		"<hr align='left'/><div>* Don�t want to receive such notification anymore?<a href='mailto:pms-noida@cdac.in?subject=Unsubscribe' target='_blank'>Click here to send a mail to unsubscribe</a></div>";
                      
                      List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1);   
                      List<Integer> ccrow =  mailhistory(strCCMail, subj, emailMsgTxt,1);
                      
                  postMailBulk( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail,allrow);
                  
                  postMail(strCCMail,emailSubjectTxt,"Recepients : "+mail+"\n"+"Body : \n"+emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",ccrow);
      		    flag=true;
      		  
      			}
      			 catch(Exception e)
      			{
      				System.out.println("THIS IS BOOLEAN MAIL CATCH");
      				 //
      				e.printStackTrace();
      				flag=false;
      	
      			}
      			return flag;
      	
      	}
          
          
    private void updateValidFlag(List<Integer> allrow)  {
		

    	 for(int i=0; i < allrow.size(); i++)
 		{
    	 SendMailHistory obj=sendMailDao.getSendMailHistory(allrow.get(i));
    	 obj.setIsMailSend(0);
    	 sendMailDao.merge(obj);
 		}
		}


	public List<Integer> mailhistory(String mail,String subj,String MsgTxt,int isMailSendFlag,String cc, String bcc,String filterType,int projectId)
          {
	 
    	List<Integer> intList = new ArrayList<Integer>();  	
    				
    				Connection conn = null;
    				//ConnInfo connInfo=new ConnInfo();
    				try {
    				
    					try{
    					int seqno=0;
    				
    					//conn = connInfo.getConnection(); 
    					conn=daoHelper.getConnection(); 
		        		Statement pst = conn.createStatement();
    					pst.executeQuery("SELECT max(NUM_ID)+1 from PMS_SEND_MAIL_HISTORY");
    					ResultSet results = pst.getResultSet();
    				
    					while (results.next()){
    						seqno=results.getInt(1);
    					}
    				
    		        		pst.close();
    		        		
    		        		String query = "INSERT INTO PMS_SEND_MAIL_HISTORY (NUM_ID,DT_TR_DATE,IS_MAILSEND,NUM_ISVALID,STR_MAIL_BODY,STR_SUBJECT,STR_TO_SEND,STR_Cc_SEND,STR_BCc_SEND,STR_TYPE,Num_PROJECT_ID) VALUES ('"+seqno+"', '"+new Date()+"', 1,'1', '"+MsgTxt+"', '"+subj+"', '"+mail+"','"+cc+"','"+bcc+"','"+filterType+"',"+projectId+")";
    		        		PreparedStatement ps=conn.prepareStatement(query);
    		    
    		        		ps.executeUpdate();
    		        		intList.add(seqno);
    		        		ps.close();
    					
    					}
    					catch (SQLException e) {
							// TODO: handle exception
						}
    		    
    				}
    				catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				finally{
    					try {
    						conn.setAutoCommit(true);
    						conn.close();
    						
    						} catch (SQLException e) {
    							e.printStackTrace();
    					}
    				}
					return intList;
        		}
        	 
   
          
          

          

	public void postMailBulk( String recipients, String subject,
			 String message , String from, String strccMail, List<Integer> allrow) throws MessagingException
{

		
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
		
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);


			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");
			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			//Address[] addressBcc = InternetAddress.parse(recipients);
			InternetAddress addressTo = new InternetAddress(recipients.trim());
			//msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			msg.setRecipient(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {

			System.out.println("THIS IS BLUK MAIL CATCH");
			updateValidFlag(allrow);
			System.out.println(e);
		}
	}
	

	public void postMailBulkDB( String recipients, String subject,
			 String message , String from, String strccMail) throws MessagingException
{

		
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
			// System.out.println("Security Manager" + security);
			// session.getInstance(props,auth)
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);

			// Session session= Session.getInstance(props);
			// session.setDebug(debug);

			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			Address[] addressBcc = InternetAddress.parse(recipients);
			InternetAddress addressTo = new InternetAddress(strccMail);
			msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			msg.setRecipient(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {
/*
			System.out.println("THIS IS BLUK MAIL CATCH");
			System.out.println(e);*/
		}
	}
	
	
	public void postMail(String recipients, String subject, String message,
			String from, String strccMail, List<Integer> ccrow)
			throws MessagingException {
		// System.out.println("------------------------------Inside postMail function.");
		// / for(int i=0;i<recipients.length;i++)
		// {
		// System.out.println(" Recipent "+i+"=========>>> "+recipients[i]);
		// }

		// System.out.println("Message =========>>> "+message);
		// List<Integer> allrow = mailhistory(recipients, subject, message);
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
			// System.out.println("Security Manager" + security);
			// session.getInstance(props,auth)
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);

			// Session session= Session.getInstance(props);
			// session.setDebug(debug);

			// create a message
			Message msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);

			InternetAddress addressTo = new InternetAddress(recipients);
			InternetAddress addresscc = new InternetAddress(strccMail);
			msg.setRecipient(Message.RecipientType.TO, addressTo);
			msg.setRecipient(Message.RecipientType.CC, addresscc);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("THIS IS single  MAIL CATCH");
			// updateValidFlag(allrow);
			updateValidFlag(ccrow);
			System.out.println(e);

		}
	}

	
	public void postMailBulkSenderName( String recipients, String subject,
			 String message , String from, String strccMail, List<Integer> allrow , String SenderMail) throws MessagingException
{
System.out.println("new mail sender .."+SenderMail);
/// for(int i=0;i<recipients.length;i++)
//{
//System.out.println(" Recipent "+i+"=========>>> "+recipients[i]);
//}

//System.out.println("Message =========>>> "+message);
		
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
			// System.out.println("Security Manager" + security);
			// session.getInstance(props,auth)
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);

			// Session session= Session.getInstance(props);
			// session.setDebug(debug);

			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from,SenderMail);
			msg.setFrom(addressFrom);

			Address[] addressBcc = InternetAddress.parse(recipients);
			InternetAddress addressTo = new InternetAddress(strccMail);
			msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			msg.setRecipient(Message.RecipientType.TO, addressTo);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {

			System.out.println("THIS IS BLUK MAIL CATCH");
			updateValidFlag(allrow);
			System.out.println(e);
		}
	}
	
	
	public void postMailSenderName(String recipients, String subject, String message,
			String from, String strccMail, List<Integer> ccrow,String SenderName)
			throws MessagingException {
		System.out.println("this is mail name---"+SenderName);
		// System.out.println("------------------------------Inside postMail function.");
		// / for(int i=0;i<recipients.length;i++)
		// {
		// System.out.println(" Recipent "+i+"=========>>> "+recipients[i]);
		// }

		// System.out.println("Message =========>>> "+message);
		// List<Integer> allrow = mailhistory(recipients, subject, message);
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
			// System.out.println("Security Manager" + security);
			// session.getInstance(props,auth)
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);

			// Session session= Session.getInstance(props);
			// session.setDebug(debug);

			// create a message
			Message msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from,SenderName);
			msg.setFrom(addressFrom);

			InternetAddress addressTo = new InternetAddress(recipients);
			InternetAddress addresscc = new InternetAddress(strccMail);
			msg.setRecipient(Message.RecipientType.TO, addressTo);
			msg.setRecipient(Message.RecipientType.CC, addresscc);

			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {
			System.out.println("THIS IS single  MAIL CATCH");
			// updateValidFlag(allrow);
			updateValidFlag(ccrow);
			System.out.println(e);

		}
	}
	

/**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/


private class SMTPAuthenticator extends javax.mail.Authenticator
{
//String bundlprop="in.cdacnoida.serb.gl.adaptor.SMTPPropertiesReaderPath";
String username="";
String password="";

public PasswordAuthentication getPasswordAuthentication()
{
String username1 =  SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");
username= username1.trim();

password=password1.trim();

return new PasswordAuthentication(username, password);
}
}




/*Added by Prashant Singh to send mails with attachment*/
public void TransferToMailServerWithAttachment(String mail,String subj,String emailMsgTxt,File fileOfAttachment, String fileName)throws MessagingException
{	    
            String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
            String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
            String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
            String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
            String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
            SMTP_HOST_NAME = hostname.trim();  
            SMTP_AUTH_USER = username1.trim();  
            SMTP_AUTH_PWD=password1.trim() ;      
            emailFromAddress=from.trim();
            strCCMail=ccMail.trim();
            emailSubjectTxt = subj;
            emailMsgTxt = addHeader(emailMsgTxt);
            emailMsgTxt = emailMsgTxt + 
            "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
           +"This is a system generated information and does not require any signature."
	  		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
	  		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
	  		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
	  		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
	  		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
	  		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
	  		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
	  		" by any recipient as a result of this e-mail.<br/><br/>" +
	  		" *******************************************************************<br/>" +
	  		"'SAVE PAPER - THINK BEFORE YOU PRINT!'"+
	  		"<br/> <span class='red'>Please do not reply to this mail </span>"+
	  		"<hr align='left'/><div>* Don�t want to receive such notification anymore?<a href='mailto:pms-noida@cdac.in?subject=Unsubscribe' target='_blank'>Click here to send a mail to unsubscribe</a></div>";
 

            List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1);   
            List<Integer> ccrow =  mailhistory(strCCMail, subj, emailMsgTxt,1);
            System.out.println("in send mail "+emailMsgTxt);
            
            postMail(mail,emailSubjectTxt,emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",ccrow,fileOfAttachment,fileName);
}

	private void postMail(String recipients, String subject, String message,
		String from, String strccMail, List<Integer> ccrow, File fileToBeAttached, String fileName) {
	
			try {

				boolean debug = true;
				SMTP_HOST_NAME = SMTPPropertiesReader
						.getValueFromKey("SMTP_HOST_NAME");

				// Set the host smtp address
				Properties props = new Properties();
				props.put("mail.smtp.host", SMTP_HOST_NAME);
				props.put("mail.smtp.sendpartial", "true");
				props.put("mail.smtp.auth", "true");
				SecurityManager security = System.getSecurityManager();
				// System.out.println("Security Manager" + security);
				// session.getInstance(props,auth)
				Authenticator auth = new SMTPAuthenticator();
				// Session session = Session.getDefaultInstance(props, auth);
				Session session = Session.getInstance(props, auth);

				// Session session= Session.getInstance(props);
				// session.setDebug(debug);

				// create a message
				Message msg = new MimeMessage(session);
				msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

				// set the from and to address
				InternetAddress addressFrom = new InternetAddress(from);
				msg.setFrom(addressFrom);

				InternetAddress addressTo = new InternetAddress(recipients);
				InternetAddress addresscc = new InternetAddress(strccMail);
				msg.setRecipient(Message.RecipientType.TO, addressTo);
				msg.setRecipient(Message.RecipientType.CC, addresscc);

				// Setting the Subject and Content Type
				msg.setSubject(subject);
				
				// Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Now set the actual message
		         messageBodyPart.setContent(message, "text/html;charset=utf-8");

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		         String filename = fileName+".pdf";
		         DataSource source = new FileDataSource(fileToBeAttached);
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName(filename);
		         multipart.addBodyPart(messageBodyPart);

		         // Send the complete message parts
		         msg.setContent(multipart);
				
				Transport.send(msg);

			} catch (Exception e) {
				System.out.println("THIS IS single  MAIL CATCH");
				updateValidFlag(ccrow);
				System.out.println(e);

			}
	
}
	
	/*Added by Prashant Singh to send mails with attachment*/
	public void TransferToMailServerWithAttachment(String mail,String subj,String emailMsgTxt,File[] fileOfAttachment, String[] fileName)throws MessagingException
	{	    

				        String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
				        String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
				        String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
				        String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
				        String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
				        SMTP_HOST_NAME = hostname.trim();  
				        SMTP_AUTH_USER = username1.trim();  
				        SMTP_AUTH_PWD=password1.trim() ;      
				        emailFromAddress=from.trim();
				     strCCMail=ccMail.trim();
				     //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
				        emailSubjectTxt = subj;
				        emailMsgTxt = addHeader(emailMsgTxt);
				        emailMsgTxt = emailMsgTxt + 
				      "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
				     +"This is a system generated information and does not require any signature."
						+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
						+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
						"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
						"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
						" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
						"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
						"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
						" by any recipient as a result of this e-mail.<br/><br/>" +
						" *******************************************************************<br/>" +
						"'SAVE PAPER - THINK BEFORE YOU PRINT!'"+
				  		"<br/> <span class='red'>Please do not reply to this mail </span>"+
				  		"<hr align='left'/><div>* Don�t want to receive such notification anymore?<a href='mailto:pms-noida@cdac.in?subject=Unsubscribe' target='_blank'>Click here to send a mail to unsubscribe</a></div>";
					            
	            postMail(mail,emailSubjectTxt,emailMsgTxt,emailFromAddress,"pms-noida@cdac.in",fileOfAttachment,fileName);
	}

		private void postMail(String recipients, String subject, String message,
			String from, String strccMail, File[] fileToBeAttached, String[] fileName) {
		
				try {

					boolean debug = true;
					SMTP_HOST_NAME = SMTPPropertiesReader
							.getValueFromKey("SMTP_HOST_NAME");

					// Set the host smtp address
					Properties props = new Properties();
					props.put("mail.smtp.host", SMTP_HOST_NAME);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.sendpartial", "true");
					SecurityManager security = System.getSecurityManager();
					// System.out.println("Security Manager" + security);
					// session.getInstance(props,auth)
					Authenticator auth = new SMTPAuthenticator();
					// Session session = Session.getDefaultInstance(props, auth);
					Session session = Session.getInstance(props, auth);

					// Session session= Session.getInstance(props);
					// session.setDebug(debug);

					// create a message
					MimeMessage msg = new MimeMessage(session);
					msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

					// set the from and to address
					InternetAddress addressFrom = new InternetAddress(from);
					msg.setFrom(addressFrom);
					Address[] addressBcc = InternetAddress.parse(recipients);
					InternetAddress addressTo = new InternetAddress(strccMail);
					msg.setRecipients(Message.RecipientType.BCC, addressBcc);
					msg.setRecipient(Message.RecipientType.TO, addressTo);

					// Setting the Subject and Content Type
					msg.setSubject(subject);
					
					// Create the message part
			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Now set the actual message
			         messageBodyPart.setContent(message, "text/html;charset=utf-8");

			         // Create a multipar message
			         Multipart multipart = new MimeMultipart("mixed");

			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         for(int i = 0 ; i < fileToBeAttached.length;i++){
				         messageBodyPart = new MimeBodyPart();
				         String filename = fileName[i]+".pdf";
				        
				         DataSource source = new FileDataSource(fileToBeAttached[i]);
				         messageBodyPart.setDataHandler(new DataHandler(source));
				         messageBodyPart.setFileName(filename);
				         multipart.addBodyPart(messageBodyPart);

			         }
			         // Send the complete message parts
			         msg.setContent(multipart);
					
					Transport.send(msg);

				} catch (Exception e) {
					System.out.println("THIS IS single  MAIL CATCH");
					System.out.println(e);

				}
		
	}
	
	public String addHeader(String emailText){
		String headerHTML = "<div style='width: 100%;display: block;clear: both;'><div style='padding-right: 10px;float: left;border-right: 2px solid gray;'><img src='www.serbonline.in/SERB/resources/app_srv/serb/gl/images/project_logo_50.png' alt='SERB India' style='height: 60; !important'></div><div style='text-align:center;width: 100%;'><div style='font-weight: 600;color:#0165a3;font-size: 22px;'>Science and Engineering Research Board</div><div style='font-size: 13px;text-indent:0px;font-weight: 600;'>(Statutory Body Established Through an Act of Parliament : SERB Act 2008)<br>Department of Science and Technology, Government of India  </div></div></div><div style='padding-top:15px;border-bottom:solid 2px #666'></div>";
		emailText = headerHTML+"<div style='background-color:#fbfbff;border-style: ridge;border-width: 3px;padding:10px; text-align: justify;'>"+emailText+"</div>";
		return emailText;
	}
	
	public void TransferToMailServerWithCc(String tomail,String subj,String emailMsgTxt,String cc, String bcc,String filterType,int projectId)throws MessagingException
	{	    

                String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
               // String ccMail=SMTPPropertiesReader.getValueFromKey("emailCC");
                SMTP_HOST_NAME = hostname.trim();  
                SMTP_AUTH_USER = username1.trim();  
                SMTP_AUTH_PWD=password1.trim() ;      
                emailFromAddress=from.trim();
	            //strCCMail=ccMail.trim();
	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                emailSubjectTxt = subj;
               /* emailMsgTxt = addHeader(emailMsgTxt);*/
                emailMsgTxt = emailMsgTxt + 
              "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
          +"This is a system generated information and does not require any signature."
      		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
      		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
      		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
      		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
      		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
      		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
      		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
      		" by any recipient as a result of this e-mail.<br/><br/>";
           if(!tomail.equals("")){
                List<Integer> allrow =  mailhistory(tomail, subj, emailMsgTxt,1,cc,bcc,filterType,projectId); 
                postMailBulkNew(tomail,cc, bcc, emailSubjectTxt, emailMsgTxt, emailFromAddress,allrow);
           }
  
	}

	public void TransferToMailServerWithAttachmentforNewsletter(String mail,String subj,String emailMsgTxt,File[] fileOfAttachment, String[] fileName,String ccMailIds, String bCcMailIds,String filterType,int projectId)throws MessagingException
	{	    

				        String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
				        String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
				        String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
				        String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
				        String ccMail=ccMailIds;
				        SMTP_HOST_NAME = hostname.trim();  
				        SMTP_AUTH_USER = username1.trim();  
				        SMTP_AUTH_PWD=password1.trim() ;      
				        emailFromAddress=from.trim();
				        strCCMail=ccMail.trim();
				     //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
				        emailSubjectTxt = subj;
				        //emailMsgTxt = addHeader(emailMsgTxt);
				        emailMsgTxt = emailMsgTxt + 
				      "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
				     +"This is a system generated information and does not require any signature."
						+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
						+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
						"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
						"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
						" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
						"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
						"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
						" by any recipient as a result of this e-mail.<br/><br/>" +
						" *******************************************************************<br/>"; 
				        if(!mail.equals("")){
				        List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1,ccMailIds,bCcMailIds,filterType,projectId);             
				        postMail(mail,ccMailIds,bCcMailIds,emailSubjectTxt,emailMsgTxt,"pms-noida@cdac.in",fileOfAttachment,fileName);
				        }
				        }
	
    public void TransferToMailServerWithoutDBforNewsletter(String mail,String subj,String emailMsgTxt,String ccMailIds,String bccMailIds)throws MessagingException
  	{/*	    

                  String hostname=SMTPPropertiesReader.getValueFromKey("SMTP_HOST_NAME") ;   
                  String username1 =SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_USER");
                  String password1 = SMTPPropertiesReader.getValueFromKey("SMTP_AUTH_PWD");  
                  String from= SMTPPropertiesReader.getValueFromKey("emailFromAddress"); 
                  String ccMail=ccMailIds;
                  SMTP_HOST_NAME = hostname.trim();  
                  SMTP_AUTH_USER = username1.trim();  
                  SMTP_AUTH_PWD=password1.trim() ;      
                  emailFromAddress=from.trim();
                  strCCMail=ccMail.trim();
  	         //System.out.println("SMTP_HOST_NAMEm " + SMTP_HOST_NAME);  
                  emailSubjectTxt = subj;
                  emailMsgTxt = addHeader(emailMsgTxt);
                  emailMsgTxt = emailMsgTxt + 
                "<br/><br/><br/>************************ LEGAL DISCLAIMER *************************<br/><br/>"
            +"This is a system generated information and does not require any signature."
        		+"This E-Mail may contain Confidential and/or legally privileged Information and is meant for the intended"
        		+"recipient(s) only. If you have received this e-mail in error and are not the intended recipient/s, kindly " +
        		"notify us at pms-noida@cdac.in and then delete this e-mail immediately from your system.  " +
        		"Any unauthorized review, use, disclosure, dissemination, forwarding, printing or copying of this email or any" +
        		" action taken in reliance on this e-mail is strictly prohibited and may be unlawful. " +
        		"Internet communications cannot be guaranteed to be timely,secure, error or virus-free. " +
        		"The sender does not accept any liability for any errors, omissions, viruses or computer problems experienced" +
        		" by any recipient as a result of this e-mail.<br/><br/>" +
        		" *******************************************************************<br/>";
       
                  
  		   // postMailBulkDB( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,strCCMail);
  		    
  		  if(mail!=null){
              List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1); 
              postMailBulk( mail, emailSubjectTxt, emailMsgTxt, emailFromAddress,bccMailIds,allrow);
         }
  		  if(mail!=null){
              List<Integer> allrow =  mailhistory(mail, subj, emailMsgTxt,1,ccMailIds,bccMailIds); 
              postMailBulkNew( mail,ccMailIds,bccMailIds, emailSubjectTxt, emailMsgTxt, emailFromAddress,allrow);
         }
         if(ccMailIds!=null){
          List<Integer> ccrow =  mailhistory(ccMailIds, subj, emailMsgTxt,1);
          postMail(mail,emailSubjectTxt,emailMsgTxt,"pms-noida@cdac.in",ccMailIds,ccrow);
         }
  		    
  	*/}
    
    public List<Integer> mailhistory(String mail,String subj,String MsgTxt,int isMailSendFlag)
    {

	
	/*String[] Email = mail.split(",");*/
	
	//String query = "INSERT INTO serb_send_mail_history (NUM_ID,DT_TR_DATE,IS_MAILSEND,NUM_ISVALID,STR_MAIL_BODY,STR_SUBJECT,STR_TO_SEND) VALUES ((select nvl(MAX(num_id), 0)+1 from serb_send_mail_history), sysdate, '0','1', '"+emailMsgTxt+"', '"+subj+"',";
	List<Integer> intList = new ArrayList<Integer>();
	
	//System.out.println("this email length ....."+Email[0]);
				Connection conn = null;
				/*ConnInfo connInfo=new ConnInfo();*/
				try {
					
					try{
					int seqno=0;
					
					conn=daoHelper.getConnection(); 
	        		Statement pst = conn.createStatement();
					pst.executeQuery("SELECT max(NUM_ID)+1 from PMS_SEND_MAIL_HISTORY");
					ResultSet results = pst.getResultSet();
				
					while (results.next()){
						seqno=results.getInt(1);
					}
				
		        		pst.close();
		        	
		        		String query = "INSERT INTO PMS_SEND_MAIL_HISTORY (NUM_ID,DT_TR_DATE,IS_MAILSEND,NUM_ISVALID,STR_MAIL_BODY,STR_SUBJECT,STR_TO_SEND,STR_Cc_SEND,STR_BCc_SEND,STR_TYPE,Num_PROJECT_ID) VALUES ('"+seqno+"', '"+new Date()+"', 1,'1', '"+MsgTxt+"', '"+subj+"', '"+mail+"','','','',0)";
		        		PreparedStatement ps=conn.prepareStatement(query);
		        		//System.out.println("this is final query ..."+query);
		        /*		ps.setString(2, MsgTxt);
		        		ps.setString(3, subj);
		        		ps.setString(4, Email[i]);   
		        		ps.setInt(1, isMailSendFlag);*/
		        		ps.executeUpdate();
		        		intList.add(seqno);
		        		ps.close();
					
					}
					catch (SQLException e) {
						// TODO: handle exception
					}
		        	
					
					
  					
  					
					//pst.executeUpdate("insert into SERB_URL_ACCESS_HISTORY (NUM_SEQNO,NUM_LOGIN_SEQNO,STR_URL_NAME,NUM_ISVALID,DT_TR_DATE) values ( (select nvl(max(NUM_SEQNO),'0')+1  from SERB_URL_ACCESS_HISTORY),'"+seqno+"','"+URL+"','1',now()  )  ");
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					try {
						conn.setAutoCommit(true);
						conn.close();
						
						} catch (SQLException e) {
							e.printStackTrace();
					}
				}
				return intList;
  		}
    
    
    public void postMailBulkNew( String strtoMail,String strccMail,String strbccMail, String subject,
			 String message , String from, List<Integer> allrow) throws MessagingException
{

		
		try {

			boolean debug = true;
			SMTP_HOST_NAME = SMTPPropertiesReader
					.getValueFromKey("SMTP_HOST_NAME");

			// Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.sendpartial", "true");
			props.put("mail.smtp.auth", "true");
			SecurityManager security = System.getSecurityManager();
			// System.out.println("Security Manager" + security);
			// session.getInstance(props,auth)
			Authenticator auth = new SMTPAuthenticator();
			// Session session = Session.getDefaultInstance(props, auth);
			Session session = Session.getInstance(props, auth);

			// Session session= Session.getInstance(props);
			// session.setDebug(debug);

			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");
			// set the from and to address
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
			if(strbccMail!=""){
			Address[] addressBcc = InternetAddress.parse(strbccMail);
			msg.setRecipients(Message.RecipientType.BCC, addressBcc);
			}
			if(strtoMail!=""){
				Address[] addressTo = InternetAddress.parse(strtoMail);
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			}
			if(strccMail!=""){
				Address[] addresscc = InternetAddress.parse(strtoMail);
			msg.setRecipients(Message.RecipientType.CC, addresscc);
			}
			// Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);

		} catch (Exception e) {

			System.out.println("THIS IS BLUK MAIL CATCH");
			//updateValidFlag(allrow);
			System.out.println(e);
		}
	}
    
    private void postMail(String strtoMail,String strccmail,String recipients, String subject, String message,
			String from, File[] fileToBeAttached, String[] fileName) {
		
				try {
 
					boolean debug = true;
					SMTP_HOST_NAME = SMTPPropertiesReader
							.getValueFromKey("SMTP_HOST_NAME");

					// Set the host smtp address
					Properties props = new Properties();
					props.put("mail.smtp.host", SMTP_HOST_NAME);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.sendpartial", "true");
					SecurityManager security = System.getSecurityManager();
					// System.out.println("Security Manager" + security);
					// session.getInstance(props,auth)
					Authenticator auth = new SMTPAuthenticator();
					// Session session = Session.getDefaultInstance(props, auth);
					Session session = Session.getInstance(props, auth);

					// Session session= Session.getInstance(props);
					// session.setDebug(debug);

					// create a message
					MimeMessage msg = new MimeMessage(session);
					msg.setHeader("List-Unsubscribe", "<mailto:pms-noida@cdac.in?subject=Unsubscribe>");

					// set the from and to address
					InternetAddress addressFrom = new InternetAddress(from);
					msg.setFrom(addressFrom);
					if(recipients!=null){
					Address[] addressBcc = InternetAddress.parse(recipients);
					msg.setRecipients(Message.RecipientType.BCC, addressBcc);
					}
					
					
					if(strtoMail!=""){
						Address[] addressTo = InternetAddress.parse(strtoMail);
						msg.setRecipients(Message.RecipientType.TO, addressTo);
						}
						if(strccmail!=""){
							Address[] addresscc = InternetAddress.parse(strtoMail);
						msg.setRecipients(Message.RecipientType.CC, addresscc);
						}

					// Setting the Subject and Content Type
					msg.setSubject(subject);
					
					// Create the message part
			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Now set the actual message
			         messageBodyPart.setContent(message, "text/html;charset=utf-8");

			         // Create a multipar message
			         Multipart multipart = new MimeMultipart("mixed");

			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         for(int i = 0 ; i < fileToBeAttached.length;i++){
				         messageBodyPart = new MimeBodyPart();
				         String filename = fileName[i]+".pdf";
				         System.out.println("Filename inside mail = "+filename);
				         DataSource source = new FileDataSource(fileToBeAttached[i]);
				         messageBodyPart.setDataHandler(new DataHandler(source));
				         messageBodyPart.setFileName(filename);
				         multipart.addBodyPart(messageBodyPart);

			         }
			         // Send the complete message parts
			         msg.setContent(multipart);
					
					Transport.send(msg);

				} catch (Exception e) {
					System.out.println("THIS IS single  MAIL CATCH");
					System.out.println(e);

				}
		
    }

public List<Integer> mailhistoryNew(String mail,String subj,String MsgTxt,int isMailSendFlag,String mailCC,int projectId)
{


/*String[] Email = mail.split(",");*/

//String query = "INSERT INTO serb_send_mail_history (NUM_ID,DT_TR_DATE,IS_MAILSEND,NUM_ISVALID,STR_MAIL_BODY,STR_SUBJECT,STR_TO_SEND) VALUES ((select nvl(MAX(num_id), 0)+1 from serb_send_mail_history), sysdate, '0','1', '"+emailMsgTxt+"', '"+subj+"',";
List<Integer> intList = new ArrayList<Integer>();

//System.out.println("this email length ....."+Email[0]);
			Connection conn = null;
			/*ConnInfo connInfo=new ConnInfo();*/
			try {
				
				try{
				int seqno=0;
				
				conn=daoHelper.getConnection(); 
        		Statement pst = conn.createStatement();
				pst.executeQuery("SELECT max(NUM_ID)+1 from PMS_SEND_MAIL_HISTORY");
				ResultSet results = pst.getResultSet();
			
				while (results.next()){
					seqno=results.getInt(1);
				}
			
	        		pst.close();
	        	
	        		String query = "INSERT INTO PMS_SEND_MAIL_HISTORY (NUM_ID,DT_TR_DATE,IS_MAILSEND,NUM_ISVALID,STR_MAIL_BODY,STR_SUBJECT,STR_TO_SEND,STR_Cc_SEND,STR_BCc_SEND,STR_TYPE,Num_PROJECT_ID) VALUES ('"+seqno+"', '"+new Date()+"', 1,'1', '"+MsgTxt+"', '"+subj+"', '"+mail+"','"+mailCC+"','','',"+projectId+")";
	        		PreparedStatement ps=conn.prepareStatement(query);
	        		//System.out.println("this is final query ..."+query);
	        /*		ps.setString(2, MsgTxt);
	        		ps.setString(3, subj);
	        		ps.setString(4, Email[i]);   
	        		ps.setInt(1, isMailSendFlag);*/
	        		ps.executeUpdate();
	        		intList.add(seqno);
	        		ps.close();
				
				}
				catch (SQLException e) {
					// TODO: handle exception
				}
	        	
				
				
					
					
				//pst.executeUpdate("insert into SERB_URL_ACCESS_HISTORY (NUM_SEQNO,NUM_LOGIN_SEQNO,STR_URL_NAME,NUM_ISVALID,DT_TR_DATE) values ( (select nvl(max(NUM_SEQNO),'0')+1  from SERB_URL_ACCESS_HISTORY),'"+seqno+"','"+URL+"','1',now()  )  ");
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					conn.setAutoCommit(true);
					conn.close();
					
					} catch (SQLException e) {
						e.printStackTrace();
				}
			}
			return intList;
		}
}

