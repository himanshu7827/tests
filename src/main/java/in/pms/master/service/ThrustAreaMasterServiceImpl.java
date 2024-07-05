package in.pms.master.service;

import in.pms.login.util.UserInfo;
import in.pms.master.dao.ThrustAreaMasterDao;
import in.pms.master.domain.ThrustAreaMasterDomain;
import in.pms.master.model.ThrustAreaMasterForm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class ThrustAreaMasterServiceImpl implements ThrustAreaMasterService{
	
	@Autowired
	ThrustAreaMasterDao thrustAreaMasterDao;
	
	@Override
	@PreAuthorize("hasAuthority('READ_THRUSTAREA_MST')")
	public List<ThrustAreaMasterForm> getAllThrustAreaData(){
		return convertThrustMasterDomainToModelList(thrustAreaMasterDao.getAllThrustData());			
	}
	
	@Override
	public List<ThrustAreaMasterForm> getActiveThrustAreaData(){
		return convertThrustMasterDomainToModelList(thrustAreaMasterDao.getActiveThrustData());			
	}

	public List<ThrustAreaMasterForm> convertThrustMasterDomainToModelList(List<ThrustAreaMasterDomain> thrustDataList){
		List<ThrustAreaMasterForm> list = new ArrayList<ThrustAreaMasterForm>();
			for(int i=0;i<thrustDataList.size();i++){
				ThrustAreaMasterDomain thrustAreaMasterDomain = thrustDataList.get(i);
				ThrustAreaMasterForm thrustAreaMasterForm = new ThrustAreaMasterForm();
				thrustAreaMasterForm.setStrThrustAreaName(thrustAreaMasterDomain.getStrThrustAreaName());
				thrustAreaMasterForm.setNumId(thrustAreaMasterDomain.getNumId());
				if(thrustAreaMasterDomain.getNumIsValid()==1){
					thrustAreaMasterForm.setValid(true);
				}else{
					thrustAreaMasterForm.setValid(false);
				}
				
				list.add(thrustAreaMasterForm);
	}
		return list;
	}
	
	@Override
	public String checkDuplicateThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm){
		String result = "";
		ThrustAreaMasterDomain thrustAreaMasterDomain = thrustAreaMasterDao.getThrustAreaByName(thrustAreaMasterForm.getStrThrustAreaName());
	
		 if(null == thrustAreaMasterDomain){
			return null; 
		 }else if(thrustAreaMasterForm.getNumId() != 0){
			 if(thrustAreaMasterDomain.getNumId() == thrustAreaMasterForm.getNumId()){
				 return null; 
			 }else{
				 result = "Thrust Area with same name already exist with Id "+thrustAreaMasterDomain.getNumId();
			 }
		 }
		 else{
				if(thrustAreaMasterDomain.getNumIsValid() == 0){
					result = "Thrust Area Already Registered with Id "+thrustAreaMasterDomain.getNumId() +". Please activate same record";
				}else{
					result = "Thrust Area Already Registered with Id "+thrustAreaMasterDomain.getNumId();
				}			
			 }
		return result;
	}
	
	@Override
	@PreAuthorize("hasAuthority('WRITE_THRUSTAREA_MST')")
        public long saveThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm){
		ThrustAreaMasterDomain thrustAreaMasterDomain = convertThrustMasterModelToDomain(thrustAreaMasterForm);
		return thrustAreaMasterDao.mergeThrustAreaMaster(thrustAreaMasterDomain);
	}
	
	public ThrustAreaMasterDomain convertThrustMasterModelToDomain(ThrustAreaMasterForm thrustAreaMasterForm){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		ThrustAreaMasterDomain thrustAreaMasterDomain = new ThrustAreaMasterDomain();
		if(thrustAreaMasterForm.getNumId()!=0){		
			thrustAreaMasterDomain =  thrustAreaMasterDao.getAllThrustDataById(thrustAreaMasterForm.getNumId());
		}
		thrustAreaMasterDomain.setStrThrustAreaName(thrustAreaMasterForm.getStrThrustAreaName());
		thrustAreaMasterDomain.setDtTrDate(new Date());
		thrustAreaMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		if(thrustAreaMasterForm.isValid()){
			thrustAreaMasterDomain.setNumIsValid(1);
			}else{
				thrustAreaMasterDomain.setNumIsValid(0);

			}
	
		
		return thrustAreaMasterDomain;
	}

	public void deleteThrustAreaData(ThrustAreaMasterForm thrustAreaMasterForm) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		int count= thrustAreaMasterForm.getNumIds().length;
		long[] thrustMaster_ids= new long[count];
		thrustMaster_ids= thrustAreaMasterForm.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ThrustAreaMasterDomain thrustAreaMasterDomain= thrustAreaMasterDao.getAllThrustDataById(thrustMaster_ids[i]);			
			thrustAreaMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			thrustAreaMasterDomain.setDtTrDate(new Date());
			thrustAreaMasterDomain.setNumIsValid(2);
			thrustAreaMasterDao.mergeThrustAreaMaster(thrustAreaMasterDomain);
			
			
		}		
	}
	
	@Override
	public List<ThrustAreaMasterDomain> getThrustAreaData(String ids){
		return thrustAreaMasterDao.getThrustAreaData(ids);
	}
}
