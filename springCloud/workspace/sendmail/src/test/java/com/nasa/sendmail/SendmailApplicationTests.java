package com.nasa.sendmail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
class SendmailApplicationTests {

	@Test
	void contextLoads() {
	}

}

/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SendmailApplication.class)
class SendmailApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("dyc87112@qq.com");
		message.setTo("chenhui@elensdata.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");

		mailSender.send(message);
	}

}
*/