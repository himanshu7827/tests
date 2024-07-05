package in.pms.transaction.model;

import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectInvoiceMasterModel;
import in.pms.master.model.ProjectMasterForm;
import in.pms.master.model.ProjectMilestoneForm;
import in.pms.master.model.ProjectPaymentReceivedModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectReportModel {
	
	ProjectMasterForm projectMasterForm;
	List<ProjectDocumentMasterModel> projectDocuments;
	List<ProjectInvoiceMasterModel> projectInvoices;
	List<ProjectPaymentReceivedModel> paymentReceived;
	List<ProjectMilestoneForm> mileStones;

}
