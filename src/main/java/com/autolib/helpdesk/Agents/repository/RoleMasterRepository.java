package com.autolib.helpdesk.Agents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Agents.entity.RoleMaster;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Integer> {

	RoleMaster findById(int id);

}
