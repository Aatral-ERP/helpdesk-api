package com.autolib.helpdesk.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autolib.helpdesk.jwt.JwtTokenUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.DecimalFormat;

@Component
public class Util {

	@Autowired
	public JwtTokenUtil jwtUtil;

	private String successStatusCode = "00";
	private String successStatusDesc = "Successfully process the request";
	private String invalidStatusCode = "01";
	private String invalidStatusDesc = "Invalid request Format";
	private String failedStatusCode = "02";
	private String failedStatusDesc = "Failed process the request";

	public static List<StateTin> state_tin = new ArrayList<>();

	// ==========isSelfCheckInAvailable
//	public static final boolean IsSelfCheckInAvailable = true;
//	public static final DateTime TrialExpiryDate = new DateTime(2060, 3, 30, 0, 0, 0, 0);
//	public static final String InstitutionName = "The Madura College, Madurai";
//	public static final String InstitutionAddress = "Vidya Nagar T.P.K. Road Madurai - 625 011";
//	public static final String LibraryTiming = "09:30AM. to 05.30PM";

	// =========ccav
	// SMS authentication
	public static final String sms_username = "CITLIB";
	public static final String sms_password = "citlib";
	public static final String sms_senderid = "CITLIB";
	public static final String sms_proxy_status = "NO";
	public static final String sms_proxy_host = "127.0.0.1";
	public static final int sms_proxy_port = 9090;

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

	static DecimalFormat decformatter = new DecimalFormat("#,##,##,##0.00");

