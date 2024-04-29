package dev.dhlee.cafekiosk.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.dhlee.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.dhlee.cafekiosk.spring.api.service.order.OrderService;
import dev.dhlee.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public OrderResponse createOrder(@RequestBody OrderCreateRequest createRequest) {
		LocalDateTime registeredAt = LocalDateTime.now();
		return orderService.createOrder(createRequest, registeredAt);
	}
}
