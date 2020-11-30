package com.bakery.order.application;

import java.util.List;
import java.util.Objects;

public class BakeryOrderResponse {

	private final List<ProductResponse> productResponseList;

	
	public BakeryOrderResponse(List<ProductResponse> productResponseList) {		
		this.productResponseList = productResponseList;
	}


	public List<ProductResponse> getProductResponseList() {
		return productResponseList;
	}


	@Override
	public int hashCode() {
		return Objects.hash(productResponseList);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BakeryOrderResponse other = (BakeryOrderResponse) obj;
		return Objects.equals(productResponseList, other.productResponseList);
	}


	@Override
	public String toString() {
		return String.format("BakeryOrderResponse [productResponseList=%s]", productResponseList);
	}
	
	

}
