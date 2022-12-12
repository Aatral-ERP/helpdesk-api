package com.autolib.helpdesk.common;

public class DBQueryUtil {

//	public static final String Insert_Signup_OTP = "insert into signup_otp(mail_id,otp,generated_time) values(?,?,CURRENT_TIMESTAMP)";
	
	public static final String User_Login = " SELECT login_id,login_password,user_rights,login_flag,slock,expiry_date,DATEDIFF(expiry_date,CURDATE()) AS expirein FROM login_mas l JOIN member_mas m ON (l.login_id = m.member_code) where login_id = ? and login_flag='Yes'";

	public static final String User_Login_timemanagement = " insert into lib_timemanagement (user_id, lastvisit_time, logout_time , login_at) values(?, CURRENT_TIMESTAMP , CURRENT_TIMESTAMP , ?); ";

	// Counter Quries

	public static final String SELECT_MEMBER = "select * from counter_member  where member_code=?";

	public static final String SelectBook = "select * from counter_book_select where 2>1 ";

	public static final String insertIssueMas = "insert into issue_mas(member_code,access_no,issue_date,due_date,issue_type,staff_code,doc_type) values(?,?,?,?,?,?,?)";

	public static final String insert_Issue_History = "insert into issue_history(member_code,access_no,issue_date,due_date,return_date,fine_amount,staff_code,doc_type,issue_type) values(?,?,?,?,?,?,?,?,?)";

	public static final String insertTransMas = "insert into trans_mas(Trans_Date,Trans_Head,member_Code,Access_No,Issue_Date,Due_Date,Trans_Amount,Remarks) values(?,?,?,?,?,?,?,?)";

	public static final String update_Issue_Mas = "update issue_mas set issue_date=?,due_date=?,issue_type=?,staff_code=? where member_code=? and access_no=?";

	public static final String insert_renewal = " insert into renewal_time (member_code,access_no,group_code,doc_type,time_renew) values (?,?,?,?,?) ";

	public static final String update_renewal = "update renewal_time set time_renew = time_renew+1 where member_code=? and access_no=?";

	public static final String Insert_Dropbox_Books = " insert into dropbox_books (member_code,access_no,is_checked,doc_type,returned_date,created_date) values (?,?,'No',?,curdate(),CURRENT_TIMESTAMP)";
	//
	public static final String Insert_Signup_OTP = "insert into signup_otp(mail_id,otp,generated_time) values(?,?,CURRENT_TIMESTAMP)";

	public static final String Insert_SignUp = "insert into tbl_customer_signup(customer_id,password,first_name,last_name,email_id,phone_no,alternate_phone,gender,dob,created_date,modified_date)values(?,?,?,?,?,?,?,?,?,UTC_TIMESTAMP,UTC_TIMESTAMP)";

	public static final String Update_Customer_Profile = "update tbl_customer_signup set first_name=?,last_name=?,email_id=?,phone_no=?,alternate_phone=?,gender=?,dob=?,street1=?,street2=?,city=?,state=?,country=?,postal_code=?,modified_date=CURRENT_TIMESTAMP where customer_id=?";

