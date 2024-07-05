package in.pms.global.service;

import in.pms.global.dao.TransactionDao;
import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.util.DateUtils;
import in.pms.global.util.PMSFtp;
import in.pms.master.dao.ProjectMasterDao;
import in.pms.master.dao.ProposalMasterDao;
import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.domain.EmpTypeMasterDomain;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.DesignationMasterModel;
import in.pms.master.model.EmpTypeMasterModel;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.GroupMasterModel;
import in.pms.master.model.ManpowerRequirementModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMasterModel;
import in.pms.master.model.ProposalMasterModel;
import in.pms.master.service.EmployeeMasterService;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.GroupMasterService;
import in.pms.master.service.ManpowerRequirementService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProposalMasterService;
import in.pms.transaction.model.DashboardModel;
import in.pms.transaction.service.DashboardService;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;

import com.ibm.icu.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.time.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

@Service
public class MiscDocumentServiceImpl implements MiscDocumentService {

	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ManpowerRequirementService manpowerRequirementService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	ProposalMasterService proposalMasterService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectMasterDao projectMasterDao;
	
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	ProposalMasterDao proposalMasterDao;
	
	@Autowired
	DashboardService dashboardService;
	
	public static final String rootPath =FTPPropertiesReader.getValueFromKey("FTP_ROOT");

