package in.pms.global.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface DataEncoderService
{
	@Transactional(propagation=Propagation.REQUIRED)
	public String encode(String strString_p);
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String decode(String strString_p);
	
	

}