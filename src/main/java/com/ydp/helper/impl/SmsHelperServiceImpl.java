package com.ydp.helper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ydp.helper.AbstractMessageHelperService;

public class SmsHelperServiceImpl extends AbstractMessageHelperService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsHelperServiceImpl.class);

    @Override
    public boolean sendMessage(String baseUrl, String mobileNo, String txnid, String link, String amount) {
        boolean messageSent = false;
        int attempt = 0;
        String url = baseUrl + "/do?txnid=" + txnid + "&link=" + link;
        while (attempt < 5 && messageSent == false) {
            if ("OK".equalsIgnoreCase(getSmsInstance(mobileNo, url))) {
                messageSent = true;
            } else {
                attempt++;
            }
            System.out.println("SMS sent to:" + mobileNo);
        }
        LOG.info("Message sent status " + messageSent);
        return messageSent; 
    }
}
