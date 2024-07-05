package in.pms.master.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.service.FileUploadService;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.dao.EmployeeSalaryDao;
import in.pms.master.domain.EmployeeSalaryDomain;
import in.pms.master.model.EmployeeSalaryModel;
@Service
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService{
	
	@Autowired
	EmployeeSalaryDao employeeSalaryDao;
	@Autowired
	EmployeeMasterDao employeeMasterDao;
	
	@Autowired
	EmployeeSalaryService employeeSalaryService;
	@Autowired
	FileUploadService fileUploadService;
	
	@Override
	@PreAuthorize("hasAuthority('READ_EMP_SALARY_MST')")
	public List<EmployeeSalaryModel> getAllEmployeeSalData(long empId){
		return convertEmployeeSalDomainToModelList(employeeSalaryDao.getAllEmployeeSalData(empId));			
	}
	
	public List<EmployeeSalaryModel> convertEmployeeSalDomainToModelList(List<EmployeeSalaryDomain> empSalDataList){
		List<EmployeeSalaryModel> list =new ArrayList< EmployeeSalaryModel>();
				for(int i=0;i<empSalDataList.size();i++){
				EmployeeSalaryDomain employeeSalaryDomain = empSalDataList.get(i);
				EmployeeSalaryModel employeeSalaryModel = new EmployeeSalaryModel();
				employeeSalaryModel.setEmployeeId(employeeSalaryDomain.getEmployeeId());
				employeeSalaryModel.setSalary(employeeSalaryDomain.getSalary());
				if(null!=employeeSalaryDomain.getStartDate()){
					employeeSalaryModel.setStartDate(DateUtils.dateToString(employeeSalaryDomain.getStartDate()));

				}
				if(null!=employeeSalaryDomain.getEndDate()){
					employeeSalaryModel.setEndDate(DateUtils.dateToString(employeeSalaryDomain.getEndDate()));

				}
				employeeSalaryModel.setNumId(employeeSalaryDomain.getNumId());
				if(employeeSalaryDomain.getNumIsValid()==1){
					employeeSalaryModel.setValid(true);
				}else{
					employeeSalaryModel.setValid(false);
				}
				list.add(employeeSalaryModel);
	}
	return list;	
			
	}
	
	@PreAuthorize("hasAuthority('WRITE_EMP_SALARY_MST')")
	public long saveEmployeeSalData(EmployeeSalaryModel employeeSalaryModel){
		long result =0;
		List<EmployeeSalaryDomain> list1=employeeSalaryDao.getSameRecordData(employeeSalaryModel.getEmployeeId(),employeeSalaryModel.getStartDate(),employeeSalaryModel.getEndDate(),employeeSalaryModel.getNumId());

		 if(list1.size()>1){
			result=-2;
			return result;
		}else if(list1.size() ==1 && list1.get(0).getNumId()!= employeeSalaryModel.getNumId()){
			result=-2;
			return result;
		}
		
		List<EmployeeSalaryDomain> list=employeeSalaryDao.getDatabyEndDate(employeeSalaryModel.getEmployeeId(),employeeSalaryModel.getNumId());
		if(list.size()>=1){	
			result =-1;
			return result;
		}
		
		
		EmployeeSalaryDomain employeeSalaryDomain = convertEmployeeSalModelToDomain(employeeSalaryModel);
		result = employeeSalaryDao.saveUpdateEmployeeSal(employeeSalaryDomain);
		
		return result;

	}
	
	public EmployeeSalaryDomain convertEmployeeSalModelToDomain(EmployeeSalaryModel employeeSalaryModel){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfo currentUser = (UserInfo)auth.getPrincipal();
		EmployeeSalaryDomain employeeSalaryDomain = new EmployeeSalaryDomain();
		if(employeeSalaryModel.getNumId()!=0){		
			employeeSalaryDomain =  employeeSalaryDao.getAllEmployeeSalDataById(employeeSalaryModel.getNumId());
		}
		employeeSalaryDomain.setEmployeeId(employeeSalaryModel.getEmployeeId());
		employeeSalaryDomain.setSalary(employeeSalaryModel.getSalary());
		if(null!=employeeSalaryModel.getStartDate()){
			try {
				employeeSalaryDomain.setStartDate(DateUtils.dateStrToDate(employeeSalaryModel.getStartDate()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		if(null!=employeeSalaryModel.getEndDate() && !employeeSalaryModel.getEndDate().equals("")){
			try {
				employeeSalaryDomain.setEndDate(DateUtils.dateStrToDate(employeeSalaryModel.getEndDate()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}

		}
		employeeSalaryDomain.setDtTrDate(new Date());
		if(employeeSalaryModel.isValid()){
			employeeSalaryDomain.setNumIsValid(1);
			}else{
				employeeSalaryDomain.setNumIsValid(0);

			}
		
		
		employeeSalaryDomain.setNumTrUserId(currentUser.getEmployeeId());		
		return employeeSalaryDomain;
	}
	
	@Override
	public double getEmployeeSalary(long employeeId,int month,int year){
		String startDate = "01/"+month+"/"+year;
		String tempDate = DateUtils.getLastDateOfMonth(startDate);
		String endDate = tempDate+"/"+month+"/"+year;
		return employeeSalaryDao.getEmployeeSalary(employeeId,startDate,endDate);	 
	}
	
	
	@Override
	public List<String> saveEmployeeSalDetails(EmployeeSalaryModel employeeSalaryModel){
        List<String> responseList = new ArrayList<String>();
        List<String> empIdList = new ArrayList<String>();
		if(!employeeSalaryModel.getSalaryDocumentFile().isEmpty()){
			
			try {
				InputStream  inputStream= employeeSalaryModel.getSalaryDocumentFile().getInputStream();
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
				
				  
		        Sheet sheet = workbook.getSheetAt(0);
		        List<EmployeeSalaryDomain> list=new ArrayList<EmployeeSalaryDomain>();

	      
		        		        for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
		        		        	int j=i+1;
		        		        	String strEmployeeId ="0";
		        		        	try{
		        		        		Row row=sheet.getRow(i);			        		        	
			        		        	EmployeeSalaryDomain domain = new EmployeeSalaryDomain();
			        		        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			        		    		UserInfo currentUser = (UserInfo)auth.getPrincipal();
			        		    		Cell cell=row.getCell(0);
			        		    		cell.setCellType(Cell.CELL_TYPE_STRING);
			        		        	strEmployeeId = cell.getStringCellValue().trim();
			        		        	if(!empIdList.contains(strEmployeeId)){
			        		        				        		        	
			        		        	Long empId=Long.parseLong(strEmployeeId);
			        		        	String startDate=row.getCell(2).getStringCellValue().trim();		        					
			        		        	domain.setEmployeeId(empId);
			        					
			        		        	double salary=row.getCell(1).getNumericCellValue();
			        		        	domain.setSalary(salary);
			        		        	try {
			        						domain.setStartDate(DateUtils.dateStrToDate(startDate));
			        					} catch (Exception e1) {
			        						// TODO Auto-generated catch block
			        						e1.printStackTrace();
			        					}
			        		        	
			        		        	
			        		        	domain.setDtTrDate(new Date());
			        		    		domain.setNumTrUserId(currentUser.getEmployeeId());
			        		    		domain.setNumIsValid(1);
			        		    		list.add(domain);
			        		        	}
		        		        	}
		        		        	catch(Exception e1){	
			        					responseList.add("Record not updated for employeeId:"+strEmployeeId+"\tbecause issue at Row no. \t"+j+" "+e1.getMessage());			        					
			        					empIdList.add(strEmployeeId);
		        		        		
		        		        	}
		        		        	
		        		    	
		        		        }

		        		        
		        		        for(int i=0;i<empIdList.size();i++){
		        		        	String tempEmp = empIdList.get(i);
		        		        	if(StringUtils.isNumeric(tempEmp)){
		        		        		long employeeId = Long.parseLong(tempEmp);
		        		        		list.removeIf(salaryMaster -> salaryMaster.getEmployeeId()== employeeId);
		        		        	}
		        		        }
		        		        Collections.sort(list);

	        					for(int j=0;j<list.size();j++){
	        		           EmployeeSalaryDomain employeeSalaryDomain=list.get(j);
	        		           List<EmployeeSalaryDomain> employeeSalaryDomainList =  employeeSalaryDao.getAllEmployeeSalData(employeeSalaryDomain.getEmployeeId());
	        		        	for(int k=0;k<employeeSalaryDomainList.size();k++){
	        		        		EmployeeSalaryDomain employeeSalaryDomainOld=employeeSalaryDomainList.get(k);
	        		        	if(employeeSalaryDomainOld.getEndDate()==null){
	        		        		try {
	        							Date date =employeeSalaryDomain.getStartDate();
	        							Date setEndDate=new DateTime(date).minusDays(1).toDate();
	        							employeeSalaryDomainOld.setEndDate(setEndDate);
	    			        		    employeeSalaryService.saveEmployeeSalaryData(employeeSalaryDomainOld);	
	        						} catch (Exception e) {
	        							e.printStackTrace();
	        						}
	        		        	}
	        		        	
	        		        	}
	        		        	employeeSalaryService.saveEmployeeSalaryData(employeeSalaryDomain);
	        		        	Calendar calendar = GregorianCalendar.getInstance();	
	        		    		String fileName= "SalaryFile"+"_"+calendar.getTimeInMillis();
	        		        	String originalFileName= employeeSalaryModel.getSalaryDocumentFile().getOriginalFilename();
	        		    		String extension = FilenameUtils.getExtension(originalFileName);
	        		    		String tempFileName = fileName +".";
	        		    		fileUploadService.uploadSalaryFile(employeeSalaryModel.getSalaryDocumentFile(),tempFileName+extension);

	        					}
								
															
			
			} catch (IOException e) {				
				e.printStackTrace();
			}
		
			responseList.add("Record saved/updated successfully");
			}
		return responseList;
	}
	
	@Override
	public EmployeeSalaryDomain saveEmployeeSalaryData(EmployeeSalaryDomain employeeSalaryDomain){
		return employeeSalaryDao.saveUpdateEmployeeSalary(employeeSalaryDomain);
	}

}