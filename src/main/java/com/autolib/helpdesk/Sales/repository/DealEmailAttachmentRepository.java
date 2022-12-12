package com.autolib.helpdesk.Sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Sales.model.DealEmailAttachments;

public interface DealEmailAttachmentRepository extends JpaRepository<DealEmailAttachments, Integer> {

	List<DealEmailAttachments> findByEmailId(int dealEmailId);

	List<DealEmailAttachments> findByDealId(int dealId);

}
