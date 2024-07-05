package in.pms.master.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

/*
 * This class will be treated as center which will participate in real Business
 * */


@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_organisation_master_log",schema="pms_log")
@Table(name="pms_organisation_master")
public class OrganisationMasterDomain extends TransactionInfoDomain implements Serializable{
	
	private static final long serialVersionUID = 3635574667594262986L;
	@Id
	@Column(name="organisation_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_organisation_name")
	private String organisationName;
	@Column(name="str_organisation_short_code")
	private String organisationShortName;
	@Column(name="str_contact_number")
	private String contactNumber;
	@Column(name="str_organisation_address")
	private String organisationAddress;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "NUM_PARENT_ORG_ID_FK")
    private ParentOrganisationMaster parentOrganisationMaster;
	
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "pms_org_thrust_area_map", joinColumns = @JoinColumn(name = "num_organisation_id"), inverseJoinColumns = @JoinColumn(name = "num_thrust_area_id"))
	public List<ThrustAreaMasterDomain> thrustAreaMaster;
	
	@Column(name="str_code",length=30)
	private String strCode;
	

}
