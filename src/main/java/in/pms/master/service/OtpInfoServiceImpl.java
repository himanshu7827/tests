package in.pms.master.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.sql.Time;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DecimalFormat;

import in.pms.global.dao.DaoHelper;
import in.pms.global.service.EncryptionService;
import in.pms.login.util.UserInfo;
import in.pms.mail.dao.SendMail;
import in.pms.master.dao.OtpInfoDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.ForgotPassword;
import in.pms.master.domain.OtpInfo;
//import in.pms.master.domain.StateMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.OtpInfoModel;
//import in.pms.master.model.StateMasterModel;
import in.pms.transaction.model.BudgetHeadMasterForm;

@Service
@Transactional
public class OtpInfoServiceImpl implements OtpInfoService {

	@Autowired
	OtpInfoDao otpInfoDao;
	
	@Autowired
	EncryptionService encryptionService;
	
	private @Autowired AutowireCapableBeanFactory beanFactory;

	
	  @Override public long addOTP(OtpInfoModel otpInfoModel) 
	  { 
		  OtpInfo otpInfoDomain = convertOtpInfoModelToDomain(otpInfoModel); 
		  System.out.println("Service");
		  
		  return otpInfoDao.addOTP(otpInfoDomain); 
		  }
	
	
	@Override
	
	public long GenOTP(OtpInfoModel otpInfoModel) {
		OtpInfo otpInfoDomain = convertOtpInfoModelToDomain(otpInfoModel);
		System.out.println("Service");
		return otpInfoDao.GenOTP(otpInfoDomain);
	}

	public OtpInfo convertOtpInfoModelToDomain(OtpInfoModel otpInfoModel) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//UserInfo userInfo = (UserInfo) authentication.getPrincipal();

		
		
		OtpInfo otpInfoDomain = new OtpInfo();
		otpInfoDomain.setDtTrDate(new Date());
		String OTP = new DecimalFormat("000000").format(new Random().nextInt(999999));
		System.out.println(OTP);
		otpInfoDomain.setOid(otpInfoModel.getOid());
		otpInfoDomain.setUserName(otpInfoModel.getStremail());
		otpInfoDomain.setOneTimePassword(OTP);
		otpInfoDomain.setCaptcha(otpInfoModel.getCaptcha());
		otpInfoDomain.setNumIsValid(0);
		//otpInfoDomain.setValidUpto(new Date());
		//Calendar cal=Calendar.getInstance();
		//Date date=cal.getTime();
		otpInfoDomain.setOtpRequestedTime((java.sql.Date) new Date());
		return otpInfoDomain;
	}

	public boolean checkSameOtp(OtpInfoModel otpInfoModel) {
		
		return otpInfoDao.getOtpInfo(otpInfoModel.getOtpenter(),otpInfoModel.getStremail());
		
	}

	
	@Override
	public List<OtpInfoModel> getAllGenOtpDomain(){
		return convertOtpGenDomainToModelList(otpInfoDao.getAllGenOtpDomain());
	}
	
	public List<OtpInfoModel> convertOtpGenDomainToModelList(List<OtpInfo> genOtpList){
		List<OtpInfoModel> list = new ArrayList<OtpInfoModel>();
			for(int i=0;i<genOtpList.size();i++){
				OtpInfo otpInfoDomain = genOtpList.get(i);
				OtpInfoModel otpInfoModel = new OtpInfoModel();
				
				otpInfoModel.setOid(otpInfoDomain.getOid());
				if(otpInfoDomain.getNumIsValid() ==1){
					otpInfoModel.setNumIsValid(true);
				}else{
					otpInfoModel.setNumIsValid(false);
				}
			
		
				otpInfoModel.setStremail(otpInfoDomain.getUserName());
				otpInfoModel.setOtpenter(otpInfoDomain.getOneTimePassword());
				otpInfoModel.setCaptcha(otpInfoDomain.getCaptcha());
				//otpInfoModel.setValidUpto(otpInfoDomain.getOtpRequestedTime());
				list.add(otpInfoModel);
	}
		return list;
	}

	@Override
	public boolean findByEmailOTP(String email) {
		return otpInfoDao.findByEmailOTP(email);
		
	}
	
	  public boolean sendOTPMail(OtpInfoModel otpInfoModel) 
	  {
		boolean flag=false;
		OtpInfo registration1=new OtpInfo();
		registration1.setUserName(otpInfoModel.getStremail());
		registration1.setCaptcha(otpInfoModel.getCaptcha());
		otpInfoDao.checkCondition(otpInfoModel.getStremail());
		try
		{
		 flag=sendMailtoUser1(registration1);
		
		}
		catch(Exception e)
		{
			//
		}
		
		 return flag;
	}

	private boolean sendMailtoUser1(OtpInfo registration1) 
	{
		boolean flag = false;
		try {
			System.out.println("In send mail to user");

			final boolean user1 = findByEmailOTP(registration1.getUserName());
			//String password="";
			String OTP = new DecimalFormat("000000").format(new Random().nextInt(999999));
			 System.out.println("OTP generated"); 
			OtpInfo otpInfoDomain = new OtpInfo();
			
			//otpInfoDomain.setOid(registration1.getOid());
			otpInfoDomain.setUserName(registration1.getUserName());
			otpInfoDomain.setOneTimePassword(OTP);
			otpInfoDomain.setCaptcha(registration1.getCaptcha());
			otpInfoDomain.setOtpType("Login");
			otpInfoDomain.setNumIsValid(1);
			otpInfoDomain.setDtTrDate(new Date());
			System.out.println(new Date());
			
			
			
			Calendar date = Calendar.getInstance();
			System.out.println("Current Date and Time : " + date.getTime());
			long timeInSecs = date.getTimeInMillis();
			Date afterAdding10Mins = new Date(timeInSecs + (10 * 60 * 1000));
			otpInfoDomain.setOtpRequestedTime(afterAdding10Mins);
			System.out.println("check");
			System.out.println("After adding 10 mins : " + afterAdding10Mins);
			
			
			System.out.println("Sending.....");
			String strSubject="One Time Password";
			String strMsgText="Dear User,<br><br> This is your OTP for PMS login.<br>Kindly login wth below OTP. It will be valid for 10 minutes<br><br> ";

			strMsgText+="<b>Username:"+registration1.getUserName()+"</b><br>";
			strMsgText+="<b>Password:"+OTP+"</b><br>";

			String mailContent=strSubject+strMsgText;
			otpInfoDomain.setMailContent(mailContent);
			
			System.out.println("here!!");
			OtpInfoModel otpInfoModel = new OtpInfoModel();
			System.out.println(otpInfoModel.getStremail());
			
			otpInfoDao.GenOTP(otpInfoDomain);
			
			//otpInfoService.GenOTP(otpInfoModel);
			SendMail mailobj=new SendMail();
			SendMail smail = new SendMail();
			beanFactory.autowireBean(smail);
			beanFactory.autowireBean(mailobj);
			smail.TransferToMailServer(registration1.getUserName(), strSubject,strMsgText);
			//flag = mailobj.TransferToMailServerBoolean(registration1.getUserName(), strSubject, strMsgText);
			System.out.println("Email sent");
			flag=true;
			//otpInfoDomain.setOtpRequestedTime(new Time());
		}catch(Exception e){}

		return flag;	
	}
	
	public OtpInfo findOTP(String email, String otp)
	{
		return otpInfoDao.findOTP(email,otp);
	}
	
	public boolean secOTPCheck(String otp)
	{
		return otpInfoDao.secOTPCheck(otp);
	}

	
}