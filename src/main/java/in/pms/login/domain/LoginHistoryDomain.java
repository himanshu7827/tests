package in.pms.login.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="pms_login_history")
public class LoginHistoryDomain extends TransactionInfoDomain {
	
	@Id
	@Column(name="login_history_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="loginHistoryDomain")
	@TableGenerator(name="loginHistoryDomain", initialValue=1, allocationSize=1)
	private long numId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_login")
	private Date loginDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_logout")
	private Date logoutDate;
	
	@Column(name="str_user_name",length=100)
	private String userName;
	
	@Column(name="str_user_email",length=100)
	private String userEmail;
	
	@Column(name="num_user_id")
	private long userId;
	
	@Column(name="str_browser_name",length=100)
	private String browserName;
	
	/*@Column(name="str_browser_version",length=50)
	private String browserVersion;*/
	
	@Column(name="str_ip_address",length=100)
	private String ipAddress;
	
	@Column(name="str_os_type",length=50)
	private String osType;
	
	@Column(name="str_session_id",length=50)
	private String sessionId;
	
	@Column(name="str_logout_Url",length=200)
	private String logoutUrl;
	

}
