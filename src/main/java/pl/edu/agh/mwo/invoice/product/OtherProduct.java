package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class OtherProduct extends Product {
	public OtherProduct(String name, BigDecimal price) throws IllegalArgumentException {
		super(name, price, new BigDecimal("0.23"));
		if (name == null ){
			throw new IllegalArgumentException("name cannot be null");
		}
		
		
	}
}
