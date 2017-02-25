package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<Product, Integer>();
	private int number;
	private static int nextNumber = -30;
	public Invoice(){
		this.number = nextNumber;
		nextNumber +=1;
	}
	
	public static void resetNextNumber() {
		nextNumber=1;
	}
	//
	//LinkedList<Integer> numery = new LinkedList<Integer>();
	
//	static int numer;
//	public Invoice(){
//		System.out.println(numer);
//		int lokalny=0;
//		while(lokalny <= numer){
//		lokalny++;
//		}
//		numer = lokalny;
//		System.out.println(numer);
//	}
//	
//	
//	public int getNumer(){
//		return numer;
//	}
	
	
	//
	
	
	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		products.put(product, quantity);
	}

	public BigDecimal getNetTotal() {
		BigDecimal totalNet = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalNet = totalNet.add(product.getPrice().multiply(quantity));
		}
		return totalNet;
	}

	public BigDecimal getTaxTotal() {
		return getGrossTotal().subtract(getNetTotal());
	}

	public BigDecimal getGrossTotal() {
		BigDecimal totalGross = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
		}
		return totalGross;
	}

	public int getNumber() {
		return number;
		
	}

	public String printedVersion() {
		String print = String.valueOf(number);
		for (Product product : products.keySet()) {
			print += "\n" + product.getName() + products.get(product) + product.getPrice() + product.getClass();
			
		}
		print += "\n" + products.size();
		// TODO Auto-generated method stub
		System.out.println(print);
		return print;
	}
}
