package dev.dhlee.cafekiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.dhlee.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import dev.dhlee.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.dhlee.cafekiosk.spring.domain.product.Product;
import dev.dhlee.cafekiosk.spring.domain.product.ProductRepository;
import dev.dhlee.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}

	public ProductResponse createProduct(ProductCreateRequest createRequest) {
		String latestProductNumber = productRepository.findLatestProductNumber();
		String nextProductNumber = createNextProductNumber(latestProductNumber);

		Product product = createRequest.toEntity(nextProductNumber);
		Product savedProduct = productRepository.save(product);

		return ProductResponse.of(savedProduct);
	}

	private String createNextProductNumber(String latestProductNumber) {
		if (latestProductNumber == null) {
			return "001";
		}

		int nextProductNumber = Integer.valueOf(latestProductNumber) + 1;
		return "%03d".formatted(nextProductNumber);
	}

}
