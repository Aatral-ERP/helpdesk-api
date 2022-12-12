package com.autolib.helpdesk.Needed.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Needed.dao.NeededDAO;

@Service
public class NeededServiceImpl implements NeededService {

	@Autowired
	private NeededDAO neededDAO;

	@Override
	public Map<String, Object> getNeeded(Map<String, Object> req) {
		return neededDAO.getNeeded(req);
	}

}
