package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.domain.Cities;
import in.pms.master.domain.States;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

/*@Entity
@Table(name="PMS_EVENT_DETAILS")
@Setter
@Getter*/
public class EventDetail extends TransactionInfoDomain{
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="eventdtl")
	@TableGenerator(initialValue=1, allocationSize=1, name="eventdtl")
	@Column(name="num_event_id")*/
	private Integer numEventId ;	
	
	@Column(name="str_event_title",length=500)
	private String eventTitle;	
	
	@Column(name = "dt_start")
    Date eventStartDate;
	
	@Column(name = "num_of_days")
	int noOfDays;
	
	@Column(name="organiser_name")
	private String strOrganiserName;
	
	@Column(name="website",length=300)
	private String strWebsite;
	

	@ManyToOne(optional = false)
	@JoinColumn(name = "num_event_type_fk")
	EventType eventType;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_state_id_fk")
	States States;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "num_city_id_fk")
	Cities cities;
	
	@Column(name="str_address",length=2000)
	private String address;

}
