package dev.dhlee.cafekiosk.spring.api.service.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.dhlee.cafekiosk.spring.client.MailSendClient;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistory;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Spy
	private MailSendClient mailSendClient;

	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;

	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void sendMail() {
		// given
		//MailSendClient mailSendClient = mock(MailSendClient.class);
		//MailSendHistoryRepository mailSendHistoryRepository = mock(MailSendHistoryRepository.class);

		//MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

		// stubbing
		// when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
		// 	.thenReturn(true);

		// spy
		doReturn(true)
			.when(mailSendClient)
			.sendEmail(anyString(), anyString(), anyString(), anyString());

		// when
		boolean result = mailService.sendMail("", "", "", "");

		// then
		assertThat(result).isTrue();
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
	}
}
