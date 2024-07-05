package in.pms.master.domain;


import in.pms.global.domain.TransactionInfoDomain;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="PMS_FORGOT_PASSWORD_MST_log",schema="pms_log")
@Table(name="PMS_FORGOT_PASSWORD_MST")
public class ForgotPassword extends TransactionInfoDomain implements Serializable
{
	
	
		 
		private static final long serialVersionUID = -3279430064453904135L;

		@Id
		@Column(name="NUM_ID")
		public int numId;
		
		@Column(name="USER_NAME")
		public String strUserName;
		
		@Column(name="PASSWORD" , length=300)
		public String password;
			
		@Column(name="MAIL_CONTENT" , length=1000)
		public String mailContent;

}
