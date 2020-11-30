package com.bakery.order.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bakery.order.domain.BakeryProductRepository;
import com.bakery.order.domain.Product;
import com.bakery.order.domain.ProductPack;

public class BakeryOrderProcessor {

	private BakeryProductRepository repo;

	public BakeryOrderProcessor(BakeryProductRepository repo) {		
		this.repo = repo;
	}

	public BakeryOrderResponse process(BakeryOrderRequest order) {
		List<ProductResponse> productResponseList = order.getProductsFromOrder().stream().map(this::processProductEntry)
				.collect(Collectors.toList());
		return new BakeryOrderResponse(productResponseList);
	}

	private ProductResponse processProductEntry(Entry<String, Integer> productEntry) {
		return repo.findProduct(productEntry.getKey()).map( p -> mapProductResponse(p, productEntry.getValue()))
				.orElseThrow(RuntimeException::new);

	}

	private ProductResponse mapProductResponse(Product product, Integer amount) {
		List<ProductPackResponse> packs = calculateProductPacks(amount, product.getPacksSortedDescByAmount());
		double totalPrice = calculateTotalPriceProduct(packs);

		return new ProductResponse(product.getCode(), amount, totalPrice, packs);
	}

	private double calculateTotalPriceProduct(List<ProductPackResponse> packs) {
		double totalPrice = packs.stream().map(p -> p.getPackAmount() * p.getPackPrice())
				.collect(Collectors.summingDouble(Double::doubleValue));
		return BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	


	private List<ProductPackResponse> calculateProductPacks(Integer amount, List<ProductPack> productPackList) {
		List<ProductPackResponse> out = new ArrayList<>();
		int remainingAmount = amount;
		for (ProductPack pack : productPackList) {
			if (remainingAmount >= pack.getAmount()) {
				int packAmount = remainingAmount / pack.getAmount();
				ProductPackResponse productPackResponse = new ProductPackResponse(packAmount, pack.getAmount(),
						pack.getPrice());
				
				out.add(productPackResponse);
				
				remainingAmount = remainingAmount % pack.getAmount();
			}
		}
		return remainingAmount == 0 ? out
				: calculateProductPacks(amount, productPackList.stream().skip(1).collect(Collectors.toList()));
	}

}
