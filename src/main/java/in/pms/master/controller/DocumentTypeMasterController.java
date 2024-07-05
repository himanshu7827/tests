package in.pms.master.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.pms.master.model.DocumentFormatModel;
import in.pms.master.model.DocumentTypeMasterModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.model.ThrustAreaMasterForm;
import in.pms.master.service.DocumentFormatService;
import in.pms.master.service.DocumentTypeMasterService;
import in.pms.master.validator.DocumentTypeMasterValidator;

@Controller
@RequestMapping("/mst")
public class DocumentTypeMasterController {
	@Autowired
	DocumentTypeMasterService documentTypeMasterService;
	
	@Autowired
	DocumentFormatService documentFormatService;

	@RequestMapping("/documentMaster")
	public String getAllDocumentTypeMaster(HttpServletRequest request,
			DocumentTypeMasterModel documentTypeMasterModel) {
		List<DocumentTypeMasterModel> list = documentTypeMasterService
				.getAllDocumentTypeMasterDomain();
		request.setAttribute("data", list);
		List<DocumentFormatModel> documentFormatList = documentFormatService.getActiveDocumentFormatModel();
		request.setAttribute("documentFormatList", documentFormatList);
		return "documentMaster";
	}

	@RequestMapping(value = "/saveUpdateDocTypeMaster", method = RequestMethod.POST)
	public ModelAndView saveUpdateDocTypeMaster(HttpServletRequest request,
			DocumentTypeMasterModel documentTypeMasterModel,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {

		ModelAndView mav = new ModelAndView();
		new DocumentTypeMasterValidator().validate(documentTypeMasterModel,
				bindingResult);
		if (bindingResult.hasErrors()) {
			List<DocumentTypeMasterModel> list = documentTypeMasterService
					.getAllActiveDocumentTypeMasterDomain();

			request.setAttribute("data", list);
			List<DocumentFormatModel> documentFormatList = documentFormatService.getActiveDocumentFormatModel();
			request.setAttribute("documentFormatList", documentFormatList);
			mav.setViewName("documentMaster");
			return mav;
		}
		String strDuplicateCheck = documentTypeMasterService
				.checkDuplicateDocumentTypeName(documentTypeMasterModel);
		if (null == strDuplicateCheck) {
			long id = documentTypeMasterService
					.saveUpdateDocTypeMaster(documentTypeMasterModel);
			if (id > 0) {
				if (documentTypeMasterModel.getNumId() == 0) {
					redirectAttributes.addFlashAttribute("message",
							"Document record saved Successfully with Id " + id);
					redirectAttributes.addFlashAttribute("status", "success");
				} else {
					redirectAttributes.addFlashAttribute("message",
							"Document record updated Successfully with Id "
									+ id);
					redirectAttributes.addFlashAttribute("status", "success");
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("message", strDuplicateCheck);
			redirectAttributes.addFlashAttribute("status", "error");
		}
		mav.setViewName("redirect:/mst/documentMaster");
		return mav;

	}

	// Delete
	@RequestMapping(value = "/deleteDocument", method = RequestMethod.POST)
	public String deleteDocument(
			DocumentTypeMasterModel documentTypeMasterModel,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		// System.out.println("inside controller");
		if (documentTypeMasterModel.getNumIds() == null
				|| documentTypeMasterModel.getNumIds().length == 0) {
			// System.out.println("length"+
			// documentTypeMasterModel.getNumIds());
			List<DocumentTypeMasterModel> list = documentTypeMasterService
					.getAllDocumentTypeMasterDomain();
			request.setAttribute("data", list);
			// System.out.println("before return");
			return "documentMaster";
		} else {
			try { // System.out.println("inside try");

				documentTypeMasterService
						.deleteDocument(documentTypeMasterModel);
				redirectAttributes.addFlashAttribute("message",
						"Document details deleted Successfully");
				redirectAttributes.addFlashAttribute("status", "success");
				List<DocumentTypeMasterModel> list = documentTypeMasterService
						.getAllDocumentTypeMasterDomain();
				request.setAttribute("data", list);

			} catch (Exception e) {
				System.out.println(e);

			}
			return "redirect:/mst/documentMaster";
		}
	}
	
	
	

}
