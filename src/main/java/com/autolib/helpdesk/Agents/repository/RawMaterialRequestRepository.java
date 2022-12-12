package com.autolib.helpdesk.Agents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Agents.entity.RawMaterialRequest;

public interface RawMaterialRequestRepository extends JpaRepository<RawMaterialRequest, Integer> {

	@Query(value = "select rq from RawMaterialRequest rq where rq.requestBy = ?1 ")
	List<RawMaterialRequest> getRawMaterialRequestsByMe(String emialId);

	@Query(value = "select rq from RawMaterialRequest rq where rq.requestTo = ?1 ")
	List<RawMaterialRequest> getRawMaterialRequestsToMe(String emialId);

	@Query(value = "select rq from RawMaterialRequest rq where rq.requestBy = ?1 ")
	List<RawMaterialRequest> getRawMaterialRequestsByMeApproved(String emialId);

	@Query(value = "select rq from RawMaterialRequest rq where rq.requestTo = ?1 ")
	List<RawMaterialRequest> getRawMaterialRequestsToMeApproved(String emialId);

}
