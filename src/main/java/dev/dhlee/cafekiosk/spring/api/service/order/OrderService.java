package dev.dhlee.cafekiosk.spring.api.service.order;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.dhlee.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import dev.dhlee.cafekiosk.spring.api.service.order.response.OrderResponse;
import dev.dhlee.cafekiosk.spring.domain.order.Order;
import dev.dhlee.cafekiosk.spring.domain.order.OrderRepository;
import dev.dhlee.cafekiosk.spring.domain.product.Product;
import dev.dhlee.cafekiosk.spring.domain.product.ProductRepository;
import dev.dhlee.cafekiosk.spring.domain.product.ProductType;
import dev.dhlee.cafekiosk.spring.domain.stock.Stock;
import dev.dhlee.cafekiosk.spring.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;

	public OrderResponse createOrder(OrderCreateRequest createRequest, LocalDateTime registeredAt) {
		List<String> productNumbers = createRequest.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		deductStockQuantities(products);

		Order order = Order.create(products, registeredAt);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private void deductStockQuantities(List<Product> products) {
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
		Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();

			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);
		}
	}

	private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(groupingBy(Function.identity(), counting()));
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		return stockRepository.findAllByProductNumberIn(stockProductNumbers)
			.stream()
			.collect(toMap(Stock::getProductNumber, s -> s));
	}

	private static List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.collect(toList());
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		Map<String, Product> productMap = productRepository.findAllByProductNumberIn(productNumbers)
			.stream()
			.collect(toMap(Product::getProductNumber, Function.identity()));

		return productNumbers.stream()
			.map(productMap::get)
			.collect(toList());
	}
}
