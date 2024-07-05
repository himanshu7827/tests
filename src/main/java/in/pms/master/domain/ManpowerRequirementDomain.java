package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.DesignationForClient;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

//@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="manpower_requirement_mst_log",schema="pms_log")
@Table(name="pms_manpower_requirement_mst")
public class ManpowerRequirementDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="manpower")
	@TableGenerator(name="manpower", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="dt_start")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name="dt_end")
	private Date endDate;
	@Column(name="num_count")
	private int count;
	@ManyToOne(optional = false)
	@JoinColumn(name="num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;
	@ManyToOne(optional = false)	
	@JoinColumn(name="num_designation_id_fk")
	@NotAudited
	private DesignationForClient designationForClient;
	@Column(name="num_involvement")
	private int involvement;
	@Column(name="str_description",length=2000)
	private String strDescription;
	@Column(name="str_purpose",length=200)
	private String purpose;
	@Column(name="num_rate_Per_ManMonth")
	private double ratePerManMonth;
	
	@Column(name="num_actual_rate_Per_ManMonth")
	private double actualRatePerManMonth;	
	
	@Column(name="num_to_be_deputed_at")
	private int numDeputedAt;
	
	@Column(name="num_project_based_roles")
	private int numProjectRoles;
	
	@Column(name="num_required_type", columnDefinition = "int default 0")
	private int numRequiredType;
}
