package utils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
//import javax.vecmath.Vector4f;
import java.util.List;

import org.joml.Vector3f;
//import javax.vecmath.Vector4f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/**
 * Object3D</br>
 * This class defines what a 3D object / object3D shall be
 * @author Shonam
 *
 */
public class Object3D {
	
//	/**
//	 * Object's position and rotation properties
//	 * Not really attributes of the objects themselves
//	 * However in the context of a 3D/or 4D environment, these values can be used
//	 */
	// Object's position in 4D space
	private float x = 0;
	private float y = 0;
	private float z = 0;
//	private float w = 0; // 4-th axis
	// Object's rotational values in 4D space
	private float xy = 0;
	private float xz = 0;
//	private float xw = 0;
	private float yz = 0;
//	private float yw = 0;
//	private float zw = 0;
	
	
	/**
	 * Object's MAIN Properties
	 */
	public int vertices = 0;
	public int faces = 0;
//	public int cells = 0;
	public int edges = 0;
	public List<Vector3f> verticesList = new ArrayList<Vector3f>();
	public List<int[]> facesList = new ArrayList<int[]>();
	/**
	 * TODO faceList should be (or add a new variable) for triangle list
	 */
	/**
	 * NEW 
	 * faces are made from 3-polygons (triangles) as oppose polygons >= 3
	 * </br>
	 * List of Vector3i or 3 integers x,y,z which are the three indices of the vertices from {@code verticesList}, not
	 * to be confused with literal x,y,z coordinate values
	 */
	public int polygonCount = 0;
	public List<Vector3i> polygonList = new ArrayList<Vector3i>();
	
	public Object3D() {
		
	}
	
//	public Object3D(int vertices, int faces, int edges) {
//		this.vertices = vertices;
//		this.faces = faces;
////		this.cells = cells;
//		this.edges = edges;
//	}
	
//	public Object3D(int vertices, int faces, int edges, ArrayList<Vector3f> verticesList) {
//		this.vertices = vertices;
//		this.faces = faces;
////		this.cells = cells;
//		this.edges = edges;
//		this.verticesList = verticesList;
//	}
	
	public Object3D(int vertices, int faces, int edges, ArrayList<Vector3f> verticesList, List<int[]> facesList, int polygonCount, List<Vector3i> polygonList) {
		this.vertices = vertices;
		this.faces = faces;
//		this.cells = cells;
		this.edges = edges;
		this.verticesList = verticesList;
		this.facesList = facesList;
		this.polygonCount = polygonCount;
		this.polygonList = polygonList;
	}
	
//	public Object3D(int vertices, int faces, int edges, List<int[]> facesList) {
//		this.vertices = vertices;
//		this.faces = faces;
////		this.cells = cells;
//		this.edges = edges;
//		this.facesList = facesList;
//	}
	
