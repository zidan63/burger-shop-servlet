package com.burger.services;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

  private final Properties PROPERTIES = new Properties();
  private final String USERNAME = "phuthinh53.it@gmail.com"; // change it
  private final String PASSWORD = "doyo agbg uoyy poif"; // change it
  private final String HOST = "smtp.gmail.com";

  private static EmailService instance;

  public static EmailService getInstance() {
    if (instance == null)
      instance = new EmailService();
    return instance;
  }

  private EmailService() {
    PROPERTIES.put("mail.smtp.host", HOST);
    PROPERTIES.put("mail.smtp.port", "587");
    PROPERTIES.put("mail.smtp.auth", "true");
    PROPERTIES.put("mail.smtp.starttls.enable", "true");
  }

  public void sendPlainTextEmail(String to, String subject, List<String> messages, boolean debug) {

    Authenticator authenticator = new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
      }
    };

    Session session = Session.getInstance(PROPERTIES, authenticator);
    session.setDebug(debug);

    try {

      // create a message with headers
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(USERNAME));
      InternetAddress[] address = { new InternetAddress(to) };
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject(subject, "utf-8");
      msg.setSentDate(new Date());

      // create message body
      Multipart mp = new MimeMultipart();
      for (String message : messages) {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(message, "utf-8");
        mp.addBodyPart(mbp);
      }
      msg.setContent(mp);

      // send the message
      Transport.send(msg);

    } catch (MessagingException mex) {
      mex.printStackTrace();
      Exception ex = null;
      if ((ex = mex.getNextException()) != null) {
        ex.printStackTrace();
      }
    }
  }
}