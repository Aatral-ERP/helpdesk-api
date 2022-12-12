package com.autolib.helpdesk.Institutes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.autolib.helpdesk.Agents.entity.Product;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteProducts;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;

public interface InstituteProductRepository extends CrudRepository<InstituteProducts, Integer> {

	List<InstituteProducts> findByInstitute(Institute inst);

	InstituteProducts findByInstituteAndProduct(Institute institute, Product product);

	@Transactional
	@Modifying
	@Query(value = "update InstituteProducts ip set ip.currentServiceUnder = ?1 where ip.amcExpiryDate < ?2")
	void updateAMCStatus(ServiceUnder serviceUnder, Date date);

	List<InstituteProducts> findByInstituteAndCurrentServiceUnderNotIn(Institute inst, List<ServiceUnder> currentServiceUnder);

}
