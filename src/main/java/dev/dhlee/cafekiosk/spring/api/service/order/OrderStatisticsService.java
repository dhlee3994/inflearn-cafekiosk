package dev.dhlee.cafekiosk.spring.api.service.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.dhlee.cafekiosk.spring.api.service.mail.MailService;
import dev.dhlee.cafekiosk.spring.domain.order.Order;
import dev.dhlee.cafekiosk.spring.domain.order.OrderRepository;
import dev.dhlee.cafekiosk.spring.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatisticsService {

	private final OrderRepository orderRepository;
	private final MailService mailService;

	public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
		List<Order> orders = orderRepository.findOrdersBy(
			orderDate.atStartOfDay(),
			orderDate.plusDays(1).atStartOfDay(),
			OrderStatus.PAYMENT_COMPLETED
		);

		int totalAmount = orders.stream()
			.mapToInt(Order::getTotalPrice)
			.sum();

		boolean result = mailService.sendMail(
			"no-reply@cafekiosk.com",
			email,
			"[매출통계] %s".formatted(orderDate),
			"총 매출 합게는 %s원 입니다.".formatted(totalAmount));

		if (!result) {
			throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
		}

		return true;
	}
}
