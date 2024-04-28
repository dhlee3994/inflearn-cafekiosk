package dev.dhlee.cafekiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.dhlee.cafekiosk.spring.api.service.product.response.ProductResponse;
import dev.dhlee.cafekiosk.spring.domain.product.Product;
import dev.dhlee.cafekiosk.spring.domain.product.ProductRepository;
import dev.dhlee.cafekiosk.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {
		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.collect(Collectors.toList());
	}
}
