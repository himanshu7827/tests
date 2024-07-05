package in.pms.master.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientMasterModel {
	
	private long numId;	
	private String encStrId;
	private String clientName;	
	private long parentClientId;	
	private String encStrParentClientId;
	private String clientAddress;	
	private String contactNumber;	
	private String shortCode;
	private boolean valid;
	private String endUserName;
	private String endUserShortCode;
	private List<ContactPersonMasterModel> contactPersonList;
	private String referrerValue;

}
