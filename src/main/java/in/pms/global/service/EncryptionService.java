package in.pms.global.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface EncryptionService {

	@Transactional(propagation=Propagation.REQUIRED)
	public String encrypt(String stString_p);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String dcrypt(String stEncString_p);
	
}
