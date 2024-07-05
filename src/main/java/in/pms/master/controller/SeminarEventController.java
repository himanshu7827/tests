package in.pms.master.controller;

import in.pms.global.service.EncryptionService;
import in.pms.master.domain.SeminarEventDomain;
import in.pms.master.model.MediaModel;
import in.pms.master.model.SeminarEventModel;
import in.pms.master.service.SeminarEventService;
import in.pms.master.validator.RoleMasterValidator;
import in.pms.transaction.model.MonthlyProgressModel;
import in.pms.transaction.service.MonthlyProgressService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/mst")
@Slf4j
public class SeminarEventController {

	@Autowired
	SeminarEventService seminarEventService;
	
	@Autowired
	MonthlyProgressService monthlyProgressService;
	
	@Autowired
	EncryptionService encryptionService;

	@RequestMapping(value="/SeminarEvent",method = { RequestMethod.GET, RequestMethod.POST })
	public String getSeminarEvent(HttpServletRequest request,
			SeminarEventModel seminarEventModel) {
	
		monthlyProgressService.writeProgressReportAuthorityCheck();

		String encMonthlyProgressId="";
		String encCategoryId="";
		String message="";
		try {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!= null){
		String monthlyProgressId=(String) flashMap.get("encMonthlyProgressId");
		String categoryIdsave=(String) flashMap.get("encCategoryId");
		 message=(String) flashMap.get("message");
		encMonthlyProgressId= monthlyProgressId;
		encCategoryId =categoryIdsave;
		}
		else{

		encMonthlyProgressId = seminarEventModel.getEncMonthlyProgressId();
		encCategoryId = seminarEventModel.getEncCategoryId();

		}

		} catch (Exception e) {
		// TODO: handle exception
		}

		if (null != encMonthlyProgressId) {

			int monthlyProgressId = Integer.parseInt(encryptionService
					.dcrypt(encMonthlyProgressId));
			long catId = Long.parseLong(encryptionService
					.dcrypt(encCategoryId));
			MonthlyProgressModel monthlyProgressModel = monthlyProgressService
					.getById(monthlyProgressId);
			request.setAttribute("monthlyProgressModel", monthlyProgressModel);
			//long categoryId = seminarEventModel.getNumCategoryId();
			// List<MediaModel> list = mediaService.getAllMediaDetails();
			List<SeminarEventModel> list = seminarEventService
					.loadSeminarEventDetails(monthlyProgressId, catId);
			request.setAttribute("encMonthlyProgressId", encMonthlyProgressId);
			request.setAttribute("categoryId", encCategoryId);
		/*	request.setAttribute("encCategoryId",
					encryptionService.encrypt("" + categoryId));*/
			// request.setAttribute("modelList", modelList);
			request.setAttribute("data", list);
			request.setAttribute("message",message);
			
		}
		else{
			return "redirect:/mst/ViewAllProjects";
		}
		return "SeminarEvent";
	}

	@RequestMapping(value = "/saveSeminarEvent", method = RequestMethod.POST)
	public String saveUpdateRoleMaster(HttpServletRequest request,
			SeminarEventModel seminarEventModel,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) {
		/*
		 * new RoleMasterValidator().validate(seminarEventModel, bindingResult);
		 * if (bindingResult.hasErrors()) { List<SeminarEventModel> list =
		 * seminarEventService.getAllSeminar(); request.setAttribute("data",
		 * list); return "SeminarEvent"; }
		 */

		SeminarEventModel seminarEventModel2 = seminarEventService
				.saveUpdateSeminarDetails(seminarEventModel);
		
		 if(seminarEventModel2!=null){
			 if(seminarEventModel2.getNumId()==0){
				redirectAttributes.addFlashAttribute("message",
						"Seminar details saved Successfully ");
				redirectAttributes.addFlashAttribute("status", "success");
			} else {
				redirectAttributes.addFlashAttribute("message",
						"Seminar details updated Successfully");
				redirectAttributes.addFlashAttribute("status", "success");
			}
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", seminarEventModel2.getEncMonthlyProgressId());
			redirectAttributes.addFlashAttribute("encCategoryId", seminarEventModel2.getEncCategoryId());
		 }

		return "redirect:/mst/SeminarEvent";
	}

	@RequestMapping(value = "/deleteSeminarDetails", method = RequestMethod.POST)
	public String deleteSeminarDetails(SeminarEventModel seminarEventModel,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		try {
			SeminarEventModel seminarEventModel2=seminarEventService.deleteSeminarDetails(seminarEventModel);
			 if(seminarEventModel2!=null){
			redirectAttributes.addFlashAttribute("message",
					"Seminar details deleted Successfully");
			redirectAttributes.addFlashAttribute("status", "success");
			redirectAttributes.addFlashAttribute("encMonthlyProgressId", seminarEventModel2.getEncMonthlyProgressId());
			redirectAttributes.addFlashAttribute("encCategoryId", seminarEventModel2.getEncCategoryId());
			 }
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",
					"Something went wrong");
			redirectAttributes.addFlashAttribute("status", "error");
			log.error(e.getCause().getMessage());
			log.error(e.getMessage());

		}
		return "redirect:/mst/SeminarEvent";
	}

}
