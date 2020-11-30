package com.bakery.order.application;

import java.util.Objects;

public class ProductPackResponse {

	private final int packAmount;
	private final int productPerPackAmount;
	private final double packPrice;
	
	public ProductPackResponse(int packAmount, int productPerPackAmount, double packPrice) {
		this.packAmount = packAmount;
		this.productPerPackAmount = productPerPackAmount;
		this.packPrice = packPrice;
	}

	public int getPackAmount() {
		return packAmount;
	}

	public int getProductPerPackAmount() {
		return productPerPackAmount;
	}

	public double getPackPrice() {
		return packPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(packAmount, packPrice, productPerPackAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPackResponse other = (ProductPackResponse) obj;
		return packAmount == other.packAmount
				&& Double.doubleToLongBits(packPrice) == Double.doubleToLongBits(other.packPrice)
				&& productPerPackAmount == other.productPerPackAmount;
	}

	@Override
	public String toString() {
		return String.format("ProductPackResponse [packAmount=%s, productPerPackAmount=%s, packPrice=%s]", packAmount,
				productPerPackAmount, packPrice);
	}

}