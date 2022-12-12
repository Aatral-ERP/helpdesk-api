package com.autolib.helpdesk.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GlobalAccessUtil extends JdbcDaoSupport {

	private String successStatusCode = "00";
	private String successStatusDesc = "Successfully process the request";
	private String invalidStatusCode = "01";
	private String invalidStatusDesc = "Invalid request Format";
	private String failedStatusCode = "02";
	private String failedStatusDesc = "Failed process the request";

	// ==========isSelfCheckInAvailable
//	public static final boolean IsSelfCheckInAvailable = true;
//	public static final DateTime TrialExpiryDate = new DateTime(2060, 3, 30, 0, 0, 0, 0);
//	public static final String InstitutionName = "The Madura College, Madurai";
//	public static final String InstitutionAddress = "Vidya Nagar T.P.K. Road Madurai - 625 011";
//	public static final String LibraryTiming = "09:30AM. to 05.30PM";

	// =========ccav
	// SMS authentication
//	public static final String sms_username = "CITLIB";
//	public static final String sms_password = "citlib";
//	public static final String sms_senderid = "CITLIB";
//	public static final String sms_proxy_status = "NO";
//	public static final String sms_proxy_host = "127.0.0.1";
//	public static final int sms_proxy_port = 9090;

	private static final String ALGORITMO = "AES/CBC/PKCS5Padding";
	private static final String CODIFICACION = "UTF-8";
	public static final String PrivateKey = "E1BC425D57CAF7ACDBBE8091A9CD73BE";
	public static final String AWSAccessKeyId = "";
	public static final String AWSSecretKey = "";
	public static final String S3BucketName = "autolib";
	public static final String RetsetPasswordLink = "autolib-india.net/resetPassword.do?";
	// =========ccav
	public static final String merchantid = "125864";
	public static final String workingKey = "4AC2A8A6D243050332A54FD32C976EA4";
	public static final String accesscode = "AVBC69EC47AB40CBBA";

	public static Map<String, Object> SuccessResponse() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "00");
		succResp.put("StatusDesc", "Successfully processed the request");
		return succResp;
	}

	public static Map<String, String> FailedResponse() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "02");
		resp.put("StatusDesc", "Failed to process the request");
		return resp;
	}

	public static Map<String, String> memberNotFound() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "01");
		resp.put("StatusDesc", "Member Not Found");
		return resp;
	}

	public static Map<String, String> InvalidRequestFormat() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "01");
		resp.put("StatusDesc", "Invalid request Format");
		return resp;
	}

	public static Map<String, String> InvalidOTP() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "01");
		resp.put("StatusDesc", "Invalid OTP");
		return resp;
	}

	public static boolean validatePassword(String pwd) {

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@$!%*#?&])(?=\\S+$).{8,}$";
		boolean matches = false;
		try {
			Pattern p = Pattern.compile(regex);

			Matcher m = p.matcher(pwd);

			matches = m.matches();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return matches;
	}

	public static Map<String, String> invalidPasswordPattern() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "04");
		resp.put("StatusDesc",
				"Password Pattern not match. Password should have atleast 1 Character, 1 Number and 1 Special character (@$!%*#?&)");
		return resp;
	}

	public static Map<String, String> invalidLogin() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "03");
		resp.put("StatusDesc", "Invalid UserId/Password");
		return resp;
	}

	public static Map<String, String> AccountExpired() {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("StatusCode", "03");
		resp.put("StatusDesc", "Account Expired, Contact Librarian!");
		return resp;
	}

	public static Map<String, String> expiredOtpResponse() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "04");
		succResp.put("StatusDesc", "OTP has been expired");
		return succResp;
	}

	public static Map<String, String> notMatch() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "04");
		succResp.put("StatusDesc", "Not match your old password");
		return succResp;
	}

	public static Map<String, Object> existingUserResponse() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "05");
		succResp.put("StatusDesc", "Phone number already Registered");
		return succResp;
	}

	public static Map<String, String> blockedUser() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "06");
		succResp.put("StatusDesc", "Your account is blocked");
		return succResp;
	}

	// Counter
	public static Map<String, String> exceeded() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "07");
		succResp.put("StatusDesc", "You have exceeded the limit");
		return succResp;
	}

	public static Map<String, String> sameTitle() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "08");
		succResp.put("StatusDesc", "You have already borrowed same title");
		return succResp;
	}

	public static Map<String, String> referenceOnly() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "09");
		succResp.put("StatusDesc", "Reference only, please contact counter . ");
		return succResp;
	}

	public static Map<String, String> notIssue() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "10");
		succResp.put("StatusDesc", "This book is not issued to you.");
		return succResp;
	}

	public static Map<String, String> notReserved() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "00");
		succResp.put("StatusDesc", "This Resource is not reserved by you!");
		return succResp;
	}

	public static Map<String, String> reserveDone() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "10");
		succResp.put("StatusDesc", "Reservation Done");
		return succResp;
	}

	public static Map<String, String> reserveCancelled() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "10");
		succResp.put("StatusDesc", "Reservation Cancelled");
		return succResp;
	}

	public static Map<String, String> minRenewDays() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "12");
		succResp.put("StatusDesc", "Due Date exceeded, Contact Library!");
		return succResp;
	}

	public static Map<String, String> renewSuccess() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "11");
		succResp.put("StatusDesc", "Renewal Success");
		return succResp;
	}

	public static Map<String, String> renewFailed() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "12");
		succResp.put("StatusDesc", "Renew Failed");
		return succResp;
	}

	public static Map<String, String> renewNotEnabled() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "12");
		succResp.put("StatusDesc", "Online Renew Disabled, Contact Library!");
		return succResp;
	}

	public static Map<String, String> reserveNotEnabled() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "12");
		succResp.put("StatusDesc", "Online Reservation Disabled, Contact Library!");
		return succResp;
	}

	public static Map<String, String> bookmarkAlreadyExist() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "20");
		succResp.put("StatusDesc", "Bookmark already exist!");
		return succResp;
	}

	public static Map<String, String> bookmarkNotExist() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "20");
		succResp.put("StatusDesc", "Bookmark Not exist!");
		return succResp;
	}

	public static Map<String, Object> exitUser() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "21");
		succResp.put("StatusDesc", "User has Expired..!!");
		return succResp;
	}

	public static Map<String, Object> invalidEmailAndMobile() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "22");
		succResp.put("StatusDesc", "You didn't update your Mobile or Email..!!");
		return succResp;
	}

	public static Map<String, Object> invalidUser() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "23");
		succResp.put("StatusDesc", "Invalid User..!!");
		return succResp;
	}

