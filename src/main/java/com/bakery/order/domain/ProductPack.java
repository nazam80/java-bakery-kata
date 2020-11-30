package com.bakery.order.domain;

public class ProductPack {
	private final Product product;
	private final Integer amount;
	private final Double price;
	
	
	public ProductPack(Product product, Integer amount, Double price) {
		super();
		this.product = product;
		this.amount = amount;
		this.price = price;		
	}


	public Product getProduct() {
		return product;
	}


	public Integer getAmount() {
		return amount;
	}


	public Double getPrice() {
		return price;
	}

	
}
