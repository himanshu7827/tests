package in.pms.mail.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "PMS_SEND_MAIL_HISTORY")
public class SendMailHistory implements Serializable {

	private static final long serialVersionUID = -7210256622594430369L;


	@Id
	@Column(name="NUM_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	public int numId;
	
	@Column(name="STR_TO_SEND")
	String strToSend; 
	
	@Column(name="STR_SUBJECT",length=3000)
	String strSubject; 
	
	@Column(name = "STR_MAIL_BODY",columnDefinition="TEXT")
	public String strMailBody;

	@Column(name="NUM_ISVALID")
	int numIsValid; 
	
	@Column(name="DT_TR_DATE")
	Date dtTrDate;
	
	@Column(name="IS_MAILSEND")
	int isMailSend; 
	
	@Column(name="STR_Cc_SEND")
	String strCcSend;
	
	@Column(name="STR_BCc_SEND")
	String strBCcSend;
	
	@Column(name="STR_TYPE")
	String strType;
	
	@Column(name="Num_PROJECT_ID" , columnDefinition = "int default 0")
	int numProjectId;
	

}

