package in.pms.master.service;
/**
 * @author amitkumarsaini
 *
 */
import in.pms.master.model.ActionModel;

import java.util.List;

import javax.transaction.Transactional;

public interface ActionService {

	@Transactional
	public ActionModel saveUpdateOthers(ActionModel actionModel);

	@Transactional
	public ActionModel deleteOthers(ActionModel actionModel);

	@Transactional
	public List<ActionModel> loadAllAction();

	public String checkDuplicateAction(ActionModel actionModel);

}
