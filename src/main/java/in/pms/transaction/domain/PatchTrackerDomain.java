package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@AuditTable(value="pms_patch_tracker_log",schema="pms_log")
@Table(name="pms_patch_tracker")
public class PatchTrackerDomain extends TransactionInfoDomain implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "patchtracker")
@TableGenerator(name = "patchtracker")

@Column(name = "num_patch_tracker_id")
private long id;

@Column(name="str_severity",length=10)
private String severity;

@Column(name="str_type",length = 20)
private String type;

@Column(name="str_description",length = 2000)
private String strdescription;

@Column(name="str_request_by",length = 1000)
private String strRequestedBy;

@Column(name="str_name_of_files",length = 2000)
private String strNameOfFiles;

@Column(name= "str_team_members",length = 2000)
private String strTeamMembers;

@Column(name="str_bugid",length = 100)
private String strBugId;

@Column(name="dt_dep_date")
private Date depDate;

@Column(name="str_svn_no",length = 20)
private String strSvnNo;

@Column(name="num_is_restart_required")
private boolean valid;

@Column(name="num_patch_status")
private boolean valid2;

@Column(name="str_stage",length = 20)
private String stage;

@Column(name="str_modules",length = 1000)
private String strModules;

}