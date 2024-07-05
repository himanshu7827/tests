package in.pms.master.service;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.OtpInfoModel;
//import in.pms.master.model.StateMasterModel;
import in.pms.transaction.model.BudgetHeadMasterForm;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.OtpInfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

//import java.util.List;

import org.springframework.transaction.annotation.Transactional;
@Service
public interface OtpInfoService 
{
	@Transactional
	public long addOTP(OtpInfoModel otpInfoModel);
	public boolean checkSameOtp(OtpInfoModel otpInfoModel);
	public List<OtpInfoModel> getAllGenOtpDomain();
	public long GenOTP(OtpInfoModel otpInfoModel); 
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
	public boolean findByEmailOTP(String email);
	@Transactional
	public boolean sendOTPMail(OtpInfoModel otpInfoModel);
	public OtpInfo findOTP(String email,String otp);
	public boolean secOTPCheck(String otp);
}
