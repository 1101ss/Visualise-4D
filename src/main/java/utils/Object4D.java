package utils;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import org.joml.Matrix4fc;
//import javax.vecmath.Vector4f;
import org.joml.Vector4f;
import org.joml.Vector4fc;


/**
 * Object4D</br>
 * This class defines what a 4D object / object4D shall be
 * @author Shonam
 *
 */
public class Object4D {
	
	/**
	 * Object's position and rotation properties
	 * Not really attributes of the objects themselves
	 * However in the context of a 3D/or 4D environment, these values can be used
	 */
	// Object's position in 4D space
	private float x = 0;
	private float y = 0;
	private float z = 0;
	private float w = 0; // 4-th axis
	// Object's rotational values in 4D space
	private float xy = 0;
	private float xz = 0;
	private float xw = 0;
	private float yz = 0;
	private float yw = 0;
	private float zw = 0;
	
	
	/**
	 * Object's MAIN Properties
	 */
	public int vertices = 0;
	public int faces = 0;
	public int cells = 0;
	public int edges = 0;
	// Vector4f(x, y, z, w) coordinate
	public List<Vector4f> verticesList = new ArrayList<Vector4f>();
	// indices at which the vertex is located in the verticesList
	public List<int[]> facesList = new ArrayList<int[]>();
	// indices for the faces that make up a cell
	public List<int[]> cellList = new ArrayList<int[]>();
	
	
	/**
	 * the vertex list from @verticesList with all the rotation and positions transformation applied to every vertex
	 */
	public List<Vector4f> appliedVerticesList;// = new ArrayList<Vector4f>();
	
	public Object4D() {
		
	}
	
	public Object4D(int vertices, int faces, int cells, int edges) {
		this.vertices = vertices;
		this.faces = faces;
		this.cells = cells;
		this.edges = edges;
	}
	
//	public Object4D(int vertices, int faces, int cells, int edges, ArrayList<Vector4f> verticesList) {
//		this.vertices = vertices;
//		this.faces = faces;
//		this.cells = cells;
//		this.edges = edges;
//		this.verticesList = verticesList;
//	}
	
	public Object4D(int vertices, int faces, int cells, int edges, List<Vector4f> verticesList, List<int[]> facesList, List<int[]> cellList) {
		this.vertices = vertices;
		this.faces = faces;
		this.cells = cells;
		this.edges = edges;
		this.verticesList = verticesList;
		this.facesList = facesList;
		this.cellList = cellList;
	}
	
//	public Object4D(int vertices, int faces, int cells, int edges, List<int[]> facesList) {
//		this.vertices = vertices;
//		this.faces = faces;
//		this.cells = cells;
//		this.edges = edges;
//		this.facesList = facesList;
//	}

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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

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

	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}

	public int getEdges() {
		return edges;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}

	public List<Vector4f> getVerticesList() {
		return verticesList;
	}
	
	
	
	/**
	 * Rotates a vertex with by all the 4D possible rotations from the object4d classes rotations
	 * @param vertex the vertex to which to apply 4d rotations to
	 * @return the vertex with the applied changes
	 */
	private Vector4f applyRotation(Vector4f vertex) {
		Vector4f returnVector = new Vector4f(vertex);
		
		float xy_rad = (float) Math.toRadians(xy);
		float xz_rad = (float) Math.toRadians(xz);
		float xw_rad = (float) Math.toRadians(xw);
		float yz_rad = (float) Math.toRadians(yz);
		float yw_rad = (float) Math.toRadians(yw);
		float zw_rad = (float) Math.toRadians(zw);
		
		/**
		 * x-y Rotations
		 * |------------------------------------|
		 * |	cos(A)	-sin(A)		0		0	|
		 * |	sin(A)	cos(A)		0		0	|
		 * |	0		0			1		0	|	
		 * |	0		0			0		1	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				(float) Math.cos(xy_rad), (float) -Math.sin(xy_rad), 0.0f, 0.0f,
				(float) Math.sin(xy_rad), (float) Math.cos(xy_rad), 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f));
		
		/**
		 * x-z Rotations
		 * |------------------------------------|
		 * |	cos(A)	0		-sin(A)		0	|
		 * |	0		1		0			0	|
		 * |	sin(A)	0		cos(A)		0	|	
		 * |	0		0		0			1	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				(float) Math.cos(xz_rad), 0.0f, (float) -Math.sin(xz_rad), 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				(float) Math.sin(xz_rad), 0.0f, (float) Math.cos(xz_rad), 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
				));
		
		/**
		 * x-w Rotations
		 * |------------------------------------|
		 * |	cos(A)	0		0		-sin(A)	|
		 * |	0		1		0		0		|
		 * |	0		0		1		0		|
		 * |	sin(A)	0		0		cos(A)	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				(float) Math.cos(xw_rad), 0.0f, 0.0f, (float) -Math.sin(xw_rad),
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				(float) Math.sin(xw_rad), 0.0f, 0.0f, (float) Math.cos(xw_rad)
				));
		
		/**
		 * y-z Rotations
		 * |------------------------------------|
		 * |	1		0		0			0	|
		 * |	0		cos(A)	-sin(A)		0	|
		 * |	0		sin(A)	cos(A)		0	|
		 * |	0		0		0			1	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, (float) Math.cos(yz_rad), (float) -Math.sin(yz_rad), 0.0f,
				0.0f, (float) Math.sin(yz_rad), (float) Math.cos(yz_rad), 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
				));
		
		/**
		 * y-w Rotations
		 * |------------------------------------|
		 * |	1		0		0		0		|
		 * |	0		cos(A)	0		-sin(A)	|
		 * |	0		0		1		0		|
		 * |	0		sin(A)	0		cos(A)	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, (float) Math.cos(yw_rad), 0.0f, (float) -Math.sin(yw_rad),
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, (float) Math.sin(yw_rad), 0.0f, (float) Math.cos(yw_rad)
				));
		
		/**
		 * z-w Rotations
		 * |------------------------------------|
		 * |	1		0		0		0		|
		 * |	0		1		0		0		|
		 * |	0		0		cos(A)	-sin(A)	|
		 * |	0		0		sin(A)	cos(A)	|
		 * |------------------------------------|
		 */
		returnVector.mul(new Matrix4f(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, (float) Math.cos(zw_rad), (float) -Math.sin(zw_rad),
				0.0f, 0.0f, (float) Math.sin(zw_rad), (float) Math.cos(zw_rad)
				));
		
		return returnVector;
	}
	
	
	/**
	 * Translates a vertex by the position of the objects position
	 * @param vertex the vertex to which to apply 4d translations to
	 * @return the vertex with the applied changes
	 */
	private Vector4f applyTranslation(Vector4f vertex) {
		Vector4f returnVector = new Vector4f(vertex.x + this.x,
				vertex.y + this.y,
				vertex.z + this.z,
				vertex.w + this.w);
		
//		System.out.println("vertex_before: " + vertex.toString());
//		vertex.set(vertex.x + this.x, 
//				vertex.y + this.y, 
//				vertex.z + this.z, 
//				vertex.w + this.w);
//		System.out.println("vertex_after:  " + vertex.toString());
		
		return returnVector;
	}
	
