package in.pms.master.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.dao.ActionMasterDao;
import in.pms.global.domain.ActionMasterDomain;
import in.pms.global.domain.WorkflowMasterDomain;
import in.pms.login.util.UserInfo;
import in.pms.master.model.ActionModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService{

	@Autowired
	ActionMasterDao actionMasterDao;
	
	@Override
	/*@PreAuthorize("hasAuthority('ROLE_ADMIN')")*/
	public List<ActionModel> loadAllAction(){
		List<ActionMasterDomain> domainList = actionMasterDao.getAllActionDetails();
		if(null != domainList){
			return convertDomainToModelList(domainList);
		}
		return null;
		
	}

	public List<ActionModel> convertDomainToModelList(List<ActionMasterDomain> actionList){
		List<ActionModel> list = new ArrayList<ActionModel>();
			for(int i=0;i<actionList.size();i++){
				ActionMasterDomain actionMasterDomain = actionList.get(i);
				ActionModel actionModel = new ActionModel();
				actionModel.setNumActionId(actionMasterDomain.getNumActionId());
				actionModel.setStrActionPerformed(actionMasterDomain.getStrActionPerformed());
				actionModel.setStrName(actionMasterDomain.getStrName());
				if(actionMasterDomain.getNumIsValid() ==1){
					actionModel.setValid(true);
				}else{
					actionModel.setValid(false);
				}
				
				list.add(actionModel);
	}
		return list;
	}

	
	@Override
	public String checkDuplicateAction(ActionModel actionModel) {
		String result=	"";
		ActionMasterDomain actionMasterDomain =  actionMasterDao.getAction(actionModel.getStrName());
		
		 if(null == actionMasterDomain){
					return null; 
				 }else if(actionModel.getNumActionId()!= 0){
					 if(actionMasterDomain.getStrName().equals(actionModel.getStrName()))
					 { return null;
						
					 }
					 else{
						 
						 result = "Action already exist!";
					 }
				 }else{ if(actionMasterDomain.getNumIsValid()==2)
				 {
					 result = " Action already exist in Delete Stage. Please Contact to Administrator! ";
				 }
				 else{
					 result = " Action already exist ! ";
				 }
					}
		
				return result;	
		}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ActionModel saveUpdateOthers(ActionModel actionModel){
		ActionMasterDomain actionMasterDomain = convertModelToDomain(actionModel);
		actionMasterDao.save(actionMasterDomain).getNumActionId();
		return actionModel;
	}

	private ActionMasterDomain convertModelToDomain(ActionModel actionModel) {
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserInfo userInfo = (UserInfo) authentication.getPrincipal();
			
			ActionMasterDomain actionMasterDomain = new ActionMasterDomain();
			if(actionModel.getNumActionId()!=0){				
				actionMasterDomain =  actionMasterDao.getOne(actionModel.getNumActionId());
			}
			if(actionModel.isValid()){
				actionMasterDomain.setNumIsValid(1);
			}else{
				actionMasterDomain.setNumIsValid(0);
			}
			  
			actionMasterDomain.setDtTrDate(new Date());
			actionMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
			//actionMasterDomain.setNumIsValid(1);
			actionMasterDomain.setStrActionPerformed(actionModel.getStrActionPerformed());
			actionMasterDomain.setStrName(actionModel.getStrName());
			return actionMasterDomain;
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ActionModel deleteOthers(ActionModel actionModel) 
	{  
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo) authentication.getPrincipal();
		
		ActionMasterDomain actionMasterDomain = actionMasterDao.getOne(actionModel.getNumActionId());
		if(actionMasterDomain != null)
			actionMasterDomain.setNumIsValid(2);
			actionMasterDomain.setDtTrDate(new Date());
			actionMasterDomain.setNumTrUserId(userInfo.getEmployeeId());
		actionMasterDao.save(actionMasterDomain).getNumActionId();
		
		return actionModel;
	}

	

}
