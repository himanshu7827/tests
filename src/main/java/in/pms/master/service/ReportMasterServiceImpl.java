package in.pms.master.service;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.WordUtils;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tiles.request.attribute.HasAddableKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import in.pms.global.dao.DaoHelper;
import in.pms.global.misc.FTPPropertiesReader;
import in.pms.global.service.EncryptionService;
import in.pms.global.util.DateUtils;
import in.pms.master.dao.GlobalDao;
import in.pms.master.dao.ReportMasterDao;
import in.pms.master.domain.InterfaceMaster;
import in.pms.master.domain.ReportMaster;
import in.pms.master.model.ReportMasterModel;


@Service
public class ReportMasterServiceImpl implements ReportMasterService
{

	/*
	 * @Autowired EncryptionService encryptionService;
	 */
	
	@Autowired
	ReportMasterDao reportMasterDao;
	
	@Autowired
	private GlobalDao globalDao;
	
	@Autowired
	DaoHelper daoHelper;
	
	
	public List<ReportMasterModel> getInterfaceDetails()
	{
		List<InterfaceMaster> list=reportMasterDao.getInterfaceDetails();
		
		InterfaceMaster list1 = new InterfaceMaster();

		Iterator<InterfaceMaster> itr = list.iterator();
		List<ReportMasterModel> result_list = new ArrayList();

		while (itr.hasNext())
		{
			
			list1 = itr.next();
		ReportMasterModel reportMasterModel=new ReportMasterModel();
		reportMasterModel.setNumInterfaceId(list1.getNumInterfaceId());
		reportMasterModel.setStrInterfaceName(list1.getStrInterfacename());
		result_list.add(reportMasterModel);
	
	}
		return result_list;
	}
	
	
	public List<ReportMasterModel> getReportType() 
	{
		List<ReportMaster> list=reportMasterDao.getReportType();
		
		ReportMaster list1 = new ReportMaster();

		Iterator<ReportMaster> itr = list.iterator();
		List<ReportMasterModel> result_list = new ArrayList();

		while (itr.hasNext())
		{
			
			list1 = itr.next();
			ReportMasterModel reportMaster=new ReportMasterModel();

			reportMaster.setNumReportId(list1.getNumQueryId());
			reportMaster.setStrName(list1.getStrName());
			reportMaster.setStrQuery(list1.getStrQuery());
			reportMaster.setStrDescription(list1.getStrDesc());
			reportMaster.setStrEcode(list1.getStrECode());
			reportMaster.setStrSheetNames(list1.getStrSheetNames());
			result_list.add(reportMaster);
	
	}
		return result_list;	
   }
	
	public void DeleteReport(ReportMasterModel reportMasterModel, int userId) 
	{
		reportMasterDao.DeleteReport(reportMasterModel,userId);
		
	}
	

