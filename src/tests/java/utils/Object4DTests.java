package utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class Object4DTests {

	@Test
	public void testObject1() {
		Object4D o = new Object4D();
		
		assertEquals(o, new Object4D());
	}
	
	@Test
	public void testgetCellList() {
		Object4D o = new Object4D();
		
		assertEquals(o.getCellList(), null);
	}
	
	@Test
	public void testgetVertices() {
		Object4D o = new Object4D();
		
		assertEquals(o.getVertices(), 0);
	}
	
	@Test
	public void testsetVertices() {
		Object4D o = new Object4D();
		o.setVertices(5);
		
		assertEquals(o.getVertices(), 5);
	}
	
	@Test
	public void testgetFaces() {
		Object4D o = new Object4D();
		
		assertEquals(o.getFaces(), 0);
	}
	@Test
	public void testsetFaces() {
		Object4D o = new Object4D();
		o.setFaces(2);
		
		assertEquals(o.getFaces(), 2);
	}

}
