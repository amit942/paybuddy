/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.snapdeal.search.core.entity.Campaign;
import com.ydp.dao.IPaymentTransactionDao;
import com.ydp.domain.PaymentTransactionResource;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */

@Repository("paymentTransactionDao")
public class PaymentTransactionDaoImpl implements IPaymentTransactionDao {

    @Autowired
    @Qualifier("ydp")
    private SessionFactory sessionFactory;

    @Override
    public void save(PaymentTransactionResource paymentTransac) {
        sessionFactory.getCurrentSession().save(paymentTransac);
    }

    @Override
    public PaymentTransactionResource getById(String id) {
        return (PaymentTransactionResource) sessionFactory.getCurrentSession().get(PaymentTransactionResource.class, id);
    }

    @Override
    public PaymentTransactionResource getByTxnId(String txnid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PaymentTransactionResource.class);
        criteria.add(Restrictions.eq("txnid", txnid));
        return (PaymentTransactionResource) criteria.list().get(0);
    }

}
