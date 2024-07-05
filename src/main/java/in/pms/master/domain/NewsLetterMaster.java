package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="PMS_NEWSLETTER_MASTER")
public class NewsLetterMaster extends TransactionInfoDomain implements Serializable {

	/**
	 * @Author: Prashant Singh
	 */
	private static final long serialVersionUID = -3279430064453904135L;

	@Id
	@Column(name="NUM_NEWSLETTER_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	public int numNewsLetterId;
	
	@Column(name="STR_SUBJECT")
	public String strSubject;
	
	@Column(name="STR_CONTENT_MAIL")
	public String strContentMail;
	
	@Column(name="STR_CONTENT_SMS")
	public String strContentSMS;
	
	@Column(name="STR_SMS_FLAG")
	public String strMailSMSFlag;
	
	@Column(name="NUM_TOTAL_SUBSCRIBERS")
	public int numTotalSubscribers;
	
	@Column(name="STR_STATUS")
	public String strStatus;

	@Column(name="scheduledDate")
    private Date scheduledDate;
	
	
	@Column(name="num_isPeriodic")
	private int isPeriodic;
	
	
	@Column(name="str_periodicScheduledDate")
	private String strPeriodicScheduleDate;
	
	@Column(name="num_newslettertype")
	private int newsLetterType;
	
	
	public int getNewsLetterType() {
		return newsLetterType;
	}

	public void setNewsLetterType(int newsLetterType) {
		this.newsLetterType = newsLetterType;
	}

	public String getStrPeriodicScheduleDate() {
		return strPeriodicScheduleDate;
	}

	public void setStrPeriodicScheduleDate(String strPeriodicScheduleDate) {
		this.strPeriodicScheduleDate = strPeriodicScheduleDate;
	}

	public int getIsPeriodic() {
		return isPeriodic;
	}

	public void setIsPeriodic(int isPeriodic) {
		this.isPeriodic = isPeriodic;
	}

	public int getNumNewsLetterId() {
		return numNewsLetterId;
	}

	public void setNumNewsLetterId(int numNewsLetterId) {
		this.numNewsLetterId = numNewsLetterId;
	}

	public String getStrSubject() {
		return strSubject;
	}

	public void setStrSubject(String strSubject) {
		this.strSubject = strSubject;
	}

	public String getStrContentMail() {
		return strContentMail;
	}

	public void setStrContentMail(String strContentMail) {
		this.strContentMail = strContentMail;
	}

	public String getStrContentSMS() {
		return strContentSMS;
	}

	public void setStrContentSMS(String strContentSMS) {
		this.strContentSMS = strContentSMS;
	}

	public String getStrMailSMSFlag() {
		return strMailSMSFlag;
	}

	public void setStrMailSMSFlag(String strMailSMSFlag) {
		this.strMailSMSFlag = strMailSMSFlag;
	}

	public int getNumTotalSubscribers() {
		return numTotalSubscribers;
	}

	public void setNumTotalSubscribers(int numTotalSubscribers) {
		this.numTotalSubscribers = numTotalSubscribers;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	
	
	
}
