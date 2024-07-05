package in.pms.master.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import in.pms.global.domain.TransactionInfoDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@AuditTable(value="pms_activity_mst_log",schema="pms_log")
@Table(name="pms_activity_master",schema="pms")
public class ActivityMasterDomain extends TransactionInfoDomain implements Serializable {

	/**
	 * 
	 */
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_activity_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="activityMaster")
	@TableGenerator(name="activityMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_activity_name",length=500)
	private String strActivityName;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "subactivity_activity_mapping", joinColumns = @JoinColumn(name = "num_activity_id"),inverseJoinColumns=@JoinColumn(name = "num_sub_activity_id"))
	public List<SubActivityMasterDomain> subActivityMasterDomain;

}


	

	
	

