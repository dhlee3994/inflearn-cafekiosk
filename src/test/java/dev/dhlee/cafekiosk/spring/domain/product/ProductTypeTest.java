package dev.dhlee.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTypeTest {

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@Test
	void containsStockType() {
		ProductType productType = ProductType.HANDMADE;

		boolean stockType = ProductType.containsStockType(productType);

		assertThat(stockType).isFalse();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
	@ParameterizedTest
	void containsStockTypeWithParameterCsvSource(ProductType productType, boolean expected) {
		// when
		boolean stockType = ProductType.containsStockType(productType);

		// then
		assertThat(stockType).isEqualTo(expected);
	}

	private static Stream<Arguments> provideProductTypeForCheckingStockType() {
		return Stream.of(
			Arguments.of(ProductType.HANDMADE, false),
			Arguments.of(ProductType.BOTTLE, true),
			Arguments.of(ProductType.BAKERY, true)
		);
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@MethodSource("provideProductTypeForCheckingStockType")
	@ParameterizedTest
	void containsStockTypeWithParameterMethodSource(ProductType productType, boolean expected) {
		// when
		boolean stockType = ProductType.containsStockType(productType);

		// then
		assertThat(stockType).isEqualTo(expected);
	}
}
