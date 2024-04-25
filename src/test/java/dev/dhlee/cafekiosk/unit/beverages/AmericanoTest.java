package dev.dhlee.cafekiosk.unit.beverages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AmericanoTest {

	@Test
	void getName() {
		Americano americano = new Americano();

		assertEquals(americano.getName(), "아메리카노");
		assertThat(americano.getName()).isEqualTo("아메리카노");
	}

	@Test
	void getPrice() {
		Americano americano = new Americano();

		assertEquals(americano.getPrice(), 4000);
		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}
