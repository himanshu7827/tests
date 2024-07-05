package in.pms.transaction.service;

import java.util.List;

import in.pms.transaction.model.ProductsDevelopedModel;
import in.pms.transaction.model.PublicationDetailsModel;

import org.springframework.transaction.annotation.Transactional;




public interface ProductsDevelopedService {	
	@Transactional
	public String SaveProductsDeveloped(ProductsDevelopedModel productsDevelopedModel);
	
	public String getSavedProductsDeveloped(int reportId);
	
	public void writeProductsDevelopedAuthorityCheck();
	
	public void readProductsDevelopedAuthorityCheck();
	
	@Transactional
	public List<ProductsDevelopedModel> loadProductsDevelopedDetail(int monthlyProgressId,long categoryId);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);
	
	@Transactional
	public void deleteProductsDeveloped(ProductsDevelopedModel productsDevelopedModel);

	
}