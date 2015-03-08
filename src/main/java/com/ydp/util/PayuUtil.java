/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */
public class PayuUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PayuUtil.class);

    /**
     * Calculates hash by using defined hash sequence.
     * @param params
     * @param salt
     * @return
     */
    public static String calculateHash(Map<String, String> params, String salt) {
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        String hashString = "";
        String[] hashVarSeq = hashSequence.split("\\|");
        for (String part : hashVarSeq) {
            hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part));
            hashString = hashString.concat("|");
        }
        hashString = hashString.concat(salt);
        return hashCal("SHA-512", hashString);
    }

    public static String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append("0");
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
            LOG.error("NoSuchAlgorithmException  :" + nsae);
        }
        return hexString.toString();
    }

    public static boolean empty(String s) {
        if (s == null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        String merchantKey = "C0Dr8m";
        String salt = "3sf0jURk";

        Map<String, String> params = generateDummyData();

        String hash = "";
        String hashString = "";

        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        if (empty(hash)) {
            String[] hashVarSeq = hashSequence.split("\\|");
            for (String part : hashVarSeq) {
                hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part));
                hashString = hashString.concat("|");
            }
            hashString = hashString.concat(salt);
            hash = hashCal("SHA-512", hashString);
        }

        String testServerUrl = "https://test.payu.in/_payment";
        //String testServerUrl = "https://test.payu.in/merchant/postservice";
        try {
            URL url = new URL(testServerUrl);
            Map<String, Object> postRequestParams = new LinkedHashMap<String, Object>();
            postRequestParams.put("key", merchantKey);
            postRequestParams.put("command", params.get("verify_payment"));
            postRequestParams.put("txnid", params.get("txnid"));
            postRequestParams.put("amount", params.get("amount"));
            postRequestParams.put("productinfo", params.get("productinfo"));
            postRequestParams.put("firstname", params.get("firstname"));
            postRequestParams.put("email", params.get("email"));
            postRequestParams.put("phone", params.get("phone"));
            postRequestParams.put("surl", params.get("surl"));
            postRequestParams.put("furl", params.get("furl"));
            postRequestParams.put("hash", hash);
            postRequestParams.put("message", "Do not pay calling");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : postRequestParams.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (int c; (c = in.read()) >= 0; System.out.print((char) c))
                ;

        } catch (MalformedURLException e) {
            System.err.println("Error in url" + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO exception" + e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static Map<String, String> generateDummyData() {
        String merchantKey = "C0Dr8m";
        String txnid = generateTxnId();
        String amount = "123";
        String productinfo = "Product 1 info";
        String firstname = "Priyanka";
        String email = "dudani.priyanka@gmail.com";
        String phone = "9958753704";
        String surl = "localhost:8080/success";
        String furl = "localhost:8080/failure";

        Map<String, String> param = new HashMap<String, String>();
        param.put("key", merchantKey);
        param.put("txnid", txnid);
        param.put("amount", amount);
        param.put("productinfo", productinfo);
        param.put("firstname", firstname);
        param.put("email", email);
        param.put("phone", phone);
        param.put("surl", surl);
        param.put("furl", furl);
        return param;
    }

    private static String generateTxnId() {
        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        return hashCal("SHA-256", rndm).substring(0, 20);
    }

}
