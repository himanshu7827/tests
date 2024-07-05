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
@Table(name="pms_Talks")
public class TalksDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="Talk_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numTalkID;
	
	@Column(name="str_Event_Name" , length=500)
	private String strEventName;
	
	@Column(name="str_Agency_Name" , length=500)
	private String strAgencyName;
	
	@Column(name="str_Talk_Title" , length=1000)
	private String strTalkTitle;
	
	@Column(name="dt_date")
	private Date dtDate;
	
	@Column(name="str_Name_Speaker" , length=500)
	private String strNameSpeaker;
	
	@Column(name="str_City_Location" , length=500)
	private String strCityLocation;
	
	@Column(name="num_group_category_id",length=10)
	private long numGroupCategoryId;
	

	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;

	
}



