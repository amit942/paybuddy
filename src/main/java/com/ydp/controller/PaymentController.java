package com.ydp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ydp.domain.PaymentTransactionResource;
import com.ydp.service.IPaymentService;



/**
 * @version 1.0, 5-March-2015
 * @author priyanka
 */

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);
    
    private static final String PAYMENT_METHOD_EMAIL = "EMAIL";
    private static final String PAYMENT_METHOD_MSG = "MESSAGE";
    
    @Autowired
    private IPaymentService paymentService;
    
    @RequestMapping("")
    @ResponseBody
    public String healthCheck() {
        return "It works";
    }
    
    @RequestMapping("/msg")
    public void sendMessage(String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, 
            String productInfo, String firstName, String email) {
        
        PaymentTransactionResource obj = paymentService.saveTransactionRequest(messageSendType, payerMobile, payerEmail, txnid, amount, productInfo, firstName, email);
        
        // TODO sendMessage
    }
    
    public void confirmTransaction() {
        
    }
    
}
