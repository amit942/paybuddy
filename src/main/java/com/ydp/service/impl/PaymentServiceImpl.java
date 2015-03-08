/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ydp.dao.IPaymentTransactionDao;
import com.ydp.domain.PaymentTransactionResource;
import com.ydp.service.IPayUService;
import com.ydp.service.IPaymentService;
import com.ydp.util.IDGenerator;
import com.ydp.util.PayuUtil;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger    LOG      = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final String    BASE_URL = "localhost:8080/payment";

    @Autowired
    private IPaymentTransactionDao paymentTransactionDao;

    @Autowired
    private IPayUService           payUService;

    @Override
    public PaymentTransactionResource saveTransactionRequest(String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, String productInfo,
            String firstName, String email) {
        PaymentTransactionResource paymentObj = paymentTransactionDao.getByTxnId(txnid);
        if (paymentObj == null) {
            String permaLink = generatePermaLink();
            LOG.info("Generated link : " + permaLink + " for txnid : " + txnid);
            paymentObj = new PaymentTransactionResource(txnid, Float.valueOf(amount), productInfo, firstName, email, payerEmail, payerMobile, permaLink);
        } else {
            LOG.info("txnid already existing. Updating info");
            String permaLink = generatePermaLink();
            LOG.info("Generated link : " + permaLink + " for txnid : " + txnid);
            paymentObj.setAmount(Float.valueOf(amount));
            paymentObj.setProductInfo(productInfo);
            paymentObj.setFirstname(firstName);
            paymentObj.setEmail(email);
            paymentObj.setPayerEmailId(payerEmail);
            paymentObj.setPayerMobileNumber(payerMobile);
            paymentObj.setPermaLink(permaLink);
        }
        paymentTransactionDao.save(paymentObj);
        return paymentObj;
    }

    @Override
    public boolean confirmTransaction(String txnid, String link) {
        PaymentTransactionResource paymentObj = paymentTransactionDao.getByTxnId(txnid);
        if (paymentObj == null || !link.equals(paymentObj.getPermaLink())) {
            return false;
        }

        // donotpay.com merchant key, success-url, failure-url
        String merchantKey = "C0Dr8m";
        String salt = "3sf0jURk";
        String surl = BASE_URL.concat("/success");
        String furl = BASE_URL.concat("/failure");

        // make payment request params.
        Map<String, String> param = new HashMap<String, String>();
        param.put("key", merchantKey);
        param.put("txnid", paymentObj.getTxnid());
        param.put("amount", String.valueOf(paymentObj.getAmount()));
        param.put("productinfo", paymentObj.getProductInfo());
        param.put("firstname", paymentObj.getFirstname());
        param.put("email", paymentObj.getEmail());
        param.put("phone", paymentObj.getPayerMobileNumber());
        param.put("surl", surl);
        param.put("furl", furl);
        param.put("hash", PayuUtil.calculateHash(param, salt));

        return payUService.makePayment(param);
    }

    private String generatePermaLink() {
        return IDGenerator.generateUniqueId(10);
    }

}
