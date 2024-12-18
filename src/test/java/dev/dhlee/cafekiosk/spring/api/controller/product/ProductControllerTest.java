package dev.dhlee.cafekiosk.spring.api.controller.product;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import dev.dhlee.cafekiosk.spring.ControllerTestSupport;
import dev.dhlee.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import dev.dhlee.cafekiosk.spring.domain.product.ProductSellingStatus;
import dev.dhlee.cafekiosk.spring.domain.product.ProductType;

class ProductControllerTest extends ControllerTestSupport {

	@DisplayName("신규 상품을 등록한다.")
	@Test
	void createProduct() throws Exception {
		// given
		ProductCreateRequest createRequest = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingStatus(ProductSellingStatus.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		// when && then
		mockMvc.perform(post("/api/v1/products/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
	@Test
	void createProductWithoutType() throws Exception {
		// given
		ProductCreateRequest createRequest = ProductCreateRequest.builder()
			.sellingStatus(ProductSellingStatus.SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		// when && then
		mockMvc.perform(post("/api/v1/products/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
			.andDo(print());
	}

	@DisplayName("신규 상품을 등록할 때 판매 상태값은 필수값이다.")
	@Test
	void createProductWithoutSellingStatus() throws Exception {
		// given
		ProductCreateRequest createRequest = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.name("아메리카노")
			.price(4000)
			.build();

		// when && then
		mockMvc.perform(post("/api/v1/products/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
			.andDo(print());
	}

	@DisplayName("신규 상품을 등록할 때 상품 이름은 필수값이다.")
	@Test
	void createProductWithoutName() throws Exception {
		// given
		ProductCreateRequest createRequest = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingStatus(ProductSellingStatus.SELLING)
			.price(4000)
			.build();

		// when && then
		mockMvc.perform(post("/api/v1/products/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
			.andDo(print());
	}

	@DisplayName("신규 상품을 등록할 때 가격은 양수여야 한다.")
	@Test
	void createProductWithZeroPrice() throws Exception {
		// given
		ProductCreateRequest createRequest = ProductCreateRequest.builder()
			.type(ProductType.HANDMADE)
			.sellingStatus(ProductSellingStatus.SELLING)
			.name("아메리카노")
			.price(0)
			.build();

		// when && then
		mockMvc.perform(post("/api/v1/products/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(createRequest)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.status").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
			.andExpect(jsonPath("$.data").isEmpty())
			.andDo(print());
	}

	@DisplayName("판매 상품을 조회한다.")
	@Test
	void getSellingProducts() throws Exception {

		when(productService.getSellingProducts()).thenReturn(List.of());

		// when && then
		mockMvc.perform(get("/api/v1/products/selling"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andExpect(jsonPath("$.data").isArray())
			.andDo(print());
	}
}
