package application;

import static org.junit.Assert.*;

import java.nio.FloatBuffer;

import org.junit.Test;
import org.lwjgl.system.MemoryStack;

import utils.Object4D;

public class MemoryManagerTests {

	@Test
	public void testMMSingleton() {
		MemoryManager mm1 = MemoryManager.getInstance();
		MemoryManager mm2 = MemoryManager.getInstance();
		
		assertEquals(mm2, mm2);
	}
	
	@Test
	public void testMMupdateStatus() {
		MemoryManager mm = MemoryManager.getInstance();
		
		assertEquals(mm.MemoryManagerUpdatedStatus(), true);
	}
	
	@Test
	public void testMMGetVertexBuffer() {
		MemoryManager mm = MemoryManager.getInstance();
		
		FloatBuffer fb = MemoryStack.stackPush().mallocFloat(0);
		fb.flip();
		
		assertEquals(mm.getMMVertexBuffer(), fb);
	}
	
	@Test
	public void testMMGetVertexCount() {
		MemoryManager mm = MemoryManager.getInstance();
		
		assertEquals(mm.getMMVertexCount(), 0);
	}
	
	@Test
	public void testMMgetObject() {
		MemoryManager mm = MemoryManager.getInstance();
		
		Object4D o = new Object4D();
		mm.load4DintoMM(o);
		
		
		assertEquals(mm.getObject(0), o);
	}

}
