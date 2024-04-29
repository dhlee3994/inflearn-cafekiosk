package dev.dhlee.cafekiosk.spring.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.dhlee.cafekiosk.spring.api.ApiResponse;
import dev.dhlee.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import dev.dhlee.cafekiosk.spring.api.service.product.ProductService;
import dev.dhlee.cafekiosk.spring.api.service.product.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ProductController {

	private final ProductService productService;

	@PostMapping("/api/v1/products/new")
	public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest createRequest) {
		return ApiResponse.ok(productService.createProduct(createRequest));
	}

	@GetMapping("/api/v1/products/selling")
	public ApiResponse<List<ProductResponse>> getSellingProducts() {
		return ApiResponse.ok(productService.getSellingProducts());
	}

}
