package in.pms.transaction.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.DocumentFormatMaster;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_progress_report_document")
public class ProgressReportDocumentsDomain extends TransactionInfoDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6576197446387667207L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="progressReportDoc")
	@TableGenerator(name="progressReportDoc", initialValue=1, allocationSize=1)
	
	@Column(name="num_document_id",length=10)
	private long numDocumentId;
	@Column(name="str_document_name",length=100)
	private String strDocumentName;
	@Column(name="str_original_Doc_Name")
	private String originalDocumentName;
	/*@Column(name="str_Document_format",length=100)
	private String strDocumentFormat;*/
	@Column(name="str_document_caption",length=150)
	private String strDocumentCaption;
	/*@Column(name="num_category_id",length=10)	
	private long numCategoryId;*/
	
	/*@ManyToOne(optional = false)
	@JoinColumn(name="document_format_id")
	private DocumentFormatMaster documentFormatMaster;
	*/
	

}
