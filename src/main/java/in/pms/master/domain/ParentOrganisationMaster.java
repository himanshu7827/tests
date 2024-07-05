package in.pms.master.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="parent_organisation_master_log",schema="pms_log")
@Table(name="pms_parent_organisation_master",schema="pms")
public class ParentOrganisationMaster extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = 996487797556144523L;	

	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="parentOrganisationMaster")
	@TableGenerator(name="parentOrganisationMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_organisation_name")
	private String organisationName;
	@Column(name="str_organisation_short_code")
	private String organisationShortName;
	@Column(name="str_contact_number")
	private String contactNumber;
	@Column(name="str_organisation_address")
	private String organisationAddress;
	
	@OneToMany(mappedBy = "parentOrganisationMaster", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List<OrganisationMasterDomain> organisationMasterDomains;

}
