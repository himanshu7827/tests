package in.pms.master.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_role_master_log",schema="pms_log")
@Table(name="pms_role_master")
public class RoleMasterDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724774084828868997L;
	
	/*private final String ALL = "All";
	private final String ORGANISATION = "Organisation";
	private final String GROUP = "Group";
	private final String APPLICATION = "Application";*/
	
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	
	private long numId;
	@Column(name="str_role_name")
	private String roleName;
	@Column(name="str_role_short_code")
	private String roleShortName;
	@Column(name="str_access_level", length=20)
	private String accessLevel;
	@Column(name="str_bg_color", length=10)
	private String bgColor;
	@Column(name="num_hierarchy",columnDefinition="int default 0")
	private int hierarchy;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@NotAudited
    @JoinTable(name = "roles_reports", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "report_id"))
    private Collection<ReportMaster> reports; 
	
}

