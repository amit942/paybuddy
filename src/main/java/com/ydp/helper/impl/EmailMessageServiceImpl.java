package com.ydp.helper.impl;

import com.ydp.helper.AbstractMessageHelperService;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailMessageServiceImpl extends AbstractMessageHelperService {

    public EmailMessageServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(String receipient, String txnid, String link) {
        boolean status = false;
        try {
            Message message = getEmailInstance();
            message.setFrom(new InternetAddress("yodonotpay@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipient));
            message.setSubject("Greeting from youdonotpay.com!");
            String url = "localhost:8080/payment/do/txnid=" + txnid + "&link=" + link;
            message.setText("Hi," + "\n\n You have been requested to complete a payment. \n\n Please enter following URL to proceed with payment : " + "\n\n " + url);
            Transport.send(message);

            status = true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public static void main(String[] args) {
        EmailMessageServiceImpl obj = new EmailMessageServiceImpl();
        boolean s = obj.sendMessage("priyanka.dudani@snapdeal.com", "newtxnid", "14bf9dde2d1vdmGsXhCYS");
        System.out.println(s);
    }
}
