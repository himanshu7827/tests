package in.pms.transaction.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DecimalFormat;

import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.master.dao.ManpowerRequirementDao;
import in.pms.master.domain.ManpowerRequirementDomain;
import in.pms.master.model.EmployeeRoleMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.service.EmployeeRoleMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.transaction.dao.ManpowerExpensesDao;
import in.pms.transaction.model.DesignationForClientModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class ManpowerExpensesServiceImpl implements ManpowerExpensesService {

	@Autowired
	ManpowerExpensesDao manpowerExpensesDao;

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	EmployeeRoleMasterService employeeRoleMasterService;
	
	@Autowired
	ManpowerRequirementDao manpowerRequirementDao;

	@Override
	public void downloadManpowerExpensesExcel(ProjectMasterForm projectMasterModel, HttpServletResponse response) {
        try {
            // Create Excel workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Manpower Expenses");

            // Create headers row
            Row headersRow = sheet.createRow(0);
            String[] headers = {"S.No.", "Project Reference No", "Project Name", "Project Duration", "Start Date",
                                "End Date", "C-DAC Outlay", "Team Details (Involvement % and Duration)", "Role", "Base Cost (Since 01/04/2024 onwards)",
                                "Rate Per Man Month", "Monthly Expenses (Based on % of involvement)", "Selected Duration Expenses ("+projectMasterModel.getStartDate()+" - "+projectMasterModel.getEndDate()+")",
                                "Total Expenses for Duration ("+projectMasterModel.getStartDate()+" - "+projectMasterModel.getEndDate()+")"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headersRow.createCell(i);
                cell.setCellValue(headers[i]);
                
                // Set font size for the header row to 15
                XSSFFont font = workbook.createFont();
                font.setFontHeightInPoints((short) 15);
                font.setBold(true);
                XSSFCellStyle style = workbook.createCellStyle();
                style.setFont(font);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                
                // Set background color for header row
                XSSFColor color = new XSSFColor(new java.awt.Color(0xD8, 0xD8, 0xD8));
                style.setFillForegroundColor(color);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                
                // Apply wrap text and border to header row
                style.setWrapText(true);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                
                cell.setCellStyle(style);
            }
            
            // Set height of header row
            headersRow.setHeightInPoints(58.5f);
            
            // Set column widths
            sheet.setColumnWidth(0, (int)(9.14 * 256));
            sheet.setColumnWidth(1, (int)(27.86 * 256));
            sheet.setColumnWidth(2, (int)(30.71 * 256));
            sheet.setColumnWidth(3, (int)(23.66 * 256));
            sheet.setColumnWidth(4, (18 * 256));
            sheet.setColumnWidth(5, (18 * 256));
            sheet.setColumnWidth(6, (20 * 256));
            sheet.setColumnWidth(7, (int)(84.14 * 256));
            sheet.setColumnWidth(8, (21 * 256));
            sheet.setColumnWidth(9, (21 * 256));
            sheet.setColumnWidth(10, (20 * 256));
            sheet.setColumnWidth(11, (23 * 256));
            sheet.setColumnWidth(12, (32 * 256));
            sheet.setColumnWidth(13, (32 * 256));
            sheet.setColumnWidth(15, (70 * 256));

            // Excel data, replace with your actual data retrieval logic
            List<List<String>> data = getDataForExcel(projectMasterModel);

            // Write data to Excel
            for (int i = 0; i < data.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // Start from row 1, after headers
                List<String> rowData = data.get(i);
                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = dataRow.createCell(j);
                    if (j == 7) { // Assuming employee names are in the 8th column
                        setRichTextForCell(workbook, cell, rowData.get(j));
                    }
                    else if(j == 6 || j == 13) {
                    	double numericValue = Double.parseDouble(rowData.get(j).replaceAll("[^0-9.]", ""));
                    	cell.setCellValue(numericValue);
                    }
                    else {
                        cell.setCellValue(rowData.get(j));
                    }
                    //cell.setCellValue(rowData.get(j));
                    
                    // Set font size for other rows to 11
                    XSSFFont font = workbook.createFont();
                    font.setFontHeightInPoints((short) 11);
                    XSSFCellStyle style = workbook.createCellStyle();
                    style.setFont(font);
                    style.setAlignment(HorizontalAlignment.LEFT);
                    
                    DataFormat dataFormat = workbook.createDataFormat();
                    
                    if (j >= 7 && j <= 12) { // Columns 8 to 13 (0-based index, so 7 to 12)
                    	style.setVerticalAlignment(VerticalAlignment.TOP);
                    	if(j >= 9 && j <= 12) {
                    		style.setDataFormat(dataFormat.getFormat("[$₹-4009] #,##0.00"));
                    	}
                    } else {
                    	if(j == 6 || j == 13) {
                    		style.setDataFormat(dataFormat.getFormat("[$₹-4009] #,##0.00"));
                    	}
                    	style.setVerticalAlignment(VerticalAlignment.CENTER);
                    }                   
                    
                    // Apply wrap text and border to header row
                    style.setWrapText(true);
                    style.setBorderTop(BorderStyle.THIN);
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderLeft(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    
                    cell.setCellStyle(style);
                }
            }
            
            // Add remark to the second row, column P
            Row secondRow = sheet.getRow(1);
            if (secondRow == null) {
                secondRow = sheet.createRow(1);
            }
            Cell remarkCell = secondRow.createCell(15); // P is the 16th column, index is 15
            remarkCell.setCellValue("*Formula for calculating Selected Duration Expenses:\n"
            		+ "s = (r / 30.0)*d* (i / 100.0)\n\nwhere s = Selected Duration Expenses, r = RatePerManMonth, "
            		+ "d = days for which employee is mapped in selected duration, i = involvementPercentage");

            XSSFFont remarkFont = workbook.createFont();
			/*remarkFont.setFontHeightInPoints((short) 11);
			remarkFont.setBold(false);*/ // Not bold
            remarkFont.setColor(new XSSFColor(new java.awt.Color(255, 0, 0))); // Set text color to red
            XSSFCellStyle remarkStyle = workbook.createCellStyle();
            remarkStyle.setFont(remarkFont);
            remarkStyle.setVerticalAlignment(VerticalAlignment.TOP);
            remarkStyle.setWrapText(true);
            remarkCell.setCellStyle(remarkStyle);
            
            // Create "Base Cost Breakup" sheet
            Sheet baseCostSheet = workbook.createSheet("Base Cost Breakup");

            // Add title to the first row and merge cells
            Row titleRow = baseCostSheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Base Cost Breakup");
            XSSFCellStyle titleStyle = workbook.createCellStyle();
            XSSFFont titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);
            baseCostSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            // Leave a row blank
            baseCostSheet.createRow(1);

            // Set column widths
            baseCostSheet.setColumnWidth(0, (8 * 256));
            baseCostSheet.setColumnWidth(1, (33 * 256));
            baseCostSheet.setColumnWidth(2, (24 * 256));
            baseCostSheet.setColumnWidth(3, (31 * 256));
            baseCostSheet.setColumnWidth(4, (27 * 256));
            baseCostSheet.setColumnWidth(5, (25 * 256));
            
            // Get the project category map with base costs
            Map<String, Map<String, List<Double>>> projectCategoryMap = projectCategoryMap(projectMasterModel);

            // Create a style for category rows
            XSSFCellStyle categoryStyle = workbook.createCellStyle();
            XSSFFont categoryFont = workbook.createFont();
            categoryFont.setFontHeightInPoints((short) 15);
            categoryFont.setBold(true);
            categoryStyle.setFont(categoryFont);
            categoryStyle.setAlignment(HorizontalAlignment.CENTER);
            categoryStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            categoryStyle.setWrapText(true);
            categoryStyle.setBorderTop(BorderStyle.THIN);
            categoryStyle.setBorderBottom(BorderStyle.THIN);
            categoryStyle.setBorderLeft(BorderStyle.THIN);
            categoryStyle.setBorderRight(BorderStyle.THIN);
            XSSFColor categoryColor = new XSSFColor(new java.awt.Color(0xD8, 0xD8, 0xD8));
            categoryStyle.setFillForegroundColor(categoryColor);
            categoryStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            // Create a style for data rows
            XSSFCellStyle dataCellStyle = workbook.createCellStyle();
            XSSFFont dataFont = workbook.createFont();
            dataFont.setFontHeightInPoints((short) 11);
            dataCellStyle.setFont(dataFont);
            dataCellStyle.setWrapText(true);
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);
            dataCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            dataCellStyle.setBorderTop(BorderStyle.THIN);
            dataCellStyle.setBorderBottom(BorderStyle.THIN);
            dataCellStyle.setBorderLeft(BorderStyle.THIN);
            dataCellStyle.setBorderRight(BorderStyle.THIN);
            
            // Create a style for data rows
            XSSFCellStyle costCellStyle = workbook.createCellStyle();
            XSSFFont costFont = workbook.createFont();
            dataFont.setFontHeightInPoints((short) 11);
            costCellStyle.setFont(costFont);
            costCellStyle.setWrapText(true);
            costCellStyle.setAlignment(HorizontalAlignment.LEFT);
            costCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            costCellStyle.setBorderTop(BorderStyle.THIN);
            costCellStyle.setBorderBottom(BorderStyle.THIN);
            costCellStyle.setBorderLeft(BorderStyle.THIN);
            costCellStyle.setBorderRight(BorderStyle.THIN);
            DataFormat dataFormat = workbook.createDataFormat();
            costCellStyle.setDataFormat(dataFormat.getFormat("[$₹-4009] #,##0.00"));

            int currentRow = 2; // Start from the third row
            int serialNumber = 1;

            for (Map.Entry<String, Map<String, List<Double>>> categoryEntry : projectCategoryMap.entrySet()) {
                // Add project category title and merge cells
                Row categoryRow = baseCostSheet.createRow(currentRow++);
                Cell categoryCell = categoryRow.createCell(0);
                categoryCell.setCellValue(categoryEntry.getKey());
                categoryCell.setCellStyle(categoryStyle);
                int mergedRegionStart = currentRow - 1;
                baseCostSheet.addMergedRegion(new CellRangeAddress(currentRow - 1, currentRow - 1, 0, 5));
                
                // Set row height of categoryRow to 25 points
                categoryRow.setHeightInPoints(25);
                
                // Apply borders to the merged category cell
                for (int row = mergedRegionStart; row <= currentRow - 1; row++) {
                    Row currentMergedRow = baseCostSheet.getRow(row);
                    if (currentMergedRow == null) {
                        currentMergedRow = baseCostSheet.createRow(row);
                    }
                    for (int col = 0; col <= 5; col++) {
                        Cell cell = currentMergedRow.getCell(col);
                        if (cell == null) {
                            cell = currentMergedRow.createCell(col);
                        }
                        cell.setCellStyle(categoryStyle);
                    }
                }

                // Add headers row
                Row categoryHeadersRow = baseCostSheet.createRow(currentRow++);
                String[] categoryHeaders = {"S.No.", "Designations", "Base Cost (Upto 31/03/23)", "Base Cost (From 01/04/23 To 31/03/24)", "Base Cost (Since 01/04/24 onwards)", "Effective Base Cost"};
                for (int i = 0; i < categoryHeaders.length; i++) {
                    Cell cell = categoryHeadersRow.createCell(i);
                    cell.setCellValue(categoryHeaders[i]);
                    cell.setCellStyle(categoryStyle);
                }

                // Add data rows
                for (Map.Entry<String, List<Double>> designationEntry : categoryEntry.getValue().entrySet()) {
                    Row dataRow = baseCostSheet.createRow(currentRow++);
                    Cell serialCell = dataRow.createCell(0);
                    serialCell.setCellValue(serialNumber++);
                    serialCell.setCellStyle(dataCellStyle);
                    Cell designationCell = dataRow.createCell(1);
                    designationCell.setCellValue(designationEntry.getKey());
                    designationCell.setCellStyle(dataCellStyle);

                    List<Double> baseCosts = designationEntry.getValue();
                    for (int i = 0; i < baseCosts.size(); i++) {
                        Double cost = baseCosts.get(i);
                        Cell costCell = dataRow.createCell(2 + i);
                        if (cost != null) {
                            costCell.setCellValue(cost);
                        }
                        costCell.setCellStyle(costCellStyle);
                    }
                }

                // Leave a row blank
                currentRow++;
                serialNumber = 1; // Reset serial number for next category
            }
            
            // Leave 2 rows blank
            currentRow ++;

            // Create remark cell by merging columns 2 to 5
            CellRangeAddress remarkCellRange = new CellRangeAddress(currentRow, currentRow, 1, 4);
            baseCostSheet.addMergedRegion(remarkCellRange);
            Row remarkRow = baseCostSheet.createRow(currentRow);
            Cell remarkCellBaseCost = remarkRow.createCell(1);
            remarkCellBaseCost.setCellValue("*Formula for calculating Effective Base Cost:\r\n"
            		+ "e = (a1+a2+………….+aNbN) / T\r\n"
            		+ "\r\n"
            		+ "where e =  Effective Base Cost, aN = days for which nth base cost is applicable in selected duration , bN = Base Cost in nth duration,\r\n"
            		+ "T= total number of days in the selected duration");
            remarkCellBaseCost.setCellStyle(remarkStyle);

            // Set row height for remark cell
            remarkRow.setHeightInPoints(75f); // Set height for 2 rows
            
            // Convert Excel workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();

            // Set HTTP headers for file download
            String filename = "manpower_expenses.xlsx";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // Write Excel bytes to response output stream
            ServletOutputStream outputStream1 = response.getOutputStream();
            outputStream1.write(excelBytes);
            outputStream1.flush();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
            // For example, you can set an error message in response and return an error view
        }
    }

    // Method to generate data, replace with your actual data retrieval logic
	private List<List<String>> getDataForExcel(ProjectMasterForm projectMasterModel) {
	    String encProjectIdsString = projectMasterModel.getEncProjectId();
	    encProjectIdsString = encProjectIdsString.substring(1, encProjectIdsString.length() - 1);
	    encProjectIdsString = encProjectIdsString.replace("\"", "");
	    String[] encProjectIdsArray = encProjectIdsString.split(",");
	    List<List<String>> rows = new ArrayList<>();
	    int serialNumber = 1;
	    DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
	    
	    for (String encProjectId : encProjectIdsArray) {
	        String strProjectId = encryptionService.dcrypt(encProjectId.trim());
	        try {
	            long projectId = Long.parseLong(strProjectId);
	            ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(projectId);
	            List<String> rowData = new ArrayList<>();
	            rowData.add(String.valueOf(serialNumber++));
	            rowData.add(projectMasterForm.getProjectRefrenceNo());
	            rowData.add(projectMasterForm.getStrProjectName());
	            
				/*LocalDate startDate = LocalDate.parse(projectMasterForm.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				LocalDate endDate = LocalDate.parse(projectMasterForm.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				Period period = Period.between(startDate, endDate);
				int months = period.getMonths() + period.getYears() * 12;*/
	            
	            Date startDate = null;
	            Date endDate = null;
	            if(null != projectMasterForm.getStartDate() && !projectMasterForm.getStartDate().equals("")){
	            	try {
						startDate = DateUtils.dateStrToDate(projectMasterForm.getStartDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
	            }
	            if(null != projectMasterForm.getEndDate() && !projectMasterForm.getEndDate().equals("")){
	            	try {
						endDate = DateUtils.dateStrToDate(projectMasterForm.getEndDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
	            }
								
	            int months = DateUtils.getDurationInMonths(startDate, endDate);

	            rowData.add(months+" months");
	            rowData.add(projectMasterForm.getStartDate());
	            rowData.add(projectMasterForm.getEndDate());
	            //rowData.add("₹ "+String.format("%.2f",projectMasterForm.getProjectCost()));
				/*rowData.add("₹ "+decimalFormat.format(projectMasterForm.getProjectCost()));*/
	            rowData.add(String.format("%.2f",projectMasterForm.getProjectCost())+"");
	            StringBuilder empName = new StringBuilder();
	            StringBuilder roles = new StringBuilder();
	            StringBuilder basecost = new StringBuilder();
	            StringBuilder ratePerManMonth = new StringBuilder();
	            StringBuilder monthlyExpenses = new StringBuilder();
	            StringBuilder monthlyExpensesForSelectedRange = new StringBuilder();
	            int serialNo = 0;
	            double sumOfroundedCostForOverlapWithInvolvement = 0;
		        List<EmployeeRoleMasterModel> employeeRoleMasterList1 = employeeRoleMasterService.getAllTeamDetailsByProjectForAllEndDate(strProjectId);
		        for (EmployeeRoleMasterModel employeeRole : employeeRoleMasterList1) 
		        {
		            LocalDate selectedStartDate = LocalDate.parse(projectMasterModel.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            LocalDate selectedEndDate = LocalDate.parse(projectMasterModel.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            LocalDate employeeStartDate = LocalDate.parse(employeeRole.getStrStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            LocalDate employeeEndDate = null;
		            if (employeeRole.getStrEndDate() != null) {
		                employeeEndDate = LocalDate.parse(employeeRole.getStrEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            }

		            LocalDate overlapStartDate = selectedStartDate.isAfter(employeeStartDate) ? selectedStartDate : employeeStartDate;
		            LocalDate overlapEndDate = null;
		            if(employeeEndDate != null) {
		            	overlapEndDate = selectedEndDate.isBefore(employeeEndDate) ? selectedEndDate : employeeEndDate;
		            }
		            else {
		            	overlapEndDate = selectedEndDate;
		            }
		            long daysBetween = ChronoUnit.DAYS.between(overlapStartDate, overlapEndDate)+1;
		        	
		            double involvementPercentage = Double.parseDouble(employeeRole.getInvolvement());
		            if(involvementPercentage == 0 && employeeRole.getStrRoleName().equals("Project Manager")) involvementPercentage=10;
		            
		            if (employeeRole.getStrEmploymentStatus().equals("Relieved"))
		        	{
		            	empName.append((serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + involvementPercentage +" %  (");
		        		
		        		if(employeeRole.getStrEndDate()==null)
		        		{
		        			empName.append("Since "+employeeRole.getStrStartDate()+")");
		        		}else{
		        			empName.append(employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")");
		        		}
		        		empName.append("("+employeeRole.getStrEmploymentStatus()+")\n");
		        	}
		        	else
		        	{
		        		empName.append((serialNo + 1) + ". " + employeeRole.getStrEmpName() + ", " + employeeRole.getDesignation() + ", " + involvementPercentage +" %  (");
		        		
		        		if(employeeRole.getStrEndDate()==null)
		        		{
		        			empName.append("Since "+employeeRole.getStrStartDate()+")\n");
		        		}else{
		        			empName.append(employeeRole.getStrStartDate()+" - "+employeeRole.getStrEndDate()+")\n");
		        		}
		        	}
		            
		            roles.append(employeeRole.getStrRoleName()+"\n");
		            
	        		ManpowerRequirementDomain manpowerRequirementDomain = manpowerRequirementDao.getManpowerRequirementById(employeeRole.getManpowerRequirementId());
	        		double costForOverlapWithInvolvement = 0;
	        		double roundedCostForOverlapWithInvolvement = 0;
	        		double monthlyExpensesWithInvolvement = 0;
	        		double numRatePerManMonth = 0;
	        		
	        		Double effectiveBaseCostFromDesignation;
	        		Double baseCostFromDesignation;
	        		// Define the desired date-time format
	        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        		// Format overlapStartDate and overlapEndDate
	        		String overlapStartFormatted = overlapStartDate.atStartOfDay().format(formatter);
	        		String overlapEndFormatted = overlapEndDate.atStartOfDay().format(formatter);
	        		int numDeputedAt = 0;
	        		if(projectMasterForm.getProjectCategory().equals("Business Project")) {
	        			if(employeeRole.getNumDeputedAt() == 0) {
	        				numDeputedAt = (manpowerRequirementDomain != null) ? (manpowerRequirementDomain.getNumDeputedAt() + 1) : 1;
	        			}
	        			else {
	        				numDeputedAt = employeeRole.getNumDeputedAt();
	        			}
	        		}
	        		
					/*if(manpowerRequirementDomain!=null) {		
					    effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
					    if (effectiveBaseCostFromDesignation == null) {
					    	effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
					    }
					}
					else {
						// Normalize designation if necessary
						String designation = employeeRole.getDesignation();
					    if ("Sr. Technical Officer".equals(designation)) {
					        designation = "Senior Technical Officer";
					    }
					
					    effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
					    
					    if (effectiveBaseCostFromDesignation == null) effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
					}*/
	        		
	        		Set<String> validDesignations = new HashSet<>(Arrays.asList(
	        			    "Scientist F", "Scientist E", "Principal Technical Officer", "Sr. Technical Officer", 
	        			    "Technical Officer", "Project Manager", "Project Engineer", "Scientist G", 
	        			    "Technical Assistant", "Knowledge Associate", "Senior Project Engineer", 
	        			    "Scientist C", "Scientist B", "Scientist D", "Principal Technical Officer-I"
	        			));

        			String designation = employeeRole.getDesignation();

        			if (validDesignations.contains(employeeRole.getDesignation())) {
        			    if ("Sr. Technical Officer".equals(designation)) {
        			        designation = "Senior Technical Officer";
        			    }

        			    effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
        			    
        			    if (effectiveBaseCostFromDesignation == null) {
        			        effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
        			    }
        			    
        			    baseCostFromDesignation = getBaseCostFromDesignationName(designation, numDeputedAt, projectId);
        			    
        			}
        			else if(manpowerRequirementDomain!=null) {		
		        	    effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
		        	    if (effectiveBaseCostFromDesignation == null) {
		        	    	effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
		        	    }
		        	    
		        	    baseCostFromDesignation = getBaseCostFromDesignationId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId);		        	    
	        		}
        			else {
        				effectiveBaseCostFromDesignation = null;
        				baseCostFromDesignation = null;
        			}
	        		
	        	    
	        		if (baseCostFromDesignation != null) basecost.append("₹ "+decimalFormat.format(baseCostFromDesignation)+"\n");
	        		else basecost.append("\n");
	        		
	        		if(manpowerRequirementDomain!=null) {
	        			//basecost.append(manpowerRequirementDomain.getActualRatePerManMonth()+"\n");
	        			numRatePerManMonth = manpowerRequirementDomain.getRatePerManMonth();
	        			if (numRatePerManMonth == 0) ratePerManMonth.append("\n");
	        			else ratePerManMonth.append("₹ "+decimalFormat.format(numRatePerManMonth)+"\n");
	        			
	        			if (numRatePerManMonth == 0 && effectiveBaseCostFromDesignation != null) numRatePerManMonth = effectiveBaseCostFromDesignation;
	        			monthlyExpensesWithInvolvement = numRatePerManMonth * involvementPercentage / 100.0;
		        		monthlyExpenses.append("₹ "+decimalFormat.format(monthlyExpensesWithInvolvement)+"\n");
		        		if (daysBetween >= 0) {
		        			double ratePerDay = numRatePerManMonth / 30.0;
				            double costForOverlap = ratePerDay * daysBetween;
				            costForOverlapWithInvolvement = costForOverlap * involvementPercentage / 100.0;
				            //DecimalFormat df = new DecimalFormat("#.##");
				            roundedCostForOverlapWithInvolvement = costForOverlapWithInvolvement;
			            }
	        		}
	        		else {
	        			ratePerManMonth.append("\n");
	        			if (effectiveBaseCostFromDesignation != null) {
	        				numRatePerManMonth = effectiveBaseCostFromDesignation;
	        				monthlyExpensesWithInvolvement = numRatePerManMonth * involvementPercentage / 100.0;
			        		monthlyExpenses.append("₹ "+decimalFormat.format(monthlyExpensesWithInvolvement)+"\n");
			        		if (daysBetween >= 0) {
			        			double ratePerDay = numRatePerManMonth / 30.0;
					            double costForOverlap = ratePerDay * daysBetween;
					            costForOverlapWithInvolvement = costForOverlap * involvementPercentage / 100.0;
					            //DecimalFormat df = new DecimalFormat("#.##");
					            roundedCostForOverlapWithInvolvement = costForOverlapWithInvolvement;
				            }
	        			}
	        			else {
	        				roundedCostForOverlapWithInvolvement = 0;
			        		monthlyExpenses.append("\n");
	        			}
	        		}
	        		monthlyExpensesForSelectedRange.append("₹ "+decimalFormat.format(roundedCostForOverlapWithInvolvement)+"\n");
	        		sumOfroundedCostForOverlapWithInvolvement += roundedCostForOverlapWithInvolvement;
		            serialNo++;
		        }
		        rowData.add(empName.toString());
		        rowData.add(roles.toString());
		        rowData.add(basecost.toString());
		        rowData.add(ratePerManMonth.toString());
		        rowData.add(monthlyExpenses.toString());
		        rowData.add(monthlyExpensesForSelectedRange.toString());
				/*rowData.add("₹ "+decimalFormat.format(sumOfroundedCostForOverlapWithInvolvement));*/
		        rowData.add(String.format("%.2f",sumOfroundedCostForOverlapWithInvolvement));
	            rows.add(rowData);
	        } catch (NumberFormatException e) {
	            e.printStackTrace(); // Handle parsing errors
	        }
	    }	    
	    return rows;
	}
	
	@Override
	public Double getBaseCostFromDesignationName(String designationName, int numDeputedAt, long projectId){
		return manpowerExpensesDao.getBaseCostFromDesignationName(designationName, numDeputedAt, projectId);
	}
	
	@Override
	public Double getBaseCostFromDesignationId(int designationId, int numDeputedAt, long projectId){
		return manpowerExpensesDao.getBaseCostFromDesignationId(designationId, numDeputedAt, projectId);
	}
	
	@Override
	public Double calculateEffectiveBaseCostByDesgName(String designationName, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted){
		return manpowerExpensesDao.calculateEffectiveBaseCostByDesgName(designationName, numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
	}
	
	@Override
	public Double calculateEffectiveBaseCostByDesgId(int designationId, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted){
		return manpowerExpensesDao.calculateEffectiveBaseCostByDesgId(designationId, numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
	}
	
	private void setRichTextForCell(XSSFWorkbook workbook, Cell cell, String value) {
        XSSFRichTextString richString = new XSSFRichTextString(value);

		XSSFFont normalFont = workbook.createFont();
	    normalFont.setFontHeightInPoints((short) 11);

	    XSSFFont boldFont = workbook.createFont();
	    boldFont.setFontHeightInPoints((short) 11);
	    boldFont.setBold(true);

	    String[] lines = value.split("\n");
	    int startIndex = 0;
	    for (String line : lines) {
            // Invert the logic: Apply bold font to text that does NOT end with "(Relieved)"
	    	XSSFFont fontToUse = line.endsWith("(Relieved)") ? normalFont : boldFont;
            richString.applyFont(startIndex, startIndex + line.length(), fontToUse);
            startIndex += line.length() + 1; // +1 for the newline character
        }

	    cell.setCellValue(richString);
	}
	
	private Map<String, Map<String, List<Double>>> projectCategoryMap(ProjectMasterForm projectMasterModel) {
		String encProjectIdsString = projectMasterModel.getEncProjectId();
		encProjectIdsString = encProjectIdsString.substring(1, encProjectIdsString.length() - 1);
		encProjectIdsString = encProjectIdsString.replace("\"", "");
		String[] encProjectIdsArray = encProjectIdsString.split(",");
		Map<String, Map<String, List<Double>>> projectCategoryMap = new HashMap<>();
		for (String encProjectId : encProjectIdsArray) {
	        String strProjectId = encryptionService.dcrypt(encProjectId.trim());
	        try {
	            long projectId = Long.parseLong(strProjectId);
	            ProjectMasterForm projectMasterForm = projectMasterService.getProjectDetailByProjectId(projectId);
				List<EmployeeRoleMasterModel> employeeRoleMasterList1 = employeeRoleMasterService.getAllTeamDetailsByProjectForAllEndDate(strProjectId);
				for (EmployeeRoleMasterModel employeeRole : employeeRoleMasterList1) 
				{
					LocalDate selectedStartDate = LocalDate.parse(projectMasterModel.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            LocalDate selectedEndDate = LocalDate.parse(projectMasterModel.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		            
		            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            String overlapStartFormatted = selectedStartDate.atStartOfDay().format(formatter);
	        		String overlapEndFormatted = selectedEndDate.atStartOfDay().format(formatter);
					
					ManpowerRequirementDomain manpowerRequirementDomain = manpowerRequirementDao.getManpowerRequirementById(employeeRole.getManpowerRequirementId());
					
					int numDeputedAt = 0;
	        		if(projectMasterForm.getProjectCategory().equals("Business Project")) {
	        			if(employeeRole.getNumDeputedAt() == 0) {
	        				numDeputedAt = (manpowerRequirementDomain != null) ? (manpowerRequirementDomain.getNumDeputedAt() + 1) : 1;
	        			}
	        			else {
	        				numDeputedAt = employeeRole.getNumDeputedAt();
	        			}
	        		}
					
					Set<String> validDesignations = new HashSet<>(Arrays.asList(
				        	"Scientist F", "Scientist E", "Principal Technical Officer", "Sr. Technical Officer", 
				        	"Technical Officer", "Project Manager", "Project Engineer", "Scientist G", 
				        	"Technical Assistant", "Knowledge Associate", "Senior Project Engineer", 
				        	"Scientist C", "Scientist B", "Scientist D", "Principal Technical Officer-I"
				        ));
	
					Double effectiveBaseCostFromDesignation = null;
					
			        String designation = employeeRole.getDesignation();
					if (validDesignations.contains(designation)) {
						if ("Sr. Technical Officer".equals(designation)) {
	    			        designation = "Senior Technical Officer";
	    			    }
						
							effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
        			    
        			    if (effectiveBaseCostFromDesignation == null) {
        			        effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgName(designation, numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
        			    }
					}
					else if(manpowerRequirementDomain!=null) {
						
						effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, overlapStartFormatted, overlapEndFormatted);
		        	    if (effectiveBaseCostFromDesignation == null) {
		        	    	effectiveBaseCostFromDesignation = calculateEffectiveBaseCostByDesgId(manpowerRequirementDomain.getDesignationForClient().getNumDesignationId(), numDeputedAt, projectId, "2023-03-01 00:00:00", "2023-03-31 00:00:00");
		        	    }
						
		        	    designation = manpowerRequirementDomain.getDesignationForClient().getDesignationName();
	        		}
	        		
	        		if (numDeputedAt == 2) {
	        	        designation = designation + " (client)";
	        	    }
					
	        		Map<String, List<Double>> designationMap = projectCategoryMap.computeIfAbsent(projectMasterForm.getProjectCategory(), k -> new HashMap<>());
	                if (!designationMap.containsKey(designation)) {
	                	designation = designation.replaceAll("\\(client\\)", "").trim();
	                    List<DesignationForClientModel> list = getBaseCostDetailsFromDesignationName(projectMasterForm.getProjectCategory(), designation, numDeputedAt);

	                    Double b1 = null;
	                    Double b2 = null;
	                    Double b3 = null;

	                    for (DesignationForClientModel designationRange : list) {
	                    	
	                    	String fromDate = designationRange.getFromDate();
	                    	String toDate = designationRange.getToDate();
	                    	
	                        if (fromDate == null && ("31/03/2023".equals(toDate) || "31/03/2024".equals(toDate))) {
	                            b1 = Double.parseDouble(designationRange.getCost());
	                        }
	                        if (("01/04/2023".equals(fromDate) && "31/03/2024".equals(toDate)) || (fromDate == null && ("31/03/2024".equals(toDate)))) {
	                            b2 = Double.parseDouble(designationRange.getCost());
	                        }
	                        if ("01/04/2024".equals(fromDate) && toDate == null) {
	                            b3 = Double.parseDouble(designationRange.getCost());
	                        }
	                    }
	                    
	                    if (numDeputedAt == 2) {
		        	        designation = designation + " (client)";
		        	    }
	                    
	                    designationMap.put(designation, new ArrayList<>(Arrays.asList(b1, b2, b3, effectiveBaseCostFromDesignation)));
	                }
				}
	        }
	        catch (NumberFormatException e) {
	            e.printStackTrace(); // Handle parsing errors
	        }
		}
		return projectCategoryMap;
	}
	
	@Override
	public List<DesignationForClientModel> getBaseCostDetailsFromDesignationName(String categoryName, String designationName, int numDeputedAt){
		List<DesignationForClientModel> modelList = new ArrayList<>();
		List<Object[]> list = manpowerExpensesDao.getBaseCostDetailsFromDesignationName(categoryName, designationName, numDeputedAt);
		
		for (Object[] objArray : list) {
			DesignationForClientModel model = new DesignationForClientModel();
			model.setNumId((int) objArray[0]);			
			model.setCost(""+(double)objArray[1]);
			if(objArray[2]!=null) model.setFromDate(DateUtils.dateToString((Date) objArray[2]));
			if(objArray[3]!=null) model.setToDate(DateUtils.dateToString((Date) objArray[3]));
			model.setNumDeputedAt((int) objArray[5]);
			model.setDesignationName((String) objArray[7]);
			model.setCategoryName((String) objArray[8]);
			modelList.add(model);
		}
		return modelList;
	}
	
}
