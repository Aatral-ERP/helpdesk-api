package com.autolib.helpdesk.common;

public class DBQueryUtil {

    public static final String Insert_Signup_OTP = "insert into signup_otp(mail_id,otp,generated_time) values(?,?,CURRENT_TIMESTAMP)";

}

	