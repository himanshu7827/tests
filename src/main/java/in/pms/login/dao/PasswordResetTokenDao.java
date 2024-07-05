package in.pms.login.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import in.pms.global.dao.DaoHelper;
import in.pms.login.domain.PasswordResetToken;
import in.pms.master.domain.EmployeeMasterDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetTokenDao {
	
	@Autowired
	DaoHelper daoHelper;
	 

	public PasswordResetToken findByToken(String token){
		PasswordResetToken passwordResetToken = null;
		String query = "from PasswordResetToken where token="+token;
		List<PasswordResetToken> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			passwordResetToken = list.get(0);
		}
		return passwordResetToken;
	}

    public PasswordResetToken findByUser(EmployeeMasterDomain user){
    	PasswordResetToken passwordResetToken = null;
    	String query = "from PasswordResetToken where employee.numId="+user.getNumId();
    	List<PasswordResetToken> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			passwordResetToken = list.get(0);
		}
		return passwordResetToken;
    }

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now){
    	String query = "from PasswordResetToken where expiryDate<"+now;    	 
    	return  listToStream(daoHelper.findByQuery(query));
    }

    void deleteByExpiryDateLessThan(Date now){
    	
    }

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now){
    	
    }
	
    public PasswordResetToken save(PasswordResetToken passwordResetToken){
    	return daoHelper.merge(PasswordResetToken.class, passwordResetToken);
    }

	public void delete(PasswordResetToken passwordToken) {
		
		
	}
	
	// Generic function to convert a list to stream
		private static <T> Stream<T> listToStream (List<T> list)
		{
			return list.stream();
		}
}
