package in.pms.login.service;

import in.pms.login.dao.LoginHistoryDao;
import in.pms.login.domain.LoginHistoryDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

	@Autowired
	LoginHistoryDao loginHistoryDao;
	
	@Override
	public LoginHistoryDomain saveLoginHistoryDomain(LoginHistoryDomain loginHistoryDomain) {		
		return loginHistoryDao.saveLoginHistoryDomain(loginHistoryDomain);
	}
	
	@Override
	public 	LoginHistoryDomain getLoginHistoryDomain(String sessionId){
		return loginHistoryDao.getLoginHistoryDomain(sessionId);
	}
	
	@Override
	public LoginHistoryDomain getLastLoginDetails(long  userId){
		return loginHistoryDao.getLastLoginDetails(userId);
	}

}
