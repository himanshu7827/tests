package in.pms.transaction.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.CurrencyUtils;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.master.dao.EmployeeMasterDao;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.EmployeeMasterModel;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.dao.ManpowerUtilizationDao;
import in.pms.transaction.domain.ManpowerUtilization;
import in.pms.transaction.domain.ManpowerUtilizationDetails;
import in.pms.transaction.model.ManpowerUtilizationDetailsModel;
import in.pms.transaction.model.ManpowerUtilizationModel;

@Service
public class ManpowerUtilizationServiceImpl implements ManpowerUtilizationService {

	@Autowired
	ManpowerUtilizationDao manpowerUtilizationDao;

	@Autowired
	EmployeeMasterDao employeeMasterDao;

	@Autowired
	ProjectMasterService projectMasterService;

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	EmployeeMasterService employeeMasterService;

	@Override
	public List<Object[]> getUtilizationForEmployee(long employeeId, int month, int year) {

		String tempDay = DateUtils.getLastDateOfMonth("01/" + month + "/" + year);
		String monthEndDate = tempDay + "/" + month + "/" + year;
		return manpowerUtilizationDao.getUtilizationForEmployee(employeeId, month, year, monthEndDate);
	}

	@Override
	@PreAuthorize("hasAuthority('WRITE_MANPOWER_UTIL_MST')")
	public long mergeManpowerUtilization(ManpowerUtilizationModel manpowerUtilizationModel) {
		ManpowerUtilization manpowerUtilization = convertUtilizationModelToDomain(manpowerUtilizationModel);
		manpowerUtilization = manpowerUtilizationDao.mergeManpowerUtilization(manpowerUtilization);
		return manpowerUtilization.getNumId();
	}

	public ManpowerUtilization convertUtilizationModelToDomain(ManpowerUtilizationModel utilizationModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		double totalSalaryCost = 0;
		List<ManpowerUtilizationDetails> utilizationDetailsList = new ArrayList<ManpowerUtilizationDetails>();
		ManpowerUtilization manpowerUtilization = new ManpowerUtilization();

		if (utilizationModel.getNumId() != 0) {
			manpowerUtilization = manpowerUtilizationDao.getManpowerUtilization(utilizationModel.getNumId());
		} else {
			EmployeeMasterDomain employeeMasterDomain = employeeMasterDao
					.getEmployeeDetails(utilizationModel.getEmployeeId());
			manpowerUtilization.setEmployeeMasterDomain(employeeMasterDomain);
		}

		manpowerUtilization.setTotalSalaryBySystem(utilizationModel.getSalaryBySystem());
		manpowerUtilization.setTotalSalaryByAuthority(utilizationModel.getSalaryByAuthority());
		manpowerUtilization.setMonth(utilizationModel.getMonth());
		manpowerUtilization.setYear(utilizationModel.getYear());
		manpowerUtilization.setDtTrDate(new Date());
		manpowerUtilization.setNumIsValid(1);
		manpowerUtilization.setNumTrUserId(userInfo.getEmployeeId());

		List<ManpowerUtilizationDetails> manpowerUtilizationDetailsList = manpowerUtilization.getUtilizationDetails();

		List<ManpowerUtilizationDetailsModel> utilizationDetailModels = utilizationModel.getUtilizationDetails();

		for (int i = 0; i < utilizationDetailModels.size(); i++) {
			ManpowerUtilizationDetailsModel detailsModel = utilizationDetailModels.get(i);
			ManpowerUtilizationDetails manpowerUtilizationDetails = null;
			if (null != manpowerUtilizationDetailsList) {
				manpowerUtilizationDetails = checkUtilizationForProject(manpowerUtilizationDetailsList,
						detailsModel.getProjectId());
			}

			if (null == manpowerUtilizationDetails) {
				manpowerUtilizationDetails = new ManpowerUtilizationDetails();
				manpowerUtilizationDetails.setProjectMasterDomain(
						projectMasterService.getProjectMasterDataById(detailsModel.getProjectId()));
			}
			manpowerUtilizationDetails.setUtilization(detailsModel.getUtilization());
			manpowerUtilizationDetails.setNumIsValid(1);
			manpowerUtilizationDetails.setSalaryInProject(detailsModel.getSalaryInProject());
			manpowerUtilizationDetails.setDtTrDate(new Date());
			manpowerUtilizationDetails.setManpowerUtilization(manpowerUtilization);
			manpowerUtilizationDetails.setNumTrUserId(userInfo.getEmployeeId());
			totalSalaryCost += detailsModel.getSalaryInProject();
			utilizationDetailsList.add(manpowerUtilizationDetails);
		}

		manpowerUtilization.setTotalSalaryCost(totalSalaryCost);
		manpowerUtilization.setUtilizationDetails(utilizationDetailsList);

		return manpowerUtilization;
	}

