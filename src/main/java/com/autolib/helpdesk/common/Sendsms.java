package com.autolib.helpdesk.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

public class Sendsms {
	public static int findSendSMS(EmailModel emailModel) throws IOException {
		System.out.println("===========inside sent sms==============");
		String username = Util.sms_username;
		String password = Util.sms_password;
		String from = Util.sms_senderid;
		String proxyStatus = Util.sms_proxy_status;
		String proxyName = Util.sms_proxy_host;
		int proxyPort = Util.sms_proxy_port;
		int a = 0;
		try {
			int TIMEOUT_VALUE = 15000;
			String url_str = "https://www.unicel.in/SendSMS/sendmsg.php?uname=" + username + "&pass=" + password
					+ "&send=" + from + "&dest=" + emailModel.getPhone_number() + "&msg="
					+ URLEncoder.encode(emailModel.getMailText(), "UTF-8");
			System.out.println(url_str);
			URL u = new URL(url_str);

			HttpURLConnection connection;
			if (proxyStatus.equalsIgnoreCase("YES")) // For Proxy Settings
			{
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyName, proxyPort)); // For
																										// Proxy
																										// Settings
				connection = (HttpURLConnection) u.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) u.openConnection();
			}

			connection.setDoOutput(true);
			connection.setDoInput(true);

			connection.setConnectTimeout(TIMEOUT_VALUE);
			connection.setReadTimeout(TIMEOUT_VALUE);

			// log4jLogger.info("connection :::::::::::::::::"+connection);

			String res = connection.getResponseMessage();

			// log4jLogger.info("responce:::::::::::::::::: :"+res);

			int code = connection.getResponseCode();

			// log4jLogger.info("responcecode:::::::::::::::::: :"+code);

			if (code == HttpURLConnection.HTTP_OK) {
				connection.disconnect();
				System.out.println("=============== Send Successfully =====" + emailModel.getMailText().length()
						+ " To:" + emailModel.getPhone_number());
			}

			u = null;

			// uc = null;

			connection = null;

			a = 1;
		} catch (Exception e) {
			a = 0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;

	}
}