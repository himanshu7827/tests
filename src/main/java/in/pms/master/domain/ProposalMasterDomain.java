package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.Application;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Check;
import org.hibernate.envers.NotAudited;

@Setter
@Getter
@Entity
@Table(name="pms_proposal_master")
public class ProposalMasterDomain extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -8160190031918235356L;
	@Id
	@Column(name="proposal_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
		
	@Temporal(TemporalType.DATE)
	@Column(name="dt_date_of_submission")
	private Date dateOfSubmission;
	@Column(name="str_proposal_abstract" , length = 1000)
	private String proposalAbstract;
	@Column(name="num_proposal_cost")
	private int proposalCost;
	@Column(name="num_duration")
	private int duration;
	@Column(name="str_submitted_by" , length = 500)
	private String submittedBy;
	
	@Column(name="str_proposal_status" , length = 100)
	private String proposalStatus;
	@Column(name="str_objectives" , length = 2000)
	private String objectives;
	@Column(name="str_summary" , length = 6000)
	private String summary;
	@Column(name="str_background" , length = 2000)
	private String background;

	@Column(name="group_id")
	private long groupId;
	
	@Column(name="num_organisation_id")
	private long organisationId;
	
	
	@ManyToOne(optional = false)
	@JoinTable(name = "application_proposal",joinColumns=@JoinColumn(name = "proposal_id"), inverseJoinColumns = @JoinColumn(name = "application_id"))
	@NotAudited
	private Application application;

	@Column(name="str_status", length=10)
	@Check(constraints="status is null or status in ('SAD','REV','SUB')")
	private String status;

	@Column(name="str_remarks", length = 2000)
	private String remarks;
	
	public ProposalMasterDomain() {
		super();
	}


	public ProposalMasterDomain(Application application) {
		super();
		this.application = application;
	}
	
	
	@Column(name="str_proposal_ref_no",length=30)
	private String strProposalRefNo;
	
	
	
	
}