	public static final String Insert_Tbl_Log_Email = "Insert into tbl_log_email (uid,email_id_from,email_id_to,email_sub,email_status,is_user_accessed,created_date,modified_date) Values (?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	public static final String Insert_Google_Id = "Insert into tbl_google_id (email_id,google_id,created_date,modified_date) Values (?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	public static final String Insert_Profile_Photo = "Insert into tbl_profile_photos (image_id,user_id,image_url,image_name,image_type,image_size,created_date,modified_date) Values (?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	public static final String Select_Country_Short_Name_List = "select country_name from tbl_country";

	public static final String Select_State_Long_Name = "Select state_long_name from tbl_state where country = ?";

	public static final String Insert_User_Address = "insert into tbl_address(user_id,full_name,address1,address2,city,state,country,postal_code,created_date,modified_date) values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

	public static final String Update_User_Address = "update tbl_address set full_name=?,address1=?,address2=?,city=?,state=?,country=?,postal_code=?,modified_date=UTC_TIMESTAMP where user_id = ?";

	public static final String insertIssueHistory = "insert into issue_history(member_code,access_no,issue_date,due_date,return_date,fine_amount,staff_code,doc_type,issue_type) values(?,?,?,?,curdate(),?,?,?,?)";

	public static final String Insert_Trans_Mas = "insert into trans_mas(Trans_Date,Trans_Head,member_Code,Access_No,Issue_Date,Due_Date,Trans_Amount,Remarks) values(?,?,?,?,?,?,?,?)";

	// Search&Stock
	public static final String Stock_Save = "insert into stock_mas (access_no)values(?)";
//	public static final String Search_Books = " select access_no,call_no,title,author_name,edition,location,availability,document from full_search where 2>1 ";
	public static final String Search_Books = " select access_no as access_no,call_no as call_no,title as title,author_name as author_name,CONCAT(place, CASE WHEN LENGTH(place) >1 THEN ': ' ELSE '' END,sp_name,CASE WHEN LENGTH(sp_name) >1 THEN ', ' ELSE '' END,year_pub,CASE WHEN LENGTH(year_pub) >1 THEN '.' ELSE '' END) AS publication ,isbn as isbn ,bprice as bprice,pages as pages,edition as edition,location as location,availability as availability,document as document,contents as contents,dept_name as dept_name,place AS place,sp_name AS publisher,year_pub AS year_pub,sres as sres,sub_name as sub_name,volno as volno,add_field3 AS add_field3,script AS script from full_search where 2>1 ";
	public static final String Search_Books_Count = " select count(*) as cnt from full_search where 2>1 ";
//	public static final String Search_Ebooks = "select access_no,publisher,title,author_name,edition,year_pub,url,content from ebook_mas_view WHERE 2>1";
	public static final String Search_Ebooks = "select access_no as access_no,call_no as call_no,CONCAT(publisher, CASE WHEN LENGTH(publisher) >1 THEN ', ' ELSE '' END,year_pub,CASE WHEN LENGTH(year_pub) >1 THEN '.' ELSE '' END) AS publication,publisher as publisher,isbn as isbn,year_pub as year_pub,pages as pages,title as title,author_name as author_name,edition as edition,year_pub as year_pub,url as url,content as content from ebook_mas_view WHERE 2>1";

	public static final String Search_Ebooks_Stats = "select access_no as access_no,call_no as call_no,CONCAT(publisher, CASE WHEN LENGTH(publisher) >1 THEN ', ' ELSE '' END,year_pub,CASE WHEN LENGTH(year_pub) >1 THEN '.' ELSE '' END) AS publication,publisher as publisher,isbn as isbn,year_pub as year_pub,pages as pages,title as title,author_name as author_name,edition as edition,year_pub as year_pub,url as url,content as content from ebook_mas_view WHERE 2>1";

	public static final String Search_Ebooks_Count = "select count(*) as cnt from ebook_mas_view WHERE 2>1";
	public static final String Search_Question_Bank_Fullview = "select qb_code as qb_code,sub_code as sub_code,sub_name as sub_name,dept_name as dept_name,course_name as course_name,course_major as course_major,month as month,year as year,semester as semester,contents as contents FROM question_bank_fullview WHERE 2>1 ";
	public static final String Search_Question_Bank_Fullview_Count = "select count(*) as cnt FROM question_bank_fullview WHERE 2>1 ";
	public static final String Insert_Suggestion = " insert into suggestion_mas (request_no,member_code,request_for,request_date,request_details,remarks,action_taken,status,action_taken_date) values(?,?,?,curdate(),?,?,'','Pending','1800-01-01')";
	public static final String Update_Suggestion = " update suggestion_mas set request_for=?,request_date=curdate(),request_details=?,remarks=? where request_no=?";

	public static final String QB_Department_Distinct = "SELECT dept_name as dept_name FROM department_mas WHERE dept_code IN (SELECT DISTINCT dept FROM question_bank) order by dept_name ; ";

	public static final String QB_Course_Distinct = "SELECT DISTINCT course_name as course_name,course_major as course_major FROM course_mas WHERE course_code IN (SELECT DISTINCT course FROM question_bank) ORDER BY course_name,course_major  ;";

	public static final String QB_University_Distinct = "SELECT DISTINCT university as university FROM question_bank order by university ";

	public static final String ReturnList_Stats = "select doc_type as document, count(*) as count from jissue_history where 2 > 1 ";

	public static final String ReturnList = "SELECT access_no AS access_no ,title AS title,author_name AS author_name ,DATE_FORMAT(issue_date,'%d/%m/%Y') AS issue_date,DATE_FORMAT(due_date,'%d/%m/%Y') AS due_date,DATE_FORMAT(return_date,'%d/%m/%Y') AS return_date,fine_amount AS fine_amount,doc_type AS document,issue_type AS issue_type FROM jissue_history WHERE 2 > 1  ";

	public static final String ReturnList_Count = "select count(*) as cnt from jissue_history where 2 > 1 ";

	public static final String Select_WebMas = "select * from web_mas where 2 > 1 ";

	public static final String Select_WebMas_Count = "select count(*) as cnt from web_mas where 2 > 1 ";

//	public static final String NewArrivalsDepartment = "SELECT access_no as access_no,title as title,author_name as author_name,edition as edition,DATEDIFF(CURDATE() , received_date) AS days,other_title as other_title,call_no as call_no,LANGUAGE AS LANGUAGE ,year_pub as year_pub,document as document,dept_code as dept_code,availability as availability,location as location,isbn as isbn,pages as pages,bprice as bprice,contents as contents,CONCAT(place, CASE WHEN LENGTH(place) >1 THEN ': ' ELSE '' END,p.sp_name,CASE WHEN LENGTH(p.sp_name) >1 THEN ', ' ELSE '' END,year_pub,CASE WHEN LENGTH(year_pub) >1 THEN '.' ELSE '' END) AS publication FROM book_mas b JOIN sup_pub_mas p ON (b.pub_code = p.sp_code) WHERE 2 > 1  ";

	public static final String NewArrivalsDepartment = "SELECT access_no AS access_no,call_no AS call_no,title AS title,author_name AS author_name,edition AS edition,'' AS other_title,LANGUAGE AS `language`,year_pub AS year_pub,document AS document,dept_code AS dept_code,availability AS availability,location AS location,isbn AS isbn,pages AS pages,bprice AS bprice,contents AS contents,CONCAT(place, CASE WHEN LENGTH(place) >1 THEN ': ' ELSE '' END,sp_name,CASE WHEN LENGTH(sp_name) >1 THEN ', ' ELSE '' END,year_pub,CASE WHEN LENGTH(year_pub) >1 THEN '.' ELSE '' END) AS publication ,isbn AS isbn ,bprice AS bprice,pages AS pages,edition AS edition,location AS location,availability AS availability,document AS document,contents AS contents,dept_name AS dept_name,place AS place,sp_name AS publisher,year_pub AS year_pub,sres AS sres,sub_name AS sub_name,volno AS volno,add_field3 AS add_field3,script AS script,DATEDIFF(CURDATE() , received_date) AS days FROM full_search WHERE 2>1 ";

	public static final String NewArrivalsDepartment_Count = "SELECT count(*) as cnt FROM book_mas WHERE 2 > 1 ";

	public static final String Member_Dept_Code = "select dept_code as dept_code from member_mas where member_code = ?";

	public static final String Journal_Department_Distinct = " SELECT dept_name AS dept_name FROM department_mas WHERE dept_code IN (SELECT DISTINCT dept_code FROM journal_mas) ORDER BY dept_name ; ";

	public static final String Journal_Subject_Distinct = "SELECT sub_name AS sub_name FROM subject_mas WHERE sub_code IN (SELECT DISTINCT sub_code FROM journal_mas) ORDER BY sub_name ;";

	public static final String Journal_Country_Distinct = "SELECT DISTINCT country AS country FROM journal_mas";

	public static final String Journal_Document_Distinct = "SELECT DISTINCT doc_type AS document FROM journal_mas";

	public static final String Search_Journals = "SELECT jnl_code as jnl_code, jnl_name as jnl_name , issn as issn , frequency as frequency , doc_type as doc_type , country as country , LANGUAGE as language, jnl_type as jnl_type, remarks as remarks,dept_name as dept_name, sub_name as sub_name, publisher as publisher FROM journal_view where 2 > 1 ";

	public static final String Search_Journals_Count = "SELECT count(*) as cnt FROM journal_view where 2 > 1 ";

	public static final String Search_Journal_Articles = "SELECT doi_no,jnl_name AS jnl_name,atl_title AS atl_title,atl_author AS atl_author,atl_subject AS atl_subject,atl_keywords AS atl_keywords,atl_abstract AS atl_abstract,atl_page_nos AS atl_page_nos,atl_no AS atl_no,vol_no AS vol_no,issue_no AS issue_no,issue_year AS issue_year,issue_month AS issue_month FROM journal_articles WHERE 2>1 ";

	public static final String Search_Journals_Articles_Count = "SELECT count(*) as cnt FROM journal_articles where 2 > 1 ";

	public static final String Select_ReserveList_Member = "SELECT r.access_no,r.member_code,r.doc_type,r.res_date,b.title,b.author_name,b.year_pub,b.availability FROM reserve_mas r JOIN book_mas b ON (r.access_no = b.access_no) where r.member_code = ? ";

	public static final String Count_Reserve_Member = "SELECT COUNT(*) FROM reserve_mas WHERE member_code = ? AND access_no = ? ";

	public static final String Delete_Reserve_Member = "DELETE FROM reserve_mas WHERE member_code = ? AND access_no = ? ";

	public static final String Bookmark_Catalog = "SELECT f.access_no AS access_no,f.call_no AS call_no,f.title AS title,f.author_name AS author_name, CONCAT(f.place, CASE WHEN LENGTH(f.place) >1 THEN ': ' ELSE '' END,f.sp_name,CASE WHEN LENGTH(f.sp_name) >1 THEN ', ' ELSE '' END,f.year_pub,CASE WHEN LENGTH(f.year_pub) >1 THEN '.' ELSE '' END) AS publication , f.isbn AS isbn ,f.bprice AS bprice,f.pages AS pages,f.edition AS edition,f.location AS location,f.availability AS availability,f.contents AS contents,f.dept_name AS dept_name,f.place AS place,f.sp_name AS publisher,f.year_pub AS year_pub,f.sres AS sres,f.sub_name AS sub_name,f.add_field3 AS add_field3,f.script AS script ,upper(b.document) as document,b.created_time FROM full_search f JOIN bookmarks b ON (f.access_no = b.access_no && f.document = b.document) where 2 > 1 ";

	public static final String Bookmark_Journal = "SELECT j.jnl_code AS jnl_code, j.jnl_name AS jnl_name , j.issn AS issn , j.frequency AS frequency , j.doc_type AS doc_type , j.country AS country , j.LANGUAGE AS `language`, j.jnl_type AS jnl_type, j.remarks AS remarks,j.dept_name AS dept_name, j.sub_name AS sub_name, j.publisher AS publisher,upper(b.document) as document,b.created_time FROM journal_view j JOIN bookmarks b ON (j.jnl_code = b.access_no && j.doc_type = b.document) WHERE 2 > 1";

	public static final String Bookmark_Article = "SELECT a.atl_no AS atl_no,a.doi_no AS doi_no,a.jnl_name AS jnl_name,a.atl_title AS atl_title,a.atl_author AS atl_author,a.atl_subject AS atl_subject,a.atl_keywords AS atl_keywords,a.atl_abstract AS atl_abstract,a.atl_page_nos AS atl_page_nos,a.vol_no AS vol_no,a.issue_no AS issue_no,a.issue_year AS issue_year,a.issue_month AS issue_month,upper(b.document) as document,b.created_time FROM journal_articles a JOIN bookmarks b ON (a.atl_no = b.access_no && b.document = 'Article') WHERE 2 > 1";

	public static final String Bookmark_EBook = "SELECT e.access_no AS access_no,e.call_no AS call_no,CONCAT(e.publisher, CASE WHEN LENGTH(e.publisher) >1 THEN ', ' ELSE '' END,e.year_pub,CASE WHEN LENGTH(e.year_pub) >1 THEN '.' ELSE '' END) AS publication,e.publisher AS publisher,e.isbn AS isbn,e.year_pub AS year_pub,e.pages AS pages,e.title AS title,e.author_name AS author_name,e.edition AS edition,e.year_pub AS year_pub,e.url AS url,e.content AS content,upper(b.document) as document,b.created_time FROM ebook_mas_view e JOIN bookmarks b ON (e.access_no = b.access_no && b.document = 'EBOOK') WHERE 2 > 1";

	public static final String Bookmark_EResources = "SELECT w.code AS `code`,w.website AS website,w.webdetails AS websitedetails,w.webtitle AS webtitle,w.websubtitle AS websubtitle,w.webtype AS webtype,upper(b.document) as document,b.created_time FROM web_mas w JOIN bookmarks b ON (w.code = b.access_no && b.document = 'EResources') WHERE 2 > 1";

	public static final String Bookmark_QuestionBank = "SELECT qb.qb_code AS qb_code,qb.sub_code AS sub_code,qb.sub_name AS sub_name,qb.dept_name AS dept_name,qb.course_name AS course_name,qb.course_major AS course_major,qb.month AS `month`,qb.year AS `year`,qb.semester AS semester,qb.contents AS contents,upper(b.document) as document,b.created_time FROM question_bank_fullview qb JOIN bookmarks b ON (qb.qb_code = b.access_no && b.document = 'QUESTION BANK') WHERE 2 > 1";

	public static final String Bookmark_NewsClipping = "SELECT n.newspaper_code AS newspaper_code,n.newspaper_name AS newspaper_name,n.newspaper_type AS newspaper_type,n.newspaper_ndate AS newspaper_ndate,n.newspaper_pno AS newspaper_pno,n.newspaper_title AS newspaper_title,n.newspaper_subject AS newspaper_subject,n.newspaper_key AS newspaper_key,n.newspaper_abstract AS newspaper_abstract,n.newspaper_content AS newspaper_content,upper(b.document) as document,b.created_time FROM newsclipping_mas n JOIN bookmarks b ON (n.newspaper_code = b.access_no && b.document = 'NEWSCLIPPING') WHERE 2 > 1";

	public static final String Bookmark_CountList = "SELECT ( SELECT COUNT(*) FROM bookmarks "
			+ "WHERE member_code = ?  AND document IN ('BOOK','BOOK BANK','NON BOOK','REPORT','THESIS','PROCEEDING','BACK VOLUME','STANDARD','PROJECT')) AS Catalog, "
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('Journal','MAGAZINE','NEWSLETTER','OTHERS')) AS Journal, "
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('Article')) AS Article, "
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('EBook')) AS EBook,"
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('EResources')) AS EResources ,"
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('QUESTION BANK')) AS `Question Bank`, "
			+ "(SELECT COUNT(*) FROM bookmarks WHERE member_code = ?  AND document IN ('NEWSCLIPPING')) AS NewsClipping ;";

	public static final String Fullview_Query = "SELECT b.access_no AS access_no,b.Title AS title,b.Author_Name AS author_name,b.Call_No AS call_no,b.Other_title AS other_title,"
			+ "b.Role AS role,b.SRes AS sres,b.Edition AS edition,b.Language AS `language`,b.place AS place,b.Year_Pub AS year_pub,b.Pages AS pages,"
			+ "b.Size AS size,b.Illustrator AS illustrator,b.isbn AS isbn,b.bprice AS bprice,b.noofcopy AS noofcopy,b.script AS script,b.Location AS location,"
			+ "b.availability AS availability,b.Document AS document,b.Type AS `type`,b.phy_media AS phy_media,b.Binding AS binding,b.Keywords AS keywords,"
			+ "b.Notes AS notes,b.Summary AS summary,b.Biblio AS biblio,b.Contents AS contents,b.VolNo AS volno,b.part_no AS part_no,b.vtitle AS vtitle,"
			+ "b.vres AS vres,b.Corp_Author_Name AS corp_author_name,b.Corp_Address AS corp_address,b.Series_Author_Name AS series_author_name,"
			+ "b.ser_Name AS ser_name,b.Series_Title AS series_title,b.issn AS issn,b.Meeting AS meeting,b.sponsor AS sponsor,b.venue AS venue,"
			+ "b.received_date AS received_date,b.Invoice_No AS invoice_no,b.invoice_date AS invoice_date,b.BCurrency AS bcurrency,b.BCost AS bcost,"
			+ "b.accepted_price AS accepted_price,b.Discount AS discount,b.Add_Field1 AS add_field1,b.add_field2 AS add_field2,b.add_field3 AS add_field3,"
			+ "b.add_field4 AS add_field4,s.sub_name AS sub_name,d.dept_name AS dept_name,pub.sp_name AS publisher_name,sub.supplier AS supplier_name,"
			+ "CONCAT(b.place, CASE WHEN LENGTH(b.place) >1 THEN ': ' ELSE '' END,sub.supplier,CASE WHEN LENGTH(sub.supplier) >1 THEN ', ' ELSE '' END,b.year_pub,CASE WHEN LENGTH(b.year_pub) >1 THEN '.' ELSE '' END) AS publication "
			+ "FROM book_mas b LEFT JOIN subject_mas s ON (b.sub_code = s.sub_code) LEFT JOIN department_mas d ON(b.dept_code = d.dept_code) LEFT JOIN sup_pub_mas pub ON(b.Pub_Code = pub.SP_Code) LEFT JOIN supplier_mas sub ON(b.Sup_Code = sub.sp_code)"
			+ "	WHERE b.access_no = ? and b.document= ? limit 1; ";

	public static final String Select_Reviews = "select * from review_mas where access_no = ?  and document= ? order by review_no desc";

	public static final String Search_Newsclipping = "SELECT newspaper_code as newspaper_code,newspaper_name as newspaper_name,newspaper_type as newspaper_type,newspaper_ndate as newspaper_ndate,newspaper_pno as newspaper_pno,newspaper_title as newspaper_title,newspaper_subject as newspaper_subject,newspaper_key as newspaper_key,newspaper_abstract as newspaper_abstract,newspaper_content as newspaper_content FROM newsclipping_mas WHERE 2>1 ";

	public static final String Search_Newsclipping_Count = "SELECT count(*) as cnt FROM newsclipping_mas where 2 > 1 ";

	public static final String Insert_Review = "insert into review_mas (access_no,member_code,review_title,review_desc,rating,review_date,document) values (?,?,?,?,?,now(),?)";

	public static final String EbookFullview_Query = "SELECT eb.access_no AS access_no,eb.title AS title,eb.author_name AS author_name,eb.call_no AS call_no,eb.role AS role,eb.edition AS edition,eb.year_pub AS year_pub,"
			+ "eb.pages AS pages,eb.document AS document,eb.type AS type,eb.keywords AS keywords,eb.add_field1 AS add_field1,eb.add_field2 AS add_field2,eb.received_date AS received_date,"
			+ "eb.content AS content,eb.isbn AS isbn,eb.url AS url,eb.invoice_no,eb.invoice_date,eb.price AS price,eb.gift_purchase AS gift_purchase,"
			+ "s.sub_name AS sub_name,d.dept_name AS dept_name,pub.sp_name AS publisher_name,sub.supplier AS supplier_name,b.branch_name AS branch_name FROM ebook_mas eb"
			+ " LEFT JOIN subject_mas s ON (eb.sub_code = s.sub_code)"
			+ " LEFT JOIN department_mas d ON(eb.dept_code = d.dept_code)"
			+ " LEFT JOIN sup_pub_mas pub ON(eb.Pub_Code = pub.SP_Code)"
			+ " LEFT JOIN supplier_mas sub ON(eb.Sup_Code = sub.sp_code)"
			+ " LEFT JOIN branch_mas b ON(eb.branch_Code = b.branch_code) WHERE access_no = ? LIMIT 1; ";

	public static final String QbFullview_Query = "SELECT qb.qb_code AS qb_code,qb.date AS date,qb.university AS university,qb.sub_name AS sub_name,qb.sub_code AS sub_code,qb.year AS year,qb.month AS month, "
			+ "qb.semester AS semester,qb.add_field1 AS add_field1,qb.add_field2 AS add_field2,qb.contents AS contents,d.dept_name AS dept_name,CONCAT(cou.course_name,'-',cou.course_major) AS course FROM question_bank qb "
			+ " LEFT JOIN department_mas d ON(qb.dept = d.dept_code)"
			+ " LEFT JOIN course_mas cou ON(qb.course = cou.course_Code) WHERE qb_code=? LIMIT 1;";

	public static final String NewsClipFullview_Query = "SELECT newspaper_code as newspaper_code,newspaper_name as newspaper_name,newspaper_type as newspaper_type,newspaper_title as newspaper_title,newspaper_subject as newspaper_subject,newspaper_key as newspaper_key,newspaper_abstract as newspaper_abstract,newspaper_content as newspaper_content FROM newsclipping_mas WHERE newspaper_code =? LIMIT 1";

	public static final String Article_Fullview_Query = "SELECT atl_no as atl_no,doi_no as doi_no,jnl_name as jnl_name,atl_title as atl_title,atl_author as atl_author,atl_subject as atl_subject,atl_keywords as atl_keywords,atl_abstract as atl_abstract,atl_page_nos as atl_page_nos,vol_no as vol_no,issue_no as issue_no,issue_year as issue_year,issue_month as issue_month FROM journal_articles WHERE atl_no = ?";

	public static final String Search_Journal_Display = "SELECT issue_access_no as issue_access_no,jnl_code as jnl_code,jnl_name as jnl_name,frequency as frequency,country as country,doc_type as doc_type,issue_year as issue_year,issue_month as issue_month,issue_volume as issue_volume,issue_no as issue_no,issue_date as issue_date,received_date as received_date,availability as availability,content_page as content_page FROM journal_issues_search WHERE jnl_code = ?";

	public static final String Search_Journal_Display_Count = "SELECT COUNT(*) FROM journal_issues_search WHERE jnl_code = ? ";

	public static final String Journal_Fullview_Query = "SELECT jnl_code as jnl_code,jnl_name as jnl_name,frequency as frequency,country as country,doc_type as doc_type,dept_name as dept_name,sub_name as sub_name,publisher as publisher,jnl_type as jnl_type FROM journal_view WHERE jnl_code = ?";

	public static final String Stats_Library_Collections = "SELECT document AS document,volumes AS volumes,title AS title FROM library_collection_final;";

	public static final String Stats_Search_User_Counts = "SELECT cdate,dt,GROUP_CONCAT(member_code) as member_codes,SUM(cnt) as cnt FROM (SELECT DATE(created_date) AS cdate,DATE_FORMAT(created_date , '%b %e') AS dt,member_code , COUNT(*) AS cnt FROM search_stats GROUP BY dt,member_code)sub GROUP BY cdate ORDER BY cdate DESC limit 10;";

	public static final String Stats_Issue_Counts = "SELECT DATE_FORMAT(issue_date , '%b %e') AS dt,issue_date,'Issue' AS issue_type,COUNT(*) AS cnt FROM issue_mas GROUP BY issue_date ORDER BY issue_date DESC LIMIT 10;";

	public static final String Stats_Return_Counts = "SELECT DATE_FORMAT(return_date , '%b %e') AS dt,return_date,'Return' AS issue_type,COUNT(*) AS cnt FROM issue_history GROUP BY return_date ORDER BY return_date DESC LIMIT 10;";

	public static final String Stats_Gate_Counts = "SELECT DATE_FORMAT(return_time,'%b %e') AS dt,COUNT(*) AS cnt FROM return_log GROUP BY YEAR(return_time),MONTH(return_time) ORDER BY return_time DESC LIMIT 10;";

	public static final String Stats_Member_Return_Counts = "SELECT DATE_FORMAT(issue_date,'%b %e') AS dt,COUNT(*) AS cnt FROM issue_history WHERE member_code = ?  GROUP BY YEAR(issue_date),MONTH(issue_date) ORDER BY issue_date DESC LIMIT 10;";

	public static final String Stats_Member_Search_Counts = "SELECT member_code,DATE_FORMAT(created_date,'%b %e') AS dt,COUNT(*) AS cnt FROM search_stats WHERE member_code = ? GROUP BY YEAR(created_date),MONTH(created_date) ORDER BY created_date DESC LIMIT 10;";

	public static final String Stats_Member_Gate_Counts = "SELECT DATE_FORMAT(return_time,'%b %e') AS dt,member_code,COUNT(*) AS cnt FROM return_log WHERE member_code = ?  GROUP BY YEAR(return_time),MONTH(return_time) ORDER BY return_time DESC LIMIT 10;";

	public static final String Today_Transaction_Stats = "SELECT"
			+ "(SELECT COUNT(*) FROM issue_mas WHERE issue_type='ISSUE' and issue_date = curdate()) AS issueCount,"
			+ "(SELECT COUNT(*) FROM issue_history WHERE return_date = curdate()) AS returnDate,"
			+ "(SELECT COUNT(*) FROM issue_mas WHERE issue_type='RENEW' and issue_date=curdate() ) AS renewCount, "
			+ "(select ifnull(sum(trans_amount) , 0) from trans_mas where trans_date=curdate()) AS todayTransAmount, "
			+ "(select ifnull(sum(amount) , 0) from payment_clearance where payment_date=curdate()) AS todaypaidAmount, "
			+ "(select COUNT(*) from return_log where return_time=curdate()) AS totalGateVistors, "
			+ "(select COUNT(*) from entry_log where entry_time=curdate()) AS activeGateVistors;";

	public static final String Reports_Current_Issue_Details = "select member_code as member_code,member_name as member_name,dept_name as dept_name,group_name as group_name,access_no as access_no,title as title,author_name as author_name,call_no as call_no,doc_type as doc_type,DATE_FORMAT(issue_date,'%d/%m/%Y') as issue_date,DATE_FORMAT(due_date,'%d/%m/%Y') as due_date,issue_type as issue_type,staff_code as staff_code from issuedbooks where 2 > 1 ";

	public static final String Reports_Current_Issue_Details_Count = "select count(*) from issuedbooks where 2>1 ";

	public static final String Reports_Issue_Details = "select access_no access_no,title as title,member_code as member_code,member_name as member_name,DATE_FORMAT(issue_date,'%d/%m/%Y') AS issue_date,DATE_FORMAT(due_date,'%d/%m/%Y') AS due_date,CASE WHEN return_date = '1800-01-01' THEN 'Not Yet Returned' ELSE DATE_FORMAT(return_date,'%d/%m/%Y') END as return_date,doc_type as doc_type,staff_code as staff_code FROM issue_transaction where 2>1 ";

	public static final String Reports_Issue_Details_Count = "select count(*) from issue_transaction where 2 > 1 ";

	public static final String Reports_Return_Details = "select access_no as access_no,title as title,author_name as author_name,member_code as member_code,member_name as member_name,DATE_FORMAT(issue_date,'%d/%m/%Y') AS issue_date,DATE_FORMAT(due_date,'%d/%m/%Y') AS due_date,DATE_FORMAT(return_date,'%d/%m/%Y') AS return_date,doc_type as doc_type,staff_code as staff_code FROM jissue_history where issue_type='Return' ";

	public static final String Reports_Return_Details_Count = "select count(*) from jissue_history where issue_type='Return' ";

	public static final String Reports_Payment_Details = "SELECT bill_no as bill_no,member_code as member_code,amount as amount,payment_date as payment_date,pay_mode as pay_mode,staff_code as staff_code,doc_type as doc_type,access_no as access_no,dept_name as dept_name,desig_name as desig_name,group_name as group_name,course_name as course_name,course_major as course_major FROM payment_clear_view where 2>1 ";

	public static final String Reports_Payment_Details_Count = "select count(*) from payment_clear_view where 2>1 ";

	public static final String Reports_Trans_Details = "SELECT member_code as member_code,member_name as member_name,trans_no as trans_no,trans_date AS trans_date,access_no as access_no,due_date as due_date,trans_amount as trans_amount FROM trans_member_view  WHERE trans_head='OVERDUE' ";

	public static final String Reports_Trans_Details_Count = "select count(*) from trans_member_view  WHERE trans_head='OVERDUE' ";
	
	public static final String instituteSearch = "SELECT institute_id , alternate_email_id,alternate_phone,city,country,createddatetime,email_id,gstno,institute_name,lastupdatedatetime,phone,state,street1,street2,zipcode,institute_type,service_under,logourl FROM institutes WHERE 2>1 ";

	public static final String instituteSearch_Count = " select count(*) as cnt from institutes where 2>1 ";
}

	