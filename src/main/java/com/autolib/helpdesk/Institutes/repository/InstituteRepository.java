package com.autolib.helpdesk.Institutes.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autolib.helpdesk.Institutes.model.Institute;

public interface InstituteRepository extends JpaRepository<Institute, String> {

	Institute findByInstituteId(String instituteId);

	@Query(value = "select new Institute(i.instituteId,i.instituteName,i.shortTerm,i.logourl,i.street1,i.street2,i.city,i.state,i.country,i.zipcode) from Institute i order by instituteName")
	List<Institute> getInstituteMimDetails();

	@Query(value = "select new Institute(i.instituteId,i.instituteName,i.shortTerm,i.logourl,i.street1,i.street2,i.city,i.state,i.country,i.zipcode) from Institute i order by instituteName")
	List<Institute> getInstituteMiniAddressDetails();

	@Query(value = "select new Institute(i.instituteId,i.instituteName,i.shortTerm,i.logourl,i.street1,i.street2,i.city,i.state,i.country,i.zipcode,i.emailId,i.alternateEmailId) from Institute i order by instituteName")
	List<Institute> getInstituteMinAddressEmailDetails();

	@Query(value = "select max(cast(i.institute_id as signed)) from institutes i ", nativeQuery = true)
	String findLastMaxId();

	@Query(value = "SELECT * FROM ("
			+ "(SELECT COUNT(*) AS institute_count FROM institutes WHERE institute_id = :instituteId) AS institute_count,"
			+ "(SELECT COUNT(*) AS institute_products_count FROM institute_products WHERE institute_id = :instituteId) AS institute_products_count,"
			+ "(SELECT COUNT(*) AS institute_contact_count FROM institute_contact WHERE institute_id = :instituteId) AS institute_contact_count,"
			+ "(SELECT COUNT(*) AS deals_count FROM deals WHERE institute_id = :instituteId) AS deals_count,"
			+ "(SELECT COUNT(*) AS amc_details_count FROM amc_details WHERE institute_id = :instituteId) AS amc_details_count,"
			+ "(SELECT COUNT(*) AS tickets_count FROM tickets WHERE institute_id = :instituteId) AS tickets_count"
			+ ")", nativeQuery = true)
	Map<String, Object> getInstitutePreDeleteDate(@Param("instituteId") String instituteId);

	List<Institute> findByInstituteIdIn(Set<String> ids);

	@Query(value = "select distinct i.instituteType from Institute i ", nativeQuery = false)
	List<String> getDistinctInstituteTypes();

}
