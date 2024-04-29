package dev.dhlee.cafekiosk.spring.api.service.order.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateServiceRequest {

	private List<String> productNumbers = new ArrayList<>();

	@Builder
	private OrderCreateServiceRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}
}