	public Map<String,Object> getQueryData(int queryId) {
		
		List<ReportMaster> reportDetailList = new ArrayList<ReportMaster>();
		ReportMaster reportmaster = new ReportMaster();
		Map<String,Object> mapData=new HashMap<String, Object>();
		reportDetailList = reportMasterDao.getReportData(queryId);
		int interfaceId=reportDetailList.get(0).getInterfaceMaster().getNumInterfaceId();
		String interfaceName=reportDetailList.get(0).getInterfaceMaster().getStrInterfacename();
			reportmaster = reportDetailList.get(0);
			mapData.put("txtEditor", reportmaster);
			mapData.put("InterfaceName", interfaceName);
			mapData.put("InterfaceId", interfaceId);
			
			
		return mapData;
	}



public void SaveEdittedReport(ReportMasterModel reportMasterModel, int userId)
{
	reportMasterDao.SaveEdittedReport(reportMasterModel,userId);
	
}



public List<ReportMaster> getReportData(int reportId){
	return reportMasterDao.getReportData(reportId);
}

public List<ReportMasterModel> getReportTypeForInterface(int interfaceId){
	List<ReportMaster> list=reportMasterDao.getReportTypeForInterface(interfaceId);
	
	ReportMaster list1 = new ReportMaster();

	Iterator<ReportMaster> itr = list.iterator();
	List<ReportMasterModel> result_list = new ArrayList();

	while (itr.hasNext())
	{
		
		list1 = itr.next();
		ReportMasterModel reportMaster=new ReportMasterModel();

		reportMaster.setNumReportId(list1.getNumQueryId());
		reportMaster.setStrName(list1.getStrName());
		reportMaster.setStrQuery(list1.getStrQuery());
		reportMaster.setStrDescription(list1.getStrDesc());
		reportMaster.setStrEcode(list1.getStrECode());
		result_list.add(reportMaster);

}
	return result_list;	
}
public boolean isDuplicateReport(String eCode){
	return reportMasterDao.isDuplicateReport(eCode);
}


@Override
/*@PreAuthorize("hasAnyAuthority('GenerateReport','GenerateBirthDayReport')")*/
public String generateReportForInterface(String query,String fileName,String strSheetNames,HttpServletRequest request, HttpServletResponse response) {
	   String filename="";		     
	   String result="EmptyPage";
	   String filepath = FTPPropertiesReader.getValueFromKey("TempFilePath");
	   if(fileName!=null){
	    filename=fileName +".xlsx";
	   }
	   else{
	    filename="excelsheetdata.xlsx";
	   }
	   String dateFormat = "yyyy-MM-dd hh:mm:ss";
       Date date = new Date();	       
       String[] strSheetDescription = null;
              
       
       //String strQueries []=query.split("$$$$$$$$$$$");
       String[] strQueries =query.split("#@#@#@#@#");
       if(!strSheetNames.equals("null")){
       strSheetDescription=strSheetNames.split("#@#@#@#@#");
       }
       try{
    	   Connection _conn=daoHelper.getConnection();   
    	   XSSFWorkbook xlsWorkbook=new XSSFWorkbook();
    	for (int iCount=0; iCount <strQueries.length; iCount++ )
    	{
    		String strSinglequery=strQueries[iCount];
    	  
    	   PreparedStatement psmnt = _conn.prepareStatement(strSinglequery);
    		
    	    if(strSinglequery.toLowerCase().contains("update ") ||strSinglequery.toLowerCase().contains("delete ") || strSinglequery.toLowerCase().contains("insert "))
	           {
	        	  return "Blank"; 
	        	   
	           }
           ResultSet rs = psmnt.executeQuery();
           
            ResultSetMetaData colInfo = rs.getMetaData();
         		            
            XSSFSheet xlsSheet;
            try{
            	if(!strSheetNames.equals("null")){
            	
            	xlsSheet = xlsWorkbook.createSheet(strSheetDescription[iCount]);

            	}
            	else{
            		xlsSheet = xlsWorkbook.createSheet();
            	}
            }
            catch(IndexOutOfBoundsException e)
            {
            	xlsSheet = xlsWorkbook.createSheet();
            }
            
         	
         	
         	XSSFCellStyle my_style = xlsWorkbook.createCellStyle();
         		          
          /* my_style.setFillForegroundColor(new XSSFColor(Color.BLUE));
           my_style.setFillBackgroundColor(new XSSFColor(Color.BLUE));*/
          
           XSSFFont font = (XSSFFont) xlsWorkbook.createFont();
         
           font.setColor(new XSSFColor(new Color(0, 0, 255)));
          /* font.setColor(new XSSFColor(Color.WHITE));
           font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);*/
           my_style.setFont(font);
           
           CreationHelper createHelper = xlsWorkbook.getCreationHelper();
           XSSFCellStyle dateStyle = xlsWorkbook.createCellStyle();
           dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
          
           dateStyle.setFont(font);
           
           short rowIndex = 0;
           String[] arrColType={};
           List<String> colType=new ArrayList<String>();
           
           List<String> colNames = new ArrayList<String>();
           XSSFRow titleRow = xlsSheet.createRow(rowIndex++);
           
           for (int i =1; i <= colInfo.getColumnCount(); i++) 
           {  
        	  colNames.add(colInfo.getColumnName(i));
        	  String[] name =colInfo.getColumnName(i).split("##");
    	      if(name.length>1)
        	      {
        	      colType.add(name[1]);
        	      
        	      Cell cell = titleRow.createCell(i-1);    
	           	  cell.setCellStyle(my_style);
	           	  cell.setCellValue(new XSSFRichTextString(WordUtils.capitalizeFully(name[0].replaceAll("_", " "))));
        	      }
    	      else
        	      {
    	    	  colType.add("0");
        	    	  Cell cell = titleRow.createCell(i-1);    
    	           	  cell.setCellStyle(my_style);
    	           	  cell.setCellValue(new XSSFRichTextString(WordUtils.capitalizeFully(colInfo.getColumnName(i).replaceAll("_", " "))));
        	      }
    	      xlsSheet.setColumnWidth((short) (i-1), (short) 4000);
    	     
            }
                arrColType = colType.toArray(new String[0]);
           
           while (rs.next()) {
        	
        	   XSSFRow dataRow = xlsSheet.createRow(rowIndex++);
        	     
        	      int colIndex = 0;
        	      for (String colName : colNames) {
        	    	    
	            
        	    	  if(rowIndex>1 && arrColType[colIndex].equals("1"))
        	    	  {		String longTemp=rs.getString(colName);
    	    	  	  		if(longTemp!=null && !longTemp.equals("")){
    	    	  	  		try{	    	    	  	  			
    	    	  	  			dataRow.createCell(colIndex).setCellValue(Long.parseLong(rs.getString(colName)));
    	    	  	  			
    	    	  	  			}
    	    	  	  			catch(Exception e){	    	    	  	  			
    	    	  	  			dataRow.createCell(colIndex).setCellValue(longTemp);
    	    	  	  	
    	    	  	  			}
    	    	  	  		}
    	    	  	  		else{
    	    	  	  		dataRow.createCell(colIndex).setCellValue(" ");	
    	    	  	  		}
        	    	  }
        	    	  else if(rowIndex>1 && arrColType[colIndex].equals("2"))
        	    	  {   
        	    		  String dateTemp=rs.getString(colName);	        	    		  
        	    	  	  if(dateTemp!=null && !dateTemp.equals("")){
        	    	  		try{
	        	    		  date =DateUtils.dateStrToDate(rs.getString(colName));
	        	    		 // DateFormat = simpleDateFormat.format(date);
	        	    		  XSSFCell dateCell =  dataRow.createCell(colIndex);	
	        	    		  dateCell.setCellValue(date);
	        	    		  dateCell.setCellStyle(dateStyle);
        	    	  		}
        	    	  		catch(Exception e){
        	    	  			dataRow.createCell(colIndex);//.setCellValue(dateTemp);	
        	    	  			
        	    	  		  }
        	    	  	  }
	        	    	  else{
	        	    		  dataRow.createCell(colIndex);//.setCellValue(" ");			        	    		 
	        	    	  }
        	    	  }
        	    	  else
        	    	  {
        	     		  XSSFCellStyle headerCellStyle= xlsWorkbook.createCellStyle(); 
        	    		  XSSFCell cell = dataRow.createCell(colIndex);
        	    		  
        	    		  String value = rs.getString(colName);
        	    		  XSSFRichTextString textString = new XSSFRichTextString(value); 
        	    		  headerCellStyle = xlsWorkbook.createCellStyle();  
            	    	  headerCellStyle.setWrapText(true); 
            	    	  cell.setCellStyle(headerCellStyle); 
            	    	  cell.setCellValue(textString);
	    		  		 
        	    	  }
        	    	  colIndex=colIndex+1;
        	      }
        	    }
           psmnt.close();
    	}
    	
    	_conn.close();
        	    // Write to disk
            xlsWorkbook.write(new FileOutputStream(filepath+filename));
    
	   
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition","attachment; filename=\""+filename+"\"");
			response.setHeader("Set-Cookie","fileDownload=true; path=/");
         
		    response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
					response.setHeader("Pragma", "public");
			


			FileInputStream inputstream=new FileInputStream(filepath+filename);
		    OutputStream out=response.getOutputStream();
		    FileCopyUtils.copy(inputstream, out);
		    out.close();
		    
         
         
    	}
    	catch(Exception e) {	    	
    		e.printStackTrace();
	         	String tempFileName="error.xlsx";	    
		   
			    response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-disposition","attachment; filename=\""+tempFileName+"\"");
				response.setHeader("Set-Cookie","fileDownload=true; path=/");
	         
