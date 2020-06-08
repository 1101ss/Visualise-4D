package renderEngine;

import static org.junit.Assert.*;

import java.nio.FloatBuffer;

import org.junit.Test;
import org.lwjgl.system.MemoryStack;

public class ObjectColourTests {

	@Test
	public void testsetVertices() {
		
		FloatBuffer fb;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			fb = stack.mallocFloat(10);
			for (int i = 0; i < 10; i++)
				fb.put(0.5f);
		}
		
		fb.flip();
		MemoryStack.stackPop();
		
		assertEquals(ObjectColour.genOneColour(10, 0.5f, 0.5f, 0.5f), fb);
	}

}
