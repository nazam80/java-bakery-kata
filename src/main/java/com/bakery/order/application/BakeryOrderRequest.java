package com.bakery.order.application;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class BakeryOrderRequest {
 
	
	private final LinkedHashMap<String, Integer> products;

	public BakeryOrderRequest() {
		super();
		this.products = new LinkedHashMap<>();
	}

	public void addProductToOrder(String productCode, int amount) {
		products.put(productCode, amount);		
	}
	
	public Set<Entry<String, Integer>> getProductsFromOrder() {
		return products.entrySet();
	}

}
