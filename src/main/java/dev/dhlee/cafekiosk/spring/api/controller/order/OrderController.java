package dev.dhlee.cafekiosk.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.dhlee.cafekiosk.spring.api.ApiResponse;
import dev.dhlee.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.dhlee.cafekiosk.spring.api.service.order.OrderService;
import dev.dhlee.cafekiosk.spring.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

	private final OrderService orderService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/orders/new")
	public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest createRequest) {
		LocalDateTime registeredAt = LocalDateTime.now();
		return ApiResponse.of(HttpStatus.CREATED,
			orderService.createOrder(createRequest.toServiceRequest(), registeredAt));
	}
}
