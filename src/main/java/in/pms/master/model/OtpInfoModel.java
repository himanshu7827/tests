package in.pms.master.model;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@ToString
public class OtpInfoModel 
{
	private long oid;
	String stremail;
	String otpenter;
	private boolean NumIsValid;
	private int captcha;
	//private Date validUpto;
}
