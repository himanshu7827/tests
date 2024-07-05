package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_doc_classification_mst_log",schema="pms_log")
@AuditOverride(forClass = TransactionInfoDomain.class)
@Table(name="pms_doc_classification_mst")
public class DocumentClassificationDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8509120137478844586L;

	@Id
	@Column(name="num_classification_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private int numId;
	
	@Column(name="str_classification_name", length = 100)
	private String classificationName;
	@Column(name="str_description", length = 1000)
	private String description;
	@Column(name="str_short_code", length = 20)
	private String shortCode;
	@Column(name="display_sequence", nullable = false, columnDefinition = "int default 0")
	private int displaySequence;
	
	@OneToMany(mappedBy = "documentClassification",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<DocumentTypeMasterDomain> documents;
}
