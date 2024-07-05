package in.pms.transaction.controller;

import in.pms.global.service.EncryptionService;
import in.pms.master.domain.ProjectMasterDomain;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectPaymentReceivedModel;
import in.pms.master.service.ProjectDocumentMasterService;
import in.pms.master.service.ProjectInvoiceMasterService;
import in.pms.master.service.ProjectMasterService;
import in.pms.master.service.ProjectPaymentReceivedService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectReportController {
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectDocumentMasterService projectDocumentMasterService;
	
	@Autowired
	ProjectInvoiceMasterService projectInvoiceMasterService;
	
	@Autowired
	ProjectPaymentReceivedService projectPaymentReceivedService;
	
	@GetMapping("/data/projectdetail/{encId}")
	public  ProjectMasterDomain viewProjectDetailsById(@PathVariable("encId") String encId){
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);
		ProjectMasterDomain projectMasterDomain =projectMasterService.getProjectMasterDataById(projectId);		
		return projectMasterDomain;
	}
	
	@GetMapping("/data/projectDocumentDetailById/{encId}")
	public  List<ProjectDocumentMasterModel> viewProjectDocumentById(@PathVariable("encId") String encId){
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);				
		return projectDocumentMasterService.uploadedDocumentByProjectId(projectId);
	}
	
	@GetMapping("/data/projectInvoiceDetailById/{encId}")
	public  List<ProjectDocumentMasterModel> viewProjectInvoiceById(@PathVariable("encId") String encId){
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);				
		return projectDocumentMasterService.uploadedDocumentByProjectId(projectId);
	}
	

	@GetMapping("/data/projectPaymentReceivedById/{encId}")
	public  List<ProjectPaymentReceivedModel> viewProjectPaymentReceivedById(@PathVariable("encId") String encId){
		String strProjectId= encryptionService.dcrypt(encId);
		long projectId= Long.parseLong(strProjectId);				
		return projectPaymentReceivedService.getActivePaymentReceivedByProjectId(projectId);
	}

}
