package in.pms.master.domain;


import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_client_contact_mst_log",schema="pms_log")
@Table(name="pms_client_contact_master")

public class ContactPersonMasterDomain extends TransactionInfoDomain implements Serializable {

	/**
	 * 
	 */ 

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_contact_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="clientContactMaster")
	@TableGenerator(name="clientContactMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_contact_person_name",length=150)
	private String strContactPersonName;
	
	@Column(name="str_contact_person_designation",length=150)
	private String strDesignation;
	
	@Column(name="Str_mobile_number",length=20)
	private String strMobileNumber;
	
	@Column(name="str_email",length=150)
	private String strEmailId;
	
	@Column(name="str_roles",length=200)
	private String strRoles;
	
	@Column(name="str_responsibility",length=200)
	private String strResponsibility;
	
	@Column(name="str_office_address",length=1000)
	private String strOfficeAddress;
	
	@Column(name="str_residence_address",length=1000)
	private String strResidenceAddress;
	
	@Column(name="organisation_id",length=12)
	private long clientId;
}


