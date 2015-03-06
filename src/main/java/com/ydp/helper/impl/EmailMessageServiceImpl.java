package com.ydp.helper.impl;

import com.ydp.helper.AbstractMessageHelperService;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailMessageServiceImpl extends AbstractMessageHelperService {

    public EmailMessageServiceImpl() {
        super();
    }

    @Override
    public boolean sendMessage(String receipient) {

        boolean status = false;
        try {
            
            Message message = getEmailInstance();
            message.setFrom(new InternetAddress("channa.aastha@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipient));
            message.setSubject("Testing Subject");
            message.setText("Testing email client," + "\n\n No spam to my email, please!");

            Transport.send(message);

            status = true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    public static void main(String[] args) {

        EmailMessageServiceImpl obj = new EmailMessageServiceImpl();
        boolean s = /*obj.sendMessage("priyanka.dudani@snapdeal.com");
                    System.out.println(s);
                    s =*/obj.sendMessage("aastha.channa@snapdeal.com");

        System.out.println(s);
    }
}
