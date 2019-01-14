package com.zte.km;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Test
	public void SimpleMailTest() {
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		//邮件标题
		simpleMailMessage.setSubject("通知今晚7:30开会");
		//邮件内容
		simpleMailMessage.setText("今晚7:30开会...");
		simpleMailMessage.setTo("291974786@qq.com");
		//发件人必须是登陆用户
		simpleMailMessage.setFrom("291974786@qq.com");
		mailSender.send(simpleMailMessage);
	}

	@Test
	public void MimeMessageTest() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		//发送复杂邮件，true表示:支持发送附件
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		//邮件标题
		messageHelper.setSubject("通知今晚8:00开会");
		//邮件内容
		messageHelper.setText("今晚<div style='color:red;'>7:30</div>开会...",true);
		messageHelper.setTo("291974786@qq.com");
		//发件人必须是登陆用户
		messageHelper.setFrom("291974786@qq.com");
		//发送附件
		messageHelper.addAttachment("1.jpg",new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\f2.jpg"));
		mailSender.send(mimeMessage);
	}

}
