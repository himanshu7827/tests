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

@Table(name="pms_award_won")
public class AwardWonDomain extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -3724774084828868997L;

	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_type")
	private String awardType;
	@Column(name="dt_date_of_seminar")
	private Date dateOfAward;
	@Column(name="str_award_name", length=200)
	private String awardName;
	@Column(name="str_recipient_name", length=200)
	private String recipientName;
	@Column(name="str_achievement_description", length=1000)
	private String achievementDescription;
	@Column(name="str_award_by", length=200)
	private String awardBy;
	@Column(name="str_project_awarded_for", length=200)
	private String   projectAwardedFor;
	@Column(name="str_location", length=200)
	private String location;
	
	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
}
