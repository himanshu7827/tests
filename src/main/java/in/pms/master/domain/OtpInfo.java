package in.pms.master.domain;
import java.io.Serializable;
import java.util.Date;

import in.pms.global.domain.TransactionInfoDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@Table(name="otp_info")
public class OtpInfo  extends TransactionInfoDomain  {


	private static final long serialVersionUID = 1L;
	//private static final long OTP_VALID_DURATION= 10*60*1000;
		
		@Id
		@Column(name="num_oid",length=5)
		
		@GeneratedValue(strategy=GenerationType.TABLE, generator="otpInfo")
		@TableGenerator(name="otpInfo", initialValue=1, allocationSize=1)	
		private long oid;
		
		
		@Column(name="user_name",length=150)
		String userName;
		
		@Column(name = "one_time_password")
	    private String oneTimePassword;
	     
	    @Column(name = "otp_requested_time")
	    Date otpRequestedTime;
		
	    @Column(name="captcha",length=10)
		private int captcha;
	    
	    @Column(name="otp_type")
	    String otpType;
	    
		int num_state_id=1;
		
		@Column(name="MAIL_CONTENT" , length=1000)
		public String mailContent;

		

}
