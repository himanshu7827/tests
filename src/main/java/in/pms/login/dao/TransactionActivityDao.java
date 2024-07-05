package in.pms.login.dao;

import in.pms.login.domain.TransactionActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionActivityDao extends JpaRepository<TransactionActivity, Long> {

}
