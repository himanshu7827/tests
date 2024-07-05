package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Digits;

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_pms_project_payment_received_log",schema="pms_log")
@Table(name="pms_project_payment_received")
public class ProjectPaymentReceivedDomain extends TransactionInfoDomain implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -6217144604255915369L;


	@Id
	@Column(name="num_payment_received_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;

	@Temporal(TemporalType.DATE)
	@Column(name="dt_payment")
	private Date dtPayment;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_received_amount",precision=19,scale=2)
	private Double numReceivedAmount;
	
	@Column(name="str_payment_mode",length=50)
	private String strPaymentMode;
	
	@Column(name="str_utr_number",length=50)
	private String strUtrNumber;
	
	/*@Column(name="num_project_id",length=12)
	private long projectId;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_project_id")	
	ProjectMasterDomain projectMasterDomain;
	
	/*@Column(name = "num_invoice_id")
	private long invoiceId;*/
	/*@OneToMany(mappedBy = "projectPaymentReceivedDomain", cascade = CascadeType.ALL)
	private List<ProjectInvoiceMasterDomain> projectInvoiceMasterDomains;*/

	
	@ManyToOne(optional = false)
    @JoinColumn(name = "num_invoice_id")	
	ProjectInvoiceMasterDomain projectInvoiceMasterDomain;
	
	@Column(name="num_mapped_withoutInvoicePayment_id")
	private long numPaymentWithoutInvId;

	/*------------------ Add 5 columns for store the value of itTDS,gstTDS,LD,OtherRecovey,Short Payment and excess_Payment value fields [05-12-2023] --------*/
	@Digits(integer=10,fraction=2)
	@Column(name="it_TDS",precision=19,scale=2, columnDefinition = "decimal(19,2) default 0.0")
    private Double itTDS;
	
	
	@Digits(integer=10,fraction=2)
	@Column(name="gst_TDS",precision=19,scale=2, columnDefinition = "decimal(19,2) default 0.0")
    private Double gstTDS;
	
	
	@Digits(integer=10,fraction=2)
	@Column(name="short_Payment",precision=19,scale=2, columnDefinition = "decimal(19,2) default 0.0")
	private Double shortPayment;
	
	
	@Digits(integer = 10, fraction = 2)
	@Column(name = "LD", precision = 19, scale = 2, columnDefinition = "decimal(19,2) default 0.0")
	private Double ldPayment;
	
	@Digits(integer=10,fraction=2)
	@Column(name="other_Recovery",precision=19,scale=2, columnDefinition = "decimal(19,2) default 0.0")
	private Double otherRecovery;
	
	
	@Digits(integer=10,fraction=2)
	@Column(name="excess_Payment",precision=19,scale=2, columnDefinition = "decimal(19,2) default 0.0")
	private Double excessPayment;
	
}



