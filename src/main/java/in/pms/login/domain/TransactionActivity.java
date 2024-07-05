package in.pms.login.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pms_transaction_activity", schema="pms")
public class TransactionActivity {
	@Id
	@Column(name="num_activity_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TransactionActivity")
	@TableGenerator(name="TransactionActivity", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_user_agent")
	@Type(type="text")
	private String userAgent;  
	@Column(name="str_request_method",length=10)
    private String requestMethod;
	
	@Column(name="str_url",length=200)
    private String url;
	@Column(name="dt_transaction")
	private Date transactionDate;
	@Column(name="num_user_id")
	private long userId;
	@Column(name="str_session_id",length=50)
	private String sessionId;
}
