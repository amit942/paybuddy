/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ydp.dao.IPaymentTransactionDao;
import com.ydp.domain.PaymentTransactionResource;
import com.ydp.service.IPaymentService;
import com.ydp.util.IDGenerator;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger    LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private IPaymentTransactionDao paymentTransactionDao;

    @Override
    public PaymentTransactionResource saveTransactionRequest(String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, String productInfo,
            String firstName, String email) {
        PaymentTransactionResource paymentObj = paymentTransactionDao.getByTxnId(txnid);
        if (paymentObj == null) {
            String permaLink = generatePermaLink();
            paymentObj = new PaymentTransactionResource(txnid, Float.valueOf(amount), productInfo, firstName, email, payerEmail, payerMobile, permaLink);
            paymentTransactionDao.save(paymentObj);
        } else {
            LOG.info("txnid already existing. Updating info");
            // TODO update info.
        }
        return paymentObj;
    }

    private String generatePermaLink() {
        return IDGenerator.generateUniqueId(10);
    }

}
