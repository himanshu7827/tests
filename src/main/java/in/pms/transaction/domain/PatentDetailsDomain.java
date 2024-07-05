package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="PMS_PROJECT_PATENT_DETAILS")
public class PatentDetailsDomain extends TransactionInfoDomain{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectPt")
	@TableGenerator(initialValue=1, allocationSize=1, name="projectPt")
	@Column(name="NUM_PROJECT_PATENT_ID")
	private Integer numProjectPatentId;
	
	; 
	@Column(name="STR_PATENT_TITLE",length=500)
	private String strPatentTitle;
	
	@Column(name="STR_INVENTOR_NAME")
	private String strInventorName;
	
	@Column(name="STR_INVENTOR_ADDRESS",length=500)
	private String strInventorAddress;
	
	@Column(name="STR_REFERENCE_NUM")
	private String strReferenceNum;
	
	@Column(name="STR_IS_FILED",length=10)
	private String strIsFiled;
	
	@Column(name="DT_FILING_DATE")
	private Date dtFilingDate;
	
	@Column(name="DT_AWARD_DATE")
	private Date dtAwardDate;
	
	@Column(name="STR_IS_AWARDED",length=1)
	private String strIsAwarded;
	
	@Column(name="STR_COUNTRY_DETAILS",length=500)
	private String strCountryDetials; 
	
	@Column(name="STR_STATUS")
	private String strStatus;

	
	
	@ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;
	

	
	

}
