package com.bakery.order.application;

import java.util.List;
import java.util.Objects;

public class ProductResponse {

	private final String code;
	private final int amount;
	private final double totalPrice;
	private final List<ProductPackResponse> packs;
	
	public ProductResponse(String code, int amount, double totalPrice, List<ProductPackResponse> packs) {
		this.code = code;
		this.amount = amount;
		this.totalPrice = totalPrice;
		this.packs = packs;
	}

	public String getCode() {
		return code;
	}

	public int getAmount() {
		return amount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public List<ProductPackResponse> getPacks() {
		return packs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, code, packs, totalPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductResponse other = (ProductResponse) obj;
		return amount == other.amount && Objects.equals(code, other.code) && Objects.equals(packs, other.packs)
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice);
	}

	@Override
	public String toString() {
		return String.format("ProductResponse [code=%s, amount=%s, totalPrice=%s, packs=%s]", code, amount, totalPrice,
				packs);
	}
	
}
