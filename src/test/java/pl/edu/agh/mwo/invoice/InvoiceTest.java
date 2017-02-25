package pl.edu.agh.mwo.invoice;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceTest {
	private static final String PRODUCT_1 = "Product 1";
	private static final String PRODUCT_2 = "Product 2";
	private static final String PRODUCT_3 = "Product 3";

//	@Test
//	public void testHasNumer() {
//		Invoice invoice = createEmptyInvoice();
//		invoice.getNumer();
//	}
	
	@Before
	public void resetNextNumber(){
		Invoice.resetNextNumber();
	}
	
	@Test
	public void testPrintedInvoiceHasNumber(){
		Invoice invoice = createEmptyInvoice();
		String printed = invoice.printedVersion();
		String invoiceNumber = String.valueOf(invoice.getNumber());
		Assert.assertThat(printed, Matchers.containsString(invoiceNumber));
	//	Assert.assertThat(printed, Matchers.startsWith(invoiceNumber);
	}
	
	@Test
	public void testInvoicehasProductName(){
//		Invoice invoice = createEmptyInvoice();
//		DairyProduct product = new DairyProduct("maslo", new BigDecimal("0.23"));
//		invoice.addProduct(product);
//		String printed = invoice.printedVersion();
//		String productName = product.getName();
//		Assert.assertThat(printed, Matchers.containsString(productName));
		
	}
	
	@Test
	public void testInvoicehasProducType(){
		Invoice invoice = createEmptyInvoice();
		DairyProduct product = new DairyProduct("maslo", new BigDecimal("0.23"));
		invoice.addProduct(product);
		String printed = invoice.printedVersion();
		String productType = String.valueOf(product.getClass());
		//System.out.println(productType);
		Assert.assertThat(printed, Matchers.containsString(productType));
		
	}
	
	@Test
	public void testInvoicehasProduPrice(){
		Invoice invoice = createEmptyInvoice();
		DairyProduct product = new DairyProduct("maslo", new BigDecimal("0.23"));
		invoice.addProduct(product);
		String printed = invoice.printedVersion();
		String productPrice = String.valueOf(product.getPrice());
		Assert.assertThat(printed, Matchers.containsString(productPrice));
		
	}
	
	@Test
	public void testInvoicehasProducQuan(){
		Invoice invoice = createEmptyInvoice();
		DairyProduct product = new DairyProduct("maslo", new BigDecimal("0.23"));
		invoice.addProduct(product,123);
		
		String printed = invoice.printedVersion();
		String productQuan = "123";
		Assert.assertThat(printed, Matchers.containsString(productQuan));
		
	}
	
	@Test
	public void testNumberGraterThenZero(){
		Invoice invoice = createEmptyInvoice();
		Assert.assertThat(invoice.getNumber(), Matchers.greaterThan(0) );
		
	}
	@Test
	public void testManyInvDifNum(){
		Invoice invoice1 = createEmptyInvoice();
		Invoice invoice2 = createEmptyInvoice();
		Assert.assertNotEquals(invoice1.getNumber(), invoice2.getNumber());
	}
	@Test
	public void testNextInvSubsNum(){
		Invoice invoice1 = createEmptyInvoice();
		Invoice invoice2 = createEmptyInvoice();
		Assert.assertEquals(1, invoice2.getNumber() - invoice1.getNumber());
	}
	
	
	@Test
	public void testEmptyInvoiceHasEmptySubtotal() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEqual(BigDecimal.ZERO, invoice.getNetTotal());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTaxAmount() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEqual(BigDecimal.ZERO, invoice.getTaxTotal());
	}

	@Test
	public void testEmptyInvoiceHasEmptyTotal() {
		Invoice invoice = createEmptyInvoice();
		assertBigDecimalsAreEqual(BigDecimal.ZERO, invoice.getGrossTotal());
	}

	@Test
	public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 1);
		assertBigDecimalsAreEqual(invoice.getNetTotal(), invoice.getGrossTotal());
	}

	@Test
	public void testInvoiceHasProperSubtotalForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 1);
		invoice.addProduct(createOtherProduct(), 1);
		invoice.addProduct(createDairyProduct(), 1);
		assertBigDecimalsAreEqual("259.99", invoice.getNetTotal());
	}

	@Test
	public void testInvoiceHasProperTaxValueForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 1);
		invoice.addProduct(createOtherProduct(), 1);
		invoice.addProduct(createDairyProduct(), 1);
		assertBigDecimalsAreEqual("12.3", invoice.getTaxTotal());
	}

	@Test
	public void testInvoiceHasProperTotalValueForManyProduct() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct());
		invoice.addProduct(createOtherProduct());
		invoice.addProduct(createDairyProduct());
		assertBigDecimalsAreEqual("272.29", invoice.getGrossTotal());
	}

	@Test
	public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 3); // Subtotal: 599.97
		invoice.addProduct(createOtherProduct(), 2); // Subtotal: 100.00
		invoice.addProduct(createDairyProduct(), 4); // Subtotal: 40.00
		assertBigDecimalsAreEqual("739.97", invoice.getNetTotal());
	}

	@Test
	public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 3); // Total: 599.97
		invoice.addProduct(createOtherProduct(), 2); // Total: 123.00
		invoice.addProduct(createDairyProduct(), 4); // Total: 43.2
		assertBigDecimalsAreEqual("766.17", invoice.getGrossTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithZeroQuantity() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithNegativeQuantity() {
		Invoice invoice = createEmptyInvoice();
		invoice.addProduct(createTaxFreeProduct(), -1);
	}

	private Invoice createEmptyInvoice() {
		return new Invoice();
	}

	private Product createTaxFreeProduct() {
		return new TaxFreeProduct(PRODUCT_1, new BigDecimal("199.99"));
	}

	private Product createOtherProduct() {
		return new OtherProduct(PRODUCT_2, new BigDecimal("50.0"));
	}

	private Product createDairyProduct() {
		return new DairyProduct(PRODUCT_3, new BigDecimal("10.0"));
	}

	private void assertBigDecimalsAreEqual(String expected, BigDecimal actual) {
		assertEquals(new BigDecimal(expected).stripTrailingZeros(), actual.stripTrailingZeros());
	}

	private void assertBigDecimalsAreEqual(BigDecimal expected, BigDecimal actual) {
		assertEquals(expected.stripTrailingZeros(), actual.stripTrailingZeros());
	}

}
