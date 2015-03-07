/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.dao;

import com.ydp.domain.PaymentTransactionResource;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */
public interface IPaymentTransactionDao {

    public void save(PaymentTransactionResource paymentTransac);

    public PaymentTransactionResource getById(String id);
    
    public PaymentTransactionResource getByTxnId(String txnid);
}
