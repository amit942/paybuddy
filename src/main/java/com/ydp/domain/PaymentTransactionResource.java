package com.ydp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @version 1.0, 05-Mar-2015
 * @author priyanka
 */

@Entity
@Table(name = "payment_transaction_resource", catalog = "ydp")
public class PaymentTransactionResource implements Serializable {

    private int    id;

    private String txnid;

    private Float  amount;

    private String productInfo;

    private String firstname;

    private String email;

    private String payerEmailId;

    private String payerMobileNumber;

    private String permaLink;

    private Date   createdDate;
    private Date   updatedDate;

    public PaymentTransactionResource() {

    }

    public PaymentTransactionResource(String txnid, Float amount, String productInfo, String firstname, String email, String payerEmailId, String payerMobileNumber,
            String permaLink) {
        this.txnid = txnid;
        this.amount = amount;
        this.productInfo = productInfo;
        this.firstname = firstname;
        this.email = email;
        this.payerEmailId = payerEmailId;
        this.payerMobileNumber = payerMobileNumber;
        this.permaLink = permaLink;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id1", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "txnid", nullable = false, length = 100)
    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    @Column(name = "amount", nullable = false)
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Column(name = "productInfo", nullable = false)
    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    @Column(name = "firstname", nullable = false)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "payerEmailId")
    public String getPayerEmailId() {
        return payerEmailId;
    }

    public void setPayerEmailId(String payerEmailId) {
        this.payerEmailId = payerEmailId;
    }

    @Column(name = "payerMobileNumber")
    public String getPayerMobileNumber() {
        return payerMobileNumber;
    }

    public void setPayerMobileNumber(String payerMobileNumber) {
        this.payerMobileNumber = payerMobileNumber;
    }

    @Column(name = "permaLink")
    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedDate")
    public Date getUpdatedDate() {
        return updatedDate;
    }

}
