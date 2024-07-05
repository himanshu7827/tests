package in.pms.global.service;

import java.util.List;

import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ProjectMasterForm;

import javax.servlet.http.HttpServletResponse;

public interface MiscDocumentService {

	public void downloadTemplate(ProjectDocumentMasterModel projectDocumentMasterModel,HttpServletResponse response);

	/*----------------- Download details of ongoing project in excel sheet -------------------------------------*/
	public Boolean downloadOngoingProjects(List<ProjectMasterForm> list,HttpServletResponse response);

	/*----------------- Download details of Weekly Report in excel sheet -------------------------------------*/
	public Boolean downloadWeeklyReport(List<ProjectMasterForm> list,HttpServletResponse response);
}
