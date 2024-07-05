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
import javax.persistence.Table;
import javax.persistence.TableGenerator;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PMS_NEWSLETTER_ROLE_MAPPING")
public class NewsLetterRoleMapping extends TransactionInfoDomain implements Serializable {


	private static final long serialVersionUID = -3279430064453904135L;

	@Id
	@Column(name="NUM_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	Integer numId;
	
	@Column(name="NUM_NEWSLETTER_ID")
	private int numNewsLetterId;
	
	@Column(name="STR_TO")
	 private String strTo;
	
	@Column(name="STR_CC")
	private String strCc;
	
	@Column(name="STR_BCC")
	private String strBcc;

	
	
	
	
	
	
	
}
