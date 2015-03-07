/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ydp.dao.IPaymentTransactionDao;
import com.ydp.domain.PaymentTransactionResource;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */
public class PaymentTransactionDaoImpl implements IPaymentTransactionDao {

    @Autowired
    @Qualifier("ydp")
    private SessionFactory sessionFactory;

    @Override
    public void save(PaymentTransactionResource paymentTransac) {
        sessionFactory.getCurrentSession().save(paymentTransac);
    }

    @Override
    public PaymentTransactionResource get(String id) {
        return (PaymentTransactionResource) sessionFactory.getCurrentSession().get(PaymentTransactionResource.class, id);
    }

}
