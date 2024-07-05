package in.pms.master.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Version;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Data;
import in.pms.global.domain.TransactionInfoDomain;

@Entity
@Data
@Audited
@AuditTable(value="pms_proposal_doc_mst_log",schema="pms_log")
@Table(name="pms_proposal_document_master" ,schema="pms")
public class ProposalDocumentMasterDomain extends TransactionInfoDomain{

	@Id
	@Column(name="num_proposal_document_id",length=15)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="proposalDocumentMaster")
	@TableGenerator(name="proposalDocumentMaster", initialValue=1, allocationSize=1)
	private long numId;	
	
	@ManyToOne(optional = false)
	@JoinColumn(name="document_type_id")
	private DocumentTypeMasterDomain documentTypeMasterDomain;
	
	@Column(name="str_Document_version")
	private String documentVersion;
	
	@Version
	private int version;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_period_from")
	private Date periodFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_period_to")
	private Date periodTo;
	
	@Column(name="str_description", length=2000)
	private String description;
	
	@Column(name="dt_document")
	private Date documentDate;
	
	@Column(name="num_proposal_id")
	private long proposalId;
	
	@OneToMany(mappedBy = "proposalDocumentMasterDomain", cascade = CascadeType.ALL)
	private List<ProposalDocumentDetailsDomain> proposalDocumentDetailsDomainList;
	
	//Added by devesh on 03-10-23 to set revision flag
	@Column(name = "Rev_flag", columnDefinition = "int default 0")
	private int revflag;
	//End of revision flag
	
}
