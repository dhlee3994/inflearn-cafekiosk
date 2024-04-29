package dev.dhlee.cafekiosk.spring.domain.stock;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dev.dhlee.cafekiosk.spring.IntegrationTestSupport;

@Transactional
class StockRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private StockRepository stockRepository;

	@DisplayName("상품번호 리스트로 재고를 조회한다.")
	@Test
	void findAllByProductNumberIn() {
		// given
		Stock stock1 = Stock.create("001", 2);
		Stock stock2 = Stock.create("002", 3);
		stockRepository.saveAll(List.of(stock1, stock2));

		// when
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

		// then
		Assertions.assertThat(stocks).hasSize(2)
			.extracting("productNumber", "quantity")
			.containsExactlyInAnyOrder(
				Tuple.tuple("001", 2),
				Tuple.tuple("002", 3)
			);
	}
}
