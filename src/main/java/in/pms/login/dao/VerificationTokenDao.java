package in.pms.login.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.domain.VerificationToken;
import in.pms.master.domain.EmployeeMasterDomain;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationTokenDao {
	
	@Autowired
	DaoHelper daoHelper;

	public VerificationToken findByToken(String token){
		VerificationToken verificationToken = null;
    	
    	String query = "from VerificationToken where token="+token;
    	List<VerificationToken> list = daoHelper.findByQuery(query);
    	if(list.size()>0){
    		verificationToken = list.get(0);    				
    	}
    	return verificationToken;
	}

    public VerificationToken findByUser(EmployeeMasterDomain employeeMasterDomain){
    	
    	return null;
    }

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now){
    	return null;
    }

    void deleteByExpiryDateLessThan(Date now){
    	
    }

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now){
    	
    }
    
    public VerificationToken save(VerificationToken myToken){
    	return daoHelper.merge(VerificationToken.class, myToken);
    }

	public void delete(VerificationToken verificationToken) {
		
	}
    
 
	
}