	/**
	 * 
	 * @return FloatBuffer containing vertex data in order v1x, v1y, v1z ... vnx, vny, vnz
	 */
	public FloatBuffer getVertexBuffer() {
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer verticesB = stack.mallocFloat(this.getPolygonCount() * 3 * 3);
			System.out.println("mem_alloc: " + (this.getPolygonCount() * 3 * 3));
			
			for (Vector3i polygon : this.getPolygonList()) {
				Vector3f v1 = this.getVerticesList().get(polygon.x - 1);
				Vector3f v2 = this.getVerticesList().get(polygon.y - 1);
				Vector3f v3 = this.getVerticesList().get(polygon.z - 1);

				System.out.println("(" + v1.x() + ", " + v1.y() + ", " + v1.z() + ")");
				verticesB.put(v1.x()).put(v1.y()).put(v1.z());

				System.out.println("(" + v2.x() + ", " + v2.y() + ", " + v2.z() + ")");
				verticesB.put(v2.x()).put(v2.y()).put(v2.z());

				System.out.println("(" + v3.x() + ", " + v3.y() + ", " + v3.z() + ")");
				verticesB.put(v3.x()).put(v3.y()).put(v3.z());
				System.out.println("----------");
			}
			verticesB.flip();
			
			return verticesB;
		}
	}
	
	/** OLD */
	public FloatBuffer getVertexBufferOLD() {
		
////		FloatBuffer verticesB = null;
//		
//		try (MemoryStack stack = MemoryStack.stackPush()) {
//			FloatBuffer verticesB = stack.mallocFloat(this.getPolygonCount() * 3 * 3);
//			System.out.println("mem_alloc: " + (this.getPolygonCount() * 3 * 3));
//			
//			for (Vector3i polygon : this.getPolygonList()) {
//				Vector3f v1 = this.getVerticesList().get(polygon.x - 1);
//				Vector3f v2 = this.getVerticesList().get(polygon.y - 1);
//				Vector3f v3 = this.getVerticesList().get(polygon.z - 1);
//
//				System.out.println("(" + v1.x() + ", " + v1.y() + ", " + v1.z() + ")");
//				verticesB.put(v1.x()).put(v1.y()).put(v1.z());
//
//				System.out.println("(" + v2.x() + ", " + v2.y() + ", " + v2.z() + ")");
//				verticesB.put(v2.x()).put(v2.y()).put(v2.z());
//
//				System.out.println("(" + v3.x() + ", " + v3.y() + ", " + v3.z() + ")");
//				verticesB.put(v3.x()).put(v3.y()).put(v3.z());
//				System.out.println("----------");
//			}
//			
//			verticesB.flip();
//			
//			return verticesB;
//		}
////		return verticesB;
		
		FloatBuffer vertices = MemoryStack.stackPush().mallocFloat(this.getPolygonCount() * 3 * 3);
//		FloatBuffer vertices = MemoryUtil.memAllocFloat(this.getPolygonCount() * 3 * 3);
		
		float [] verticesA = new float[this.getPolygonCount() * 3 * 3];
//		3value for vertex, 3value for colour
//		vertices = stack.mallocFloat(this.getPolygonCount() * 3 * 3);

		System.out.println("mem_alloc: " + (this.getPolygonCount() * 3 * 3));
//		float r = 1.0f;
//		float g = 1.0f;
//		float b = 1.0f;

		for (Vector3i polygon : this.getPolygonList()) {
			Vector3f v1 = this.getVerticesList().get(polygon.x - 1);
			Vector3f v2 = this.getVerticesList().get(polygon.y - 1);
			Vector3f v3 = this.getVerticesList().get(polygon.z - 1);

			System.out.println("(" + v1.x() + ", " + v1.y() + ", " + v1.z() + ")");
			vertices.put(v1.x()).put(v1.y()).put(v1.z());
//			vertices.put(r).put(g).put(b);

			System.out.println("(" + v2.x() + ", " + v2.y() + ", " + v2.z() + ")");
			vertices.put(v2.x()).put(v2.y()).put(v2.z());
//			vertices.put(b).put(g).put(r);

			System.out.println("(" + v3.x() + ", " + v3.y() + ", " + v3.z() + ")");
			vertices.put(v3.x()).put(v3.y()).put(v3.z());
//			vertices.put(g).put(b).put(r);
			System.out.println("----------");
			// colour
//			r -= 0.051f;
//			g += 0.051f;
//			b -= 0.051f;
		}
		
		for (int i = 0; i < this.getPolygonCount(); i++) {
			Vector3f v1 = this.getVerticesList().get(this.getPolygonList().get(i).x - 1);
			Vector3f v2 = this.getVerticesList().get(this.getPolygonList().get(i).y - 1);
			Vector3f v3 = this.getVerticesList().get(this.getPolygonList().get(i).z - 1);
			
			verticesA[(i * 9)] = v1.x();
			verticesA[(i * 9) + 1] = v1.y();
			verticesA[(i * 9) + 2] = v1.z();
			
			verticesA[(i * 9) + 3] = v2.x();
			verticesA[(i * 9) + 4] = v2.y();
			verticesA[(i * 9) + 5] = v2.z();
			
			verticesA[(i * 9) + 6] = v3.x();
			verticesA[(i * 9) + 7] = v3.y();
			verticesA[(i * 9) + 8] = v3.z();
		}
		vertices.flip();
//		MemoryStack.stackPop();
		return vertices;
	}
	

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
//	public float getW() {
//		return w;
//	}
//
//	public void setW(float w) {
//		this.w = w;
//	}

	public int getVertices() {
		return vertices;
	}

	public void setVertices(int vertices) {
		this.vertices = vertices;
	}

	public int getFaces() {
		return faces;
	}

	public void setFaces(int faces) {
		this.faces = faces;
	}

//	public int getCells() {
//		return cells;
//	}
//
//	public void setCells(int cells) {
//		this.cells = cells;
//	}

	public int getPolygonCount() {
		return polygonCount;
	}

	public void setPolygonCount(int polygonCount) {
		this.polygonCount = polygonCount;
	}

	public List<Vector3f> getVerticesList() {
		return verticesList;
	}

	public void setVerticesList(List<Vector3f> verticesList) {
		this.verticesList = verticesList;
	}

	public List<int[]> getFacesList() {
		return facesList;
	}

	public void setFacesList(List<int[]> facesList) {
		this.facesList = facesList;
	}
	
	public int getEdges() {
		return edges;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}
	
	public List<Vector3i> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(List<Vector3i> polygonList) {
		this.polygonList = polygonList;
	}

	public float getXY() {
		return xy;
	}

	public void setXY(float xy) {
		this.xy = xy;
	}

	public float getXZ() {
		return xz;
	}

	public void setXZ(float xz) {
		this.xz = xz;
	}

//	public float getXW() {
//		return xw;
//	}
//
//	public void setXW(float xw) {
//		this.xw = xw;
//	}

	public float getYZ() {
		return yz;
	}

	public void setYZ(float yz) {
		this.yz = yz;
	}

//	public float getYW() {
//		return yw;
//	}
//
//	public void setYW(float yw) {
//		this.yw = yw;
//	}
//
//	public float getZW() {
//		return zw;
//	}
//
//	public void setZW(float zw) {
//		this.zw = zw;
//	}

}
