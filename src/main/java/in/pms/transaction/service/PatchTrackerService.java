 package in.pms.transaction.service;
import in.pms.transaction.model.PatchTrackerModel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
public interface PatchTrackerService {
	
	@Transactional	
	 public long addpatchdetails(PatchTrackerModel patchTrackerModel);
    
   
	public List<String> getRequesterName(String searchString);
	public List<String> getNameOfFiles(String searchString);
	public List<String> getTeamMembers(String searchString);
	public List<String> getModules(String searchString);
	
	


}