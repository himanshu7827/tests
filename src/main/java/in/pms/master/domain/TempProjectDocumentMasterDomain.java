package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Getter
@Setter
@ToString
@Entity
@Audited
@AuditTable(value="pms_temp_project_doc_mst_log",schema="pms_log")
@Table(name="pms_temp_project_document_master")
public class TempProjectDocumentMasterDomain extends TransactionInfoDomain implements
		Serializable {
	
	private static final long serialVersionUID = 9195110698845709191L;
	@Id
	@Column(name="num_project_document_Id",length=15)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectDocumentMaster")
	@TableGenerator(name="projectDocumentMaster", initialValue=1, allocationSize=1)
	private long numId;	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_document")
	private Date documentDate;	
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_period_from")
	private Date periodFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_period_to")
	private Date periodTo;
	
	@Column(name="str_document_description",columnDefinition= "TEXT")
	private String description;
	
	@Column(name="str_Document_version")
	private String documentVersion;
	
	@Version
	private int version;	
	
	@OneToMany(mappedBy = "projectDocumentMasterDomain", cascade = CascadeType.ALL)
	private List<TempProjectDocumentDetailsDomain> projectDocumentDetailsDomainList;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="document_type_id")
	private DocumentTypeMasterDomain documentTypeMasterDomain;
	
	@Column(name="num_project_id")
	private long projectId;
	
}
