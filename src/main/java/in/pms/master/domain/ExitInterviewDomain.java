package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@AuditTable(value="pms_interview_exit_child_mst_log",schema="pms_log")
@Table(name="pms_interview_exit_child_mst")
public class ExitInterviewDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="interviewExitMaster")
	@TableGenerator(name="interviewExitMaster", initialValue=1, allocationSize=1)
	private long numId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_interview_exit_id_fk")
	private ExitInterviewMainDomain exitInterviewMainDomain;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="answer_id_fk")
	private AnswerMasterDomain answerMasterDomain;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="question_id_fk")
	private QuestionMasterDomain QuestionMasterDomain;
			
	}
