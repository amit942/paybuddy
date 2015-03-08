/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ydp.service;

import com.ydp.domain.PaymentTransactionResource;

/**
 *  
 *  @version     1.0, 07-Mar-2015
 *  @author priyanka
 */
public interface IPaymentService {

    PaymentTransactionResource saveTransactionRequest(String messageSendType, String payerMobile, String payerEmail, String txnid, String amount, String productInfo,
            String firstName, String email);

    boolean confirmTransaction(String txnid, String link);
    
}