//	/**
//	 * Translates a vertex by the position of the objects position
//	 * @param vertex the vertex to which to apply 4d translations to
//	 * @return the vertex with the applied changes
//	 */
//	private static Vector4f applyTranslationV2(Vector4f vertex, float x, float y, float z, float w) {
////		System.out.println("vertex_before: " + vertex.toString());
//		vertex.set(vertex.x + x, 
//				vertex.y + y, 
//				vertex.z + z, 
//				vertex.w + w);
////		System.out.println("vertex_after:  " + vertex.toString());
//		
//		return vertex;
//	}
	
	public void setVerticesList_compute_AppliedTransformation() {
		appliedVerticesList = new ArrayList<Vector4f>();
		
		// for each vertex, apply the rotations
		for (Vector4f vertex: this.getVerticesList()) {
			
			// rotates > translates > adds to new list
//			appliedVerticesList.add(applyTranslationV2(applyRotation(vertex), getX(), getY(), getZ(), getW()));
//			appliedVerticesList.add(applyTranslationV2(vertex, getX(), getY(), getZ(), getW()));
			
			appliedVerticesList.add(applyTranslation(applyRotation(vertex)));
		}
	}
	
	
	/**
	 * 
	 * @return the list of vertex with position and rotation transformations applied to every vertex
	 */
	public List<Vector4f> getVerticesList_AppliedTransformation() {
		return this.appliedVerticesList;
	}

	
	
	public void setVerticesList(ArrayList<Vector4f> verticesList) {
		this.verticesList = verticesList;
	}
	
	public List<int[]> getFacesList() {
		return facesList;
	}

	public void setFacesList(List<int[]> facesList) {
		this.facesList = facesList;
	}
	
	public List<int[]> getCellList() {
		return cellList;
	}

	public void setCellList(List<int[]> cellList) {
		this.cellList = cellList;
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

	public float getXW() {
		return xw;
	}

	public void setXW(float xw) {
		this.xw = xw;
	}

	public float getYZ() {
		return yz;
	}

	public void setYZ(float yz) {
		this.yz = yz;
	}

	public float getYW() {
		return yw;
	}

	public void setYW(float yw) {
		this.yw = yw;
	}

	public float getZW() {
		return zw;
	}

	public void setZW(float zw) {
		this.zw = zw;
	}

}
