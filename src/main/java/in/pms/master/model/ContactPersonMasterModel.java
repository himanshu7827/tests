package in.pms.master.model;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactPersonMasterModel {

	private long numId;
	private boolean valid;
	
	private String strContactPersonName;
	private String strDesignation;
	private String strMobileNumber;
	private String strEmailId;
	private String strRoles;
	private String strResponsibility;
	private String strOfficeAddress;
	private String strResidenceAddress;
	private String encOrganisationId;
	private String idCheck;
	private int organisationId;
	private String organisationName;
	private long projectId;
	private long clientId;
	 private List<Long> contactPersonId;
		private String contactPerson;
		private String referrerValue;


}
