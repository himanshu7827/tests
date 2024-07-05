package in.pms.timesheet.service;

import javax.mail.MessagingException;

import in.pms.timesheet.dao.TSMailDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimesheetMailService {
	
	@Autowired
	TSMailDao mailDao;
	
	public void sendingTimesheetMail() throws MessagingException{
		mailDao.sendingTimesheetMail();
		return;
	}
}
