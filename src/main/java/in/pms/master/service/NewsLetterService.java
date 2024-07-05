package in.pms.master.service;

import in.pms.master.domain.NewsLetterFilterMaster;
import in.pms.master.domain.NewsLetterMaster;
import in.pms.master.domain.NewsLetterRoleMapping;
import in.pms.master.domain.NewsletterDocuments;
import in.pms.master.model.NewsLetterFilterForm;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface NewsLetterService {

	/*

	
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean checkValidation(String queryString);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean checkValidationForProposalID(String queryString);*/
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<NewsLetterFilterForm> getAllSavedFilters();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveFilter(NewsLetterFilterForm form);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteFilter(NewsLetterFilterForm form);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<NewsLetterFilterForm> getAllFilters();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<NewsLetterMaster> getAllSavedNewsLetter();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void sendAllNewsLetterNow(NewsLetterFilterForm newsLetterFilterForm);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveNewsLetters(NewsLetterFilterForm newsLetterFilterForm)throws ParseException; 
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteNewsLetter(String[] ids);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Integer> getNewsLetterFilterMappingDetails(int parseInt);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String getPeriodicNewsLetterDisplayDate(int parseInt);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String getNewsLetterDisplayDate(int newsletterid);
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public HashMap<Integer, String> getDocumentMapForNewsletter(int newsletterid);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public NewsLetterFilterForm getDetailsOfRole(int newsletterid);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public NewsletterDocuments getNewsletterDocuments(int docId);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void getNewsLettersToBeTransfer();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void getNewsLettersToBeTransferredPeriodically();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateNewsLetter(NewsLetterFilterForm newsLetterFilterForm)throws ParseException;
	
	@Transactional
	public String getDetailsOfEmplApplLevel(int roleId, int projectId);
	
	/*

	
		
	@Transactional(propagation=Propagation.REQUIRED)
	public void getNewsLettersToBeTransfer();
	
	



	


	



	@Transactional(propagation=Propagation.REQUIRED)
	public List<NewsLetterFilterMaster> getAllFiltersByFilterType(String filterType);
	
	//This is Suitable for FilterIds of Filter Type 'P' 
	@Transactional
	public List<BigInteger> getFilterDataByFilterIds(String filterId);*/		
		
	
}
