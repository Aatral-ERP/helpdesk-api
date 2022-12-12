package com.autolib.helpdesk.Accounting.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Accounting.model.IncomeExpense;

public interface IncomeExpenseRepository extends JpaRepository<IncomeExpense, Integer> {

	IncomeExpense findById(int id);

	// Accounting Queries
	@Query(value = "CALL get_accounts_income_data()", nativeQuery = true)
	List<Map<String, Object>> getAccountingIncomeReport();

	@Query(value = "call get_accounts_expense_data()", nativeQuery = true)
	List<Map<String, Object>> getAccountingExpenseReport();

	@Query(value = "SELECT _dates.`name`,IFNULL(_data.`value` , 0) AS `value` FROM "
			+ "( SELECT _date,DATE_FORMAT(_date,'%b %Y') AS `name`" + "FROM ("
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AS _date UNION ALL"
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 2 MONTH UNION ALL"
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 3 MONTH UNION ALL"
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 4 MONTH UNION ALL"
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 5 MONTH UNION ALL"
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 6 MONTH "
			+ ") AS _dates ) AS _dates LEFT JOIN("
			+ " SELECT SUM(dp.total_amount) AS `value`,DATE_FORMAT(dp.payment_date,'%b %Y') AS `name`,dp.payment_date "
			+ " FROM deal_payments dp"
			+ " WHERE dp.payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 2 YEAR) , '%Y-%m-01')"
			+ " GROUP BY `name` ORDER BY dp.payment_date ) AS _data ON (_dates.name = _data.name)"
			+ " ORDER BY _dates._date ", nativeQuery = true)
	List<Map<String, Object>> getLast6MonthsIncome();

	@Query(value = "SELECT _dates.`name`,IFNULL(_data.`value` , 0) AS `value` FROM  "
			+ "( SELECT _date,DATE_FORMAT(_date,'%b %Y') AS `name` " + "FROM ( "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AS _date UNION ALL "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 2 MONTH UNION ALL "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 3 MONTH UNION ALL "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 4 MONTH UNION ALL "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 5 MONTH UNION ALL "
			+ " SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 6 MONTH  "
			+ ") AS _dates ) AS _dates LEFT JOIN(  " + "SELECT SUM(`value`) AS `value`, `name` FROM ( "
			+ "SELECT SUM(dibp.total_amount) AS `value`,DATE_FORMAT(dibp.payment_date,'%b %Y') AS `name` "
			+ "FROM purchase_inputs_bill_payments dibp "
			+ "WHERE dibp.payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 2 YEAR) , '%Y-%m-01') "
			+ "GROUP BY `name` UNION ALL "
			+ "SELECT SUM(net_pay) AS `value`,CONCAT(LEFT(salary_month, 3), ' ', salary_year) AS `name` FROM salary_entries  "
			+ "GROUP BY `name`) AS _dd GROUP BY _dd.name ) AS _data ON (_dates.name = _data.name) "
			+ " ORDER BY _dates._date ", nativeQuery = true)
	List<Map<String, Object>> getLast6MonthsExpense();

	@Query(value = "SELECT _dates.`name`,IFNULL(_data.`value` , 0) AS `value` FROM "
			+ "( SELECT _date,DATE_FORMAT(_date,'%b %Y') AS `name`" + "FROM ("
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AS _date UNION ALL"
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 2 MONTH UNION ALL"
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 3 MONTH UNION ALL"
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 4 MONTH UNION ALL"
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 5 MONTH UNION ALL"
			+ "SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 6 MONTH "
			+ ") AS _dates ) AS _dates LEFT JOIN("
			+ "SELECT SUM(dibp.total_amount) AS `value`,DATE_FORMAT(dibp.payment_date,'%b %Y') AS `name`,dibp.payment_date "
			+ "FROM purchase_inputs_bill_payments dibp"
			+ "WHERE dibp.payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 2 YEAR) , '%Y-%m-01')"
			+ "GROUP BY `name` ORDER BY dibp.payment_date ) " + "AS _data ON (_dates.name = _data.name)"
			+ "ORDER BY _dates._date ", nativeQuery = true)
	List<Map<String, Object>> getAccountingChartThisFinancialYear();

	@Query(value = "SELECT SUM(total_amount),DATE_FORMAT(payment_date,'%b %y') AS label FROM deal_payments"
			+ " WHERE CASE WHEN MONTH(CURDATE()) <= 3 THEN "
			+ " payment_date BETWEEN DATE(CONCAT(YEAR(NOW())-2 , '-04-01')) AND DATE(CONCAT(YEAR(NOW())-1 , '-03-31'))  "
			+ " WHEN MONTH(CURDATE()) > 3 THEN "
			+ " payment_date BETWEEN DATE(CONCAT(YEAR(NOW())-1 , '-04-01')) AND DATE(CONCAT(YEAR(NOW()) , '-03-31')) END "
			+ " GROUP BY DATE_FORMAT(payment_date,'%Y-%m')", nativeQuery = true)
	List<Map<String, Object>> getAccountingChartLastFinancialYear();

	@Query(value = "CALL get_recent_expenses(?1 , ?2);", nativeQuery = true)
	List<Map<String, Object>> get_recent_expenses(Date from, Date to);

	@Query(value = "CALL get_recent_incomes(?1 , ?2);", nativeQuery = true)
	List<Map<String, Object>> get_recent_incomes(Date from, Date to);

	@Query(value = "select distinct category from IncomeExpense where type= ?1")
	List<String> findDistinctCategory(String type);

	List<IncomeExpense> findByTypeAndRelatedToAgentId(String type, String emailId);

}
