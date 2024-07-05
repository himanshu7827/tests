package in.pms.master.domain;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_third_party_master_log", schema = "pms_log")
@Table(name = "pms_third_party_master")

public class ThirdPartyMasterDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1556954284610107490L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "thirdPartyMaster")
	@TableGenerator(name = "thirdPartyMaster", initialValue = 1, allocationSize = 1)
	@Column(name = "num_third_party_id")
	private long numId;
	
	@Column(name="str_agency_name", length = 50)
	private String strAgencyName;
	
	@Column(name="str_agency_address", length = 100)
	private String strAgencyAddress;
	
	@Column(name="str_contact_person", length = 50)
	private String strContactPerson;
	
	@Column(name="str_mobile_number", length = 15)
	private String strMobileNo;
	
	@Column(name="str_contact_number", length = 15)
	private String strContactNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_start_date")
	private Date dtStartDate;
	
	@Column(name="dt_end_date")
	private Date dtEndDate;
	

}
