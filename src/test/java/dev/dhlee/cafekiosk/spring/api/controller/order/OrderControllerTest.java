package dev.dhlee.cafekiosk.spring.api.controller.order;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dev.dhlee.cafekiosk.spring.ControllerTestSupport;
import dev.dhlee.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;

class OrderControllerTest extends ControllerTestSupport {

	@DisplayName("신규 주문을 등록한다.")
	@Test
	void createOrder() throws Exception {

		OrderCreateRequest createRequest = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value("201"))
			.andExpect(jsonPath("$.status").value("CREATED"))
			.andExpect(jsonPath("$.message").value("CREATED"))
			.andDo(print());
	}

	@DisplayName("신규 주문을 등록할 때 상품번호는 1개 이상이어야 한다.")
	@Test
	void createOrderWithEmptyProductNumber() throws Exception {

		OrderCreateRequest createRequest = OrderCreateRequest.builder()
			.productNumbers(List.of())
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
			.andDo(print());
	}

}
