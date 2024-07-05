package in.pms.transaction.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeploymentTotDetailsModel {

	private long numDeploymentId;
	private long numTotId;
	private String strServiceName;
	private String strDescription;
	private long numUnitsDeployedOrSold;
	private String strDeploymentTotDate;
	private String dtDeploymenTotDate;
	private String strAgencyName;
	private String strAgencyCity;
	private int numAgencyStateId;
	private String strDeploymentCity;
	private int numDeploymentStateId;
	private long numDocumentId;
	private String strDocumentCaption;
	private long numCategoryId;
	private String strAgencyState;
	private String strDeploymentState;
	private long[] numDeploymentIds;
	private long[] numTotIds;
	private MultipartFile progressReportQualityImages;
	private String strDocumentName;
	private long[] numDocumentsIds;
	private String encMonthlyProgressId;
	private String encCategoryId;
	private String strDocumentIds;
	private String strDeploymentIds;
	
}
