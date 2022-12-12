package com.autolib.helpdesk.Notification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.autolib.helpdesk.common.GlobalAccessUtil;

public class PushNotification {

	public static int sentNotification(String tokenId, Map data, Map notification)
			throws UnsupportedEncodingException, MalformedURLException {
		Map<String , Object> testNotification = new HashMap<>();

//		notification.put("title",data.get("notification_type").toString());
//		notification.put("body",data.get("title").toString());
		notification.put("sound", "default");
//		notification.put("click_action", "OPEN_ACTIVITY_1");
		testNotification.put("to", tokenId);
		testNotification.put("content_available", true);
		testNotification.put("mutable_content", true);
		testNotification.put("data", data);
		testNotification.put("notification", notification);

		String result = "";
		String jsonValue = ""; // this string stors json value.
		try {

			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Authorization",
					"key=AAAA8DLCzgY:APA91bFxLaUWCjPb5OcougP5ZTPYbMZXj0bhPziwnTKye2uG3U3EDxVS7c0IzMe7ma4fekuhlF7mDUSRRRd8avOZwwhAwd7nKMlQaV_sWMJpSVLHM77gs3T3WlLuezk91veKlI7CpMHl");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			jsonValue = GlobalAccessUtil.toJson(testNotification);
			os.write(jsonValue.getBytes("UTF-8")); // here json object (json format);
			os.close();
			System.out.println("Notification response code========" + conn.getResponseMessage());
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseMessage());
			} else {
				// read the response
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				result = br.readLine();
				conn.disconnect();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	

	/*public static void main(String[] args) {

		String tokenId = "chv7yDQ5EeI:APA91bG4nt92_rzbHpCK02EseLRYpsT2TY6EflYbdXHXGt-R-3oJwx64SvuK6LbJkKGFtU7a510JTpBhYQDRXIluFTZAh_0KMENZqvJ3hbrPSXTnETl1byvdtqfQzxFy8EsY40f0IjCw";
		Map<String, String> notification = new HashMap();

		Map data = new HashMap();

		data.put("post_id", "0dfc176f-9543-4aa8-845d-986288efd407");
		data.put("employer_id", "c3ffdc44-aa10-4d8f-bc6e-8e72a2270cbe");
		data.put("action", "APPLIED_JOB");

		data.put("notification_type", "EventReminder");

		notification.put("title", data.get("action").toString());
		notification.put("body", data.get("action").toString() + " - Reminder");
		System.out.println("Inside Thread");

		try {
			int notify = sentNotification(tokenId, data, notification);

		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Inside Thread");
					int notify = sentNotification(tokenId, data, notification);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}*/

}
