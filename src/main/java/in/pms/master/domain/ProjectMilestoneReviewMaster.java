package in.pms.master.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

//@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="project_milestone_review_mst_log",schema="pms_log")
@Table(name="pms_project_milestone_review_mst")
public class ProjectMilestoneReviewMaster extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_milestone_review_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="milestoneReviewgenerator")
	@TableGenerator(name="milestoneReviewgenerator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="dt_review")
	@Temporal(TemporalType.DATE)
	private Date reviewDate;
	@Temporal(TemporalType.DATE)
	@Column(name="dt_completion")
	private Date completionDate;
	@Column(name="str_remarks" , length=2000)
	private String strRemarks;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="num_milestone_id_fk")
	private ProjectMilestoneDomain projectMilestoneDomain;

}