	@Override
	public void downloadTemplate(ProjectDocumentMasterModel projectDocumentMasterModel,HttpServletResponse response){
	
	FTPClient ftpClient = new FTPClient();
	boolean login = PMSFtp.loginFTP(ftpClient);
	if(login){
		String filePath = FTPPropertiesReader.getValueFromKey(projectDocumentMasterModel.getTemplate());			
		try {				
			
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);					
			InputStream inputStream = ftpClient.retrieveFileStream(rootPath+"/"+filePath+"/"+projectDocumentMasterModel.getDocumentTypeName());				
			response.setHeader("Content-Disposition", "attachment; filename=\"" + projectDocumentMasterModel.getDocumentTypeName() + "\"");
			//response.setContentType("application/"+ originalFileName.substring(originalFileName.lastIndexOf(".")+1,originalFileName.length()));		
			response.getOutputStream().write(IOUtils.toByteArray(inputStream));
			response.flushBuffer();          
		} catch (IOException e) {			
			e.printStackTrace();
		}finally{
			PMSFtp.logoutFTP(ftpClient);
		}
	}
}
	
	/*----------------- Download details of ongoing project in excel sheet -------------------------------------*/
	
	@Override
	public Boolean downloadOngoingProjects(List<ProjectMasterForm> list,HttpServletResponse response){
		String ftpPath=FTPPropertiesReader.getValueFromKey("EMPLOYEE_REG_TEMPLATE");
		String fileName="Download.xlsx";
		FTPClient ftpClient = new FTPClient();
		PMSFtp.loginFTP(ftpClient);
		/*------------------------ Get Error Excel template and write to the 0 index sheet -------------------------------*/
		try 
		{
			InputStream file = ftpClient.retrieveFileStream(rootPath + "/" + ftpPath + "/" + fileName);
			XSSFWorkbook workbook1 = new XSSFWorkbook(file);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);

		    /*------------------------ Populate data in the Excel sheet -----------------------------------------------------*/
		    for (int i = 0; i < list.size(); i++) 
		    {
		    	ProjectMasterForm data = list.get(i);
				ProjectMasterForm projectMasterForm =projectMasterService.getProjectDetailByProjectId(data.getProjectId());
					
				//Map<String,List<EmployeeRoleMasterModel>> assignedManpower = manpowerRequirementService.getManpowerRequirementWithAssignedRole(data.getProjectId());


				XSSFCellStyle cellStyle = workbook1.createCellStyle();
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setWrapText(true);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				
				//Added by devesh on 28-11-23 to bold active members in team details
				XSSFCellStyle boldcellStyle = workbook1.createCellStyle();
				boldcellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				boldcellStyle.setWrapText(true);
				boldcellStyle.setBorderTop(BorderStyle.THIN);
				boldcellStyle.setBorderBottom(BorderStyle.THIN);
				boldcellStyle.setBorderLeft(BorderStyle.THIN);
				boldcellStyle.setBorderRight(BorderStyle.THIN);
				XSSFFont boldFont = workbook1.createFont();
		        //End of bold style
				
				XSSFCellStyle cellStyle1 = workbook1.createCellStyle();
				cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle1.setWrapText(true);
				cellStyle1.setBorderTop(BorderStyle.THIN);
				cellStyle1.setBorderBottom(BorderStyle.THIN);
				cellStyle1.setBorderLeft(BorderStyle.THIN);
				cellStyle1.setBorderRight(BorderStyle.THIN);
				cellStyle1.setAlignment(HorizontalAlignment.CENTER);
				
				//Added by devesh on 03-05-24 to set data format for cdac outlay column
				XSSFCellStyle cellStyle2 = workbook1.createCellStyle();
				cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle2.setWrapText(true);
				cellStyle2.setBorderTop(BorderStyle.THIN);
				cellStyle2.setBorderBottom(BorderStyle.THIN);
				cellStyle2.setBorderLeft(BorderStyle.THIN);
				cellStyle2.setBorderRight(BorderStyle.THIN);
				cellStyle2.setAlignment(HorizontalAlignment.CENTER);
				DataFormat dataFormat = workbook1.createDataFormat();
				cellStyle2.setDataFormat(dataFormat.getFormat("_ [$₹-4009] * #,##0.000_ ;_ [$₹-4009] * -#,##0.000_ ;_ [$₹-4009] * \"-\"???_ ;_ @_ "));
				//End of cell style
				
		        Row row = sheet1.createRow(i+1);

		        Cell cell0 = row.createCell(0);
		        cell0.setCellValue(i+1);
		        cell0.setCellStyle(cellStyle1);

		        Cell cell1 = row.createCell(1);
		    	cell1.setCellValue(projectMasterForm.getProjectRefrenceNo());
		    	cell1.setCellStyle(cellStyle1);
		        
		    	//comment the below line by varun on 09-10-2023
		       /* String projectName = data.getStrProjectName();
		        String projectRefNo = data.getProjectRefrenceNo();
		        //cell1.setCellValue(data.getStrProjectName()+"("+data.getProjectRefrenceNo()+")");
		        String cellValue = (projectRefNo != null || projectRefNo=="NA") ? projectName + "(" + projectRefNo + ")" : projectName+"";
		        cell1.setCellValue(cellValue);
		        cell1.setCellStyle(cellStyle1);*/
		    	
		    	//added the column project refernce and get the value and increased the cell by 1 by varun on 09-10-2023
		    	Cell cell2 = row.createCell(2);
		    	cell2.setCellValue(projectMasterForm.getStrProjectName());
		    	cell2.setCellStyle(cellStyle1);

		        Cell cell3 = row.createCell(3);
		        cell3.setCellValue(projectMasterForm.getProjectType());
		        cell3.setCellStyle(cellStyle1);
        
		     // Manpower  
		        String empName = "";
		        String involvement = "";
		        String deputedAt = "";

		        int serialNo = 0;

		        /*List<EmployeeRoleMasterModel> employeeRoleMasterList1 = employeeRoleMasterService.getAllTeamDetailsByProject(""+data.getProjectId());*/
		      //Added by devesh on 04/09/23 to get team details members including those whose end date has passed
		        List<EmployeeRoleMasterModel> employeeRoleMasterList1 = employeeRoleMasterService.getAllTeamDetailsByProjectForAllEndDate(""+data.getProjectId());
		      //End of service
		        List<EmployeeRoleMasterModel> employeeRoleMasterList = new ArrayList<>();
		        for(int record=0;record<employeeRoleMasterList1.size();record++)
		        {
		        	EmployeeRoleMasterModel recordRoleMaster = employeeRoleMasterList1.get(record);
		        	if(!recordRoleMaster.getManpowerRequirementId().equals(-1)){
		        		employeeRoleMasterList.add(recordRoleMaster);
		        	}
		        }
		     
/*--------------------------------------------Added by devesh on 28-11-23 to add HOD column in the excel--------------------------------------------*/
		        String HODName = "";
		        List<EmployeeRoleMasterModel> copiedHODList = new ArrayList<>();
		        if(data.getStrHODName()!=null)
		        {
			        String[] splitArray = data.getStrHODName().split(",");
			        List<EmployeeRoleMasterModel> HODRoleMasterList = new ArrayList<>();
			        for (String entry : splitArray) {
			            String[] parts = entry.split(" \\(");
			            String name = parts[0];
			            
			            String[] dates = parts[1].split(" - ");
			            String startDate = dates[0];
			            String endDate = dates.length > 1 ? dates[1].replace(")", "") : "";
			        
			            EmployeeRoleMasterModel r1 =new EmployeeRoleMasterModel();
			            r1.setStrEmpName(name);
			            r1.setStrStartDate(startDate);
			            r1.setStrEndDate(endDate);
			            HODRoleMasterList.add(r1);
			        }

			        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			        Collections.sort(HODRoleMasterList, new Comparator<EmployeeRoleMasterModel>() {
			            @Override
			            public int compare(EmployeeRoleMasterModel model1, EmployeeRoleMasterModel model2) {
			                String endDate1 = model1.getStrEndDate();
			                String endDate2 = model2.getStrEndDate();

			                if (endDate1 == null || endDate1.isEmpty()) {
			                    if (endDate2 == null || endDate2.isEmpty()) {
			                        return HODRoleMasterList.indexOf(model1) - HODRoleMasterList.indexOf(model2);
			                    }
			                    return -1;
			                } else if (endDate2 == null || endDate2.isEmpty()) {
			                    return 1; 
			                }
			                try {
			                    java.util.Date date1 = dateFormat.parse(endDate1);
			                    java.util.Date date2 = dateFormat.parse(endDate2);

			                    return date2.compareTo(date1);
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }

			                return 0;
			            }
			        });

			        Set<String> addedNames = new HashSet<>();
			        List<EmployeeRoleMasterModel> uniqueNames = new ArrayList<>();
			        for (EmployeeRoleMasterModel model : HODRoleMasterList) {
			            String name = model.getStrEmpName();
			            if (!addedNames.contains(name)) {
			                uniqueNames.add(model);
			                addedNames.add(name);
			            }
			        }

			        for(int k=0;k<uniqueNames.size();k++)
			        {
			        	EmployeeRoleMasterModel r1 =uniqueNames.get(k);
			        	HODName += r1.getStrEmpName() +"(";
		        		if(r1.getStrEndDate()==null || r1.getStrEndDate()=="" ||r1.getStrEndDate().isEmpty())
		        		{
		        			HODName +="Since "+r1.getStrStartDate()+")\n";
		        		}else{
		        			HODName +=r1.getStrStartDate()+" - "+r1.getStrEndDate()+")\n";
		        		}
			        }
			        copiedHODList = new ArrayList<>(uniqueNames);
			        
			        
		        }
		        
		        Cell cell4 = row.createCell(4);
		        cell4.setCellValue(HODName);
		        cell4.setCellStyle(cellStyle);
		  

/*-----------------------------------------------------------------End of HOD Column----------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------------------------------------------------*/
		        String plName = "";
		        List<EmployeeRoleMasterModel> copiedList = new ArrayList<>();//Added by devesh on 04-09-23 to copy pl name list
		        if(data.getStrPLName()!=null)
		        {
			        String[] splitArray = data.getStrPLName().split(",");
			        List<EmployeeRoleMasterModel> plRoleMasterList = new ArrayList<>();
			        for (String entry : splitArray) {
			            String[] parts = entry.split(" \\(");
			            String name = parts[0];
			            
			            String[] dates = parts[1].split(" - ");
			            String startDate = dates[0];
			            String endDate = dates.length > 1 ? dates[1].replace(")", "") : "";
			        
			            EmployeeRoleMasterModel r1 =new EmployeeRoleMasterModel();
			            r1.setStrEmpName(name);
			            r1.setStrStartDate(startDate);
			            r1.setStrEndDate(endDate);
			            plRoleMasterList.add(r1);
			        }

			        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			        Collections.sort(plRoleMasterList, new Comparator<EmployeeRoleMasterModel>() {
			            @Override
			            public int compare(EmployeeRoleMasterModel model1, EmployeeRoleMasterModel model2) {
			                String endDate1 = model1.getStrEndDate();
			                String endDate2 = model2.getStrEndDate();

			                if (endDate1 == null || endDate1.isEmpty()) {
			                    if (endDate2 == null || endDate2.isEmpty()) {
			                        return plRoleMasterList.indexOf(model1) - plRoleMasterList.indexOf(model2);
			                    }
			                    return -1;
			                } else if (endDate2 == null || endDate2.isEmpty()) {
			                    return 1; 
			                }
			                try {
			                    java.util.Date date1 = dateFormat.parse(endDate1);
			                    java.util.Date date2 = dateFormat.parse(endDate2);

			                    return date2.compareTo(date1);
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }

			                return 0;
			            }
			        });

			        Set<String> addedNames = new HashSet<>();
			        List<EmployeeRoleMasterModel> uniqueNames = new ArrayList<>();
			        for (EmployeeRoleMasterModel model : plRoleMasterList) {
			            String name = model.getStrEmpName();
			            if (!addedNames.contains(name)) {
			                uniqueNames.add(model);
			                addedNames.add(name);
			            }
			        }

			        for(int k=0;k<uniqueNames.size();k++)
			        {
			        	EmployeeRoleMasterModel r1 =uniqueNames.get(k);
			        	plName += r1.getStrEmpName() +"(";
		        		if(r1.getStrEndDate()==null || r1.getStrEndDate()=="" ||r1.getStrEndDate().isEmpty())
		        		{
		        			plName +="Since "+r1.getStrStartDate()+")\n";
		        		}else{
		        			plName +=r1.getStrStartDate()+" - "+r1.getStrEndDate()+")\n";
		        		}
			        }
			        copiedList = new ArrayList<>(uniqueNames);//Added by devesh on 04-09-23 to copy pl name list
			        
			        
		        }
		        
		        Cell cell5 = row.createCell(5);
		        cell5.setCellValue(plName);
		        cell5.setCellStyle(cellStyle);
		  

/*--------------------------------------------------------------------------------------------------------------------------------------------------*/
	            deputedAt += " \n";
	            //Added by devesh on 04-09-23 to remove PL named from Team details column
	            // Create an iterator for the original list
	            Iterator<EmployeeRoleMasterModel> iterator = employeeRoleMasterList.iterator();

	            // Iterate through the original list and remove matching records
	            while (iterator.hasNext()) {
	                EmployeeRoleMasterModel originalRecord = iterator.next();
	                for (EmployeeRoleMasterModel copiedRecord : copiedList) {
	                    if (areEqual(originalRecord.getStrStartDate(), copiedRecord.getStrStartDate()) &&
	                        areEqual(originalRecord.getStrEndDate(), copiedRecord.getStrEndDate()) &&
	                        areEqual(originalRecord.getStrEmpName(), copiedRecord.getStrEmpName())) {
	                        iterator.remove(); // Remove the current record using the iterator
	                        break; // No need to check further, record is matched
	                    }
	                }
	            }
	            
				//End of removal
	            
	          //Added by devesh on 04-09-23 to remove PL named from Team details column
	            // Create an iterator for the original list
	            Iterator<EmployeeRoleMasterModel> iterator1 = employeeRoleMasterList.iterator();

	            // Iterate through the original list and remove matching records
	            while (iterator1.hasNext()) {
	                EmployeeRoleMasterModel originalRecord = iterator1.next();
	                for (EmployeeRoleMasterModel copiedRecord : copiedHODList) {
	                    if (areEqual(originalRecord.getStrStartDate(), copiedRecord.getStrStartDate()) &&
	                        areEqual(originalRecord.getStrEndDate(), copiedRecord.getStrEndDate()) &&
	                        areEqual(originalRecord.getStrEmpName(), copiedRecord.getStrEmpName())) {
	                        iterator1.remove(); // Remove the current record using the iterator
	                        break; // No need to check further, record is matched
	                    }
	                }
	            }
	            
				//End of removal
	            XSSFRichTextString richText = new XSSFRichTextString();
	            boldFont.setBold(true);
	            XSSFFont normalFont = workbook1.createFont();
	            
		        for (EmployeeRoleMasterModel employeeRole : employeeRoleMasterList) 
		        {        	
		        	if (employeeRole.getStrEmploymentStatus().equals("Relieved"))
		        	{
		        		/*empName += (serialNo + 1) + ". " + employeeRole.getStrEmpName() +"(";*/
		        		//Added by devesh on 28-11-23 to add involvement in team details column
		        		/*empName += (serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + employeeRole.getInvolvement() +" (";*/
		        		richText.append((serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + employeeRole.getInvolvement() +" %  (", normalFont);
		        		
		        		if(employeeRole.getStrEndDate()==null)
		        		{
		        			/*empName +="Since "+employeeRole.getStrStartDate()+")";*/
		        			richText.append("Since "+employeeRole.getStrStartDate()+")", normalFont);
		        		}else{
		        			/*empName +=employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")";*/
		        			richText.append(employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")", normalFont);
		        		}
		        		/*empName +="("+employeeRole.getStrEmploymentStatus()+")\n";*/
		        		richText.append("("+employeeRole.getStrEmploymentStatus()+")\n", normalFont);
		        	}
		        	else
		        	{
		        		/*empName += (serialNo + 1) + ". " + employeeRole.getStrEmpName() +"(";*/
		        		//Added by devesh on 28-11-23 to add involvement in team details column
		        		/*empName += (serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + employeeRole.getInvolvement() +" (";*/
		        		richText.append((serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + employeeRole.getInvolvement() +" %  (", boldFont);
		        		
		        		if(employeeRole.getStrEndDate()==null)
		        		{
		        			/*empName +="Since "+employeeRole.getStrStartDate()+")\n";*/
		        			richText.append("Since "+employeeRole.getStrStartDate()+")\n", boldFont);
		        		}else{
		        			/*empName +=employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")\n";*/
		        			richText.append(employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")\n", boldFont);
		        		}
		        		//Added by devesh on 28-11-23 to bold active members in team details
				        //End of bold style
		        	}
		            
		            /*involvement += (serialNo + 1) + ") " + employeeRole.getInvolvement() + "\n";*/

		            if (employeeRole.getNumDeputedAt() == 1) {
		            	deputedAt += " C-DAC\n";
		            } else if (employeeRole.getNumDeputedAt() == 2) {
		            	deputedAt += " At Client\n";
		            } else {
		            	deputedAt += " NULL \n";
		            }
		            serialNo++;
		            
		        }

		        Cell cell6 = row.createCell(6);
		        cell6.setCellValue(data.getStartDate());
		        cell6.setCellStyle(cellStyle1);

		        Cell cell7 = row.createCell(7);
		        cell7.setCellValue(data.getEndDate());
		        cell7.setCellStyle(cellStyle1);

		        Cell cell8 = row.createCell(8);
				/*DecimalFormat decimalFormat = new DecimalFormat("#,##0");*/
		        DecimalFormat df = new DecimalFormat("##,##,##0.00");
		        String formattedProjectCost = df.format(projectMasterForm.getProjectCost());
		        cell8.setCellValue("₹ "+formattedProjectCost);
		        cell8.setCellStyle(cellStyle2);

		        /*Cell cell9 = row.createCell(9);
		        cell9.setCellValue(empName);
		        cell9.setCellStyle(cellStyle);*/
		        
		        //Added by devesh on 28-11-23 to bold active members in team details
        		Cell cell9 = row.createCell(9);
		        cell9.setCellValue(richText);
		        /*boldcellStyle.setFont(boldFont);*/
		        cell9.setCellStyle(cellStyle);
		        //End of bold style

		        //Commented by devesh on 29-11-23 to remove Involvement column
		        /*Cell cell10 = row.createCell(10);
		        cell10.setCellValue(involvement);
		        cell10.setCellStyle(cellStyle);*/
		        
		        Cell cell11 = row.createCell(10);
		        cell11.setCellValue(deputedAt);
		        cell11.setCellStyle(cellStyle);
		        /*------------------------------------*/
		        /*
		        String s="hello";
		        String d="world";

		        // Set the cell value as a rich text string
		        RichTextString richTextString = new XSSFRichTextString("Hello, World!");
		        
		        // Apply different colors to different parts of the string
		        Font font1 = workbook1.createFont();
		        font1.setColor(Font.COLOR_RED);
		        richTextString.applyFont(0, 5, font1); // Apply red color to "Hello"
		        
		        Font font2 = workbook1.createFont();
		        font2.setColor(Font.COLOR_BLUE);
		        richTextString.applyFont(7, 12, font2); // Apply blue color to "World"

	            RichTextString richTextString = new XSSFRichTextString(s);
	
	            // Set the rich text string as the cell value
	            Cell cell10 = row.createCell(10);
	            cell10.setCellValue(richTextString);
		        */
		        /*------------------------------------*/

		    }
		    sheet1.createFreezePane(0, 1);
		    /*------------------------ End Populate data in the Excel sheet -------------------------------------------------*/
		    String filePath = "Project Details.xlsx";
		    // For Local File 
		    String remoteFilePath = "C:/Users/himan/Downloads/Download1.xlsx";
		    // For Production
		    //String remoteFilePath = rootPath + "/" + ftpPath + "/" + filePath;
		    try (FileOutputStream output_file = new FileOutputStream(remoteFilePath)) {
		        workbook1.write(output_file);
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
		    /*-----------------------------------------------------------------------------------------------------------*/
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return false;
	}
	
	// Custom method to compare values, including handling null values added by devesh on 04-09-23
    private static boolean areEqual(Object obj1, Object obj2) {
		 if (obj1 instanceof String) {
		        obj1 = ((String) obj1).trim().isEmpty() ? null : obj1;
		    }
    	    
	    if (obj2 instanceof String) {
	        obj2 = ((String) obj2).trim().isEmpty() ? null : obj2;
	    }
	    
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }
    //End of Custom Method
 
    //Bhavesh(15-05-24) download weekly report Function
    @Override
	public Boolean downloadWeeklyReport(List<ProjectMasterForm> list,HttpServletResponse response){
		String ftpPath=FTPPropertiesReader.getValueFromKey("EMPLOYEE_REG_TEMPLATE");
		String fileName="WeeklyReport.xlsx";
		FTPClient ftpClient = new FTPClient();
		PMSFtp.loginFTP(ftpClient);
		
		
		/*------------------------ Get Error Excel template and write to the 0 index sheet -------------------------------*/
		try 
		{
			InputStream file = ftpClient.retrieveFileStream(rootPath + "/" + ftpPath + "/" + fileName);
			XSSFWorkbook workbook1 = new XSSFWorkbook(file);
			XSSFSheet sheet1 = workbook1.getSheetAt(0);
			
			/*--------------------------------------*/
			Calendar calendar = Calendar.getInstance();
	    	int currentYear = calendar.get(Calendar.YEAR);
	    	 LocalDate currentDate = LocalDate.now();
	    	 
	    	 DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

	         // Calculate the number of days to subtract to get to last week's Saturday
	         
	         int daysToSubtractForSat = currentDayOfWeek.getValue() % 7 + 1; // Offset to reach last week's Saturday (7 days ago)

	         // Subtract the calculated days from the current date to get the last week's Saturday
	         
	         LocalDate lastWeekSaturday =  currentDate.minusDays(daysToSubtractForSat);               
	         // Format the current date and last week's Saturday date as "dd/MM/yyyy"
	         
	      // Calculate last Saturday
	         LocalDate lastSaturday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));

	         // Calculate the upcoming Friday of the current week
	         LocalDate thisFriday = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
	         
	         // Get the name of the current month
	         Month currentMonth = currentDate.getMonth();
	         String monthName = currentMonth.name();
	        
	         String formattedLastWeekSaturday = lastWeekSaturday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        
	    	String calendarStart="";
	    	String calendarEnd="";
	    	
	    	
	    	Date startRange = null;
			Date endRange = null;
			
			Date lastSaturdayRange = null;
			try {
				
				calendarStart = "01/01/"+currentYear;
				calendarEnd = "31/12/"+currentYear;
				
				
		    	 startRange = DateUtils.dateStrToDate(calendarStart);
		    	 endRange = DateUtils.dateStrToDate(calendarEnd);
		    	 lastSaturdayRange = DateUtils.dateStrToDate(formattedLastWeekSaturday);
		    	 
		    	

			
			} catch (ParseException e) {		
				e.printStackTrace();
			}
			
			
			
			/*--------------------------------------------------------------*/
			
			//for total curren week total cumulative
			List<ProposalMasterModel> proposalList=proposalMasterService.getActiveProposals(calendarStart, calendarEnd);
			//--------- Get the Proposal Data where the project start date and end date are provided [08-02-2024] ----------
			List<ProposalMasterModel> proposalProjectList = proposalMasterService.getProposalDataOfCurrentYearProjects(calendarStart, calendarEnd);
			
			//List<ProposalMasterDomain> proposalListCurrWeekTotal=proposalMasterDao.getActiveProposalsWeekly(lastSaturdayRange, endRange,0);
			//--------- Get the Proposal Data where the project start date and end date are provided [08-02-2024] ----------
			/*List<ProposalMasterModel> proposalProjectListCurrWeek = proposalMasterService.getProposalDataOfCurrentYearProjects(formattedLastWeekSaturday, calendarEnd);
			*/
			Long orgId=(long) 4;
			List<GroupMasterModel> groupnames =groupMasterService.getAllActiveGrpMasterDomain(orgId);	
			
			 List <Object[]> ListTechnicalClosurePendingCumulativeTotal = projectMasterDao.getPendingClosureDetailByDates(0,calendarStart,calendarEnd);
		    	
	    	 List <Object[]> ListTechnicalClosurePendingWeeklyTotal = projectMasterDao.getPendingClosureDetailByDates(0,formattedLastWeekSaturday,calendarEnd);
		 
	    	 List <Object[]> listFinacialClosurePendingCumulativeTotal=projectMasterDao.getAllFinancialClosurePendingProjectByDate("0",9,startRange,endRange);
	    	 
	    	// List <Object[]> listFinacialClosurePendingWeeklyTotal=projectMasterDao.getAllFinancialClosurePendingProjectByDate("0",9,lastSaturdayRange,endRange);
	    	 
             List <Object[]> listTechnicalClosureCumulativeTotal=projectMasterDao.getAllFinancialClosurePendingProjectByTechnicalClosureDate("0",9,startRange,endRange);
	    	 
	    	// List <Object[]> listTechnicalClosureWeeklyTotal=projectMasterDao.getAllFinancialClosurePendingProjectByTechnicalClosureDate("0",9,lastSaturdayRange,endRange);
	    	 
				/*
				 * List<Object[]> listCompletedProjectTotalCumulative =
				 * projectMasterDao.getCompletedProjectData(startRange, endRange,0,9);
				 */
				/*
				 * List<Object[]> listCompletedProjectTotalWeek =
				 * projectMasterDao.getCompletedProjectData(lastSaturdayRange, endRange,0,9);
				 */
	    	 
	    	
			
						    	
						    	
			Row row0 = sheet1.getRow(1);
			
			String sheetName="PMS Proposal/Project Weekly Data Progress Report for "+lastSaturday.getDayOfMonth()+"-"+thisFriday.getDayOfMonth()+" "+monthName+" "+currentYear;
			
			Cell cell = row0.getCell(1);
			cell.setCellValue(sheetName);
						    			
		    Row row = sheet1.getRow(4);
		  
			Cell cell2 = row.getCell(2);
			Cell cell3 = row.getCell(3);
			Cell cell4 = row.getCell(4);
			Cell cell5 = row.getCell(5);
			Cell cell6 = row.getCell(6);
			Cell cell7 = row.getCell(7);
			Cell cell8 = row.getCell(8);
			Cell cell9 = row.getCell(9);
			Cell cell10 = row.getCell(10);
			Cell cell11 = row.getCell(11);
			Cell cell12 = row.getCell(12);
			Cell cell13 = row.getCell(13);
			
			
			//for new Projects
			
			 Row row1 = sheet1.getRow(5);
			  
				Cell cell22 = row1.getCell(2);
				Cell cell23 = row1.getCell(3);
				Cell cell24 = row1.getCell(4);
				Cell cell25 = row1.getCell(5);
				Cell cell26 = row1.getCell(6);
				Cell cell27 = row1.getCell(7);
				Cell cell28 = row1.getCell(8);
				Cell cell29 = row1.getCell(9);
				Cell cell30 = row1.getCell(10);
				Cell cell31 = row1.getCell(11);
				Cell cell32 = row1.getCell(12);
				Cell cell33 = row1.getCell(13);
				
				//for ongoing
				
				 Row row2 = sheet1.getRow(6);
				  
					Cell cell42 = row2.getCell(2);
					Cell cell43 = row2.getCell(3);
					Cell cell44 = row2.getCell(4);
					Cell cell45 = row2.getCell(5);
					Cell cell46 = row2.getCell(6);
					Cell cell47 = row2.getCell(7);
					Cell cell48 = row2.getCell(8);
					Cell cell49 = row2.getCell(9);
					Cell cell50 = row2.getCell(10);
					Cell cell51 = row2.getCell(11);
					Cell cell52 = row2.getCell(12);
					Cell cell53 = row2.getCell(13);
					
				
				//for Tehnical Closure Pending
				
				 Row row3 = sheet1.getRow(7);
				  
					Cell cell62 = row3.getCell(2);
					Cell cell63 = row3.getCell(3);
					Cell cell64 = row3.getCell(4);
					Cell cell65 = row3.getCell(5);
					Cell cell66 = row3.getCell(6);
					Cell cell67 = row3.getCell(7);
					Cell cell68 = row3.getCell(8);
					Cell cell69 = row3.getCell(9);
					Cell cell70 = row3.getCell(10);
					Cell cell71 = row3.getCell(11);
					Cell cell72 = row3.getCell(12);
					Cell cell73 = row3.getCell(13);
					
					/*
					 * //for Financial Closure Pending/Closed=Technically closed
					 * 
					 * Row row4 = sheet1.getRow(8);
					 * 
					 * Cell cell82 = row4.getCell(2); Cell cell83 = row4.getCell(3); Cell cell84 =
					 * row4.getCell(4); Cell cell85 = row4.getCell(5); Cell cell86 =
					 * row4.getCell(6); Cell cell87 = row4.getCell(7); Cell cell88 =
					 * row4.getCell(8); Cell cell89 = row4.getCell(9); Cell cell90 =
					 * row4.getCell(10); Cell cell91 = row4.getCell(11); Cell cell92 =
					 * row4.getCell(12); Cell cell93 = row4.getCell(13); String value4 =
					 * "("+listFinacialClosurePendingCumulativeTotal.size() + "/" +
					 * listFinacialClosurePendingWeeklyTotal.size()+")/("+
					 * listCompletedProjectTotalCumulative.size() + "/" +
					 * listCompletedProjectTotalWeek.size()+")";
					 * 
					 * // Get the cell where you want to set the value (e.g., column index 14) Cell
					 * cell94 = row4.getCell(14);
					 * 
					 * 
					 * 
					 * // Set the cell value cell94.setCellValue(value4);
					 */

					//Technically closed
					
					 Row row4 = sheet1.getRow(8);
					  
						Cell cell82 = row4.getCell(2);
						Cell cell83 = row4.getCell(3);
						Cell cell84 = row4.getCell(4);
						Cell cell85 = row4.getCell(5);
						Cell cell86 = row4.getCell(6);
						Cell cell87 = row4.getCell(7);
						Cell cell88 = row4.getCell(8);
						Cell cell89 = row4.getCell(9);
						Cell cell90 = row4.getCell(10);
						Cell cell91 = row4.getCell(11);
						Cell cell92 = row4.getCell(12);
						Cell cell93 = row4.getCell(13);
						/*
						 * String value4 = "("+listFinacialClosurePendingCumulativeTotal.size() + "/" +
						 * listFinacialClosurePendingWeeklyTotal.size()+")/("+
						 * listCompletedProjectTotalCumulative.size() + "/" +
						 * listCompletedProjectTotalWeek.size()+")";
						 * 
						 * // Get the cell where you want to set the value (e.g., column index 14) Cell
						 * cell94 = row4.getCell(14);
						 * 
						 * 
						 * 
						 * // Set the cell value cell94.setCellValue(value4);
						 */
						
						
				
				
                    //for Financial Closure Pending
						
						 Row row5 = sheet1.getRow(9);
						  
							Cell cell102 = row5.getCell(2);
							Cell cell103 = row5.getCell(3);
							Cell cell104 = row5.getCell(4);
							Cell cell105 = row5.getCell(5);
							Cell cell106 = row5.getCell(6);
							Cell cell107 = row5.getCell(7);
							Cell cell108 = row5.getCell(8);
							Cell cell109 = row5.getCell(9);
							Cell cell110 = row5.getCell(10);
							Cell cell111 = row5.getCell(11);
							Cell cell112 = row5.getCell(12);
							Cell cell113 = row5.getCell(13);
							
						
						  /* //for validated project= Total Closed
						
						 Row row5 = sheet1.getRow(9);
						  
							Cell cell102 = row5.getCell(2);
							Cell cell103 = row5.getCell(3);
							Cell cell104 = row5.getCell(4);
							Cell cell105 = row5.getCell(5);
							Cell cell106 = row5.getCell(6);
							Cell cell107 = row5.getCell(7);
							Cell cell108 = row5.getCell(8);
							Cell cell109 = row5.getCell(9);
							Cell cell110 = row5.getCell(10);
							Cell cell111 = row5.getCell(11);
							Cell cell112 = row5.getCell(12);
							Cell cell113 = row5.getCell(13);*/
							/*String value5 = "("+uncheckedTotal + " and " + checkedTotal+")/("+uncheckedTotalWeekly + " and " +checkedTotalWeekly+")";

							// Get the cell where you want to set the value (e.g., column index 14)
							Cell cell114 = row5.getCell(14);

							

							// Set the cell value
							cell114.setCellValue(value5);*/
							
							
							 //for  Total Closed
							
							 Row row6 = sheet1.getRow(10);
							  
								Cell cell115 = row6.getCell(2);
								Cell cell116 = row6.getCell(3);
								Cell cell117 = row6.getCell(4);
								Cell cell118 = row6.getCell(5);
								Cell cell119 = row6.getCell(6);
								Cell cell120 = row6.getCell(7);
								Cell cell121 = row6.getCell(8);
								Cell cell122 = row6.getCell(9);
								Cell cell123 = row6.getCell(10);
								Cell cell124 = row6.getCell(11);
								Cell cell125 = row6.getCell(12);
								Cell cell126 = row6.getCell(13);
								
								int completedProjectCount=0;
								
						
						  //currentlly ongoing(Live)
						
						 Row row7 = sheet1.getRow(11);
						  
							Cell cell131 = row7.getCell(2);
							Cell cell132 = row7.getCell(4);
							Cell cell133 = row7.getCell(6);
							Cell cell134 = row7.getCell(8);
							Cell cell135 = row7.getCell(10);
							Cell cell136 = row7.getCell(12);
							Cell cell137 = row7.getCell(14);
							
							
		   int countTotalCumulativeProjectRecieved=0;
		    int countTotalCumulativeOngoing=0;
		    int countTotalWeeklyOngoing=0;
		    int countTotalCumulativeclosureInitiated =0;
		    int countTotalWeeklyclosureInitiated=0;
		    
		    
		    int countTotalWeeklyProjectRecieved=0;
		    int weeklyclosedProjectCount=0;
		    
		    int subPmoProposal=0;
		    
			/* int proposalCurrentWeek=0; */
		    
		    int technicallyCloseWeekly=0;
		    
		    int financialPendingWeekly=0;
		    
		    int totalWeeklyProposalCount=0;
		    
	    for(int i=0;i<groupnames.size();i++){
		    	
		    	List<ProjectMasterForm> formList = projectMasterService.viewProjectDetailsData(groupnames.get(i).getEncGroupId());
		    	
		    	String projectIdString;
		        StringBuilder sb = new StringBuilder();
		    	for(int data=0;data<formList.size(); data++){
		    		
		    		 sb.append(formList.get(data).getProjectId());
			            if (data < formList.size() - 1) {
			                sb.append(",");
			            }
		    		
		    		
		    	}
		    	String pId=sb.toString();
		    	 projectIdString= sb.toString();
		    	 
		    	 
		    	 
		    	 
		    	 
		    	 List<String> projectIdList = Arrays.asList(projectIdString.split(","));
		    	 /*System.out.println("projectIdString "+projectIdString);*/
		    	
		    	
		    	
		    	 String strAssignedGroup=encryptionService.dcrypt(groupnames.get(i).getEncGroupId());
		    	 int assignedGroup = Integer.parseInt(strAssignedGroup);
		    	 
		    	/* System.out.println("assignedGroup "+assignedGroup);*/
		    	 
		    	 List<Object[]> OngoingTotalCumulative=null;
		    	 List<Object[]> OngoingTotalCurrentWeek=null;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 if(pId!=null||pId!=""){
		    	  OngoingTotalCumulative = transactionDao.getOngoingProjectCount(startRange,endRange,projectIdList);
			    	
			    	countTotalCumulativeOngoing+=OngoingTotalCumulative.size();
			    	
			    	OngoingTotalCurrentWeek = transactionDao.getOngoingProjectCount(lastSaturdayRange,endRange,projectIdList);
			    	countTotalWeeklyOngoing+=OngoingTotalCurrentWeek.size();
			    	
		    		 }
		    	 }
		    	 
		    	 
		    	 
		    	 List <Object[]> ListTechnicalClosurePendingCumulative = projectMasterDao.getPendingClosureDetailByDates(assignedGroup,calendarStart,calendarEnd);
		    	
		    	 List <Object[]> ListTechnicalClosurePendingWeekly = projectMasterDao.getPendingClosureDetailByDates(assignedGroup,formattedLastWeekSaturday,calendarEnd);
		    	 
		    	 List <Object[]> listFinacialClosurePendingCumulativeTotalGroup=projectMasterDao.getAllFinancialClosurePendingProjectByDate(strAssignedGroup,5,startRange,endRange);
		    	 
		    	// List <Object[]> listFinacialClosurePendingWeeklyTotalGroup=projectMasterDao.getAllFinancialClosurePendingProjectByDate(strAssignedGroup,5,lastSaturdayRange,endRange);
		    	 
                  List <Object[]> listTechnicalClosureCumulativeTotalGroup=projectMasterDao.getAllFinancialClosurePendingProjectByTechnicalClosureDate(strAssignedGroup,5,startRange,endRange);
		    	 
		    	// List <Object[]> listTechnicalClosureWeeklyTotalGroup=projectMasterDao.getAllFinancialClosurePendingProjectByTechnicalClosureDate(strAssignedGroup,5,lastSaturdayRange,endRange);
			    
		    	 List<Object[]> listCompletedProjectCumulative = projectMasterDao.getCompletedProjectData(startRange,endRange,assignedGroup,5); 
		    	 
					/*
					 * List<Object[]> listCompletedProjectWeekly =
					 * projectMasterDao.getCompletedProjectData(lastSaturdayRange,endRange,
					 * assignedGroup,5);
					 */
		    	 
		    	 List<Object[]> proposalWeekly=null;
		    	 int proposalWeeklyCount=0;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 proposalWeekly=transactionDao.getProposalCurrentWeek(lastSaturdayRange,endRange,assignedGroup);
		    		 totalWeeklyProposalCount+=proposalWeekly.size();
		    		 proposalWeeklyCount=proposalWeekly.size();
		    		 
		    	 }
		    	 
		    	 List<Object[]> technicallyCloseWeek=null;
		    	 int technicallyCloseWeekly1=0;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 technicallyCloseWeek=transactionDao.technicallyClosedProjectCurrentWeek(lastSaturdayRange,endRange,assignedGroup);
		    		 technicallyCloseWeekly+=technicallyCloseWeek.size();
		    		 technicallyCloseWeekly1=technicallyCloseWeek.size();
		    		 
		    	 }
		    	 
		    	 List<Object[]> financialPendingWeek=null;
		    	 int financialPendingWeekly1=0;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 financialPendingWeek=transactionDao.financialPendingProjectCurrentWeek(lastSaturdayRange,endRange,assignedGroup);
		    		 financialPendingWeekly+=financialPendingWeek.size();
		    		 financialPendingWeekly1=financialPendingWeek.size();
		    		 
		    	 }
		    	 List<Object[]> listCompletedProjectWeeklyByGroup=  projectMasterDao.getCompletedProjectDataByGroup(assignedGroup);
		    	 
		    	 StringBuilder closedProjectId = new StringBuilder();
		    	 if(null != listCompletedProjectWeeklyByGroup){
						for(int lists=0;lists<listCompletedProjectWeeklyByGroup.size();lists++){
							Object[] obj = listCompletedProjectWeeklyByGroup.get(lists);
							
							 closedProjectId.append(((BigInteger) obj[10]).toString());
							if(lists<listCompletedProjectWeeklyByGroup.size()-1) {
								
								closedProjectId.append(",");
							}
							
							}
		    	 }
		    	 
		    	 String closedProjectIds= closedProjectId.toString();
		    	 
				   /* System.out.println("strAssignedGroup "+strAssignedGroup+" "+sbProjectId.toString());*/
				    List<String> closedProjectIdArr =Arrays.asList(closedProjectIds.split(","));
				    
				    
				    List<Object[]> completedProjectCurrentWeek=null;
			    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
			    		 
			    		 if(closedProjectIds!=null||closedProjectIds!=""){
			    			 
			    			
			    			/* projectRecievedTotalCumulative = transactionDao.getProjectRecievedCount(startRange,endRange,projectIdLists);
				    	
			    			 countTotalCumulativeProjectRecieved+=projectRecievedTotalCumulative.size();*/
				    	
			    			 completedProjectCurrentWeek = transactionDao.completedProjectCurrentWeek(lastSaturdayRange,endRange,closedProjectIdArr);
			    			 weeklyclosedProjectCount+=completedProjectCurrentWeek.size();
				    	
			    		 }
			    	 }
		    	/* List<Object[]> validatedProjectDetailsDataCumulativeGroup= projectMasterDao.viewValidatedProjectDetailsDataForReport(startRange, endRange,assignedGroup,5);
		    	 
		    	 List<Object[]> validatedProjectDetailsDataWeeklyGroup= projectMasterDao.viewValidatedProjectDetailsDataForReport(lastSaturdayRange, endRange,assignedGroup,5);*/
				int proposalReceivedCount = 0; 
				
				
					
			     List<ProposalMasterModel> filteredProposals = new ArrayList<>();
			     
			    
			     
			    for (ProposalMasterModel proposal : proposalList) {
			        if (proposal.getGroupName().equals(groupMasterService.getDistinctGroupsForOrganisation(strAssignedGroup))) {
			            filteredProposals.add(proposal);
			        }
			    }
			    
			    StringBuilder sbProjectId = new StringBuilder();
			    int proposalReceivedCounter=0;
			    for (ProposalMasterModel proposal : proposalProjectList) {
			        if (proposal.getGroupName().equals(groupMasterService.getDistinctGroupsForOrganisation(strAssignedGroup))) {
			        	proposalReceivedCounter++;
			        	
			        	
			        	
			        	/*projectIdLists.add(encryptionService.dcrypt(proposal.getEncProjectId()));*/
			        }
			    }
			    for (ProposalMasterModel proposal : proposalProjectList) {
			        if (proposal.getGroupName().equals(groupMasterService.getDistinctGroupsForOrganisation(strAssignedGroup))) {
			        	proposalReceivedCount++;
			        	
			        	sbProjectId.append(encryptionService.dcrypt(proposal.getEncProjectId()));
			        	/*System.out.println("proposalReceivedCount "+proposalReceivedCount+"  proposalReceivedCounter "+proposalReceivedCounter);*/
			            if (proposalReceivedCount <proposalReceivedCounter ) {
			            	sbProjectId.append(",");
			            }
			        	
			        	
			        	/*projectIdLists.add(encryptionService.dcrypt(proposal.getEncProjectId()));*/
			        }
			    }
			    String projectIdStrings= sbProjectId.toString();
			  
			    List<String> projectIdLists =Arrays.asList(projectIdStrings.split(","));
			    
			    
		    	 List<Object[]> projectRecievedTotalCurrentWeek=null;
		    	 List<Object[]> projectRecievedTotalCumulative=null;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 if(projectIdStrings!=null||projectIdStrings!=""){
		    			 
		    			
		    			projectRecievedTotalCumulative = transactionDao.getProjectRecievedCount(startRange,endRange,projectIdLists);
			    	
		    		 countTotalCumulativeProjectRecieved+=projectRecievedTotalCumulative.size();
			    	
		    			 projectRecievedTotalCurrentWeek = transactionDao.getProjectRecievedCount(lastSaturdayRange,endRange,projectIdLists);
		    			 countTotalWeeklyProjectRecieved+=projectRecievedTotalCurrentWeek.size();
			    	
		    		 }
		    	 }
		    	 
		    	 
		    	 List<Object[]> closureInitiatedCumulative=null;
		    	 List<Object[]> closureInitiatedCurrentWeek=null;
		    	 if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
		    		 
		    		 if(pId!=null||pId!=""){
		    			 closureInitiatedCumulative = transactionDao.getProjectClosureInitiatedCount(startRange,endRange,assignedGroup,projectIdList);
			    	
			    	countTotalCumulativeclosureInitiated+=closureInitiatedCumulative.size();
			    	
			    	closureInitiatedCurrentWeek = transactionDao.getProjectClosureInitiatedCount(lastSaturdayRange,endRange,assignedGroup,projectIdList);
			    	countTotalWeeklyclosureInitiated+=closureInitiatedCurrentWeek.size();
			    	
		    		 }
		    	 }
		    	 
			    
					/*
					 * List<ProposalMasterDomain>
					 * proposalListCurrWeek=proposalMasterDao.getActiveProposalsWeekly(
					 * lastSaturdayRange, endRange,assignedGroup);
					 */
			    
			    
                
			    
			    
			    
			   
			    if(assignedGroup==1){
			    	
			    	
			    	 subPmoProposal= subPmoProposal+filteredProposals.size();
						/* proposalCurrentWeek+=proposalListCurrWeek.size(); */

			      cell2.setCellValue(proposalWeeklyCount);
			       
			      cell3.setCellValue(filteredProposals.size());
			      
			      cell22.setCellValue(projectRecievedTotalCurrentWeek.size());
			       
			      cell23.setCellValue(proposalReceivedCount);
			      
			      cell42.setCellValue(OngoingTotalCurrentWeek.size());
			       
			      cell43.setCellValue(OngoingTotalCumulative.size());
			      
					
			      
			      if(closureInitiatedCurrentWeek!=null) {
				      cell62.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
				      }
				      else {
				    	  cell62.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
				    	  
				      }
				      
				       
				      cell63.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
			      
						
				      cell82.setCellValue(technicallyCloseWeekly1);
			       
						
				      
				      cell83.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
			        
			     
				      cell102.setCellValue(financialPendingWeekly1);
				       
				      cell103.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
				      
				      cell115.setCellValue(completedProjectCurrentWeek.size());
				       
				      cell116.setCellValue(listCompletedProjectCumulative.size());
				      completedProjectCount+=listCompletedProjectCumulative.size();
				     
			      
			    }
			    else if(assignedGroup==2){
			    	 subPmoProposal= subPmoProposal+filteredProposals.size();
						/* proposalCurrentWeek+=proposalListCurrWeek.size(); */
			    	
			        
			    	 cell4.setCellValue(proposalWeeklyCount);
				       
				      cell5.setCellValue(filteredProposals.size());
				      
				      cell24.setCellValue(projectRecievedTotalCurrentWeek.size());
				       
				      cell25.setCellValue(proposalReceivedCount);
				      
				      cell44.setCellValue(OngoingTotalCurrentWeek.size());
				       
				      cell45.setCellValue(OngoingTotalCumulative.size());
				      
						
				      
				     
				      if(closureInitiatedCurrentWeek!=null) {
					      cell64.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
					      }
					      else {
					    	  cell64.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
					    	  
					      }
					       
					      cell65.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
					      
							
					      
					      cell84.setCellValue(technicallyCloseWeekly1);
					       
					      cell85.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
				      
				     
					      cell104.setCellValue(financialPendingWeekly1);
					       
					      cell105.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
					      
					      cell117.setCellValue(completedProjectCurrentWeek.size());
					       
					      cell118.setCellValue(listCompletedProjectCumulative.size());
					      
					      completedProjectCount+=listCompletedProjectCumulative.size();
			    }
                    else if(assignedGroup==6){
                    	 subPmoProposal= subPmoProposal+filteredProposals.size();
							/* proposalCurrentWeek+=proposalListCurrWeek.size(); */
			    	
			       
                    	 cell6.setCellValue(proposalWeeklyCount);
      			       
       			      cell7.setCellValue(filteredProposals.size());
       			      
       			   cell26.setCellValue(projectRecievedTotalCurrentWeek.size());
			       
 			      cell27.setCellValue(proposalReceivedCount);
 			      
 			     cell46.setCellValue(OngoingTotalCurrentWeek.size());
			       
 			     
			      cell47.setCellValue(OngoingTotalCumulative.size());
 			      
					
			      
			     
			      if(closureInitiatedCurrentWeek!=null) {
		 			     cell66.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
					      }
					      else {
					    	  
					    	  cell66.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
					      }
					       
					      cell67.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
			      
							
					      
					      cell86.setCellValue(technicallyCloseWeekly1);
					       
					      cell87.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
			      
			      
					      
					      cell106.setCellValue(financialPendingWeekly1);
					       
					      cell107.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
					      
					      cell119.setCellValue(completedProjectCurrentWeek.size());
					       
					      cell120.setCellValue(listCompletedProjectCumulative.size());
					      completedProjectCount+=listCompletedProjectCumulative.size();    
					     
			    }
                    else if(assignedGroup==11){
                    	
                    	 subPmoProposal= subPmoProposal+filteredProposals.size();
							/* proposalCurrentWeek+=proposalListCurrWeek.size(); */
    			    	
    			       
                    	 cell8.setCellValue(proposalWeeklyCount);
      			       
       			      cell9.setCellValue(filteredProposals.size());
       			      
       			   cell28.setCellValue(projectRecievedTotalCurrentWeek.size());
			       
 			      cell29.setCellValue(proposalReceivedCount);
 			      
 			     cell48.setCellValue(OngoingTotalCurrentWeek.size());
			       
			      cell49.setCellValue(OngoingTotalCumulative.size());
 			      
					
			      
			     
			      if(closureInitiatedCurrentWeek!=null) {
		 			     cell68.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
					      }
					      else {
					    	  
					    	  cell68.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
					      }
					       
					      cell69.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
					      
							
					      
					      cell88.setCellValue(technicallyCloseWeekly1);
					       
					      cell89.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
			     
					      
					      cell108.setCellValue(financialPendingWeekly1);
					       
					      cell109.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
					      
					      cell121.setCellValue(completedProjectCurrentWeek.size());
					       
					      cell122.setCellValue(listCompletedProjectCumulative.size());
					      
					      completedProjectCount+=listCompletedProjectCumulative.size(); 
    			    }
                       else if(assignedGroup==3){
                    	   
                    	   subPmoProposal= subPmoProposal+filteredProposals.size();
							/* proposalCurrentWeek+=proposalListCurrWeek.size(); */
    			    	
    			        
                    	   cell10.setCellValue(proposalWeeklyCount);
        			       
         			      cell11.setCellValue(filteredProposals.size());
         			      
         			     cell30.setCellValue(projectRecievedTotalCurrentWeek.size());
      			       
       			      cell31.setCellValue(proposalReceivedCount);
       			      
       			   cell50.setCellValue(OngoingTotalCurrentWeek.size());
			       
 			      cell51.setCellValue(OngoingTotalCumulative.size());
       			     
					
 			      
 			      
 			     
 			     if(closureInitiatedCurrentWeek!=null) {
 	       			   cell70.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
 	 			     }else {
 	 			    	 
 	 			    	 cell70.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
 	 			     }
 				       
 	 			      cell71.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
 	 			      
						
 	 			      
 	 			    cell90.setCellValue(technicallyCloseWeekly1);
 			       
 				      cell91.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
			      
			      
 				      
 				     cell110.setCellValue(financialPendingWeekly1);
 				       
 				      cell111.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
 				      
 				     cell123.setCellValue(completedProjectCurrentWeek.size());
				       
				      cell124.setCellValue(listCompletedProjectCumulative.size());
				      completedProjectCount+=listCompletedProjectCumulative.size();
				      
    			    }
                       else if(assignedGroup==4){
                    	   
                    	   subPmoProposal= subPmoProposal+filteredProposals.size();
							/* proposalCurrentWeek+=proposalListCurrWeek.size(); */
       			    	
       			       
                    	   cell12.setCellValue(proposalWeeklyCount);
        			       
         			      cell13.setCellValue(filteredProposals.size());
         			     cell32.setCellValue(projectRecievedTotalCurrentWeek.size());
      			       
       			      cell33.setCellValue(proposalReceivedCount);
       			      
       			   cell52.setCellValue(OngoingTotalCurrentWeek.size());
			       
 			      cell53.setCellValue(OngoingTotalCumulative.size());
       			    
					
 			      
 			    
 			      
 			     if(closureInitiatedCurrentWeek!=null) {
 	       			   cell72.setCellValue(closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size());
 	 			     }
 	 			     else {
 	 			    	 
 	 			    	cell72.setCellValue(0+ListTechnicalClosurePendingWeekly.size());
 	 			     }
 	 			     
 				       
 	 			      cell73.setCellValue(closureInitiatedCumulative.size()+ListTechnicalClosurePendingCumulative.size());
 			      
						
			      
 	 			    cell92.setCellValue(technicallyCloseWeekly1);
 			       
 				      cell93.setCellValue(listTechnicalClosureCumulativeTotalGroup.size());
			     
 				     cell112.setCellValue(financialPendingWeekly1);
 				       
 				      cell113.setCellValue(listFinacialClosurePendingCumulativeTotalGroup.size());
 				      
 				     cell125.setCellValue(completedProjectCurrentWeek.size());
				       
				      cell126.setCellValue(listCompletedProjectCumulative.size());
				      
				      completedProjectCount+=listCompletedProjectCumulative.size();
       			    }
			    
			   
			    
			    String value2 = countTotalWeeklyOngoing  + "/" +countTotalCumulativeOngoing;

				
				Cell cell54 = row2.getCell(14);

				
				// Set the cell value
				cell54.setCellValue(value2);
				
				String value1 = countTotalWeeklyProjectRecieved + "/" +countTotalCumulativeProjectRecieved ;

				// Get the cell where you want to set the value (e.g., column index 14)
				Cell cell34 = row1.getCell(14);


				// Set the cell value
				cell34.setCellValue(value1);
				
				int cumulativeClosureInitiatedCount=countTotalCumulativeclosureInitiated+ListTechnicalClosurePendingCumulativeTotal.size();
				
	              int weeklyClosureInitiatedCount =countTotalWeeklyclosureInitiated+ListTechnicalClosurePendingWeeklyTotal.size();
	              
	            		  
				
					
				

					String value3 = weeklyClosureInitiatedCount + "/" +cumulativeClosureInitiatedCount;

					// Get the cell where you want to set the value (e.g., column index 14)
					Cell cell74 = row3.getCell(14);

					

					// Set the cell value
					cell74.setCellValue(value3);
					
			    
					String value4 = technicallyCloseWeekly + "/" + listTechnicalClosureCumulativeTotal.size();

					// Get the cell where you want to set the value (e.g., column index 14)
					Cell cell94 = row4.getCell(14);

					

					// Set the cell value
					cell94.setCellValue(value4);
			   
		    }
	    
	    
	    String value = totalWeeklyProposalCount + "/" + subPmoProposal;

		// Get the cell where you want to set the value (e.g., column index 14)
		Cell cell14 = row.getCell(14);

		

		// Set the cell value
		cell14.setCellValue(value);
		
		
		List<DashboardModel>  countProjects = dashboardService.getProjectCountandCostByGroup();	
        
		
		int totalProjectCount=0;
		for(int count=0;count<countProjects.size();count++) {
			
			
			if(countProjects.get(count).getGroupName().equals("e-Governance")) {
				
				 cell131.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
			      
				
			}else if(countProjects.get(count).getGroupName().equals("Health Informatics - I")) {
				
				 cell132.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
				 
				
			}else if(countProjects.get(count).getGroupName().equals("Health Informatics - II")) {
				
				cell133.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
				 
				
			}else if(countProjects.get(count).getGroupName().equals("Education & Training")) {
				
				cell134.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
				
				
			}else if(countProjects.get(count).getGroupName().equals("Embedded Systems")) {
				
				cell135.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
				
				
			}else if(countProjects.get(count).getGroupName().equals("Speech & Natural Language Processing")) {
				
				cell136.setCellValue(countProjects.get(count).getProjectCount());
				 totalProjectCount+=countProjects.get(count).getProjectCount();
				
				
			}
		}
		
		int value7 = totalProjectCount;

		

		

		// Set the cell value
		cell137.setCellValue(value7);
		
		String value5 = financialPendingWeekly+"/"+listFinacialClosurePendingCumulativeTotal.size();

		// Get the cell where you want to set the value (e.g., column index 14)
		Cell cell114 = row5.getCell(14);

		

		// Set the cell value
		cell114.setCellValue(value5);

		
		String value6 = weeklyclosedProjectCount+"/"+completedProjectCount;

		// Get the cell where you want to set the value (e.g., column index 14)
		Cell cell333 = row6.getCell(14);

		

		// Set the cell value
		cell333.setCellValue(value6);
		    
		    
		    sheet1.createFreezePane(0, 1);
		    /*------------------------ End Populate data in the Excel sheet -------------------------------------------------*/
		    String filePath = "Weekly_Report.xlsx";
		    // For Local File 
		 //String remoteFilePath = "C:/Users/bhaveshgupta/Downloads/WeeklyRepor45t.xlsx";
		    // For Production
		  String remoteFilePath = rootPath + "/" + ftpPath + "/" + filePath;
		    try (FileOutputStream output_file = new FileOutputStream(remoteFilePath)) {
		        workbook1.write(output_file);
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
		    /*-----------------------------------------------------------------------------------------------------------*/
		 
			
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return false ;
		    
	}

	@Override
	public Map<String, Map<String, List<List<String>>>> fetchWeeklyGraphData() {

	    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Map<String, List<Date>> weeks = new LinkedHashMap<>();
        
        // First day of the year
        Calendar firstDayOfYear = Calendar.getInstance();
        firstDayOfYear.set(currentYear, Calendar.JANUARY, 1);

        // Calculate the first Friday of the year
        Calendar firstFriday = (Calendar) firstDayOfYear.clone();
        while (firstFriday.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            firstFriday.add(Calendar.DAY_OF_YEAR, 1);
        }

        Calendar currentStartDate = (Calendar) firstDayOfYear.clone();
        Calendar currentEndDate = (Calendar) firstFriday.clone();

        int weekNumber = 1;

        // Add the first week
        List<Date> dat = new ArrayList<>();
        dat.add(currentStartDate.getTime());
        dat.add(currentEndDate.getTime());
        weeks.put("W "+ weekNumber, dat);

        // Move to the next week starting on Saturday
        currentStartDate = (Calendar) currentEndDate.clone();
        currentStartDate.add(Calendar.DAY_OF_YEAR, 1);
        weekNumber++;

        // Get the current date
        Calendar today = Calendar.getInstance();

        // Add subsequent weeks
        while (currentStartDate.get(Calendar.YEAR) == currentYear && currentEndDate.before(today)) {
            currentEndDate = (Calendar) currentStartDate.clone();
            currentEndDate.add(Calendar.DAY_OF_YEAR, 6);

            if (currentEndDate.get(Calendar.YEAR) != currentYear) {
                currentEndDate.set(currentYear, Calendar.DECEMBER, 31);
            }

            List<Date> date = new ArrayList<>();
            date.add(currentStartDate.getTime());
            date.add(currentEndDate.getTime());
            weeks.put("W "+ weekNumber, date);

            currentStartDate = (Calendar) currentEndDate.clone();
            currentStartDate.add(Calendar.DAY_OF_YEAR, 1);
            weekNumber++;
        }
        
       String calendarStart = "01/01/"+currentYear;
		String calendarEnd = "31/12/"+currentYear;

        Long orgId=(long) 4;
		List<GroupMasterModel> groupnames =groupMasterService.getAllActiveGrpMasterDomain(orgId);
		
				
		//--------- Get the Proposal Data where the project start date and end date are provided [08-02-2024] ----------
		List<ProposalMasterModel> proposalProjectList = proposalMasterService.getProposalDataOfCurrentYearProjects(calendarStart, calendarEnd);
		
		
		int totalWeeklyProposalCount=0;
		int countTotalWeeklyProjectRecieved=0;
		
		int countTotalWeeklyOngoing=0;
		
		int countTotalWeeklyclosureInitiated=0;
		
		int technicallyCloseWeekly=0;
		
		int financialPendingWeekly=0;
		
		int weeklyclosedProjectCount=0;
		
		//String strAssignedGroupName =encryptionService.dcrypt(groupnames.get(0).getEncGroupId());
        // Format and print the week start and end dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       
       
        Map<String, Map<String, List<List<String>>>> time=new LinkedHashMap<>();
		
        
            Map<String, List<List<String>>> groupName= new LinkedHashMap<>();
            for(int i=0;i<groupnames.size();i++){
            	
            	 
            	 String strAssignedGroup=encryptionService.dcrypt(groupnames.get(i).getEncGroupId());
		    	 int assignedGroup = Integer.parseInt(strAssignedGroup);
        		
        		String strAssignedGroupName =groupnames.get(i).getGroupName();
        		
        		List<Object[]> proposalWeekly=null;
        		
        		 List<Object[]> projectRecievedTotalCurrentWeek=null;
        		 
        		 List<ProjectMasterForm> formList = projectMasterService.viewProjectDetailsData(groupnames.get(i).getEncGroupId());
 		    	
 		    	String projectIdString;
 		        StringBuilder sb = new StringBuilder();
 		    	for(int data=0;data<formList.size(); data++){
 		    		
 		    		 sb.append(formList.get(data).getProjectId());
 			            if (data < formList.size() - 1) {
 			                sb.append(",");
 			            }
 		    		
 		    		
 		    	}
 		    	String pId=sb.toString();
 		    	 projectIdString= sb.toString();
 		    	 
 		    	 
 		    	 
 		    	 
 		    	 
 		    	 List<String> projectIdList = Arrays.asList(projectIdString.split(","));
 		    	
 		    	
 		    	 List<Object[]> OngoingTotalCurrentWeek=null;
 		    	 
 		    	
		    	 List<Object[]> closureInitiatedCurrentWeek=null;
		    	
		    	 
        		if(assignedGroup==1||assignedGroup==2||assignedGroup==3||assignedGroup==6||assignedGroup==11||assignedGroup==4){
        			
        			List<List<String>> groupDataList = new ArrayList<>();
               	 
               	 List<String> constantData=  new ArrayList<>();
               	 constantData.add("Week");
               	 constantData.add("New Proposals");
               	 constantData.add("New Projects Received");
               	 constantData.add("Ongoing(Post Internal Approvals)");
               	 constantData.add("Technical Closure Pending");
               	 constantData.add("Technically closed");
               	 constantData.add("Financial Closure Pending");
               	 constantData.add("Total Closed");
               	 
               	 groupDataList.add(constantData);
        			
        			for (Map.Entry<String, List<Date>> entry : weeks.entrySet()) {
        				List<String> GroupData=  new ArrayList<>();
        	            String week = entry.getKey();
        	            List<Date> dates = entry.getValue();
        	            String fromDate = dateFormat.format(dates.get(0));
        	            String toDate = dateFormat.format(dates.get(1));
        	            
        	            
        	            Date startRange = null;
        	    		Date endRange = null;
        	    		
        	    		Date lastRange = null;
        	            
        	            try {
        	    			
        	            	
        	   	    	 startRange = DateUtils.dateStrToDate(fromDate);
        	   	    	 endRange = DateUtils.dateStrToDate(toDate);
        	   	    
        	   	   // Parse the input date string
        	             Date endRangeDay = dateFormat.parse(toDate);
        	   	   // Use Calendar to add one day
        	             Calendar calendar = Calendar.getInstance();
        	             calendar.setTime(endRangeDay);
        	             calendar.add(Calendar.DAY_OF_YEAR, 1);
        	              lastRange = calendar.getTime();
        	   	    	

        	   		
        	   		} catch (ParseException e) {		
        	   			e.printStackTrace();
        	   		}
        			
        			GroupData.add(week);
        			
        			//for Proposal
        			
        			 proposalWeekly=transactionDao.getProposalCurrentWeek(startRange,lastRange,assignedGroup);
		    		 totalWeeklyProposalCount+=proposalWeekly.size();
		    		
		    		 
		    		 GroupData.add(""+proposalWeekly.size());
		    		 
		    		 		    		 
		    		 //New Projects Received
		    		 
		    		 int proposalReceivedCount = 0; 
		    		 StringBuilder sbProjectId = new StringBuilder();
		    		 
		    		 int proposalReceivedCounter=0;
					    for (ProposalMasterModel proposal : proposalProjectList) {
					        if (proposal.getGroupName().equals(groupMasterService.getDistinctGroupsForOrganisation(strAssignedGroup))) {
					        	proposalReceivedCounter++;
					        	
					        	
					        	
					        	
					        }
					    }
		    		 
		    		 for (ProposalMasterModel proposal : proposalProjectList) {
					        if (proposal.getGroupName().equals(groupMasterService.getDistinctGroupsForOrganisation(strAssignedGroup))) {
					        	proposalReceivedCount++;
					        	
					        	sbProjectId.append(encryptionService.dcrypt(proposal.getEncProjectId()));
					        	
					            if (proposalReceivedCount <proposalReceivedCounter ) {
					            	sbProjectId.append(",");
					            }
					        	
					        	
					        	
					        }
					    }
					    String projectIdStrings= sbProjectId.toString();
					  
					    List<String> projectIdLists =Arrays.asList(projectIdStrings.split(","));
					    
					    
				    	
				    	
		    		 
		    		 if(projectIdStrings!=null||projectIdStrings!=""){
		    			 

			    			 projectRecievedTotalCurrentWeek = transactionDao.getProjectRecievedCount(startRange,lastRange,projectIdLists);
			    			 countTotalWeeklyProjectRecieved+=projectRecievedTotalCurrentWeek.size();
			    			 GroupData.add(""+projectRecievedTotalCurrentWeek.size());
				    	
			    			 
			    			 	
			    		 }
		    		 
		    		 //Ongoing(Post Internal Approvals
		    		 
		    		 if(pId!=null||pId!=""){
		 		    	  
		 			    	
		 			    	OngoingTotalCurrentWeek = transactionDao.getOngoingProjectCount(startRange,lastRange,projectIdList);
		 			    	countTotalWeeklyOngoing+=OngoingTotalCurrentWeek.size();
		 			    	 GroupData.add(""+OngoingTotalCurrentWeek.size());
		 			    	 
		 			    	 
		 		    		 }
		    		 
		    		 //Technical Closure Pending
		    		 
		    		 if(pId!=null||pId!=""){
		    			 
		    			 //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    		        
		    			 String startRangeStr = dateFormat.format(startRange);
		    			 String lastRangeStr = dateFormat.format(lastRange);
		    			
		    		List <Object[]> ListTechnicalClosurePendingWeekly = projectMasterDao.getPendingClosureDetailByDates(assignedGroup,startRangeStr,lastRangeStr);
		    		
			    	closureInitiatedCurrentWeek = transactionDao.getProjectClosureInitiatedCount(startRange,lastRange,assignedGroup,projectIdList);
			    	countTotalWeeklyclosureInitiated+=closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size();
			    	int totalPendingClosure=closureInitiatedCurrentWeek.size()+ListTechnicalClosurePendingWeekly.size();
			    	 GroupData.add(""+totalPendingClosure);
			    	 
			    	
			    	
		    		 }
		    		 
		    		 
		    		 //Technically closed
		    		 List<Object[]> technicallyCloseWeek=null;
			    	 technicallyCloseWeek=transactionDao.technicallyClosedProjectCurrentWeek(startRange,lastRange,assignedGroup);
			    	 technicallyCloseWeekly+=technicallyCloseWeek.size();
			    	 
			    	 GroupData.add(""+technicallyCloseWeek.size());
				    	 
			    	
			    	 //Financial Closure Pending
			    	 
			    	 List<Object[]> financialPendingWeek=null;
			    	 
			    	  financialPendingWeek=transactionDao.financialPendingProjectCurrentWeek(startRange,lastRange,assignedGroup);
			    		financialPendingWeekly+=financialPendingWeek.size();
			    		GroupData.add(""+financialPendingWeek.size());
			    	
		    		 //Total Closed
			    		
			    		 List<Object[]> listCompletedProjectWeeklyByGroup=  projectMasterDao.getCompletedProjectDataByGroup(assignedGroup);
				    	 
				    	 StringBuilder closedProjectId = new StringBuilder();
				    	 if(null != listCompletedProjectWeeklyByGroup){
								for(int lists=0;lists<listCompletedProjectWeeklyByGroup.size();lists++){
									Object[] obj = listCompletedProjectWeeklyByGroup.get(lists);
									
									 closedProjectId.append(((BigInteger) obj[10]).toString());
									if(lists<listCompletedProjectWeeklyByGroup.size()-1) {
										
										closedProjectId.append(",");
									}
									
									}
				    	 }
				    	 
				    	 String closedProjectIds= closedProjectId.toString();
				    	 
						   /* System.out.println("strAssignedGroup "+strAssignedGroup+" "+sbProjectId.toString());*/
						    List<String> closedProjectIdArr =Arrays.asList(closedProjectIds.split(","));
			    		
			    		List<Object[]> completedProjectCurrentWeek=null;
				    	 
				    		 
				    		 if(closedProjectIds!=null||closedProjectIds!=""){
				    			 
				    			 completedProjectCurrentWeek = transactionDao.completedProjectCurrentWeek(startRange,lastRange,closedProjectIdArr);
				    			 weeklyclosedProjectCount+=completedProjectCurrentWeek.size();
				    			 
				    			 GroupData.add(""+completedProjectCurrentWeek.size());
					    	
				    		 }
				    	 
		    		 
		    		 
		    		 groupDataList.add(GroupData);
	        		 
			    	 
        			} 
        			groupName.put(""+assignedGroup, groupDataList);	 
        		}
        		
        		
        		
        		 
        		
            	
            }
            
            time.put("weekly", groupName) ;
          
        
        /*
        Map<String, Map<String, List<List<String>>>> global;
        List<String> a;
        List<List<String>> a1;
        a1.add(a)
        Map<String, List<List<String>>> a2;
        a2.put("",a1);
        global.put("",a2);        
        */
       
        return time;
    }
	
}


















