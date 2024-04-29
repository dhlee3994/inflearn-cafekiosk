package dev.dhlee.cafekiosk.spring.api.controller.order.request;

import java.util.ArrayList;
import java.util.List;

import dev.dhlee.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderCreateRequest {

	@NotEmpty(message = "상품 번호 리스트는 필수입니다.")
	private List<String> productNumbers = new ArrayList<>();

	@Builder
	private OrderCreateRequest(List<String> productNumbers) {
		this.productNumbers = productNumbers;
	}

	public OrderCreateServiceRequest toServiceRequest() {
		return OrderCreateServiceRequest.builder()
			.productNumbers(productNumbers)
			.build();
	}

}
