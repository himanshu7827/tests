package in.pms.login.service;

import in.pms.login.domain.LoginHistoryDomain;

import org.springframework.transaction.annotation.Transactional;

public interface LoginHistoryService {
	
	@Transactional
	LoginHistoryDomain saveLoginHistoryDomain(LoginHistoryDomain loginHistoryDomain);
	
	LoginHistoryDomain getLoginHistoryDomain(String sessionId);
	
	public LoginHistoryDomain getLastLoginDetails(long  userId);

}
