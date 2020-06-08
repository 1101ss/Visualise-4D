package utils;

import static org.junit.Assert.*;

import java.nio.FloatBuffer;

import org.junit.Test;
import org.lwjgl.system.MemoryStack;

public class Object3DTests {

	@Test
	public void testObject1() {
		Object3D o = new Object3D();
		
		assertEquals(o, new Object3D());
	}
	
	@Test
	public void testgetVertexBuffer() {
		Object3D o = new Object3D();
		
		FloatBuffer fb = MemoryStack.stackPush().mallocFloat(0);
		
		assertEquals(o.getVertexBuffer(), fb);
	}
	
	@Test
	public void testgetVertices() {
		Object3D o = new Object3D();
		
		assertEquals(o.getVertices(), 0);
	}
	
	@Test
	public void testsetVertices() {
		Object3D o = new Object3D();
		o.setVertices(5);
		
		assertEquals(o.getVertices(), 5);
	}
	
	@Test
	public void testgetFaces() {
		Object3D o = new Object3D();
		
		assertEquals(o.getFaces(), 0);
	}
	@Test
	public void testsetFaces() {
		Object3D o = new Object3D();
		o.setFaces(2);
		
		assertEquals(o.getFaces(), 2);
	}

}

