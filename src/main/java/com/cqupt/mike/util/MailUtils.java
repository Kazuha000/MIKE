package com.cqupt.mike.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import static java.awt.SystemColor.text;

/**
 * 邮件发送工具类
 */
public class MailUtils {
     /*
		email: 注册用户的邮箱
		emailMsg: 服务器要发送的激活信息
	 */
	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.host", "smtp.qq.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("2078501297", "gczqagbcbxbjgadg");
				return new PasswordAuthentication("3458977769", "vpkwrbmgnlwgdbaj");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

//		message.setFrom(new InternetAddress("2078501297@qq.com")); // 设置发送者
		message.setFrom(new InternetAddress("3458977769@qq.com")); // 设置发送者
		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject("mike教育平台邮件验证");  //邮件标题
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		message.setContent("您正在找回密码，验证码为： "+ emailMsg ,"text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送

		Transport.send(message);
	}
}
