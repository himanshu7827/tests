package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_proposal_doc_detail_log",schema="pms_log")
@Table(name="pms_proposal_document_details")
public class ProposalDocumentDetailsDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8869366075665208285L;
	
	@Id	
	@GeneratedValue(strategy=GenerationType.TABLE, generator="proposalDocumentDetailsDomain")
	@TableGenerator(name="proposalDocumentDetailsDomain", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_original_Doc_Name")
	private String originalDocumentName;
	
	@Column(name="str_Document_Name")
	private String documentName;
	
	@Column(name="str_Document_format",length=100)
	private String documentFormat;

	@ManyToOne(optional = false)
    @JoinColumn(name = "num_proposal_document_Id")
    private ProposalDocumentMasterDomain proposalDocumentMasterDomain;	
	
	@ManyToOne(optional = false)
	@JoinColumn(name="document_format_id")
	private DocumentFormatMaster documentFormatMaster;
	
	//Added by devesh on 03-10-23 to set revision flag
	@Column(name = "Rev_flag", columnDefinition = "int default 0")
	private int revflag;
	//End of revision flag
	
}
