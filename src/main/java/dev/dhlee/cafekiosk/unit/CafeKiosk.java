package dev.dhlee.cafekiosk.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.dhlee.cafekiosk.unit.beverages.Beverage;
import dev.dhlee.cafekiosk.unit.order.Order;
import lombok.Getter;

@Getter
public class CafeKiosk {

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public int calculateTotalPrice() {
		return beverages.stream()
			.mapToInt(beverage -> beverage.getPrice())
			.sum();
	}

	public Order createOrder() {
		return new Order(LocalDateTime.now(), beverages);
	}
}