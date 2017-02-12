package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class DairyProduct extends Product {
	public DairyProduct(String name, BigDecimal price) throws IllegalArgumentException {
		super(name, price, new BigDecimal("0.08"));
		if (price == null ){
			throw new IllegalArgumentException("price cannot be null");
		}
		
	}
}
