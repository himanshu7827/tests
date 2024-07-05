package in.pms.master.service;

import in.pms.master.dao.BudgetHeadMasterDao;
import in.pms.master.dao.ProcessMasterDao;
import in.pms.master.dao.RoleMasterDao;
import in.pms.master.domain.BudgetHeadMasterDomain;
import in.pms.master.domain.ProcessMasterDomain;
import in.pms.master.domain.RoleMasterDomain;
import in.pms.master.model.ProcessMasterModel;
import in.pms.master.model.RoleMasterModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ProcessMasterServiceImp implements ProcessMasterService {
	
	@Autowired
	ProcessMasterDao processMasterDao;
	
	public List<ProcessMasterModel> getAllProcessData()
	{
		//System.out.println("in viewProcess imp");
		List<ProcessMasterDomain> processlist=processMasterDao.getAllProcessData();
		List<ProcessMasterModel> result_list=convertProcesstoFrom(processlist);
		
		return result_list;
	}
	
	private List<ProcessMasterModel> convertProcesstoFrom(List<ProcessMasterDomain> processlist)
	{
		//System.out.println("in convertProcesstoFrom imp");
		ProcessMasterDomain process=new ProcessMasterDomain();
		Iterator<ProcessMasterDomain> itr=processlist.iterator();
		List<ProcessMasterModel> result_list=new ArrayList();
	
		while(itr.hasNext())
		{
			ProcessMasterModel ProcessForm=new ProcessMasterModel();
			process=itr.next();
			ProcessForm.setNumProcessId(process.getNumProcessId());
			ProcessForm.setStrProcessName(process.getStrProcessName());
			ProcessForm.setStrProcessPath(process.getStrProcessPath());
			ProcessForm.setStrProcessDescription(process.getStrProcessDescription());
			ProcessForm.setStrProcessEcode(process.getStrProcessEcode());
			if(process.getNumIsValid()==1){
				ProcessForm.setValid(true);
			}else{
				ProcessForm.setValid(false);
			}
			result_list.add(ProcessForm);
		}
		
		return result_list;
		
	}


	@Override
	public long saveUpdateProcessMaster(ProcessMasterModel processMasterModel){
		ProcessMasterDomain processMasterDomain = convertProcessMasterModelToDomain(processMasterModel);
		return processMasterDao.saveUpdateProcessMaster(processMasterDomain);
	}
	
	public ProcessMasterDomain convertProcessMasterModelToDomain(ProcessMasterModel processMasterModel){
		ProcessMasterDomain processMasterDomain = new ProcessMasterDomain();
		if(processMasterModel.getNumProcessId()!=0){		
			processMasterDomain =  processMasterDao.getProcessMasterDomainById(processMasterModel.getNumProcessId());
		}
		processMasterDomain.setStrProcessName(processMasterModel.getStrProcessName());
		processMasterDomain.setStrProcessDescription(processMasterModel.getStrProcessDescription());
		processMasterDomain.setStrProcessPath(processMasterModel.getStrProcessPath());
		processMasterDomain.setStrProcessEcode(processMasterModel.getStrProcessEcode());
		processMasterDomain.setDtTrDate(new Date());
	
		if(processMasterModel.isValid()){
			processMasterDomain.setNumIsValid(1);
			}else{
				processMasterDomain.setNumIsValid(0);

			}		
		
		return processMasterDomain;
	}

	
	@Override
	public String checkDuplicateProcessName(ProcessMasterModel processMasterModel){
		String result = "";
		ProcessMasterDomain processMasterDomain = processMasterDao.getProcessMasterByName(processMasterModel.getStrProcessName());
	
		 if(null == processMasterDomain){
			return null; 
		 }else if(processMasterModel.getNumProcessId() != 0){
			 if(processMasterDomain.getNumProcessId() == processMasterModel.getNumProcessId()){
				 return null; 
			 }else{
				 result = "Process with same name already exist with Id "+processMasterDomain.getNumProcessId();
			 }
		 }
		 else{
				if(processMasterDomain.getNumIsValid() == 0){
					result = "Process Already Registered with Id "+processMasterDomain.getNumProcessId() +". Please activate same record";
				}else{
					result = "Process Already Registered with Id "+processMasterDomain.getNumProcessId();
				}			
			 }
		return result;
	}

	

	public void deletProcessData(ProcessMasterModel processMasterModel) {
		int count= processMasterModel.getNumIds().length;
		long[] processMaster_ids= new long[count];
		processMaster_ids= processMasterModel.getNumIds();
		
		for(int i=0;i<count;i++)
		{
			ProcessMasterDomain processMasterDomain= new ProcessMasterDomain();
			processMasterDomain.setNumProcessId(processMaster_ids[i]);
			
			Date date= new Date();
			processMasterDomain.setDtTrDate(date);

			processMasterDao.deleteProcessMaster(processMasterDomain);
			
			
		}	
	
}
}





