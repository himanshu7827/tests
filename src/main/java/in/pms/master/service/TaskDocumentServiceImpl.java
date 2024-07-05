package in.pms.master.service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.TaskDocumentDao;
import in.pms.master.domain.TaskDocumentDomain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class TaskDocumentServiceImpl implements TaskDocumentService{
	
	@Autowired
	TaskDocumentDao taskDocumentDao;
	
	@Override
        public long saveTaskDocument(TaskDocumentDomain taskDocumentDomain){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		taskDocumentDomain.setNumTrUserId(userInfo.getEmployeeId());
		taskDocumentDomain.setDtTrDate(new Date());
		return taskDocumentDao.saveTaskDocument(taskDocumentDomain);
	}
	
}
