package com.nasa.sendmail;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

}