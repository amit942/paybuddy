package com.ydp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @version 1.0, 5-March-2015
 * @author priyanka
 */

@Controller("/payment")
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
    
    @RequestMapping("")
    @ResponseBody
    public String healthCheck() {
        return "It works";
    }
    
    @RequestMapping("/msg")
    public void sendMessage(String messageSendType, String input, String merchantTransactionId, String amount, String redirectionUrl, String accessKey) {
        //saveTransactionDetails()
        //sendMsg
    }
    
    public void confirmTransaction() {
        
    }
    
}