	private static ManpowerUtilizationDetails checkUtilizationForProject(
			List<ManpowerUtilizationDetails> utilizationDetails, long projectId) {
		ManpowerUtilizationDetails utilization = utilizationDetails.stream()
				.filter(x -> projectId == x.getProjectMasterDomain().getNumId()).findAny().orElse(null);

		return utilization;

	}

	@Override
	@PreAuthorize("hasAuthority('READ_MANPOWER_UTIL_MST')")
	public void getUtilAccess() {

	}

	@Override
	/*
	 * public List<Object[]> getManpowerUtilization(String projectId, int month,
	 * int year) { return
	 * manpowerUtilizationDao.getManpowerUtilization(projectId, month, year); }
	 */
	public List<ManpowerUtilizationModel> getManpowerUtilization(long projectId, int month, int year) {
		List<Object[]> List = manpowerUtilizationDao.getManpowerUtilization(projectId, month, year);
		List<ManpowerUtilizationModel> dataList = new ArrayList<ManpowerUtilizationModel>();
		// System.out.println(List.size());
		for (int i = 0; i < List.size(); i++) {
			Object[] obj = List.get(i);
			float utilization = (Float) obj[0];
			// double salaryCostProject = (Double) obj[1];
			String employeeName = (String) obj[2];
			// double salaryByAuthority = (Double) obj[3];
			// double salaryBySystem = (Double) obj[4];
			int utilizationMonth = (Integer) obj[5];
			int utilizationYear = (Integer) obj[6];
			long projectId1 = (Long) obj[7];

			String strSalaryCostProject = CurrencyUtils.convertToINR(obj[1]);
			String strSalaryByAuthority = CurrencyUtils.convertToINR(obj[3]);
			String strSalaryBySystem = CurrencyUtils.convertToINR(obj[4]);
			ManpowerUtilizationModel manpowerUtilizationModel = new ManpowerUtilizationModel();
			manpowerUtilizationModel.setUtilization(utilization);
			manpowerUtilizationModel.setStrSalaryCostProject(strSalaryCostProject);
			manpowerUtilizationModel.setEmployeeName(employeeName);
			manpowerUtilizationModel.setStrSalaryByAuthority(strSalaryByAuthority);
			manpowerUtilizationModel.setStrSalaryBySystem(strSalaryBySystem);
			manpowerUtilizationModel.setMonth(utilizationMonth);
			manpowerUtilizationModel.setYear(utilizationYear);

			manpowerUtilizationModel.setEncProjectId(encryptionService.encrypt("" + projectId1));
			dataList.add(manpowerUtilizationModel);
		}
		return dataList;
	}

	@Override
	public ManpowerUtilization saveManpowerUtilizationData(ManpowerUtilization manpowerUtilization) {
		return manpowerUtilizationDao.mergeManpowerUtilization(manpowerUtilization);
	}

