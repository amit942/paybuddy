package com.ydp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ydp.domain.PaymentTransactionResource;
import com.ydp.helper.impl.EmailMessageServiceImpl;
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

    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage(HttpServletRequest request, String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, String productInfo, String firstName, String email) {
        LOG.info(" Transaction creation request for txnid : " + txnid);
        PaymentTransactionResource paymentResource = paymentService.saveTransactionRequest(messageSendType, payerMobile, payerEmail, txnid, amount, productInfo, firstName, email);

        String baseUrl = getBaseUrl(request.getRequestURL().toString());
        String payerEmailId = paymentResource.getPayerEmailId();
        LOG.info("Sending mail to  : " + payerEmailId);
        EmailMessageServiceImpl obj = new EmailMessageServiceImpl();
        boolean success = obj.sendMessage(baseUrl, payerEmail, paymentResource.getTxnid(), paymentResource.getPermaLink(), String.valueOf(paymentResource.getAmount()));
        String message = "Email has been sent. Please wait for payment to complete.";
        return message;
    }
    
    private String getBaseUrl(String reqUrl) {
        int lastIndexOf = reqUrl.lastIndexOf("/");
        return reqUrl.substring(0, lastIndexOf);
    }
    
    public static void main(String[] args) {
        //System.out.println(getBaseUrl("http://localhost:8080/payment/msg"));
    }

    @RequestMapping(value = "/testing")
    public ModelAndView testing() {
        return new ModelAndView("redirect:http://www.google.com");
    }
    
    @RequestMapping(value = "/show")
    public ModelAndView show(HttpServletRequest request, String txnid, String link) {
        return new ModelAndView("/WEB-INF/pages/payment.jsp");
    }

    @RequestMapping(value = "/do", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
    public ModelAndView confirmTransaction(HttpServletRequest request, String txnid, String link) {
        LOG.info(" payement request for, txnid : " + txnid + " link : " + link);
        Map<String, String> params = paymentService.confirmTransaction(txnid, link);
        LOG.info("payment request success.");
        ModelAndView modelAndView = new ModelAndView("/WEB-INF/pages/payment.jsp");
        modelAndView.addObject("data", params);
        return modelAndView;
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
