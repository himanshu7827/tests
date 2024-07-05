package in.pms.login.controller;

import in.pms.login.domain.Privilege;
import in.pms.login.model.PrivilegeModel;
import in.pms.login.model.RolePrivilegeModel;
import in.pms.login.service.PrivilegeService;
import in.pms.login.service.RoleService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RolePrivilegeController {
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PrivilegeService privilegeService;

	@RequestMapping("/rolePrivilege")
	public String getAllRolePrivileges(HttpServletRequest request, RolePrivilegeModel rolePrivilegeModel,RedirectAttributes redirectAttributes){		
		List<RolePrivilegeModel> roleList = roleService.getAllRoleDetails();	
		request.setAttribute("roleList", roleList);		
		List<Privilege> privilegeList = privilegeService.privilegeName();
		request.setAttribute("privilegeList", privilegeList);
		return "rolePrivilege";
	}
	
	@RequestMapping("/saveRolePrivilege")
	public String saveRolePrivileges(HttpServletRequest request, RolePrivilegeModel rolePrivilegeModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = roleService.checkDuplicateRoleData(rolePrivilegeModel);
		if(null == strDuplicateCheck){
		long id = roleService.saveRoleData(rolePrivilegeModel);
		if(id>0){
			if(rolePrivilegeModel.getRoleId()==0){
				redirectAttributes.addFlashAttribute("message",  "RolePrivilege details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "RolePrivilege details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					  
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
				
		return "redirect:/rolePrivilege";
	}
	
	@RequestMapping("/getPrivilegeByRoleId")
	public @ResponseBody String getPrivilegeByRoleId(HttpServletRequest request){	
		long roleId = 0;
		if(null !=request.getParameter("roleId")) {
			roleId = Long.parseLong(request.getParameter("roleId"));
			String privileges =roleService.getPrivilegeByRoleId(roleId);
			return privileges;
		}		
		return "";
	}
	
	@RequestMapping("/privilege")
	public String getAllPrivileges(HttpServletRequest request, PrivilegeModel privilegeModel,RedirectAttributes redirectAttributes){				
		List<Privilege> privilegeList = privilegeService.privilegeName();
		request.setAttribute("privilegeList", privilegeList);
		return "Privilege";
	}
	
	
	@RequestMapping("/saveUpdatePrivilegeMaster")
	public String saveUpdatePrivilegeMaster(HttpServletRequest request, PrivilegeModel privilegeModel, RedirectAttributes redirectAttributes){		
		String strDuplicateCheck = privilegeService.checkDuplicatePrivilegeData(privilegeModel);
		if(null == strDuplicateCheck){
		long id = privilegeService.savePrivilegeData(privilegeModel);
		if(id>0){
			if(privilegeModel.getPrivlegeId()==0){
				redirectAttributes.addFlashAttribute("message",  "Privilege details saved Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}else{
				redirectAttributes.addFlashAttribute("message",  "Privilege details updated Successfully with Id "+id);	
				redirectAttributes.addFlashAttribute("status", "success");
			}					  
		}
		}else{
			redirectAttributes.addFlashAttribute("message",strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
				
		return "redirect:/privilege";
	}
}
