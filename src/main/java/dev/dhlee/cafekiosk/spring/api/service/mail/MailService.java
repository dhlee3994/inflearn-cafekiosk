package dev.dhlee.cafekiosk.spring.api.service.mail;

import org.springframework.stereotype.Service;

import dev.dhlee.cafekiosk.spring.client.MailSendClient;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistory;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

		boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
				.fromEmail(fromEmail)
				.toEmail(toEmail)
				.subject(subject)
				.content(content)
				.build());

			mailSendClient.a();
			mailSendClient.b();
			mailSendClient.c();
		}

		return result;
	}

}
