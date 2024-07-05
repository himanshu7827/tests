package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_document_format_master_log",schema="pms_log")
@Table(name="pms_document_format_master")
public class DocumentFormatMaster extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2443476078944950831L;
	
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="documentFormatMaster")
	@TableGenerator(name="documentFormatMaster", initialValue=1, allocationSize=1)
	private int numId;	
	@Column(name="str_format_name",length=50)
	private String formatName;
	@Column(name="str_mime_types", length=250)
	private String mimeTypes;	
	@Column(name="is_mime_type")
	private boolean mimeType;
	@Column(name="str_icon", length=250)
	private String icon;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "documentFormatList")
	private List<DocumentTypeMasterDomain> documentTypeMasterList;
}
