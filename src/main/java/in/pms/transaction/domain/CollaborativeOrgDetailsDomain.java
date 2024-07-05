package in.pms.transaction.domain;
import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_collaborative_details_log",schema="pms_log")
@Table(name="pms_collaborative_details",schema="pms")

public class CollaborativeOrgDetailsDomain extends TransactionInfoDomain implements Serializable{
	private static final long serialVersionUID = -3737901472864669311L;
	
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_organisation_name", length = 200)
	private String organisationName;
	
	@Column(name="str_organization_address", length = 2000)
	private String organisationAddress;
	
	@Column(name="str_contact_number", length = 20)
	private String contactNumber;
	
	@Column(name="str_email", length = 100)
	private String email;
	
	@Column(name="str_website", length = 150)
	private String website;
	
	 @ManyToOne
	  @JoinTable(name = "application_collaborative_org",
	          joinColumns = {@JoinColumn(name = "collaborative_org_id")},
	          inverseJoinColumns = {@JoinColumn(name = "application_id")})
	 @NotAudited
	 private Application application;

}