	@Override
	public List<String> saveUtilizationDetails(ManpowerUtilizationModel manpowerUtilizationModel) {
		List<String> responseList = new ArrayList<String>();
		List<String> empIdList = new ArrayList<String>();
		Map<Integer,String> projectIdMap = new HashMap<Integer, String>();
		Map<String, List<ManpowerUtilizationDetails>> map = new HashMap<String, List<ManpowerUtilizationDetails>>();

		if (!manpowerUtilizationModel.getUtilizationDocumentFile().isEmpty()) {
			List<ProjectMasterModel> allProjects = projectMasterService.getAllActiveProjectMasterData();
			List<EmployeeMasterModel> allEmployees = employeeMasterService.getAllEmployeeMasterDomain();
			try {
				InputStream inputStream = manpowerUtilizationModel.getUtilizationDocumentFile().getInputStream();
				XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

				Sheet sheet = workbook.getSheetAt(0);
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();

				UserInfo currentUser = (UserInfo) auth.getPrincipal();
				
				int month=manpowerUtilizationModel.getMonth();
				int year=manpowerUtilizationModel.getYear();
				int saveCount = 0;
				
				for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
					String strEmployeeId = "0";
					
					
					try {

						Row row = sheet.getRow(i);
						
						if(i==3){
							Iterator<Cell> cellIterator = row.cellIterator();
				            while (cellIterator.hasNext()) {
				                Cell cell = cellIterator.next();
				                if(cell.getColumnIndex()>=7)
				                {
				                	int columnIndex=cell.getColumnIndex();
				                	Cell projectIdCell=row.getCell(columnIndex);
				                	projectIdCell.setCellType(Cell.CELL_TYPE_STRING);
									String strProjectId = cell.getStringCellValue().trim();
									//long projectId=Long.parseLong(strProjectId);
				                	projectIdMap.put(columnIndex, strProjectId);	
				                }
				            }
			           	}else{
			           		List<ManpowerUtilizationDetails> finalUtilizationForEmployee = new ArrayList<ManpowerUtilizationDetails>();
			           		List<ManpowerUtilizationDetails> previousUtilizations = new ArrayList<ManpowerUtilizationDetails>();
			           		double totalSalaryCost = 0.0;
							float totalUtilization = 0.0f;

							
						Cell cell = row.getCell(0);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						strEmployeeId = cell.getStringCellValue().trim();
						if(StringUtils.isNumeric(strEmployeeId)){
						long employeeId = Long.parseLong(strEmployeeId);
						
						ManpowerUtilization manpowerUtilization = manpowerUtilizationDao
								.getManpowerUtilizationByYrAndMonth(month, year, employeeId);
						
						EmployeeMasterModel employeeMasterModel = allEmployees.stream() 
								.filter(x -> employeeId == x.getNumId()).findAny().orElse(null);
						
						
					
						Cell salaryCell = row.getCell(4);
						salaryCell.setCellType(Cell.CELL_TYPE_STRING);
						String strSalary = salaryCell.getStringCellValue().trim();
						strSalary = strSalary.replaceAll(",", "");
						
						double salary = Double.parseDouble(strSalary);
						
						if (null != employeeMasterModel) {
							EmployeeMasterDomain employeeMasterDomain = new EmployeeMasterDomain();
							employeeMasterDomain.setNumId(employeeId);
							if(null != manpowerUtilization){
				           		List<ManpowerUtilizationDetails>  list= manpowerUtilization.getUtilizationDetails();
				           		for(int j=0;j<list.size();j++){
				           		ManpowerUtilizationDetails manpowerUtilizationDetails=list.get(j);
				           		manpowerUtilizationDetails.setNumIsValid(2);
				           		manpowerUtilizationDetails.setDtTrDate(new Date());
				           		previousUtilizations.add(manpowerUtilizationDetails);
				           		}
								
							}else{
								manpowerUtilization= new ManpowerUtilization();																
								manpowerUtilization.setNumIsValid(1);								
								manpowerUtilization.setMonth(month);
								manpowerUtilization.setYear(year);
								manpowerUtilization.setEmployeeMasterDomain(employeeMasterDomain);								
							}
							
							
							
							
							for (Map.Entry<Integer, String> entry : projectIdMap.entrySet()) {
								Integer column = entry.getKey();
								String projectId = entry.getValue();
								
								Cell cell2 = row.getCell(column, Row.RETURN_BLANK_AS_NULL);
								String utilization = "0";
								if(null != cell2){
									cell2.setCellType(Cell.CELL_TYPE_STRING);
									 utilization = cell2.getStringCellValue().trim();	
								}
										
								
								
								if (!empIdList.contains(strEmployeeId)) {
									if(null != utilization && !utilization.equals("0")){
										
										boolean flag = true;
										ManpowerUtilizationDetails manpowerUtilizationDetails = new ManpowerUtilizationDetails();
									
										float numUtilization = Float.parseFloat(utilization);
										
										ProjectMasterModel projectMasterModel = allProjects.stream() 
												.filter(x -> projectId.equals(x.getProjectRefNo())).findAny().orElse(null);

										if (null != projectMasterModel) {
											ProjectMasterDomain projectMasterDomain = new ProjectMasterDomain();
											projectMasterDomain.setNumId(projectMasterModel.getNumId());
											manpowerUtilizationDetails.setProjectMasterDomain(projectMasterDomain);
										} else {
											flag = false;
											empIdList.add(strEmployeeId);
											responseList.add("Record for Row no " + i
													+ " does not saved because Project Reference No  "+projectId+" does not exist");
										}

										manpowerUtilizationDetails.setUtilization(numUtilization);
										manpowerUtilizationDetails.setDtTrDate(new Date());
										manpowerUtilizationDetails.setNumTrUserId(currentUser.getEmployeeId());
										manpowerUtilizationDetails.setNumIsValid(1);
									
										manpowerUtilizationDetails.setManpowerUtilization(manpowerUtilization);
										 

										if(flag){
											finalUtilizationForEmployee.add(manpowerUtilizationDetails);
											 double salaryInProject=salary*(numUtilization*0.01);
											  manpowerUtilizationDetails.setSalaryInProject(salaryInProject);
											manpowerUtilization.setTotalSalaryByAuthority(salary);
											manpowerUtilization.setTotalSalaryBySystem(salary);
											manpowerUtilization.setNumTrUserId(currentUser.getEmployeeId());
											manpowerUtilization.setDtTrDate(new Date());
											  totalSalaryCost+=salaryInProject;
											  totalUtilization+=numUtilization;
										}

									}
								}
								}
							if(totalUtilization<= 100){
								finalUtilizationForEmployee.addAll(previousUtilizations);
								manpowerUtilization.setUtilizationDetails(finalUtilizationForEmployee);
								manpowerUtilization.setTotalSalaryCost(totalSalaryCost);
								manpowerUtilizationDao.mergeManpowerUtilization(manpowerUtilization);
								saveCount++;
								
							} else {
								responseList.add("Total Utilization for employee Id " + strEmployeeId + " for " + month + "/" + year
										+ " is " + totalUtilization + " which can't be more than 100");
							}		
							
						}else {
							empIdList.add(strEmployeeId);
							responseList.add("Employee with Id: " + strEmployeeId + " is not registered");
						}

			           	}
							 
			           	}		
					
						

					} catch (Exception e1) {
						responseList.add("Record not saved for employeeId:" + strEmployeeId
								+ "\t because issue at Row no. \t" + i + " " + e1.getMessage());
						empIdList.add(strEmployeeId);

					}
				}
				
				
				responseList.add(saveCount+ " Record(s) saved/updated successfully");
			} catch (IOException e) {
					e.printStackTrace();
				}		
			}else {
				responseList.add("Empty File");
			}		
				return responseList;
			}
	}
