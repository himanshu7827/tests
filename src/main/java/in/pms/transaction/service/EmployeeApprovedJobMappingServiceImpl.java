package in.pms.transaction.service;

import in.pms.global.util.DateUtils;
import in.pms.transaction.dao.EmployeeApprovedJobMappingDao;
import in.pms.transaction.model.EmployeeApprovedJobMappingModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeApprovedJobMappingServiceImpl implements EmployeeApprovedJobMappingService {

	@Autowired
	EmployeeApprovedJobMappingDao employeeApprovedJobMappingDao;
	
	@Override
	public void getApprovedJob(EmployeeApprovedJobMappingModel model){}
}
