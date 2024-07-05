package in.pms.master.domain;

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

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

//@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="milestone_activity_mapping_log",schema="pms_log")
@Table(name="pms_milestone_activity_mapping")
public class MilestoneActivityDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="num_module_id",columnDefinition="int default 0")
	private long moduleId;
	@Column(name="dt_start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name="dt_end_date")
	private Date endDate;
	@Column(name="str_description",length=2000)
	private String strDescription;
	@Column(name="num_with_module")
	private int withModule;
		
	@ManyToOne(optional = false)
    @JoinColumn(name = "num_milestone_id")
    private ProjectMilestoneDomain projectMilestoneDomain;
	
	}
