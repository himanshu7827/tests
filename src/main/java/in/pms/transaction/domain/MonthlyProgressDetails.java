package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.AwardWonDomain;
import in.pms.master.domain.MediaDomain;
import in.pms.master.domain.SeminarEventDomain;

import java.io.Serializable;
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

import lombok.Getter;
import lombok.Setter;




@Entity
@Getter
@Setter
@Table(name="pms_monthly_progress_status_dtl",schema="pms")
public class MonthlyProgressDetails  extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -969836155808546788L;
	
	@Id
	@Column(name="num_progress_status_dtl_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="pms_monthly_progress_status")
	@TableGenerator(name="pms_monthly_progress_status", initialValue=1, allocationSize=1)	
	private long numId;
	
	@Column(name="num_category_id")
	private long numCateoryId;

	@Column(name="num_parent_category")
	private int numParentCateory;
	
	@Column(name="num_cateory_sequence")	
	private int numCategorySequence;
	
	@ManyToOne(cascade=CascadeType.ALL)	
	@JoinColumn(name="num_progress_id_fk")
	private MonthlyProgressDomain monthlyProgressDomain;	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProjectPublicationDomain> projectPublications;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<TalksDomain> talksDomain;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<MediaDomain> media;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<AwardWonDomain> awardWon;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<SeminarEventDomain> seminarEvent;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<CopyRightDomain> copyRight;
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<OthersDomain> othersDomain;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<AppreciationLetterDomain> appreciationLetterDomain;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<MouCollaborationDomain> mouCollaborationDomain;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<PatentDetailsDomain> projectPatents;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProductDevelopedDomain> projectDevelopedProducts;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProjectHighlightsDomain> projectHighlights;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProjectOthersDomain> projectOthers;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProjectInnovationsDomain> projectInnovations;

	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<AdditionalQualificationDomain> projectAdditionalQualification;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<DeploymentTotDetailsDomain> deploymentTotDetailsDomain;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="monthlyProgressDetails",fetch=FetchType.LAZY)
	List<ProductServiceDetailsDomain> productServiceDetailsDomain;

}