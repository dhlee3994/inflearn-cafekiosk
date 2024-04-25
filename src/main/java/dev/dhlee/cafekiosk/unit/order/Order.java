package dev.dhlee.cafekiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;

import dev.dhlee.cafekiosk.unit.beverages.Beverage;

public record Order(
	LocalDateTime orderDateTime,
	List<Beverage> beverages
) {}
