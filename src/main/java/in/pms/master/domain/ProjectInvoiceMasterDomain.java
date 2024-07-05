package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_project_invoice_log",schema="pms_log")
@Table(name="pms_project_invoice_mst")

public class ProjectInvoiceMasterDomain extends TransactionInfoDomain implements Serializable  { /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="num_invoice_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_invoice_refno",length=50)
	private String strInvoiceRefno;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_invoice")
	private Date dtInvoice;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_invoice_amt",precision=19,scale=2)
	private Double numInvoiceAmt;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_tax_amount",precision=19,scale=2)
	private Double numTaxAmount;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_invoice_total_amt",precision=19,scale=2)
	private Double numInvoiceTotalAmt;
	
	@Column(name="str_invoice_status", length=20)
	private String strInvoiceStatus;
	
	@Column(name="num_project_id",length=12)
	private long projectId;

	@Column(name="str_invoice_type", length=20)
	private String strInvoiceType;
	/*@ManyToOne(optional = false)
    @JoinColumn(name = "num_payment_received_id")
	ProjectPaymentReceivedDomain projectPaymentReceivedDomain;*/
	
	@OneToMany(mappedBy = "projectInvoiceMasterDomain", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ProjectPaymentReceivedDomain> projectPaymentReceivedDomain;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "invoice_schedule_mst", joinColumns = @JoinColumn(name = "num_invoice_id"), inverseJoinColumns = @JoinColumn(name = "num_project_payment_id"))
	private Set<ProjectPaymentSeheduleMasterDomain> projectPaymentSeheduleMasterDomain;
	
}





