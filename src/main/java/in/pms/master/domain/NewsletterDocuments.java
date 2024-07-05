package in.pms.master.domain;




import in.pms.global.domain.TransactionInfoDomain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="serb_newsletter_documents")
public class NewsletterDocuments extends TransactionInfoDomain 

{
	
	@Id
	@Column(name="NUM_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	Integer numId;
	
	@Column(name="STR_DOC_NAME")
	private String strdocname;
	
	@Column(name="IS_DRAFT")
	private int isDraft;
	
	@Column(name="NUM_NEWSLETTER_ID", columnDefinition = "int default 0")
	private int newsLetterId;

	public int getNewsLetterId() {
		return newsLetterId;
	}

	public void setNewsLetterId(int newsLetterId) {
		this.newsLetterId = newsLetterId;
	}

	public Integer getNumId() {
		return numId;
	}

	public void setNumId(Integer numId) {
		this.numId = numId;
	}

	public String getStrdocname() {
		return strdocname;
	}

	public void setStrdocname(String strdocname) {
		this.strdocname = strdocname;
	}

	public int getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(int isDraft) {
		this.isDraft = isDraft;
	}


}
