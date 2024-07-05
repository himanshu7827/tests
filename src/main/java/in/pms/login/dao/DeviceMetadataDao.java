package in.pms.login.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.login.domain.DeviceMetadata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceMetadataDao {
	@Autowired
	DaoHelper daoHelper;
	
	 public List<DeviceMetadata> findByEmployeeId(long employeeId){
		 return null;		 
	 }
	 
	 public DeviceMetadata save(DeviceMetadata deviceMetadata){
		 return daoHelper.merge(DeviceMetadata.class, deviceMetadata);
	 }
	
}
