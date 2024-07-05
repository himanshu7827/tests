package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PMS_PROJECT_PUBLICATION_DETAILS")
public class ProjectPublicationDomain extends TransactionInfoDomain implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1188046270527472012L;

	
	
	@Id
	@Column(name="NUM_PROJECT_PUBLICATION_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_ppd")
	@TableGenerator(name="generator_ppd", initialValue=1, allocationSize=1)
	private int numProjectPublicationId;
	
	@Column(name = "STR_PUBLICATION_TYPE")
	private String strPublicationType;
	
	@Column(name="DT_PUBLICATION_DATE")
	private Date dtpublication;
	
	@Column(name = "STR_PUBLICATION_TITLE", length=500)
	private String strPublicaionTitle;

	@Column(name = "STR_AUTHOR_DETAILS")
	private String strAuthorDetails;
	
	@Column(name = "STR_JOURNAL_NAME")
	private String strJournalName;
	
	
	@Column(name = "STR_CONFERENCE_CITY")
	private String strConferenceCity;
	
	@Column(name = "STR_REFERENCE_NUMBER")
	private String strReferenceNumber;
	
	@Column(name = "STR_PUBLISHER")
	private String strPublisher;
	
	@Column(name = "STR_ORGANISATION")
	private String strOrganisation;
	
	@Column(name = "str_publication_description")
	private String strPublicationDescription;
	

	
	
	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;


}