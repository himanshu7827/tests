 package in.pms.transaction.service;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.pms.global.misc.ResourceBundleFile;
import in.pms.global.util.DateUtils;
import in.pms.login.util.UserInfo;
import in.pms.transaction.dao.PatchTrackerDao;
import in.pms.transaction.domain.PatchTrackerDomain;
import in.pms.transaction.model.PatchTrackerModel;

 @Service
public class PatchTrackerServiceImpl implements PatchTrackerService {
	 
	 @Autowired
	 PatchTrackerDao patchTrackerDao;


	 
	 
	@Override
	@PreAuthorize("hasAuthority('WRITE_PATCH_TRACKER')")
     public long  addpatchdetails(PatchTrackerModel patchTrackerModel) {
		
		
		// Making an object for the conversion of ModeltoDomain
		PatchTrackerDomain patchTrackerDomain = convertPatchTrackerModelToDomain(patchTrackerModel);
		
		//calling the JPA repository save function
		return patchTrackerDao.save(patchTrackerDomain).getId();
	}
		public PatchTrackerDomain convertPatchTrackerModelToDomain(PatchTrackerModel patchTrackerModel){
		
			
			//setting the user Id from the session 
			Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo= (in.pms.login.util.UserInfo) authentication.getPrincipal();
			
			
		 
			PatchTrackerDomain patchTrackerDomain = new PatchTrackerDomain();	
			if(patchTrackerModel.isValid())
			   {
				patchTrackerDomain.setValid(true);
			   }
			else{
				patchTrackerDomain.setValid(false);
			    }
			patchTrackerDomain.setValid2(true);
		    patchTrackerDomain.setNumIsValid(1);
			patchTrackerDomain.setId(patchTrackerModel.getId());
			patchTrackerDomain.setSeverity(patchTrackerModel.getSeverity());
		    patchTrackerDomain.setType(patchTrackerModel.getType());
		    patchTrackerDomain.setStrdescription(patchTrackerModel.getStrdescription());
		    patchTrackerDomain.setStrRequestedBy(patchTrackerModel.getStrRequestedBy());
		    patchTrackerDomain.setStrNameOfFiles(patchTrackerModel.getStrNameOfFiles());
		    patchTrackerDomain.setStrTeamMembers(patchTrackerModel.getStrTeamMembers());
		    patchTrackerDomain.setStrBugId(patchTrackerModel.getStrBugId());
	        try {
				patchTrackerDomain.setDepDate(DateUtils.dateStrToDate(patchTrackerModel.getDepDate()));
			    } catch (ParseException e)  
	                {				
				      e.printStackTrace();
			         }
	        patchTrackerDomain.setStage(patchTrackerModel.getStage());
	        patchTrackerDomain.setStrSvnNo(patchTrackerModel.getStrSvnNo());
	        patchTrackerDomain.setStrModules(patchTrackerModel.getStrModules());
			patchTrackerDomain.setNumTrUserId(userInfo.getEmployeeId());
		
			//setting the transaction date
			patchTrackerDomain.setDtTrDate(new Date());
		    return patchTrackerDomain;
		}
	    @Override
		public List<String> getRequesterName(String searchString)
	    {
	    	int days = 0;
	    	String strDays = ResourceBundleFile.getValueFromKey("noOfDaysForPatchSearch").trim();
	    	if(null != strDays){
	    		days = Integer.parseInt(strDays);
	    	}
	    	
	    	Date date = DateUtils.addDays(new Date(),-days);
	    	
	    	return patchTrackerDao.getRequesterName("%"+searchString+"%",date);
		}
		
		@Override
		public List<String> getNameOfFiles(String searchString)
		{
			int days = 0;
	    	String strDays = ResourceBundleFile.getValueFromKey("noOfDaysForPatchSearch").trim();
	    	if(null != strDays){
	    		days = Integer.parseInt(strDays);
	    	}
	    	
	    	Date date = DateUtils.addDays(new Date(),-days);
			return patchTrackerDao.getNameOfFiles("%"+searchString+"%",date);
		}
		
		
		 public List<PatchTrackerModel> convertPatchTrackerDomainToModelList(List<PatchTrackerDomain> patchTrackerDomainList)
		    {
			  List<PatchTrackerModel> list = new ArrayList<PatchTrackerModel>();
				for(int i=0;i<patchTrackerDomainList.size();i++)
				{
					PatchTrackerDomain patchTrackerDomain = patchTrackerDomainList.get(i);
					PatchTrackerModel patchTrackerModel = new PatchTrackerModel();
					patchTrackerModel.setStrModules(patchTrackerDomain.getStrModules());
					patchTrackerModel.setStrNameOfFiles(patchTrackerDomain.getStrNameOfFiles());
					patchTrackerModel.setStrRequestedBy(patchTrackerDomain.getStrRequestedBy());					
					patchTrackerModel.setStrTeamMembers(patchTrackerDomain.getStrTeamMembers());
				}
			return list;
		
		   }
		@Override
		public List<String> getTeamMembers(String searchString) 
		{
			int days = 0;
	    	String strDays = ResourceBundleFile.getValueFromKey("noOfDaysForPatchSearch").trim();
	    	if(null != strDays){
	    		days = Integer.parseInt(strDays);
	    	}
	    	
	    	Date date = DateUtils.addDays(new Date(),-days);
			return patchTrackerDao.getTeamMembers("%"+searchString+"%",date);
		}
		@Override
		public List<String> getModules(String searchString)
		{
			int days = 0;
	    	String strDays = ResourceBundleFile.getValueFromKey("noOfDaysForPatchSearch").trim();
	    	if(null != strDays){
	    		days = Integer.parseInt(strDays);
	    	}
	    	
	    	Date date = DateUtils.addDays(new Date(),-days);
			return patchTrackerDao.getModules("%"+searchString+"%",date);
		}
		
		
		}
	
