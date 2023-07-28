package jp.co.sss.shop;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JUnitTest {
	@Test
	public void testSum() {
		Calculator c = new Calculator();
		double result = c.sum(10,50);
		assertEquals(60,result, 0);
	}
	
}
