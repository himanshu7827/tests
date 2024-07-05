package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="PMS_CITIES_MST")
@Setter
@Getter
public class Cities extends TransactionInfoDomain {	

	@Id
	@Column(name="NUM_CITY_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1000, allocationSize=1)
	private int cityId;  	
	
	@Column(name="STR_CITY_NAME")
    private String cityName;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_state_id_fk")
	States States;
}
