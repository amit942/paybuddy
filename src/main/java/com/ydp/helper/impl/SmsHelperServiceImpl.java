package com.ydp.helper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ydp.helper.AbstractMessageHelperService;

public class SmsHelperServiceImpl extends AbstractMessageHelperService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SmsHelperServiceImpl.class);

    @Override
    public boolean sendMessage(String mobileNo) {
        boolean messageSent = false;
        int attempt = 0;
        while (attempt < 5 && messageSent == false) {
            if ("OK".equalsIgnoreCase(getSmsInstance(mobileNo, "23"))) {
                messageSent = true;
            } else {
                attempt++;
            }
            System.out.println("SMS sent to:" + mobileNo);
        }
        LOG.info("Message sent status " + messageSent);
        return messageSent; 
    }

    public static void main(String[] args) {
        SmsHelperServiceImpl obj = new SmsHelperServiceImpl();
        obj.sendMessage("9837049100");
    }

}
