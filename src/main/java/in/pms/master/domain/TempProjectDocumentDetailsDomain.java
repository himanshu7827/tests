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
@AuditTable(value="pms_temp_project_doc_detail_log",schema="pms_log")
@Table(name="pms_temp_project_document_details")
public class TempProjectDocumentDetailsDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8869366075665208285L;
	
	@Id	
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectDocumentDetailsDomain")
	@TableGenerator(name="projectDocumentDetailsDomain", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_original_Doc_Name")
	private String originalDocumentName;
	
	@Column(name="str_Document_Name")
	private String documentName;
	
	@Column(name="str_Document_format",length=100)
	private String documentFormat;

	@ManyToOne(optional = false)
    @JoinColumn(name = "num_project_document_Id")
    private TempProjectDocumentMasterDomain projectDocumentMasterDomain;	
	
	@ManyToOne(optional = false)
	@JoinColumn(name="document_format_id")
	private DocumentFormatMaster documentFormatMaster;
	
	
}
