package in.pms.transaction.service;

import in.pms.master.model.ProjectMasterForm;
import in.pms.transaction.model.DesignationForClientModel;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

public interface ManpowerExpensesService {

	@Transactional
	public void downloadManpowerExpensesExcel(ProjectMasterForm projectMasterModel, HttpServletResponse response);

	public Double getBaseCostFromDesignationName(String designationName, int numDeputedAt, long projectId);

	public Double getBaseCostFromDesignationId(int designationId, int numDeputedAt, long projectId);

	public Double calculateEffectiveBaseCostByDesgId(int designationId, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted);

	public Double calculateEffectiveBaseCostByDesgName(String designationName, int numDeputedAt, long projectId, String overlapStartFormatted, String overlapEndFormatted);

	public List<DesignationForClientModel> getBaseCostDetailsFromDesignationName(String categoryName, String designationName,int numDeputedAt);

}
