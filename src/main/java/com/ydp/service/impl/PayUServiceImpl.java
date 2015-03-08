/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ydp.service.IPayUService;

/**
 * @version 1.0, 08-Mar-2015
 * @author priyanka
 */

@Service("payUService")
public class PayUServiceImpl implements IPayUService {

    private static final Logger LOG = LoggerFactory.getLogger(PayUServiceImpl.class);

    @Override
    public String makePayment(Map<String, String> params) {
        String testServerUrl = "https://test.payu.in/_payment";
        //String testServerUrl = "https://test.payu.in/merchant/postservice";
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(testServerUrl);
            Map<String, Object> postRequestParams = new LinkedHashMap<String, Object>();
            postRequestParams.put("key", params.get("key"));
            //postRequestParams.put("command", params.get("verify_payment"));
            postRequestParams.put("txnid", params.get("txnid"));
            postRequestParams.put("amount", params.get("amount"));
            postRequestParams.put("productinfo", params.get("productinfo"));
            postRequestParams.put("firstname", params.get("firstname"));
            postRequestParams.put("email", params.get("email"));
            postRequestParams.put("phone", params.get("phone"));
            postRequestParams.put("surl", params.get("surl"));
            postRequestParams.put("furl", params.get("furl"));
            postRequestParams.put("hash", params.get("hash"));
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

            LOG.info("Hitting Url : " + testServerUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            //TODO return response.
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            for (int c; (c = in.read()) >= 0;) {
                //System.out.print((char) c);
                sb.append((char) c);
            }

        } catch (MalformedURLException e) {
            LOG.error("Error in url" + e);
            e.printStackTrace();
        } catch (IOException e) {
            LOG.error("IO exception" + e);
            e.printStackTrace();
        }
        return sb.toString();
    }

}
