package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.util.UserInfo;
import in.pms.master.domain.OrganisationMasterDomain;
import in.pms.master.model.EmployeeRoleMasterModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrganisationMasterDao {
	@Autowired
	DaoHelper daoHelper;
	
	
	
	
	public long saveUpdateOrganisationMaster(OrganisationMasterDomain organisationMasterDomain){
		organisationMasterDomain =daoHelper.merge(OrganisationMasterDomain.class, organisationMasterDomain);		
		return organisationMasterDomain.getNumId();
	}
	
	public OrganisationMasterDomain getOrganisationMasterById(long id){
		List<OrganisationMasterDomain> organisationMasterList =  daoHelper.findByQuery("from OrganisationMasterDomain where numId="+id);
		if(organisationMasterList.size()>0){
			return organisationMasterList.get(0);
		}
		return null;
	}
	
	public OrganisationMasterDomain getOrganisationMasterByName(String organisationName){
		String query = "from OrganisationMasterDomain where lower(organisationName)=lower('"+organisationName+"')";	
		List<OrganisationMasterDomain> clientMasterDomainList = daoHelper.findByQuery(query);		
		if(clientMasterDomainList.size()>0){
			return clientMasterDomainList.get(0);
		}
		return null;
	}

	public List<OrganisationMasterDomain> getAllOrganisationMasterDomain(){
		String query = "Select distinct a from OrganisationMasterDomain a join fetch a.thrustAreaMaster where a.numId<>0 and a.numIsValid=1";
		return  daoHelper.findByQuery(query);	
	}
	
	public List<OrganisationMasterDomain> getAllActiveOrganisationMasterDomain(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		EmployeeRoleMasterModel selectedRoleMasterModel = userInfo.getSelectedEmployeeRole();
		StringBuffer query =new  StringBuffer("from OrganisationMasterDomain a where a.numId<>0 and a.numIsValid=1");
		if(null!=selectedRoleMasterModel){
			query.append(" and numId in ("+selectedRoleMasterModel.getNumOrganisationId()+")");
		}
		query.append(" order by organisationName");
		return  daoHelper.findByQuery(query.toString());	
	}
	public String getOrganisationNames(String assignedOrganisation){
		String query = "select string_agg(str_organisation_name,',') from pms_organisation_master where organisation_id IN ("+assignedOrganisation+")";
		List<String> list = daoHelper.runNative(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public String getOrganisationShortNames(String assignedOrganisation){
		String query = "select string_agg(str_organisation_short_code,',') from pms_organisation_master where organisation_id IN ("+assignedOrganisation+")";
		List<String> list = daoHelper.runNative(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
