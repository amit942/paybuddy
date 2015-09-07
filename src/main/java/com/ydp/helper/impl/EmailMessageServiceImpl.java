package com.ydp.helper.impl;

import com.ydp.helper.AbstractMessageHelperService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

public class EmailMessageServiceImpl extends AbstractMessageHelperService {

    public EmailMessageServiceImpl() {
        super();
    }

    public static void main(String[] args) {
        EmailMessageServiceImpl obj = new EmailMessageServiceImpl();
        boolean                 s   = obj.sendMessage("localhost:8080", "amit.das@snapdeal.com", "newtxnid", "14bf9dde2d1vdmGsXhCYS", "123");
        System.out.println(s);
    }

    @Override public boolean sendMessage(String baseUrl, String recipient, String txnid, String link, String amount) {
        boolean status = false;
        try {
            Message message = getEmailInstance();
            message.setFrom(new InternetAddress("PayBuddy"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            String mainUrl = "http://localhost:8888/payment" + "/do?txnid=" + txnid + "&link=" + link;
	   // String url = mainUrl.replace("http//", "");
            /*message.setText(
                    "Hi," + "\n\n Your friend has asked to pay on their behalf. \n\n Transaction id is : " + txnid + " and amount is :" + amount + " \n\n Please visit the following link for same : "
                            + "\n\n " + url);*/
            message.setSubject("Pay For A Friend For Their Order on Snapdeal.com");
            message.setContent(
                    "<p>Greetings from <span class=\"il\">Snapdeal</span>.com!</p><p>\n" + "    Your friend has asked you to pay on their behalf for Order : <span style=\"color:#00648b\">\n"
                            + "    <a href=\"http://www.snapdeal.com" + "\" target=\"_blank\">" + txnid + "</a></span>" + " . Please review the order and click <span style=\"color:#00648b\">\n "
                            + "<a target=\"_blank\" href=\"" + mainUrl + "\"" + "><button>HERE</button></a></span>"
                            + "</p><table border=\"1\" cellpadding=\"2\" cellspacing=\"2\" style=\"border-collapse:collapse;font-size:13px;border:1px solid #d2d9e7\">\n" + "    <tbody>\n"
                            + "    <tr style=\"background-color:#00648b;color:#ffffff\">\n" + "\n" + "        <td> Product Details</td>\n" + "\n" + "                <td> Seller Details</td>\n"
                            + "                <td> Ordered Quantity</td>\n" + "        <td> Price/unit (Rs.)</td>\n" + "        <td> Confirmed Quantity</td>\n" + "        <td> Sub Total</td>\n"
                            + "    </tr>\n" + "        <tr>\n" + "        <td><a href=\"http://www.snapdeal.com/product/ipad-mini-3-wifi/1039288731\"" + "target=\"_blank\"> " + "Yay</a></td>\n"
                            + "                <td>PrettyPleaseMe</td>\n" + "                <td>1</td>\n" + "        <td>250.00</td>\n" + "        <td>1</td>\n" + "        <td>250.00</td>\n"
                            + "    </tr>\n" + "            <tr>\n" + "        <td></td>\n" + "                    <td></td>\n" + "                <td></td>\n" + "        <td></td>\n"
                            + "        <td>Shipping Charge</td>\n" + "        <td>30.00</td>\n" + "    </tr>\n" + "        <tr>\n" + "        <td></td>\n" + "                   <td></td>\n"
                            + "                <td></td>\n" + "        <td></td>\n" + "        <td>Total</td>\n" + "        <td>280.00</td>\n" + "    </tr>\n" + "    </tbody>\n"
                            + "</table>", "text/html");

            Transport.send(message);
            status = true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (Exception e1) {
            System.out.println(e1);
        }
        return status;
    }
}

