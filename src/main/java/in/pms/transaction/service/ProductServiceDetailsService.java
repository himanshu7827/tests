package in.pms.transaction.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import in.pms.transaction.domain.ProductServiceDetailsDomain;
import in.pms.transaction.model.ProductServiceDetailsModel;

public interface ProductServiceDetailsService {

	
	boolean saveUpdateProductServiceDetails(HttpServletRequest request,
			ProductServiceDetailsModel productServiceDetailsModel);

	@Transactional
	List<ProductServiceDetailsModel> getProductServiceDetails(int monthlyProgressId, long numCategoryId);
	@Transactional
	void deleteProductServiceDetails(ProductServiceDetailsModel productServiceDetailsModel);
	
	public List<Object[]> getPreviewDataWithHeader(long progressDetailsId);

}
