package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class TaxFreeProduct extends Product {
	public TaxFreeProduct(String name, BigDecimal price) throws IllegalArgumentException {
		super(name, price, BigDecimal.ZERO);
		
		if (name == "" ){
			throw new IllegalArgumentException("name cannot be empty");
		}
		if (price.signum() < 0 ){
			throw new IllegalArgumentException("price cannot be less than 0");
		}
	}
}
