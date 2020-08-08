package com.user.util;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
	    /*
	    *@param [toMail, code]收件人邮箱 激活码
	    *@return void
	    */
	    public static void secdMail(String toMail, String code) throws MessagingException {
	        //设置邮件服务器
	        Properties properties = new Properties();
	        //可以设置邮件服务器
	        properties.setProperty("mail.host","smtp.163.com");
	        properties.setProperty("mail.smtp.auth","true");
	        //与邮件服务器的连接
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("15620511032@163.com","TAZCMTPKTMHZFKSL");
	            }
	        });

	        //创建邮件
	        Message message = new MimeMessage(session);
	        //设置发件人地址
	        message.setFrom(new InternetAddress("15620511032@163.com"));
	        //抄送
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
	        //设置邮件的主体
	        message.setSubject("来自校园二手商品交易平台的邮件");
	        //设置内容
	        String msg="<h1>点击链接激活账号<a href='http://localhost:8080/upload/user/activation.do?code="+code+"'>http://localhost:8080/upload/user/activation.do</a><h1>";
	        message.setContent(msg, "text/html;charset=utf-8");
	        //发送邮件
	        Transport.send(message);
	    }
	    
	    public static void rePasswordMail(String toMail,String authcode) throws MessagingException {
	        //设置邮件服务器
	        Properties properties = new Properties();
	        //可以设置邮件服务器
	        properties.setProperty("mail.host","smtp.163.com");
	        properties.setProperty("mail.smtp.auth","true");
	        //与邮件服务器的连接
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("15620511032@163.com","TAZCMTPKTMHZFKSL");
	            }
	        });

	        //创建邮件
	        Message message = new MimeMessage(session);
	        //设置发件人地址
	        message.setFrom(new InternetAddress("15620511032@163.com"));
	        //抄送
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
	        //设置邮件的主体
	        message.setSubject("来自校园二手商品交易平台的邮件");
	        String str="验证码："+authcode;
	        String msg=str;
	        message.setContent(msg, "text/html;charset=utf-8");
	        //发送邮件
	        Transport.send(message);
	    }
}
