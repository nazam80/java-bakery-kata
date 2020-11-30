package com.bakery.order.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.bakery.order.domain.BakeryProductRepository;
import com.bakery.order.domain.Product;

public class InMemoryBakeryProductRepository implements BakeryProductRepository {

	private Map<String, Product> products = new HashMap<>();

    public InMemoryBakeryProductRepository() {
    	 this.products = new HashMap<>(); 
	}


	@Override
    public void save(Product product) {
    	products.put(product.getCode(), product);
    }

 	@Override
	public Optional<Product> findProduct(String code) {
		 return Optional.ofNullable(products.get(code));
	}

}
