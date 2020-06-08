package utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.junit.Test;

public class FourDtoThreeDTests {

	@Test
	public void testCalcIntersection1() {
		Vector4f v1 = new Vector4f(0, 0, 0, 0);
		Vector4f v2 = new Vector4f(0, 0, 0, 1);
		
		assertEquals(FourDtoThreeD.calc_intersection(v1, v2), new Vector4f(0, 0, 0, 0));
	}
	
	@Test
	public void testCalcIntersection2() {
		Vector4f v1 = new Vector4f(0, 0, 0, 0);
		Vector4f v2 = new Vector4f(0, 0, 0, 1);
		
		assertEquals(FourDtoThreeD.calc_intersection(v2, v1), new Vector4f(0, 0, 0, 0));
	}
	
	@Test
	public void testTriangulatePolygon() {
		int[] face = {1, 2, 3, 4, 5};
		
		List<Vector3i> expecting = new ArrayList<Vector3i>();
		expecting.add(new Vector3i(1, 2, 3));
		expecting.add(new Vector3i(1, 3, 4));
		expecting.add(new Vector3i(1, 4, 5));
		
		assertEquals(FourDtoThreeD.triangulate_polygons(face, 0), expecting);
	}
	
	@Test
	public void testList4f_to_list3f() {
		List<Vector4f> list4f = new ArrayList<Vector4f>();
		list4f.add(new Vector4f());
		list4f.add(new Vector4f());
		list4f.add(new Vector4f());
		
		List<Vector3f> list3f = new ArrayList<Vector3f>();
		list3f.add(new Vector3f());
		list3f.add(new Vector3f());
		list3f.add(new Vector3f());
		
		assertEquals(FourDtoThreeD.list4f_to_list3f(list4f), list3f);
	}
	
	@Test
	public void testvector4f_to_vector3f1() {
		Vector4f vec4 = new Vector4f(1, 2, 3, 4);
		
		Vector3f vec3 = new Vector3f(1, 2, 3);
		
		assertEquals(FourDtoThreeD.vector4f_to_vector3f(vec4), vec3);
	}
	
	@Test
	public void testvector4f_to_vector3f2() {
		Vector4f vec4 = new Vector4f(112, 314, 34, 23);
		
		Vector3f vec3 = new Vector3f(112, 314, 34);
		
		assertEquals(FourDtoThreeD.vector4f_to_vector3f(vec4), vec3);
	}
	
	
	

}
