package utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.junit.Test;

public class Object4DLoaderTests {

	@Test
	public void testFindOrigin() {
		List<Vector4f> v = new ArrayList<Vector4f>();
		v.add(new Vector4f(0, 0, 0, 0));
		v.add(new Vector4f(0, 1, 0, 0));
		v.add(new Vector4f(1, 0, 0, 0));
		v.add(new Vector4f(1, 1, 0, 0));

		assertEquals(Object4DLoader.findCurrentOrigin(v), new Vector4f(0.5f, 0.5f, 0, 0));
	}
	
	@Test
	public void testCentraliseVertexList() {
		List<Vector4f> v = new ArrayList<Vector4f>();
		v.add(new Vector4f(0, 0, 0, 0));
		v.add(new Vector4f(0, 1, 0, 0));
		v.add(new Vector4f(1, 0, 0, 0));
		v.add(new Vector4f(1, 1, 0, 0));
		
		Vector4f origin = new Vector4f(0.5f, 0.5f, 0, 0);
		
		List<Vector4f> va = new ArrayList<Vector4f>();
		v.add(new Vector4f(-0.5f, -0.5f, 0, 0));
		v.add(new Vector4f(-0.5f, 0.5f, 0, 0));
		v.add(new Vector4f(0.5f, -0.5f, 0, 0));
		v.add(new Vector4f(0.5f, 0.5f, 0, 0));

		assertEquals(Object4DLoader.centeraliseVertexList(v, origin), va);
	}

}
