package in.pms.mail.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.mail.domain.SendMailHistory;
import in.pms.transaction.domain.Application;

@Repository
public class SendMailDao {

	@Autowired
	DaoHelper daoHelper;
	
	public SendMailHistory merge(SendMailHistory obj){
		return daoHelper.merge(SendMailHistory.class,obj);
	}

	public SendMailHistory getSendMailHistory(int id) {
		return daoHelper.findById(SendMailHistory.class,id);

	}
	
	
}
