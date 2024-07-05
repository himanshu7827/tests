package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.BusinessTypeMaster;
import in.pms.master.domain.ClientMasterDomain;
import in.pms.master.domain.ContactPersonMasterDomain;
import in.pms.master.domain.EndUserMasterDomain;
import in.pms.master.domain.GroupMasterDomain;
import in.pms.master.domain.ProjectCategoryMaster;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.domain.ProjectTypeMaster;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.domain.ThrustAreaMasterDomain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.NotAudited;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Entity
@Table(name="trans_application")
public class Application extends TransactionInfoDomain implements Serializable {

	private static final long serialVersionUID = -5625421529285153860L;
	@Id
	@Column(name="num_application_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="trans_application")
	@TableGenerator(name="trans_application", initialValue=1, allocationSize=1)	
	private long numId;

	 @OneToMany
	 @JoinTable(name = "application_proposal", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "proposal_id"))
	 @NotAudited
	 private List<ProposalMasterDomain> proposalMaster;

	 @OneToMany
	 @JoinTable(name = "application_project", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	 private List<ProjectMasterDomain> projectMaster;
	
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "num_business_type_fk")
	 private BusinessTypeMaster businessType;
	 
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "num_project_type_fk")
	 private ProjectTypeMaster projectTypeMaster;
	 
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "num_project_category_fk")
	 private ProjectCategoryMaster categoryMaster;
	
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "num_group_id_fk")
	 private GroupMasterDomain groupMaster;
	 
	 @Column(name="num_proposal_cost")	 
	 private double totalProposalCost;
	 
	 @Column(name="str_proposal_title" , length = 300)
	 private String proposalTitle;
	
	 @OneToMany
	 @JoinTable(name = "application_collaborative_org", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "collaborative_org_id"))
	 private List<CollaborativeOrgDetailsDomain> collaborativeOrganisations;
	
	@ManyToOne
	@JoinColumn(name="client_id_fk")
	private ClientMasterDomain clientMaster;
	
	@ManyToOne
	@JoinColumn(name="end_user_id_fk")
	private EndUserMasterDomain endUserMaster;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "application_thrust_area", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "num_thrust_area_id"))
	private Set<ThrustAreaMasterDomain> thrustAreas;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "application_contact_person", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "num_contact_id"))
	private Set<ContactPersonMasterDomain> contactMaster;
	
	@Column(name="is_converted_To_Project")	
	private boolean convertedToProject;
	
	@Column(name="num_corporate_approval",nullable = false, columnDefinition = "int default 0")
	private int numCorporateApproval;
	@Column(name="dt_date_of_sub")
	private Date dateOfSub;
	@Column(name="dt_clearance_receive_date")
	private Date clearanceReceiveDate;
	
	@Column(name="is_collaborative")	
	private boolean collaborative;	

}