	public static Map<String, Object> SuccessResponse() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "00");
		succResp.put("StatusDesc", "Successfully processed the request");
		return succResp;
	}

	public static Map<String, String> FailedResponse() {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "02");
		Resp.put("StatusDesc", "Failed to process the request");
		Resp.put("message", "Failed to process the request");
		return Resp;
	}

	public static Map<String, String> FailedResponse(String message) {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "02");
		Resp.put("StatusDesc", "Failed to process the request");
		Resp.put("message", message);
		return Resp;
	}

	public static Map<String, String> InvalidRequestFormat() {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "01");
		Resp.put("StatusDesc", "Invalid request Format");
		return Resp;
	}

	public static Map<String, String> InvalidOTP() {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "01");
		Resp.put("StatusDesc", "Invalid OTP");
		return Resp;
	}

	public static Map<String, String> invalidLogin() {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "03");
		Resp.put("StatusDesc", "Invalid UserId/Password");
		return Resp;
	}

	public static Map<String, String> invalidMessage(String message) {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "03");
		Resp.put("StatusDesc", message);
		return Resp;
	}

	public static Map<String, String> AccountExpired() {
		Map<String, String> Resp = new HashMap<String, String>();
		Resp.put("StatusCode", "03");
		Resp.put("StatusDesc", "Account Expired, Contact Librarian!");
		return Resp;
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

	public static Map<String, String> referenceOnly() {
		Map<String, String> succResp = new HashMap<String, String>();
		succResp.put("StatusCode", "09");
		succResp.put("StatusDesc", "Reference only, please contact counter . ");
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

	public static Map<String, Object> exitUser() {
		Map<String, Object> succResp = new HashMap<String, Object>();
		succResp.put("StatusCode", "21");
		succResp.put("StatusDesc", "User has Expired..!!");
		return succResp;
	}

	public static boolean validateEmailID(String emailId) {
		if (emailId != null) {
			try {
				System.out.println(emailId);
				String regex = "^(.+)@(.+)$";

				Pattern pattern = Pattern.compile(regex);

				Matcher matcher = pattern.matcher(emailId);

				return matcher.matches();
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		return false;
	}

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

	public static String getUniqueString(String original) {
		try {
			return original.replaceFirst("(.*?)(\\.[^.]+)?$", "$1_" + String.valueOf(generateOTP()) + "$2");
		} catch (Exception e) {
			return original;
		}

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
		String resultString = str.replaceAll("'", "\\\'");
		return resultString;
	}

	public static String decimalFormatter(Double value) {
		String resp = "";
		try {
			resp = decformatter.format(value);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			resp = "";
		}
//		if (resp.endsWith(".00"))
//			resp = resp.replace(".00", "");

		return resp;
	}

	public static String getSGSTCGSTPercentage(int gstPercentage) {
		if (gstPercentage % 2 == 0)
			return gstPercentage / 2 + "";
		else
			return (double) gstPercentage / 2 + "";
	}

	public static String sdfFormatter(Date value, String format) {
		String resp = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			resp = sdf.format(value);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			resp = "";
		}
		return resp;
	}

	public static String sdfFormatter(Date value) {
		String resp = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			resp = sdf.format(value);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			resp = "";
		}
		return resp;
	}

	public static String getDateAfterDays(int days, String format) {
		String dueDate = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.DAY_OF_YEAR, days);
			Date resultDate = c1.getTime();
			dueDate = sdf.format(resultDate);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			dueDate = "";
		}
		return dueDate;
	}

	public static Date sdfFormatter(String value, String format) {
		try {
			DateFormat sdf = new SimpleDateFormat(format);

			return sdf.parse(value);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
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

	public static char[] generateRandomPassword() {

		String pwdPattern = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
		Random random = new Random();
		char[] pwdChar = new char[8];

		for (int i = 0; i < 8; i++) {
			pwdChar[i] = pwdPattern.charAt(random.nextInt(pwdPattern.length()));
		}
		return pwdChar;
	}

	public static String EnglishNumberToWords(Double number_str) {
		String inWords = "";
		try {
			inWords = NumberToWord(String.valueOf(number_str.longValue()));
			if (inWords.trim().length() == 0) {
				inWords = "Zero";
			}
			inWords = "Indian Rupee " + inWords.replaceAll("  ", " ") + " Only.";

			System.out.println("Given Number : " + number_str);
			System.out.println("InWords :" + inWords);
		} catch (Exception e) {
			System.err.println("Error in EnglishNumberToWords::" + e.getMessage());
		}

		return inWords;
	}

	private static String NumberToWord(String number) {
		String twodigitword = "";
		String word = "";
		String[] HTLC = { "", "Hundred", "Thousand", "Lakh", "Crore" }; // H-hundread , T-Thousand, ..
		int split[] = { 0, 2, 3, 5, 7, 9 };
		String[] temp = new String[split.length];
		boolean addzero = true;
		int len1 = number.length();
		if (len1 > split[split.length - 1]) {
			System.out.println("Error. Maximum Allowed digits " + split[split.length - 1]);
			System.exit(0);
		}
		for (int l = 1; l < split.length; l++)
			if (number.length() == split[l])
				addzero = false;
		if (addzero == true)
			number = "0" + number;
		int len = number.length();
		int j = 0;
		// spliting & putting numbers in temp array.
		while (split[j] < len) {
			int beg = len - split[j + 1];
			int end = beg + split[j + 1] - split[j];
			temp[j] = number.substring(beg, end);
			j = j + 1;
		}

		for (int k = 0; k < j; k++) {
			twodigitword = ConvertOnesTwos(temp[k]);
			if (k >= 1) {
				if (twodigitword.trim().length() != 0)
					word = twodigitword + " " + HTLC[k] + " " + word;
			} else
				word = twodigitword;
		}
		return (word);
	}

	private static String ConvertOnesTwos(String t) {
		final String[] ones = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
				"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
		final String[] tens = { "", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty",
				"Ninety" };

		String word = "";
		int num = Integer.parseInt(t);
		if (num % 10 == 0)
			word = tens[num / 10] + " " + word;
		else if (num < 20)
			word = ones[num] + " " + word;
		else {
			word = tens[(num - (num % 10)) / 10] + word;
			word = word + " " + ones[num % 10];
		}
		return word;
	}

	public static String autoIncrementNo(String inNo) {

		String outNo = "";

		try {
			System.out.println(inNo);
			String[] bits = inNo.split("/");

			String original = bits[bits.length - 1];
			String incremented = String.format("%0" + original.length() + "d", Integer.parseInt(original) + 1);

			inNo = inNo.replaceAll(original + "$", incremented);
			outNo = inNo;

			System.out.println(original);
			System.out.println(incremented);
			System.out.println(inNo);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			outNo = inNo + "/01";
		}

		return outNo;

	}

	public static void mergePDF(final List<String> filePaths, String destinationPath) {

		try {
			List<File> files = new ArrayList<>();
			List<PDDocument> docs = new ArrayList<>();

//			// Loading an existing PDF document
//			File file1 = new File(
//					"E:\\Kannadasan\\HelpDesk\\Contents\\_preamble_documents\\Library_Preamble_Document.pdf");
//			PDDocument doc1 = PDDocument.load(file1);
//
//			File file2 = new File("E:\\Kannadasan\\HelpDesk\\Contents\\Invoices\\33\\Inv-test-01.pdf");
//			PDDocument doc2 = PDDocument.load(file2);

			// Instantiating PDFMergerUtility class
			PDFMergerUtility PDFmerger = new PDFMergerUtility();

			// Setting the destination file
//			File directory = new File(destinationPath);
//			if (!directory.exists()) {
//				System.out.println("Directory destinationPath created ::" + directory.getAbsolutePath());
//				directory.mkdirs();
//			}
			PDFmerger.setDestinationFileName(destinationPath);

			// adding the source files
			filePaths.forEach(path -> {
				try {
					File file = new File(path);
					PDDocument doc = PDDocument.load(file);

					files.add(file);
					docs.add(doc);
					PDFmerger.addSource(new File(path));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			// Merging the two documents
			PDFmerger.mergeDocuments();
			System.out.println("Documents merged");

			// Closing the documents
			for (PDDocument doc : docs) {
				doc.close();
			}
//			doc1.close();
//			doc2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<StateTin> getStateTins() {
		return state_tin;
	}

	@PostConstruct
	private void populateStateTinData() {

		System.out.println("populateStateTinData");

		// https://economictimes.indiatimes.com/nation-world/here-is-how-your-pin-code-is-decided/first-two-digits-reflect/slideshow/57889807.cms

		state_tin.add(new StateTin("01", "JAMMU AND KASHMIR", Arrays.asList("18", "19")));
		state_tin.add(new StateTin("02", "HIMACHAL PRADESH", Arrays.asList("17")));
		state_tin.add(new StateTin("03", "PUNJAB", Arrays.asList("14", "15", "16")));
		state_tin.add(new StateTin("04", "CHANDIGARH", Arrays.asList()));
		state_tin.add(new StateTin("05", "UTTARAKHAND", Arrays.asList()));
		state_tin.add(new StateTin("06", "HARYANA", Arrays.asList("12", "13")));
		state_tin.add(new StateTin("07", "DELHI", Arrays.asList("11")));
		state_tin.add(new StateTin("08", "RAJASTHAN", Arrays.asList("30", "31", "32", "33", "34")));
		state_tin.add(new StateTin("09", "UTTAR PRADESH",
				Arrays.asList("20", "21", "22", "23", "24", "25", "26", "27", "28")));
		state_tin.add(new StateTin("10", "BIHAR", Arrays.asList("80", "81", "82", "83", "84", "85")));
		state_tin.add(new StateTin("11", "SIKKIM", Arrays.asList("79")));
		state_tin.add(new StateTin("12", "ARUNACHAL PRADESH", Arrays.asList("79")));
		state_tin.add(new StateTin("13", "NAGALAND", Arrays.asList("79")));
		state_tin.add(new StateTin("14", "MANIPUR", Arrays.asList("79")));
		state_tin.add(new StateTin("15", "MIZORAM", Arrays.asList("79")));
		state_tin.add(new StateTin("16", "TRIPURA", Arrays.asList("79")));
		state_tin.add(new StateTin("17", "MEGHLAYA", Arrays.asList("79")));
		state_tin.add(new StateTin("18", "ASSAM", Arrays.asList("78")));
		state_tin.add(new StateTin("19", "WEST BENGAL", Arrays.asList("70", "71", "72", "73", "74")));
		state_tin.add(new StateTin("20", "JHARKHAND", Arrays.asList("80", "81", "82", "83", "84", "85")));
		state_tin.add(new StateTin("21", "ODISHA", Arrays.asList("75", "76", "77")));
		state_tin.add(new StateTin("22", "CHATTISGARH", Arrays.asList("45", "46", "47", "48", "49")));
		state_tin.add(new StateTin("23", "MADHYA PRADESH", Arrays.asList("45", "46", "47", "48", "49")));
		state_tin.add(new StateTin("24", "GUJARAT", Arrays.asList("36", "37", "38", "39")));

//		state_tin.add(new StateTin("25", ""));

		state_tin.add(new StateTin("26*", "DADRA AND NAGAR HAVELI AND DAMAN AND DIU (NEWLY MERGED UT)",
				Arrays.asList("18", "19")));
		state_tin.add(new StateTin("27", "MAHARASHTRA", Arrays.asList("40", "41", "42", "43", "44")));
		state_tin.add(new StateTin("28", "ANDHRA PRADESH(BEFORE DIVISION)", Arrays.asList("18", "19")));
		state_tin.add(new StateTin("29", "KARNATAKA", Arrays.asList("56", "57", "58", "59")));
		state_tin.add(new StateTin("30", "GOA", Arrays.asList()));
		state_tin.add(new StateTin("31", "LAKSHWADEEP", Arrays.asList()));
		state_tin.add(new StateTin("32", "KERALA", Arrays.asList("67", "68", "69")));
		state_tin.add(new StateTin("33", "TAMIL NADU", Arrays.asList("60", "61", "62", "63", "64")));
		state_tin.add(new StateTin("34", "PUDUCHERRY", Arrays.asList()));
		state_tin.add(new StateTin("35", "ANDAMAN AND NICOBAR ISLANDS", Arrays.asList("18", "19")));
		state_tin.add(new StateTin("36", "TELANGANA", Arrays.asList()));
		state_tin.add(new StateTin("37", "ANDHRA PRADESH", Arrays.asList("50", "51", "52", "53")));
		state_tin.add(new StateTin("38", "LADAKH", Arrays.asList()));

//		System.out.println(state_tin);

	}

}

class StateTin {
	private String stateCode = "";
	private String stateName = "";
	private List<String> pincodeIndexes = new ArrayList<>();

	public StateTin(String stateCode, String stateName, List<String> pincodeIndexes) {
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.pincodeIndexes = pincodeIndexes;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public List<String> getPincodeIndexes() {
		return pincodeIndexes;
	}

	@Override
	public String toString() {
		return "StateTin [stateCode=" + stateCode + ", stateName=" + stateName + ", pincodeIndexes=" + pincodeIndexes
				+ "]";
	}

}