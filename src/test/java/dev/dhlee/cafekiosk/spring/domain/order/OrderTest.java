package dev.dhlee.cafekiosk.spring.domain.order;

import static dev.dhlee.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static dev.dhlee.cafekiosk.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.dhlee.cafekiosk.spring.domain.product.Product;

class OrderTest {

	@DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
	@Test
	void calculateTotalPrice() {
		// given
		Product product1 = createProduct("001", 1000);
		Product product2 = createProduct("002", 2000);
		Product product3 = createProduct("003", 3000);
		List<Product> products = List.of(product1, product2, product3);

		// when
		Order order = Order.create(products, LocalDateTime.now());

		// then
		assertThat(order.getTotalPrice()).isEqualTo(6000);
	}

	@DisplayName("주문 생성 시 주문 상태는 INIT이다.")
	@Test
	void init() {
		// given
		Product product1 = createProduct("001", 1000);
		Product product2 = createProduct("002", 2000);
		Product product3 = createProduct("003", 3000);
		List<Product> products = List.of(product1, product2, product3);

		// when
		Order order = Order.create(products, LocalDateTime.now());

		// then
		assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
	}

	@DisplayName("주문 생성 시주문 등록 시간을 기록한다.")
	@Test
	void registeredAt() {
		// given
		LocalDateTime registeredAt = LocalDateTime.now();
		Product product1 = createProduct("001", 1000);
		Product product2 = createProduct("002", 2000);
		Product product3 = createProduct("003", 3000);
		List<Product> products = List.of(product1, product2, product3);

		// when
		Order order = Order.create(products, registeredAt);

		// then
		assertThat(order.getRegisteredAt()).isEqualTo(registeredAt);
	}

	private Product createProduct(String productNumber, int price) {
		return Product.builder()
			.type(HANDMADE)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