//	public static int TrailExpiredInDays() {
//		return Days.daysBetween(new DateTime(), TrialExpiryDate).getDays();
//	}

	//
	// public String getS3BucketName() {
	// return S3BucketName;
	// }
	//
	// public void setS3BucketName(String s3BucketName) {
	// S3BucketName = s3BucketName;
	// }
	public String getSuccessStatusCode() {
		return successStatusCode;
	}

	public void setSuccessStatusCode(String successStatusCode) {
		this.successStatusCode = successStatusCode;
	}

	public String getSuccessStatusDesc() {
		return successStatusDesc;
	}

	public void setSuccessStatusDesc(String successStatusDesc) {
		this.successStatusDesc = successStatusDesc;
	}

	public String getInvalidStatusCode() {
		return invalidStatusCode;
	}

	public void setInvalidStatusCode(String invalidStatusCode) {
		this.invalidStatusCode = invalidStatusCode;
	}

	public String getInvalidStatusDesc() {
		return invalidStatusDesc;
	}

	public void setInvalidStatusDesc(String invalidStatusDesc) {
		this.invalidStatusDesc = invalidStatusDesc;
	}

	public String getFailedStatusCode() {
		return failedStatusCode;
	}

	public void setFailedStatusCode(String failedStatusCode) {
		this.failedStatusCode = failedStatusCode;
	}

	public String getFailedStatusDesc() {
		return failedStatusDesc;
	}

	public void setFailedStatusDesc(String failedStatusDesc) {
		this.failedStatusDesc = failedStatusDesc;
	}

	public static Map fromJson(String jsonStr) throws Exception {
		Map params = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			params = mapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			throw e;
		}
		return params;
	}

	public static String toJson(Object obj) throws Exception {
		StringWriter objStream = new StringWriter();
		String encryptedJson = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.USE_DEFAULTS);
			objectMapper.writeValue(objStream, obj);
			encryptedJson = objStream.toString();
		} catch (Exception e) {
			throw e;
		}

		return encryptedJson;
	}

	public static boolean verifyChannael(String channelName) {
		boolean status = false;

		if (!channelName.equalsIgnoreCase("WEB") && !channelName.equalsIgnoreCase("Android")) {
			status = true;
		}

		return status;
	}

	public static char[] generateOTP() {
		String numbers = "1234567890";
		Random random = new Random();
		char[] otp = new char[6];

		for (int i = 0; i < 6; i++) {
			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		return otp;
	}

	public static String toLocalArrivalTime(String dateString, String timeZoneID) throws ParseException {

		DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterIST.setTimeZone(TimeZone.getTimeZone("UTC")); // better than
																// using IST
		Date date = formatterIST.parse(dateString);

		DateFormat formatterLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterLocal.setTimeZone(TimeZone.getTimeZone(timeZoneID)); // client
																		// timezone
		String arrivalTime = formatterLocal.format(date).toString();
		DateFormat dbformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date time = dbformatter.parse(arrivalTime);
		SimpleDateFormat formatterUtc = new SimpleDateFormat("HH:mm");
		arrivalTime = formatterUtc.format(time);
		return arrivalTime;

	}

	public static String toUtcArrivalTime(String dateString, String timeZoneID) throws ParseException {

		DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterIST.setTimeZone(TimeZone.getTimeZone(timeZoneID)); // better
																	// than
																	// using IST
																	// (UserTimezone)
		Date date = formatterIST.parse(dateString);

		DateFormat formatterLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterLocal.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
		String arrivalTime = formatterLocal.format(date);
		DateFormat dbformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date time = dbformatter.parse(arrivalTime);
		SimpleDateFormat formatterUtc = new SimpleDateFormat("HH:mm");
		arrivalTime = formatterUtc.format(time);
		return arrivalTime;
	}

	public static String converttoutc(String dateString, String timeZoneID) throws ParseException {

		DateFormat formatterIST = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterIST.setTimeZone(TimeZone.getTimeZone(timeZoneID)); // better
																	// than
																	// using IST
		Date date = formatterIST.parse(dateString);

		DateFormat formatterUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone

		return formatterUTC.format(date).toString();

	}

	public static String converttodbfomat(String dateString) {
		java.util.StringTokenizer stz = new java.util.StringTokenizer(dateString, "/");
		int bim = Integer.parseInt(stz.nextToken());
		int bid = Integer.parseInt(stz.nextToken());
		int Year = Integer.parseInt(stz.nextToken());
		String Month = new Integer(bim).toString();
		if (Month.length() == 1)
			Month = "0" + Month;
		String Day = new Integer(bid).toString();
		if (Day.length() == 1)
			Day = "0" + Day;
		String ITdate = Year + "-" + Month + "-" + Day;
		return ITdate;

	}

	public static String converttodbfomat2(String dateString) {
		java.util.StringTokenizer stz = new java.util.StringTokenizer(dateString, "-");
		int bim = Integer.parseInt(stz.nextToken());
		int bid = Integer.parseInt(stz.nextToken());
		int Year = Integer.parseInt(stz.nextToken());
		String Month = new Integer(bim).toString();
		if (Month.length() == 1)
			Month = "0" + Month;
		String Day = new Integer(bid).toString();
		if (Day.length() == 1)
			Day = "0" + Day;
		String ITdate = Year + "-" + Month + "-" + Day;
		return ITdate;

	}

	public static String converttoUIformat(String dateString) {
		java.util.StringTokenizer stz = new java.util.StringTokenizer(dateString, "-");

		int Year = Integer.parseInt(stz.nextToken());
		int bim = Integer.parseInt(stz.nextToken());
		int bid = Integer.parseInt(stz.nextToken());
		String Month = new Integer(bim).toString();
		if (Month.length() == 1)
			Month = "0" + Month;
		String Day = new Integer(bid).toString();
		if (Day.length() == 1)
			Day = "0" + Day;
		String ITdate = Month + "/" + Day + "/" + Year;
		return ITdate;

	}

	public static String TodayDate() {
		java.util.Date d = new java.util.Date();

		SimpleDateFormat OrderDate = new SimpleDateFormat("MM/dd/yyyy");
		return OrderDate.format(d);

	}

	public static String getDateYMD() {
		String ss = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			ss = dateFormat.format(date);
		} catch (Exception e) {
			System.out.println(e);
		}

		return ss;
	}

	public static String getDuedate(int days) {
		String Duedate = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, days);
			Duedate = dateFormat.format(c.getTime());
		} catch (Exception e) {
			System.out.println(e);
		}
		return Duedate;
	}

	public static String convertTimeToDBFormat(String time) throws ParseException {

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date date = parseFormat.parse(time);

		String dbTime = displayFormat.format(date);

		return dbTime;
	}

	public static String encrypt(String plaintext, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] raw = DatatypeConverter.parseHexBinary(key);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(ALGORITMO);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] cipherText = cipher.doFinal(plaintext.getBytes(CODIFICACION));
		byte[] iv = cipher.getIV();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(iv);
		outputStream.write(cipherText);
		byte[] finalData = outputStream.toByteArray();
		String encodedFinalData = DatatypeConverter.printBase64Binary(finalData);
		return encodedFinalData;
	}

	public static String decrypt(String encodedInitialData, String key)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		byte[] encryptedData = DatatypeConverter.parseBase64Binary(encodedInitialData);
		byte[] raw = DatatypeConverter.parseHexBinary(key);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance(ALGORITMO);
		byte[] iv = Arrays.copyOfRange(encryptedData, 0, 16);
		byte[] cipherText = Arrays.copyOfRange(encryptedData, 16, encryptedData.length);
		IvParameterSpec iv_specs = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv_specs);
		byte[] plainTextBytes = cipher.doFinal(cipherText);
		String plainText = new String(plainTextBytes);
		return plainText;
	}

	public static String getUID() {
		UUID UID = UUID.randomUUID();
		String uniqueId = String.valueOf(UID);
		return uniqueId;
	}

	public static String quotesReplace(String str) {
		String resultString = str.replaceAll("'", "\\\\\'");
		return resultString;
	}

	// public static String inputStreamToString(InputStream inputStream) throws
	// IOException {
	// StringWriter writer = new StringWriter();
	// IOUtils.copy(inputStream, writer);
	// String str = writer.toString();
	//
	// return str;
	// }
	public String profanityFilter(String str) throws IOException {

		str = str + " ";

		List<String> list = getJdbcTemplate().queryForList(" select lower(profanity_word) from tbl_profanity_filter ",
				String.class);
		for (String s : list) {
			if (str.toLowerCase().contains(s.toLowerCase())) {
				str = str.replaceAll("(?i)" + s.toLowerCase(), "xxxxxx");
			}
		}

		str = str.replaceAll("xxxxxx.*? ", "xxxxxx ");

		return str.trim();
	}

	public String listToInQueryFormat(List list) {

		String result = "";

		for (Object str : list) {
			if (!str.toString().trim().equals("")) {
				if (result.equals(""))
					result = "'" + str.toString() + "'";
				else
					result = result + ",'" + str.toString() + "'";
			}
		}

		return result;
	}

}
