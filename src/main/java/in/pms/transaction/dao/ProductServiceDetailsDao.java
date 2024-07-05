package in.pms.transaction.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.transaction.domain.DeploymentTotDetailsDomain;
import in.pms.transaction.domain.PatentDetailsDomain;
import in.pms.transaction.domain.ProductServiceDetailsDomain;


@Repository
public interface ProductServiceDetailsDao extends JpaRepository<ProductServiceDetailsDomain, Long>{
	
	@Query(" from ProductServiceDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.monthlyProgressDomain.numId =:monthlyProgressId and a.monthlyProgressDetails.numCateoryId=:categoryId")
	public List<ProductServiceDetailsDomain> getProductServiceDetails(@Param("monthlyProgressId") int monthlyProgressId,@Param("categoryId") long categoryId);
	
	@Query(" from ProductServiceDetailsDomain a  where a.numIsValid = 1 and a.monthlyProgressDetails.numId =:progressDetailsId order by dtTrDate desc")
	public List<ProductServiceDetailsDomain> loadProductServiceByDetailsId(@Param("progressDetailsId") long progressDetailsId);
}
