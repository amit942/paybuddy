package com.ydp.helper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMessageHelperService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMessageHelperService.class);

    private static Message      message;

    private static String       msgString;

    public AbstractMessageHelperService() {

    }

    public AbstractMessageHelperService(String message) {
        //this.message = message;
    }

    public static Message getEmailInstance() {

        if (message == null) {
            final String username = "aastha.channa@snapdeal.com";
            final String password = "life0282!!";

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            message = new MimeMessage(session);
        }
        return message;
    }

    public static String getSmsInstance(String mobileNo, String x) {

        HttpURLConnection urlConnection = null;
        String returnMessage = "ERROR";
        if (msgString == null) {
            msgString = "Dear customer, Your order has been placed successfully with order ID " + x + ". Team JewelPari.";
        }
        try {
            String uname = "20140202";
            String pass = "tricubestech@123";
            String send = "JlPari";
            String requestUrl = "http://103.247.98.91/API/SendMsg.aspx?" + "uname=" + URLEncoder.encode(uname, "UTF-8") + "&pass=" + URLEncoder.encode(pass, "UTF-8") + "&send="
                    + URLEncoder.encode(send, "UTF-8") + "&dest=" + URLEncoder.encode(mobileNo, "UTF-8") + "&msg=" + URLEncoder.encode(msgString, "UTF-8");
            URL url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            returnMessage = urlConnection.getResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return returnMessage;
    }

    abstract public boolean sendMessage(String receipient, String txnid, String link);

}
