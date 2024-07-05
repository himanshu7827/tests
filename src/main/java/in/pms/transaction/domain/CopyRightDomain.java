package in.pms.transaction.domain;

/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.domain.TransactionInfoDomain;

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

@ToString
@Getter
@Setter
@Entity
@Table(name="pms_Copy_Right")
public class CopyRightDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="copyright_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numCopyRightID;
	
	@Column(name="str_title" , length=500)
	private String strTitle;
	
	@Column(name="dt_copyright_date")
	private Date dateOfCopyright;
	
	@Column(name="str_Agency" , length=500)
	private String strAgency;
	
	@Column(name="copyright_Filed")
	private Boolean copyrightFiled;
	
	@Column(name="copyright_FiledAwarded")
	private String strcopyrightFiledAwarded;
	
	@Column(name="DT_FILING_DATE")
	private Date dtFilingDate;
	
	@Column(name="DT_AWARD_DATE")
	private Date dtAwardDate;
	
	@Column(name="copyright_Awarded")
	private Boolean copyrightAwarded;
	
	@Column(name="str_reference_number",length=100)
	private String strReferenceNumber;
	

	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
	
}



