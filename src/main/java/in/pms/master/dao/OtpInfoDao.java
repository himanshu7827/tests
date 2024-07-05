package in.pms.master.dao;


import in.pms.global.dao.DaoHelper;
import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.OtpInfo;
//import in.pms.master.domain.StateMasterDomain;
import in.pms.master.model.OtpInfoModel;
import in.pms.transaction.domain.ApprovedJobDomain;
import in.pms.transaction.model.ApprovedJobModel;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import com.ibm.icu.impl.duration.TimeUnit;

@Repository
public class OtpInfoDao 
{
	@Autowired
	DaoHelper daoHelper;
    public long addOTP(OtpInfo otpInfoDomain){
		
	otpInfoDomain =daoHelper.merge(OtpInfo.class, otpInfoDomain);	
	System.out.println("Values saved");
			return otpInfoDomain.getOid();
		}
    
    public void checkCondition(String stremail)
    {
    	String query =  "from OtpInfo where userName = ('"+stremail+"') AND numIsValid=1";
    	List<OtpInfo> otpInfoDomainList1 = daoHelper.findByQuery(query);
    	if(otpInfoDomainList1.size()>0)
    	{
    		otpInfoDomainList1.get(0).setNumIsValid(0);
        	System.out.println(otpInfoDomainList1);
    		OtpInfo otpInfoDomain = new OtpInfo();
    		otpInfoDomain =daoHelper.merge(OtpInfo.class, otpInfoDomainList1.get(0));
    		System.out.println("Done!!");
    	}
    	
    }

    @Modifying
	@Transactional
	public boolean getOtpInfo(String otpenter, String stremail) {
		
		Calendar currentTimeNow = Calendar.getInstance();
		System.out.println("Current time now : " + currentTimeNow.getTime());
		
	String query =  "from OtpInfo where oneTimePassword = ('"+otpenter+"')  AND otpRequestedTime > ('"+currentTimeNow.getTime()+"') AND numIsValid=1";
	List<OtpInfo> otpInfoDomainList = daoHelper.findByQuery(query);
	if(otpInfoDomainList.size()>0){
		System.out.println("OTP Verified");
	
		otpInfoDomainList.get(0).setNumIsValid(0);
		OtpInfo otpInfoDomain = new OtpInfo();
		otpInfoDomain =daoHelper.merge(OtpInfo.class, otpInfoDomainList.get(0));
		System.out.println("NumIsValid Toggled");
		//String query1 = "UPDATE OtpInfo SET numIsValid=1 WHERE userName = ('"+stremail+"') AND otpType = 'Login'";
		//String query1 = "from OtpInfo WHERE userName = ('"+stremail+"') AND otpType = 'Login' AND numIsValid=1";
		//String query1 = "from OtpInfo WHERE numIsValid=1";
		//List<OtpInfo> otpInfoDomainList1 = daoHelper.findByQuery(query1);
		//System.out.println(otpInfoDomainList1);
		//System.out.println(otpInfoDomainList1.get(1));
		//OtpInfo otpInfoDomain = new OtpInfo();
		//otpInfoDomain.setNumIsValid(0);
		//GenOTP(otpInfoDomain);
		//System.out.println("NumIsValid Toggled");
		//Session currentSession = sessionFactory.getCurrentSession();
		//Query query1 = currentSession.createNativeQuery("update OtpInfo set numIsValid=? where userName=?");
		//query1.setParameter(1,0);
		//query1.setParameter(2,stremail);
		//query1.setParameter(3,Login);
		return true;
		
	}
	String query1 =  "from OtpInfo where oneTimePassword = ('"+otpenter+"')  AND otpRequestedTime > ('"+currentTimeNow.getTime()+"') AND numIsValid=0";
	List<OtpInfo> otpInfoDomainList1 = daoHelper.findByQuery(query);
	System.out.println("OTP Expired!! Try Again!!");
	if(otpInfoDomainList1.size()>0)
	{
		System.out.println("OTP Expired!! Try Again!!");
		return false;
	}
	return false;
}
	
	public long GenOTP(OtpInfo otpInfoDomain){
		
		otpInfoDomain =daoHelper.merge(OtpInfo.class, otpInfoDomain);	
		System.out.println("Values saved");
				return otpInfoDomain.getOid();
			}
		
	
	public List<OtpInfo> getAllGenOtpDomain(){
		String query = "from OtpInfo where oid<>0 and numIsValid<>0";
		return  daoHelper.findByQuery(query);	
	}
	
	public boolean findByEmailOTP(String email){
		OtpInfo otpInfoDomain = null;
		System.out.println("checking email id");
		String query = "from EmployeeMasterDomain a join fetch a.groupMasterDomain join fetch a.empTypeMasterDomain join fetch a.designationMaster where a.numIsValid=1 and a.employmentStatus !='Relieved' and a.officeEmail='"+email+"'";
		//String query = "from EmployeeMasterDomain officeEmail='"+email+"'";
		List<EmployeeMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return true;			
		}
		else 
			return false;		
	}
	
	public OtpInfo findOTP(String email, String otp)
	{
		OtpInfo otpInfoDomain1 = null;
		Calendar currentTimeNow = Calendar.getInstance();
		System.out.println("Current time now : " + currentTimeNow.getTime());
		String query="from OtpInfo where userName='"+email+"' AND oneTimePassword='"+otp+"' AND otpRequestedTime > ('"+currentTimeNow.getTime()+"') AND numIsValid=1";
		List<OtpInfo> list = daoHelper.findByQuery(query);	
		if(list.size()>0)
		{
			System.out.println("OTP Verified");
			list.get(0).setNumIsValid(0);
			OtpInfo otpInfoDomain = new OtpInfo();
			otpInfoDomain =daoHelper.merge(OtpInfo.class, list.get(0));
			System.out.println("NumIsValid Toggled");
			otpInfoDomain1 = list.get(0);
		}
		//	String query1 =  "from OtpInfo where userName = ('"+email+"')  AND otpRequestedTime > ('"+currentTimeNow.getTime()+"') AND numIsValid=0";
			//List<OtpInfo> otpInfoDomainList1 = daoHelper.findByQuery(query);
			//System.out.println("OTP Expired!! Try Again!!");
			//if(otpInfoDomainList1.size()>0)
		//	{
			//	System.out.println("OTP Expired!! Try Again!!");
			//}
		
		
		return otpInfoDomain1;
	}
	
	
	public boolean secOTPCheck(String otp){
		String query = "from OtpInfo where oneTimePassword='"+otp+"' AND numIsValid=1";
		//String query = "from EmployeeMasterDomain officeEmail='"+email+"'";
		List<OtpInfo> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return true;			
		}
		else 
			return false;		
	}
	
}
