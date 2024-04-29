package dev.dhlee.cafekiosk.spring.api.service.order;

import static dev.dhlee.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dev.dhlee.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.dhlee.cafekiosk.spring.client.MailSendClient;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistory;
import dev.dhlee.cafekiosk.spring.domain.history.MailSendHistoryRepository;
import dev.dhlee.cafekiosk.spring.domain.order.Order;
import dev.dhlee.cafekiosk.spring.domain.order.OrderRepository;
import dev.dhlee.cafekiosk.spring.domain.order.OrderStatus;
import dev.dhlee.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import dev.dhlee.cafekiosk.spring.domain.product.Product;
import dev.dhlee.cafekiosk.spring.domain.product.ProductRepository;
import dev.dhlee.cafekiosk.spring.domain.product.ProductType;

@SpringBootTest
class OrderStatisticsServiceTest {

	@Autowired
	private OrderStatisticsService orderStatisticsService;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;

	@MockBean
	private MailSendClient mailSendClient;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendHistoryRepository.deleteAllInBatch();
	}

	@DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
	@Test
	void sendOrderStatisticsMail() {
		// given
		LocalDateTime now = LocalDateTime.of(2024, 4, 29, 0, 0);

		Product product1 = createProduct(HANDMADE, "001", 1000);
		Product product2 = createProduct(HANDMADE, "002", 2000);
		Product product3 = createProduct(HANDMADE, "003", 3000);
		List<Product> products = List.of(product1, product2, product3);
		productRepository.saveAll(products);

		Order order1 = createPaymentCompletedOrder(products, now.minusSeconds(1));
		Order order2 = createPaymentCompletedOrder(products, now);
		Order order3 = createPaymentCompletedOrder(products, now.plusDays(1).minusSeconds(1));
		Order order4 = createPaymentCompletedOrder(products, now.plusDays(1));

		// stubbing
		when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class),
			any(String.class))).thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2024, 4, 29), "test@test.com");

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
		assertThat(histories).hasSize(1)
			.extracting("content")
			.contains("총 매출 합계는 12000원 입니다.");
	}

	private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
		Order order = Order.builder()
			.products(products)
			.orderStatus(OrderStatus.PAYMENT_COMPLETED)
			.registeredAt(now)
			.build();

		return orderRepository.save(order);
	}

	private Product createProduct(ProductType type, String productNumber, int price) {
		return Product.builder()
			.type(type)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
