package in.pms.login.service;

import in.pms.login.domain.Privilege;
import in.pms.login.model.PrivilegeModel;

import java.util.List;

import javax.transaction.Transactional;

public interface PrivilegeService {
	public List<Privilege> privilegeName();

	public String checkDuplicatePrivilegeData(PrivilegeModel rolePrivilegeModel);

	@Transactional
	public long savePrivilegeData(PrivilegeModel privilegeModel);
}
