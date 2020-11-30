package com.bakery.order.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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

	private ProductResponse mapProductResponse(Product product, int amount) {
		List<ProductPackResponse> packs = calculateProductPacks(amount, product.getPacksSortedDescByAmount());
		double totalPrice = calculateTotalPriceProduct(packs);

		return new ProductResponse(product.getCode(), amount, totalPrice, packs);
	}

	private double calculateTotalPriceProduct(List<ProductPackResponse> packs) {
		double totalPrice = packs.stream().map(p -> p.getPackAmount() * p.getPackPrice())
				.collect(Collectors.summingDouble(Double::doubleValue));
		return BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	


	private List<ProductPackResponse> calculateProductPacks(int amount, List<ProductPack> productPackList) {
		Deque<ProductPackResponse> combination = new ArrayDeque<>();
		
		calculateProductPacksCombination(combination, 0, amount, productPackList);
		
		return combination.stream().collect(Collectors.toList());
	}

	private void calculateProductPacksCombination(Deque<ProductPackResponse> combination, int index, int amount,
			List<ProductPack> productPackList) {				 
		int remainingAmount = calculateCombinationFromIndex(combination, index, productPackList, amount);
		
		if( remainingAmount > 0 ) {			
			tryOtherCombination(combination, productPackList, remainingAmount);
		}
		
		
	}



	private Integer calculateCombinationFromIndex(Deque<ProductPackResponse> combination, int index,
			List<ProductPack> productPackList, int amount) {
		int remainingAmount = amount;
		for (; index < productPackList.size(); index++) {
			ProductPack pack = productPackList.get(index);
			int packAmount = remainingAmount / pack.getAmount();
			if (packAmount > 0) {
				combination.offer(new ProductPackResponse(packAmount, pack.getAmount(), pack.getPrice()) );		
				remainingAmount = remainingAmount % pack.getAmount();
				if( remainingAmount== 0) {
					break;
				}
			}
		}
		return remainingAmount;
	}
	
	private void tryOtherCombination(Deque<ProductPackResponse> combination, List<ProductPack> productPackList,
			int amount) {
		int remainingAmount = amount;
		if( combination.isEmpty() || combination.size()==1) {
			throw new RuntimeException("Not found");
		}
		//Remove last pack
		ProductPackResponse lastProductPackResponse = combination.pollLast();
		if( isProductPackResponseTheLastProductPackInList(lastProductPackResponse, productPackList) ) {				
			remainingAmount += lastProductPackResponse.getPackAmount() * lastProductPackResponse.getProductPerPackAmount();
			lastProductPackResponse = combination.pollLast(); //Last product response is last - 1 now 				
		}
		//If last pack removed had multiple packs, reduce one pack and insert again 
		if( lastProductPackResponse.getPackAmount() > 1) {
			combination.offer(new ProductPackResponse(lastProductPackResponse.getPackAmount() - 1 , lastProductPackResponse.getProductPerPackAmount(), lastProductPackResponse.getPackPrice()) );
		}
		remainingAmount += lastProductPackResponse.getProductPerPackAmount();						
		int nextIndex = calculateNextIndexForCombination(lastProductPackResponse, productPackList);
		calculateProductPacksCombination(combination, nextIndex, remainingAmount, productPackList);
	}
	
	private boolean isProductPackResponseTheLastProductPackInList(ProductPackResponse productPackResponse, List<ProductPack> productPackList ) {
		return productPackResponse.getProductPerPackAmount() == productPackList.get(productPackList.size()-1).getAmount();
	}
	
	private int calculateNextIndexForCombination(ProductPackResponse productPackResponse, List<ProductPack> productPackList ) {
		int lastProductAmountOffered = productPackResponse.getProductPerPackAmount(); 
		int i = productPackList.size() - 1;
		while( lastProductAmountOffered != productPackList.get(i).getAmount() ) {
			i--;
		}
		return i+1;
	}

}
