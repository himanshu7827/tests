package in.pms.master.domain;

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

@Entity
@Table(name="PMS_STATES_MST")
@Setter
@Getter
public class States extends TransactionInfoDomain {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="state")
	@TableGenerator(name="state", initialValue=1, allocationSize=1)	
	@Column(name="NUM_STATE_ID")
	int numStateId;
	
	@Column(name="STR_STATE_NAME",length=100)
    String strStateName;
}
