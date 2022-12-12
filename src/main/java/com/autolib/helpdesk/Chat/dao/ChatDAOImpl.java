package com.autolib.helpdesk.Chat.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.autolib.helpdesk.Chat.repository.MessageRepository;
import com.autolib.helpdesk.common.GlobalAccessUtil;

@Repository
@Transactional
public class ChatDAOImpl implements ChatDAO {

	@Autowired
	private JdbcTemplate jdbcTemp;
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private MessageRepository msgRepo ;

	@Override
	public Map<String, Object> getChatSettings() {
		logger.info("getChatSettings DAO starts:::::::");
		Map<String, Object> resmap = new HashMap<>();
		try {

			resmap.put("ChatSettings",
					jdbcTemp.queryForList("select m.first_name as first_name,m.email_id as agent_email_id,"
							+ "m.photo as photo,m.designation as designation,m.employee_id as employee_id "
							+ "from agents m left outer join chat_setting cs on (cs.agent_email_id = m.email_id) "));

			resmap.putAll(GlobalAccessUtil.SuccessResponse());

		} catch (Exception Ex) {
			resmap.putAll(GlobalAccessUtil.FailedResponse());
			logger.error("ERROR getChatSettings DAO ::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("getChatSettings Ends DAO :::::::");
		return resmap;
	}

	@Override
	public Map<String, Object> saveChatSettings(Map<String, Object> req) {
		logger.info("saveChatSettings DAO starts:::::::" + req);
		Map<String, Object> resmap = new HashMap<>();
		 
		try {
			List<Map<String, Object>> ChatSettings = (List<Map<String, Object>>) req.get("ChatSettings");
			Set<String> admins = new HashSet<>();
			
			ChatSettings.forEach(setting -> {
				if (String.valueOf(setting.get("chat_enable")).equalsIgnoreCase("true")
						|| String.valueOf(setting.get("chat_enable")).equalsIgnoreCase("1")) {
					admins.add(String.valueOf(setting.get("agent_email_id")));
				}
			});

			jdbcTemp.update("delete from chat_setting");

			admins.forEach(admin -> {
				jdbcTemp.update(
						"insert into chat_setting(agent_email_id , chat_enable , enable_date , last_enable_date , last_disable_date) "
								+ "values ('" + admin + "' , 1 , now() , now() , now())");
			});

			resmap.putAll(GlobalAccessUtil.SuccessResponse());

		} catch (Exception Ex) {
			resmap.putAll(GlobalAccessUtil.FailedResponse());
			logger.error("ERROR saveChatSettings DAO ::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("saveChatSettings Ends DAO :::::::");
		return resmap;
	}

	@Override
	public Map<String, Object> getMessages(Map<String, Object> req) {
		logger.info("getMessages DAO starts:::::::" + req);
		Map<String, Object> resp = new HashMap<>();
		
		try {

			String filter = "";

			if (Integer.parseInt(req.get("lastid").toString()) > 0)
				filter = " and id < " + req.get("lastid");

			String Query = "select id,agent_email_id as agentEmailId,chat_id as chatId,"
					+ "is_deleted as isDeleted,user_email_id as userEmailId,message as message,"
					+ "message_by as messageBy,message_datetime as messageDateTime,chat_type as chatType from chats where chat_id='"
					+ req.get("chat_id") + "' OR chat_id='"+req.get("chat_id2")+"' " + filter + " order by id desc limit 50;";
			
			System.out.println(Query);
			resp.put("Messages", jdbcTemp.queryForList(Query));		
			jdbcTemp.update("UPDATE chats SET is_read=true WHERE chat_id='" + req.get("chat_id2") + "'");
				
			jdbcTemp.update("UPDATE chats_last_messages SET is_read_by_agent=true,is_read_by_user=true WHERE chat_id='" + req.get("chat_id2") + "'"); 
			System.out.println("Done:::::::::"+req.get("chat_id"));
			
			
			resp.putAll(GlobalAccessUtil.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(GlobalAccessUtil.FailedResponse());
			logger.error("ERROR getMessages DAO ::" + Ex);
			Ex.printStackTrace();
		}
		logger.info("getMessages Ends DAO :::::::");
		return resp;
	}

	@Override
	public Map<String, Object> getRecentMessageMembers(Map<String, Object> req) {
		logger.info("getRecentMessageMembers DAO starts:::::::" + req);
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> recentMessageMembers = new ArrayList<>();
		
		try {
			String Query = "select 1;";

			jdbcTemp.update("SET session sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
		
//			Query = "SELECT m.first_name,m.designation,lm.lastmsgid,lm.last_message,m.employee_id as employee_id,"
//					+ " lm.agent_email_id,lm.chat_id,lm.user_email_id,lm.agent_email_id as agent_email_id,"
//					+ " lm.is_read_by_agent as is_read,lm.last_message_datetime,lm.last_message_by,m.photo"
//					+ " FROM chats_last_messages lm join agents m on (lm.agent_email_id = m.email_id) AND lm.agent_email_id !='"+req.get("agent_email_id")+"'\r\n" + 
//					"ORDER BY last_message_datetime desc";
			
			
//			Query = "SELECT m.first_name,m.designation,lm.lastmsgid,lm.last_message,m.employee_id as employee_id,"
//					+ " lm.agent_email_id,lm.chat_id,lm.user_email_id,lm.agent_email_id as agent_email_id,"
//					+ " lm.is_read_by_agent as is_read,lm.last_message_datetime,lm.last_message_by,m.photo"
//					+ " FROM chats_last_messages lm join agents m on (lm.agent_email_id = m.email_id) AND lm.agent_email_id !='"+req.get("agent_email_id")+"'\r\n" + 
//					"AND user_email_id='"+req.get("agent_email_id")+"' ORDER BY last_message_datetime desc";
//			
			
//			Query = "SELECT recent.*,m.first_name,m.photo,m.designation,m.employee_id as employee_id FROM ((SELECT lm.agent_email_id AS agent_email_id,lm.chat_id,lm.lastmsgid,lm.last_message,lm.is_read_by_user AS is_read,lm.last_message_datetime,lm.last_message_by FROM chats_last_messages lm WHERE user_email_id = '"+req.get("agent_email_id")+"') "
//					+ "UNION(SELECT agent_email_id AS agent_email_id,'' AS chat_id,0 AS lastmsgid,'' AS last_message,1 AS is_read,NOW() AS last_message_datetime,'' AS last_message_by FROM chat_setting)) AS recent JOIN agents m ON (recent.agent_email_id = m.email_id) "
//					+ "WHERE agent_email_id !='"+req.get("agent_email_id")+"' GROUP BY agent_email_id";
			
			Query = "SELECT ic.email_id,ic.first_name,lm.chat_id,lm.is_read_by_agent,lm.is_read_by_user,\r\n" + 
					"lm.last_message_by,lm.last_message_datetime,lm.last_message,lm.lastmsgid,lm.user_email_id,lm.agent_email_id,ins.institute_id,ins.institute_name FROM institute_contact ic\r\n" + 
					"JOIN chats_last_messages lm ON (ic.email_id=lm.user_email_id)\r\n" + 
					"JOIN institutes ins ON (lm.institute_id=ins.institute_id)\r\n" + 
					"";
			

			recentMessageMembers = jdbcTemp.queryForList(Query);
	
			resp.putAll(GlobalAccessUtil.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(GlobalAccessUtil.FailedResponse());
			logger.error("ERROR getRecentMessageMembers DAO ::" + Ex);
			Ex.printStackTrace();
		}
		resp.put("RecentMessageMembers", recentMessageMembers);
		
		logger.info("getRecentMessageMembers Ends DAO :::::::" + resp);
		return resp;
	}

	@Override
	public Map<String, Object> getUnreadMessageCount(String chatEmployeeId) {
		
		Map<String,Object> resp = new HashMap<>();
		logger.info("getUnreadMessageCount DAO :::::::" + chatEmployeeId);
		try {		
			resp.put("unreadTexts", msgRepo.findUnreadMessagesCount(chatEmployeeId));
			resp.putAll(GlobalAccessUtil.SuccessResponse());
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return resp;
	}

	@Override
	public Map<String, Object> getUserAgentMessages(Map<String, Object> req) {
		Map<String,Object> respMap = new HashMap<>();
		try {
		logger.info("getUserAgentMessages DAO :::::::" + req);
		String query ="";
		query="SELECT id as id,agent_email_id as agentMailId,chat_id as chatId,is_deleted as isDeleted,is_read as isRead,message as message,message_by as messageBy,message_datetime as messageDatetime,user_email_id as userMailId,chat_type as chatType FROM chats\r\n" + 
				"WHERE chat_id='"+req.get("chat_id")+"' OR chat_id='"+req.get("chat_id2")+"'";
		
		respMap.put("Messages",jdbcTemp.queryForList(query));
		respMap.putAll(GlobalAccessUtil.SuccessResponse());
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
			respMap.putAll(GlobalAccessUtil.FailedResponse());
		}
		return respMap;
	}

	@Override
	public Map<String, Object> getRecentMessageUser(Map<String, Object> req) {
		logger.info("getRecentMessageUser DAO starts:::::::" + req);
		Map<String, Object> resp = new HashMap<>();
		List<Map<String, Object>> recentMessageMembers = new ArrayList<>();
		
		try {
			String Query = "select 1;";

			jdbcTemp.update("SET session sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
		
			Query = "SELECT ic.email_id,ic.first_name,lm.chat_id,lm.is_read_by_agent,lm.is_read_by_user,\r\n" + 
					"lm.last_message_by,lm.last_message_datetime,lm.last_message,lm.lastmsgid,lm.user_email_id,lm.agent_email_id,ins.institute_id,ins.institute_name FROM institute_contact ic\r\n" + 
					"JOIN chats_last_messages lm ON (ic.email_id=lm.user_email_id)\r\n" + 
					"JOIN institutes ins ON (lm.institute_id=ins.institute_id)\r\n" + 
					"";
			

			recentMessageMembers = jdbcTemp.queryForList(Query);
	
			resp.putAll(GlobalAccessUtil.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(GlobalAccessUtil.FailedResponse());
			logger.error("ERROR getRecentMessageUser DAO ::" + Ex);
			Ex.printStackTrace();
		}
		resp.put("RecentMessageMembers", recentMessageMembers);
		
		logger.info("getRecentMessageUser Ends DAO :::::::" + resp);
		return resp;
	}

}
