package com.autolib.helpdesk.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptDBProperties {

	private static String username;
	private static String password;
	private static String InstitutionName;
	private static String InstitutionAddress;
	private static String TrialExpiryDate;

	public static void main(final String[] args) {
		try {
			EncryptDBProperties.InstitutionName = encrypt(EncryptDBProperties.InstitutionName,
					"E1BC425D57CAF7ACDBBE8091A9CD73BE");
			EncryptDBProperties.InstitutionAddress = encrypt(EncryptDBProperties.InstitutionAddress,
					"E1BC425D57CAF7ACDBBE8091A9CD73BE");
			EncryptDBProperties.TrialExpiryDate = encrypt(EncryptDBProperties.TrialExpiryDate,
					"E1BC425D57CAF7ACDBBE8091A9CD73BE");
			EncryptDBProperties.username = encrypt(EncryptDBProperties.username, "E1BC425D57CAF7ACDBBE8091A9CD73BE");
			EncryptDBProperties.password = encrypt(EncryptDBProperties.password, "E1BC425D57CAF7ACDBBE8091A9CD73BE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		final StringBuffer sb = new StringBuffer();
		sb.append("\r\nkoha.InstitutionName= " + EncryptDBProperties.InstitutionName);
		sb.append("\r\nkoha.InstitutionAddress = " + EncryptDBProperties.InstitutionAddress);
		sb.append("\r\nkoha.TrialExpiryDate = " + EncryptDBProperties.TrialExpiryDate);
		sb.append("\r\nkoha.db.datasource.username = " + EncryptDBProperties.username);
		sb.append("\r\nkoha.db.datasource.password = " + EncryptDBProperties.password);
		System.out.println(sb.toString());
//        final BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
//        final String hashed = enc.encode((CharSequence)"admin");
//        System.out.println(hashed);
//        System.out.println(enc.matches((CharSequence)"admin", "$2a$10$2lqUbd4WvwuYZAj8.6qVCunXyEEWPPHeSYDSJBL8pkx.6TUXAZqu."));
	}

	static {
//		EncryptDBProperties.username = "MJ4tWI6QmBvMxZAJxynRBwn8fSsT1CZ8/4e8szjXgmU=";
//		EncryptDBProperties.password = "PGh2PbgL83gC1Khf4jRwdDxMq6ckoOqlPFfxolClt5s=";
		EncryptDBProperties.username = "bio";
		EncryptDBProperties.password = "Bio@12345";
		EncryptDBProperties.InstitutionName = "Lal Bahadur Shastri National Academy of Administration";
		EncryptDBProperties.InstitutionAddress = "Mussoorie-248179 (Uttarakhand)";
		EncryptDBProperties.TrialExpiryDate = "2020-12-30T00:00:00.000+05:30";
	}

	public static String encrypt(final String plaintext, final String key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		final byte[] raw = DatatypeConverter.parseHexBinary(key);
		final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(1, skeySpec);
		final byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));
		final byte[] iv = cipher.getIV();
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(iv);
		outputStream.write(cipherText);
		final byte[] finalData = outputStream.toByteArray();
		final String encodedFinalData = DatatypeConverter.printBase64Binary(finalData);
		return encodedFinalData;
	}

	public static String decrypt(final String encodedInitialData, final String key)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		final byte[] encryptedData = DatatypeConverter.parseBase64Binary(encodedInitialData);
		final byte[] raw = DatatypeConverter.parseHexBinary(key);
		final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		final byte[] iv = Arrays.copyOfRange(encryptedData, 0, 16);
		final byte[] cipherText = Arrays.copyOfRange(encryptedData, 16, encryptedData.length);
		final IvParameterSpec iv_specs = new IvParameterSpec(iv);
		cipher.init(2, skeySpec, iv_specs);
		final byte[] plainTextBytes = cipher.doFinal(cipherText);
		final String plainText = new String(plainTextBytes);
		return plainText;
	}

}
