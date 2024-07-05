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
@Table(name="pms_Appreciation_Letter")
public class AppreciationLetterDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="appreciation_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numAppreciationID;
	
	@Column(name="str_Description" , length=1000)
	private String strDescription;
	
	@Column(name="dt_Appreciation_date")
	private Date dateOfAppreciation;
	
	@Column(name="str_Appreciation_Agency" , length=500)
	private String strAppreciaitionAgency;
	
	@Column(name="str_Recipient_Name",length=500)
	private String strRecipientName;
	
	
	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;

	
}



