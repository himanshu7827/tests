package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;

import java.io.Serializable;
import java.util.Date;

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
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity


@Table(name="pms_media")
public class MediaDomain extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -3724774084828868997L;

	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_source")
	private String source;
	@Column(name="dt_date_of_media")
	private Date dateOfmedia;
	@Column(name="str_source_details", length=1000)
	private String sourceDetails;
	@Column(name="str_details", length=1000)
	private String details;
	@Column(name="str_location", length=1000)
	private String location;
	@Column(name="str_any_other_source", length=1000)
	private String anyOtherSource;
	
	
	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
	
}
