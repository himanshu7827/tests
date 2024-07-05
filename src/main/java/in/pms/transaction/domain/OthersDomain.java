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
@Table(name="pms_Others")
public class OthersDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="others_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numOthersID;
	
	@Column(name="str_Recognition" , length=1000)
	private String strRecognition;
	
	@Column(name="dt_Others_date")
	private Date dateOfOthers;
	
	@Column(name="str_Appreciation_Agency" , length=500)
	private String strAppreciaitionAgency;
	
	@Column(name="str_City_Location",length=500)
	private String strCityLocation;
	

	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;
	
}



