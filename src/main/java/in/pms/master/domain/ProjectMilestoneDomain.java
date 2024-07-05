package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="project_milestone_mst_log",schema="pms_log")
@Table(name="pms_project_milestone_mst")
@JsonIgnoreProperties(value= {"milestoneActivityDomains"})
public class ProjectMilestoneDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_milestone_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="dt_milestone_date")
	@Temporal(TemporalType.DATE)
	private Date expectedStartDate;
	@Column(name="str_milestone_name" , length=200)
	private String milestoneName;
	@Column(name="description",length=2000)
	String description;	
	@ManyToOne(optional = false)
	@JoinColumn(name="num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="num_milestone_review_id_fk")
	private List<ProjectMilestoneReviewMaster> projectMilestoneReviewMasters;
	@Column(name="str_status" , length=15)
	private String strStatus;
	@Column(name="dt_completion_date")
	@Temporal(TemporalType.DATE)
	private Date completionDate;
	@Column(name="milestone_type_id", columnDefinition = "int default 3")
	int numMilestoneTypeId;	
}
