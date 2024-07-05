package in.pms.global.domain;

import java.io.Serializable;

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
@Table(name = "pms_action_master")
public class ActionMasterDomain extends TransactionInfoDomain implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2875154255543702382L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="actionMaster")
	@TableGenerator(name="actionMaster", initialValue=1, allocationSize=1)
	@Column(name="num_action_id",length=10)	
	private long numActionId;
	@Column(name="str_name",length=250,unique=true)
	private String strName;
	@Column(name="str_action_performed",length=250)
	private String strActionPerformed;
	
	
}
