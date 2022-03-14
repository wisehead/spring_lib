package com.nasa.sendmail;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

/*
@SpringBootTest
class SendmailApplicationTests {

	@Test
	void contextLoads() {
	}

}
*/

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SendmailApplication.class)
@SpringBootTest
public class SendmailApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("chenhui_bupt@126.com");
		message.setTo("chenhui@elensdata.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");

		mailSender.send(message);
	}

	@Test
	public void sendAttachmentsMail() throws Exception {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("chenhui_bupt@126.com");
		helper.setTo("chenhui@elensdata.com");
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");

		FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
		//helper.addAttachment("附件-1.jpg", file);
		//helper.addAttachment("附件-2.jpg", file);
		helper.addAttachment("fujian1.jpg", file);
		//helper.addAttachment("fujian2.jpg", file);

		mailSender.send(mimeMessage);

	}

}