package in.pms.master.dao;

import in.pms.master.domain.DocumentClassificationDomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentClassificationDao extends JpaRepository<DocumentClassificationDomain, Integer>{

}
