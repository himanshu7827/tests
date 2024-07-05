package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.TaskDocumentDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDocumentDao {

	@Autowired
	DaoHelper daoHelper;
	
	public long saveTaskDocument(TaskDocumentDomain taskDocumentDomain){
		taskDocumentDomain = daoHelper.merge(TaskDocumentDomain.class, taskDocumentDomain);
		return taskDocumentDomain.getNumId();
	}
}