package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.Audited;


/*@Getter
@Setter
@Entity
@Audited
@Table(name="pms_event_type")*/
public class EventType extends TransactionInfoDomain {

	
/*	@Id
	@Column(name="num_event_type_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="eventType")
	@TableGenerator(name="eventType", initialValue=1, allocationSize=1)*/	
	private long numId;
	
	@Column(name="str_event_type_name",length=100)
	private String eventTypeName;	
	
	@Column(name="str_description",length=2000)
	private String description;
	
	@OneToMany(mappedBy = "eventType", cascade = CascadeType.ALL)	
	private List<EventDetail> eventDetails;
	
}
