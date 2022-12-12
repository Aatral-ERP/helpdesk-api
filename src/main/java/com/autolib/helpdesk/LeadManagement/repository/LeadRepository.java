package com.autolib.helpdesk.LeadManagement.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.LeadManagement.model.Lead;

public interface LeadRepository extends JpaRepository<Lead, Integer> {

	Lead findById(int leadId);

	@Query(value = "select DISTINCT(l.company) from Lead l")
	List<String> findDistinctCompanies();

	@Query(value = "select DISTINCT(l.products) from Lead l")
	List<String> findDistinctProducts();

	@Query(value = "select DISTINCT(l.industryType) from Lead l where l.industryType is not null")
	List<String> findDistinctIndustryType();

	@Query(value = "select DISTINCT(l.category) from Lead l where l.category is not null")
	List<String> findDistinctCategory();

	@Query(value = "select DISTINCT(l.leadSource) from Lead l where l.leadSource is not null")
	List<String> findDistinctLeadSources();

	@Query(value = "select DISTINCT(l.status) from Lead l")
	List<String> findDistinctLeadStatus();
	
	@Query(value = "select DISTINCT(l.city) from Lead l")
	List<String> findDistinctLeadCities();
	
	@Query(value = "select DISTINCT(l.country) from Lead l")
	List<String> findDistinctLeadCountries();

	List<Lead> findByStatusIn(Set<String> status);

	List<Lead> findByStatus(String status);

	@Query(value = "select DISTINCT(l.state) from Lead l where l.state is not null")
	List<String> findDistinctLeadStates();
	
	
	
	@Query(value = "select DISTINCT(l.company) from Lead l")
	List<String> findDistinctLeadInstitutes();
	
	@Query(value = "select DISTINCT(l.title) from Lead l")
	List<String> findDistinctLeadTitles();

	List<Lead> findBySendEmailUpdatesAndStatusInAndCategoryInAndIndustryTypeInAndStateIn(boolean b, List<String> asList,
			List<String> asList2, List<String> asList3, List<String> asList4);

	@Query(value = "SELECT owner_email_id,owner_name,`status`,COUNT(*) as cnt FROM leads "
			+ "WHERE lead_date BETWEEN ?1 AND ?2 GROUP BY owner_email_id,status", nativeQuery = true)
	List<Map<String, String>> findLeadOwnerByStatus(Date fromDate, Date toDate);

	@Query(value = "SELECT lead_source,COUNT(*) as cnt FROM leads WHERE lead_date BETWEEN ?1 AND ?2 "
			+ "GROUP BY lead_source;", nativeQuery = true)
	List<Map<String, String>> findLeadBySources(Date fromDate, Date toDate);

	@Query(value = "SELECT category,COUNT(*) as cnt FROM leads WHERE lead_date BETWEEN ?1 AND ?2 "
			+ "GROUP BY category", nativeQuery = true)
	List<Map<String, String>> findLeadByCategory(Date fromDate, Date toDate);

	@Query(value = "SELECT `status`,COUNT(*) as cnt FROM leads WHERE lead_date BETWEEN ?1 AND ?2 "
			+ "GROUP BY STATUS", nativeQuery = true)
	List<Map<String, String>> findLeadByStatus(Date fromDate, Date toDate);

	@Query(value = "SELECT state,COUNT(*) as cnt FROM leads WHERE lead_date BETWEEN ?1 AND ?2 "
			+ "GROUP BY state", nativeQuery = true)
	List<Map<String, String>> findLeadByStates(Date fromDate, Date toDate);

	@Query(value = "SELECT products FROM leads WHERE lead_date BETWEEN ?1 AND ?2 ", nativeQuery = true)
	List<String> findLeadByProducts(Date fromDate, Date toDate);

}
