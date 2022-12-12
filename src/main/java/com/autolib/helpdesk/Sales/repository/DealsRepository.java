/**
 * 
 */
package com.autolib.helpdesk.Sales.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Sales.model.Deal;
import com.autolib.helpdesk.Sales.model.Invoice.DealInvoice;

/**
 * @author Kannadasan
 *
 */
public interface DealsRepository extends JpaRepository<Deal, Integer> {

	Deal findById(int dealId);

	@Query(value = "select d.* from deals d where d.deal_type in (select dl.deal_type from deals dl where dl.id = ?1 ) and d.id != ?1 order by d.id desc limit 1 ", nativeQuery = true)
	Deal findLastDealByIdAndDealType(int dealId);

	// Sales Queries
	@Query(value = "SELECT * FROM("
			+ "( SELECT COUNT(*) AS quotationCount FROM deal_quotations WHERE quote_date BETWEEN ?1 and ?2) AS quotationCount, "
			+ "( SELECT COUNT(*) AS poCount FROM deal_purchase_orders WHERE purchase_order_date BETWEEN ?1 and ?2) AS poCount, "
			+ "( SELECT COUNT(*) AS invoiceCount FROM deal_invoices WHERE invoice_date BETWEEN ?1 and ?2) AS invoiceCount, "
			+ "( SELECT SUM(total_amount) AS totalAmount FROM deal_payments WHERE payment_date BETWEEN ?1 and ?2) AS totalAmount , "
			+ "( SELECT SUM(gst_amount) AS taxableAmount FROM deal_payments WHERE  payment_date BETWEEN ?1 and ?2) AS taxableAmount )", nativeQuery = true)
	Map<String, Object> getSalesDashboardStatsData(String from, String to);

	@Query(value = "SELECT IFNULL(di.id,0) AS id,IFNULL(di.deal_type,'NULL') AS dealType,dp.amount,dp.gst_amount AS gstAmount,dp.total_amount AS totalAmount, dp.mode "
			+ " FROM deal_payments dp LEFT JOIN deal_invoices di ON (dp.invoice_id = di.id)"
			+ " WHERE dp.payment_date BETWEEN  ?1 and ?2", nativeQuery = true)
	List<Map<String, Object>> getSalesDashboardPaymentsData(String from, String to);

	@Query(value = "SELECT _dates.`name`,IFNULL(_data.`value` , 0) AS `value` FROM "
			+ "( SELECT _date,DATE_FORMAT(_date,'%b %Y') AS `name`" + "FROM ("
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AS _date UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 2 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 3 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 4 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 5 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 6 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 7 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 8 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 9 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 10 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 11 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 12 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 13 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 14 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 15 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 16 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 17 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 18 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 19 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 20 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 21 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 22 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 23 MONTH UNION ALL"
			+ "    SELECT LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 24 MONTH"
			+ ") AS _dates ) AS _dates LEFT JOIN("
			+ " SELECT SUM(dp.total_amount) AS `value`,DATE_FORMAT(dp.payment_date,'%b %Y') AS `name`,dp.payment_date FROM deal_payments dp "
			+ " LEFT JOIN deal_invoices di ON (dp.invoice_id = di.id) "
			+ " WHERE di.deal_type= ?1 AND dp.payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 2 YEAR) , '%Y-%m-01')"
			+ " GROUP BY `name` ORDER BY dp.payment_date ) AS _data ON (_dates.name = _data.name)"
			+ " ORDER BY _dates._date ", nativeQuery = true)
	List<Map<String, Object>> getMonthlySalesAmountsStatsByDealType(String dealType);

	@Query(value = "SELECT DISTINCT deal_type FROM deal_payments dp LEFT JOIN deal_invoices di ON (dp.invoice_id = di.id)  "
			+ "	WHERE dp.payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 YEAR) , '%Y-%m-01')", nativeQuery = true)
	List<String> getDistinctDealTypes();

	@Query(value = "SELECT _data.*,p.name FROM ("
			+ " (SELECT dip.product_id,SUM(quantity) AS quantity,quantity*price AS total_price,COUNT(*) AS no_of_orders "
			+ " FROM deal_invoice_products dip "
			+ " WHERE invoice_id IN (SELECT invoice_id FROM deal_payments WHERE payment_date BETWEEN ?1 AND ?2 )"
			+ " GROUP BY product_id) AS _data"
			+ " LEFT JOIN products p ON (_data.product_id = p.id))", nativeQuery = true)
	List<Map<String, Object>> getProductwiseSalesReport(String from, String to);

	Deal findByPurchaseOrderNo(String poNo);

}
