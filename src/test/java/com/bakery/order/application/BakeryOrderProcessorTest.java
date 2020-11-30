package com.bakery.order.application;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.bakery.order.domain.BakeryProductRepository;
import com.bakery.order.domain.Product;
import com.bakery.order.infrastructure.InMemoryBakeryProductRepository;

public class BakeryOrderProcessorTest {

	BakeryOrderProcessor processor;

	public BakeryOrderProcessorTest() {
		BakeryProductRepository repository = new InMemoryBakeryProductRepository();
		Product vegemiteScroll = new Product("VS5", "Vegemite Scroll");
		vegemiteScroll.addPack(3, 6.99);
		vegemiteScroll.addPack(5, 8.99);
		repository.save(vegemiteScroll);

		Product blueberryMuffin = new Product("MB11", "Blueberry Muffin");
		blueberryMuffin.addPack(2, 9.95);
		blueberryMuffin.addPack(5, 16.95);
		blueberryMuffin.addPack(8, 24.95);
		repository.save(blueberryMuffin);

		Product croissant = new Product("CF", "Croissant");
		croissant.addPack(3, 5.95);
		croissant.addPack(5, 9.95);
		croissant.addPack(9, 16.99);
		repository.save(croissant);

		this.processor = new BakeryOrderProcessor(repository);
	}

	@Test
	public void testOrderTenVegemiteScroll() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("VS5", 10);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("VS5", 10, 17.98, List.of(new ProductPackResponse(2, 5, 8.99))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);

		assertEquals(expected, out);
	}

	@Test
	public void testOrderThirteenCroissant() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("CF", 13);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List.of(new ProductResponse("CF", 13, 25.85,
				List.of(new ProductPackResponse(2, 5, 9.95), new ProductPackResponse(1, 3, 5.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderFourteenMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 14);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List.of(new ProductResponse("MB11", 14, 54.8,
				List.of(new ProductPackResponse(1, 8, 24.95), new ProductPackResponse(3, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderTwoMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 2);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("MB11", 2, 9.95, List.of(new ProductPackResponse(1, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderFiveMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 5);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("MB11", 5, 16.95, List.of(new ProductPackResponse(1, 5, 16.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderEightMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 8);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("MB11", 8, 24.95, List.of(new ProductPackResponse(1, 8, 24.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderSevenMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 7);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List.of(new ProductResponse("MB11", 7, 26.9,
				List.of(new ProductPackResponse(1, 5, 16.95), new ProductPackResponse(1, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderTwentySevenMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 27);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("MB11", 27, 96.7, List.of(new ProductPackResponse(2, 8, 24.95),
						new ProductPackResponse(1, 5, 16.95), new ProductPackResponse(3, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderThirtyFiveMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("MB11", 35);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List
				.of(new ProductResponse("MB11", 35, 121.65, List.of(new ProductPackResponse(3, 8, 24.95),
						new ProductPackResponse(1, 5, 16.95), new ProductPackResponse(3, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);
		assertEquals(expected, out);
	}

	@Test
	public void testOrderTenVegemiteScrollThirteenCroissantFourteenMuffin() {
		BakeryOrderRequest order = new BakeryOrderRequest();
		order.addProductToOrder("VS5", 10);
		order.addProductToOrder("CF", 13);
		order.addProductToOrder("MB11", 14);
		BakeryOrderResponse out = processor.process(order);

		List<ProductResponse> productResponseList = List.of(
				new ProductResponse("VS5", 10, 17.98, List.of(new ProductPackResponse(2, 5, 8.99))),
				new ProductResponse("CF", 13, 25.85,
						List.of(new ProductPackResponse(2, 5, 9.95), new ProductPackResponse(1, 3, 5.95))),
				new ProductResponse("MB11", 14, 54.8,
						List.of(new ProductPackResponse(1, 8, 24.95), new ProductPackResponse(3, 2, 9.95))));
		BakeryOrderResponse expected = new BakeryOrderResponse(productResponseList);

		assertEquals(expected, out);
	}

}
