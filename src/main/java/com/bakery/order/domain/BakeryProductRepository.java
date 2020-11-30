package com.bakery.order.domain;

import java.util.Optional;

public interface BakeryProductRepository {
	Optional<Product> findProduct(String code);

	void save(Product product);
}
