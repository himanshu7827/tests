package in.pms.master.dao;
import in.pms.global.dao.DaoHelper;




import in.pms.master.domain.DesignationMasterDomain;
import in.pms.master.model.DesignationMasterModel;
import in.pms.transaction.domain.ApprovedJobDomain;
import in.pms.transaction.model.ApprovedJobModel;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DesignationMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	
	public long saveUpdateDesignationMaster(DesignationMasterDomain designationMasterDomain){
		
		designationMasterDomain =daoHelper.merge(DesignationMasterDomain.class, designationMasterDomain);		
			return designationMasterDomain.getNumId();
		}
		
		public DesignationMasterDomain getDesignationMasterById(long id){
		
			return daoHelper.findById(DesignationMasterDomain.class, id);

		}
		
		public DesignationMasterDomain getDesignationMasterByName(String strDesignationename){
			String query = "from DesignationMasterDomain where lower(designationName)=lower('"+strDesignationename+"') and numIsValid<>2" ;	
			List<DesignationMasterDomain> designationMasterDomainList = daoHelper.findByQuery(query);		
			if(designationMasterDomainList.size()>0){
				return designationMasterDomainList.get(0);
			}
			return null;
		}

		public List<DesignationMasterDomain> getAllDesignationMasterDomain(){
			String query = "from DesignationMasterDomain where numId<>0 and numIsValid<>2 order by dtTrDate,hierarchy";
			return  daoHelper.findByQuery(query);	
		}
		
		public List<DesignationMasterDomain> getAllActiveDesignationMasterDomain(){
			String query = "from DesignationMasterDomain where  numIsValid=1 order by hierarchy";
			return  daoHelper.findByQuery(query);	
		}

		
		public List<DesignationMasterDomain> getDesignationMasterById(String ids){
			List<DesignationMasterDomain> designationMasterrList =  daoHelper.findByQuery ("from DesignationMasterDomain where numId in ("+ids+")");		
			return designationMasterrList;
		}
		
		public List<String> getActiveDesignationName(){
			//String query = "select designationName from DesignationMasterDomain where numIsValid=1 order by hierarchy";
			String query = "select designationName from DesignationMasterDomain where numIsValid=1 order by designationorder";//updated by devesh on 19/6/23 for order by designation order
			return daoHelper.findByQuery(query);
		}
		
		

		public List<DesignationMasterDomain> getSpecificDesigantion(int isThirdPartySpecific) {
			String query = "from DesignationMasterDomain where  numIsValid=1 and numIsThirdPartyDesignation="+isThirdPartySpecific+" order by hierarchy";
			return  daoHelper.findByQuery(query);	
		}
		
		public int  checkUniqueJobCode(ApprovedJobModel approvedJobModel){		
			int flag= 0;
			String previousJobCode="";
			List<String> jobReferences=approvedJobModel.getJobReferences();	
			if(jobReferences.size()>0){
				
				for (int i = 0; i < jobReferences.size(); i++) {
					String jobCode=jobReferences.get(i);
					if(i>0){
					previousJobCode=jobReferences.get(i-1);
					if(jobCode.trim().equalsIgnoreCase(previousJobCode.trim())){
						flag=2;
			 			break;
			 		}
					}
					String query= "Select p from ApprovedJobDomain p where p.numIsValid='1'"  ;
			 		
			 		List<ApprovedJobDomain> count = daoHelper.findByQuery(query);
			 		
			 		for(ApprovedJobDomain ajd:count)
			 		{
			 			if(ajd.getJobCode()!=null && !ajd.getJobCode().equals("") && ajd.getJobCode().trim().equalsIgnoreCase(jobCode.trim()))
			 			{
				 			flag=1;
				 			break;
			 			}	
			 		}	

				}
				
				}

				return flag;
			
			}
		
}
