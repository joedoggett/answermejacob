package com.joe.answermejacob.AnswerMeJacob.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Transport;


@Component
public class SendAuthenticatedEmailUtility implements java.io.Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 201605170856L;

    private static final String HOST = "email-smtp.us-west-2.amazonaws.com";
    private static final int PORT = 25;

    private static final String FROM = "validate_registration@answermejacob.com";
    private static final String SUBJECT = "validate registration";
    private static final String BODY = "please navigate to the following link to validate your registration. http://answermejacob.com/validateregistration.html?token=";

    @Value("${smtp.username}")
    private String SMTP_USERNAME;  // Replace with your SMTP username.

    @Value("${smtp.password}")
    private String SMTP_PASSWORD;  // Replace with your SMTP password.

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String SMTP = "smtp";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    private static final String STRING_TRUE = "true";
    private static final String TEXT_PLAIN = "text/html";


    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void sendValidateRegistrationEmail(String toEmail, String token)
    {
        try
        {
            Properties props = new Properties();
            props.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
            props.put(MAIL_SMTP_PORT, PORT);

            // Set properties indicating that we want to use STARTTLS to encrypt the connection.
            // The SMTP session will begin on an unencrypted connection, and then the client
            // will issue a STARTTLS command to upgrade to an encrypted connection.
            props.put(MAIL_SMTP_AUTH, STRING_TRUE);
            props.put(MAIL_SMTP_STARTTLS_ENABLE, STRING_TRUE);
            props.put(MAIL_SMTP_STARTTLS_REQUIRED, STRING_TRUE);

            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);

            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(SUBJECT);
            msg.setContent(BODY + token, TEXT_PLAIN);

            Transport transport = session.getTransport();
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();


        }
        catch(Exception e)
        {
            log.error("exception: ", e);
        }
    }

}
