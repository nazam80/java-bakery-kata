package com.bakery.order.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Product {
	private final String code;
	private final String name;
	private final List<ProductPack> packs;

	public Product(String code, String name) {
		super();
		this.code = code;
		this.name = name;
		this.packs = new ArrayList<>();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public void addPack(Integer amount, Double price) {
		packs.add(new ProductPack(this, amount, price));
	}
	
	public List<ProductPack> getPacks() {
		return packs;
	}


	public List<ProductPack> getPacksSortedDescByAmount() { 
		return packs.stream().sorted( Comparator.comparingInt(ProductPack::getAmount).reversed() ).collect(Collectors.toList());
	}
	


}
