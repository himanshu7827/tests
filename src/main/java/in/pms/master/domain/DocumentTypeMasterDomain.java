package in.pms.master.domain;
import in.pms.global.domain.TransactionInfoDomain;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;



@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_doc_type_mst_log",schema="pms_log")
@Table(name="pms_doc_type_mst")
public class DocumentTypeMasterDomain extends TransactionInfoDomain implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3624261877693629549L;
	@Id
	@Column(name="num_doc_type_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="num_doc_type_name", length = 100)
	private String docTypeName;
	@Column(name="str_description", length = 1000)
	private String description;
	@Column(name="str_doc_short_code", length = 20)
	private String shortCode;
	@Column(name="str_icon", length = 100)
	private String icon;
	@Column(name="num_show_on_dashboard",nullable = false, columnDefinition = "int default 0")
	private int showOnDashboard;
	@Column(name="display_sequence", nullable = false, columnDefinition = "int default 0")
	private int displaySequence;
	@Column(name="str_classes", length=20)
	private String classColor;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "document_type_format_mapping", joinColumns = { 
			@JoinColumn(name = "num_doc_type_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "num_id", 
					nullable = false, updatable = false) })
	private List<DocumentFormatMaster> documentFormatList;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="num_classification_id_fk")
    private DocumentClassificationDomain documentClassification;
	
}
