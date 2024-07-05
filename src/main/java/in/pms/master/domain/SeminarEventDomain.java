package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.MonthlyProgressDetails;

import java.io.Serializable;
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
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity

@Table(name="pms_seminar_event_workshop")
public class SeminarEventDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724774084828868997L;
	
	
	
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_type")
	private String seminarType;
	@Column(name="dt_date_of_seminar")
	private Date dateOfSeminar;
	@Column(name="str_objective", length=1000)
	private String objectives;
	@Column(name="str_coordinating_person", length=100)
	private String coordinatingPerson;
	@Column(name="str_collaborators", length=1000)
	private String Collaborators;
	@Column(name="str_cdac_roles", length=1000)
	private String cdacRoles;
	@Column(name="str_venue", length=200)
	private String venue;
	@Column(name="num_no_of_participant",columnDefinition="int default 0")
	private int noOfParticipant;
	@Column(name="str_profile_of_participant", length=1000)
	private String strProfileOfParticipant;
	@Column(name="str_any_other_deatils", length=1000)
	private String anyOtherTypeDetails;
	
	
	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
}
