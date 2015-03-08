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

    private static final Logger LOG                  = LoggerFactory.getLogger(PaymentController.class);

    private static final String PAYMENT_METHOD_EMAIL = "email";
    private static final String PAYMENT_METHOD_MSG   = "message";

    @Autowired
    private IPaymentService     paymentService;

    @RequestMapping("")
    @ResponseBody
    public String healthCheck() {
        return "It works";
    }

    @RequestMapping("/msg")
    @ResponseBody
    public String sendMessage(String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, String productInfo, String firstName, String email) {
        LOG.info(" Transaction creation request for txnid : " + txnid);
        PaymentTransactionResource obj = paymentService.saveTransactionRequest(messageSendType, payerMobile, payerEmail, txnid, amount, productInfo, firstName, email);

        // TODO send mail/message here

        String message = messageSendType + " has been sent. Please wait until payment is done.";
        return message;
    }

    @RequestMapping("/do")
    @ResponseBody
    public String confirmTransaction(String txnid, String link) {
        LOG.info(" payement request for, txnid : " + txnid + " link : " + link);
        boolean success = paymentService.confirmTransaction(txnid, link);
        if (success) {
            return "Your payment has been done successfully.";
        }
        return "Found Invalid Request.";
    }

    @RequestMapping("success")
    @ResponseBody
    public String success() {
        return "Thank you. Your transaction is completed successfully.";
    }

    @RequestMapping("failure")
    @ResponseBody
    public String failure() {
        return "Your transaction has failed.";
    }

}