			    response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
						response.setHeader("Pragma", "public");
				
				try {
					FileInputStream  inputstream = new FileInputStream(filepath+"error.xlsx");
					 OutputStream out=response.getOutputStream();
					    FileCopyUtils.copy(inputstream, out);
					    out.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
    	}
       return result;
    }

public HSSFColor setColor(HSSFWorkbook workbook){
	byte r=4;
	byte g=93;
	byte b=124;
    HSSFPalette palette = workbook.getCustomPalette();
    HSSFColor hssfColor = null;
    try {
        hssfColor= palette.findSimilarColor(r, g, b); 
        if (hssfColor == null ){
            //palette.setColorAtIndex(HSSFColor.DARK_BLUE.index, r, g,b);
            hssfColor = palette.getColor(HSSFColor.DARK_BLUE.index);
        }
    } catch (Exception e) {
    }

    return hssfColor;
}

	public String isReportSchemeSpecificQuery(String eCode){
	return reportMasterDao.isSchemeSpecificQuery(eCode);
}
	
	
	@Override
	public ReportMaster SaveNewReport(ReportMasterModel reportMasterModel,
			int userId) {
		
		ReportMaster reportMaster=new ReportMaster();
		reportMaster.setStrName(reportMasterModel.getStrName());
		reportMaster.setStrDesc(reportMasterModel.getStrDescription());
		reportMaster.setStrQuery(reportMasterModel.getStrQuery());
		reportMaster.setStrSheetNames(reportMasterModel.getStrSheetNames());
		reportMaster.setNumIsValid(1);
		Date date=new Date();
		reportMaster.setDtTrDate(date);
		reportMaster.setNumTrUserId(userId);
		
		List<InterfaceMaster> list=reportMasterDao.getInterfaceDetailsByID(reportMasterModel.getNumInterfaceId());
		
		if(list.size()>0){
			reportMaster.setInterfaceMaster(list.get(0));
		}
		reportMaster.setIsSchemeSpecific(0);
		reportMaster.setStrECode(reportMasterModel.getStrEcode());
		return reportMasterDao.saveReportMaster(reportMaster);
	}
	
	@Override
	public List<ReportMaster> getReportDetailByEcode(String eCode){
		return reportMasterDao.getReportDetailByEcode(eCode);
	}
	
	@PreAuthorize("hasAuthority('READ_REPORT')")
	public void readReportAuthorityCheck(){
		
	}
	
	@Autowired
	public List<ReportMaster> reportName(){
		return reportMasterDao.reportName();
	}
	
	
	 @Override
	 @PreAuthorize("hasAuthority('GENERATE_REPORT_INTERFACE')")
	 public List<ReportMasterModel> getReportsByRoleInterface(int interfaceId) { 
		 return convertRoleReportDetailsToModelList(reportMasterDao.getReportsByRoleInterface(interfaceId)); 
	 }
	
	
	
	 public List<ReportMasterModel>  convertRoleReportDetailsToModelList(List<Object[]>roleReportDetailsList){
	  List<ReportMasterModel> list = new ArrayList<ReportMasterModel>();
	  	for(int i=0;i<roleReportDetailsList.size();i++){
		  Object[] obj =	 roleReportDetailsList.get(i);
		  String strReportId= ""+ obj[0]; 
		  String strReportName= ""+ obj[1];
		  ReportMasterModel reportMasterModel = new  ReportMasterModel(); 
		  reportMasterModel.setNumReportId(Integer.parseInt(strReportId));
		  reportMasterModel.setStrName(strReportName);
		  list.add(reportMasterModel);	  
	  	}	 
	  return list; 
	  }
		
	
}


