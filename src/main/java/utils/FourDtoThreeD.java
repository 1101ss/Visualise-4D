package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.GeometryUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

/**
 * 4D to 3D class</br>
 * This class converts an {@link Object4D} to a {@link Object3D} with triangle polygons.</br>
 * Faces with more then three vertices will be separated into multiple, 3-vertex polygons as this is what OpenLG
 * uses.
 * 
 * @author Shonam
 *
 */
public class FourDtoThreeD {
	
//	private static Object3D final_3D_representation_of_4D_shape;// = new Object3D();
	
	public FourDtoThreeD() {
		
	}
	
	/**
	 * NEW PRE VIVA
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector4f calc_intersection(Vector4f v1, Vector4f v2) {
		Vector4f rtn = new Vector4f();
		
		// Ratio = -v1 (dotProd) vw / (v2 - v1) (dotProd) vw
		// where vw = (0,0,0,1), hence we only use w
//		System.out.println("numerator: " +  (0 - v1.w()));
//		System.out.println("denom: " +  (v2.w() - v1.w()));
		float ratio = (0 - v1.w()) / (v2.w() - v1.w());
		
		// Intersection = v1 + (v2 - v1)*ratio
		v2.sub(v1, rtn);
		rtn.mul(ratio, rtn);
		v1.add(rtn, rtn);
		
		return rtn;
	}
	
	
	/**
	 * Calculates the intersection between two 4D vertex and the xyz hyperplane where w = 0
	 * @param v1 4D Vector 1
	 * @param v2 4D Vector 2
	 * @return 4D Vector at which the intersection occurs
	 */
	public static Vector4f calc_intersectionOLD(Vector4f v1, Vector4f v2) {
		
		// Ratio = -v1 (dotProd) vw / (v2 - v1) (dotProd) vw
		// where vw = (0,0,0,1), hence we only use w
		float ratio = (0 - v1.w()) / (v2.w() - v1.w());
		
		// Intersection = v1 + (v2 - v1)*ratio
		return v1.add(v1.sub(v1).mul(ratio));
	}
	
	
	/**
	 * Converts an array of indices into an array of tuple indices which identify the 3 indices that make a polygon
	 * @param face list of indices that altogether make up a face
	 * @return tuple of indices that make up individual polygons
	 */
	public static List<Vector3i> triangulate_polygonsOLD(int[] face) {
		List<Vector3i> returnList = new ArrayList<Vector3i>();
		
		if (face.length > 3) {
			for(int i = 0; i < face.length - 2; i++) {
				returnList.add(new Vector3i(face[0], 
						face[i + 1], 
						face[i + 2]));
			}
		} else {
			returnList.add(new Vector3i(face[0], 
					face[1], 
					face[2]));
		}
		
		System.out.println("triangulated face indices for: " + Arrays.toString(face));
		for (Vector3i vec3i: returnList)
			System.out.println(vec3i.toString());
		
		return returnList;
	}
	
	
	/**
	 * Converts an array of indices into an array of tuple indices which identify the 3 indices that make a polygon
	 * @param face list of indices that altogether make up a face
	 * @param offet positive offet that need to be added to indices if need be
	 * @return tuple of indices that make up individual polygons
	 */
	public static List<Vector3i> triangulate_polygons(int[] face, int offset) {
		List<Vector3i> returnList = new ArrayList<Vector3i>();
		
		if (face.length > 3) {
			for(int i = 0; i < face.length - 2; i++) {
				returnList.add(new Vector3i(face[0] + offset, 
						face[i + 1] + offset, 
						face[i + 2] + offset));
			}
		} else {
			returnList.add(new Vector3i(face[0] + offset, 
					face[1] + offset, 
					face[2] + offset));
		}
		
//		System.out.println("triangulated face indices for: " + Arrays.toString(face) + "; offset: " + offset);
//		for (Vector3i vec3i: returnList)
//			System.out.println(vec3i.toString());
		
		return returnList;
	}
	
	
	/**
	 * Converts a List<Vector4f> to List<Vector3f>
	 * @param list4f List of Vector4f's
	 * @return List<Vector4f>
	 */
	public static List<Vector3f> list4f_to_list3f(List<Vector4f> list4f) {
		List<Vector3f> returnList = new ArrayList<Vector3f>();
//		System.out.println("new vector3f list");
		for (Vector4f vec4f: list4f) {
			returnList.add(vector4f_to_vector3f(vec4f));
//			System.out.println(vector4f_to_vector3f(vec4f).toString());
		}
		return returnList;
	}
	
	
	/**
	 * Converts Vector4f to Vector3f by dropping the z value
	 * @param Vector4f
	 * @return Vector3f
	 */
	public static Vector3f vector4f_to_vector3f(Vector4f vec4) {
		return new Vector3f(vec4.x(), vec4.y(), vec4.z());
	}
	
	
	/**
	 * checks weather a face is usable for rendering, i.e. does it contain 3 or more vertex in the xyz hyperplane
	 * @param faceIndices indices at which the vertex are located in the vertexList
	 * @param verticesList the full list of vertex for a given object
	 * @Param wPosition the visible plane
	 * @return true if visualisable with 3 or more vertex, false if not
	 */
	public static boolean visualisable(int[] faceIndices, List<Vector4f> verticesList, float wPosition) {
		int number_of_visable_vertex = 0;
		
		for (int i: faceIndices) {
			if (verticesList.get(i - 1).w() == wPosition)
				number_of_visable_vertex++;
		}
		
		return (number_of_visable_vertex >= 3) ? true : false;
	}
	
	public static boolean visualisableV2(int[] faceIndices, List<Vector4f> verticesList, float wPosition) {
		int n = faceIndices.length;
		int number_of_renderable_vertex = 0;
		
		for (int i = 0; i < faceIndices.length; i++) {
			Vector4f v1 = verticesList.get(faceIndices[i % n] - 1);
			Vector4f v2 = verticesList.get(faceIndices[(i + 1) % n] - 1);
			
			if (v1.w() == wPosition)
				number_of_renderable_vertex++;
			if (v1.w() < wPosition && v2.w() > wPosition)
				number_of_renderable_vertex++;
			
		}
//		System.out.println("_visualisable: " + number_of_renderable_vertex);
		return (number_of_renderable_vertex >= 3) ? true : false;
	}
	
	
	/**
	 * Cross Product Magnitude
	 * @param current
	 * @param next
	 * @param compareing
	 * @return
	 */
	public static float cross_product_magnitudeOLD(Vector4f current, Vector4f next, Vector4f compareing) {
		Vector4f current_to_next = next.sub(current);
		Vector4f current_to_comparing = compareing.sub(current);
		
		/**
		 * Cross Product of current_to_next and current_to_comparing
		 */
		Vector3f cross_product = new Vector3f(current_to_next.x(), current_to_next.y(), current_to_next.z())
				.cross(current_to_comparing.x(), current_to_comparing.y(), current_to_comparing.z()); 
		
		/**
		 * Return Cross Product Magnitude
		 */
		float cp_magnitude = new Vector3f(current.x(), current.y(), current.z()).dot(cross_product);
		
		return cp_magnitude;
	}
	
	
	/**
	 * NEW PRE VIVA
	 * @param center
	 * @param vertex1
	 * @param vertex2
	 * @param normal
	 * @return
	 */
	public static float cross_product_magnitude_v4(Vector4f center, Vector4f vertex1, Vector4f vertex2, Vector3f normal) {
		Vector4f c_v1 = new Vector4f();
		vertex1.sub(center, c_v1);
		Vector4f c_v2 = new Vector4f();
		vertex2.sub(center, c_v2);
		
		Vector3f c_v1_3f = new Vector3f(c_v1.x(), c_v1.y(), c_v1.z());
		Vector3f c_v2_3f = new Vector3f(c_v2.x(), c_v2.y(), c_v2.z());
		
		Vector3f cross_product = new Vector3f();
		c_v1_3f.cross(c_v2_3f, cross_product);
		
		return normal.dot(cross_product);
	}
	
	
	/**
	 * NEW PRE VIVA
	 * @param center
	 * @param vertex1
	 * @param vertex2
	 * @return
	 */
	public static double AngleIn3D(Vector4f center, Vector4f vertex1, Vector4f vertex2) {
		Vector4f c_v1 = new Vector4f();
		vertex1.sub(center, c_v1);
		Vector4f c_v2 = new Vector4f();
		vertex2.sub(center, c_v2);
		Vector4f v1_v2 = new Vector4f();
		vertex2.sub(vertex1, v1_v2);
		
//		System.out.println((c_v1.lengthSquared() + c_v2.lengthSquared() - v1_v2.lengthSquared()) / (2 * c_v1.length() * c_v2.length()) + 0.000001);
		double angle_rad = Math.acos((c_v1.lengthSquared() + c_v2.lengthSquared() - v1_v2.lengthSquared()) / (2 * c_v1.length() * c_v2.length()) + 0.000001d);		
		
		return Math.toDegrees(angle_rad);
	}
	
	
	/**
	 * 
	 * @param center
	 * @param vertex1
	 * @param vertex2
	 * @return angle in degrees
	 */
	public static double AngleIn3DOLD(Vector4f center, Vector4f vertex1, Vector4f vertex2) {
		Vector4f center_vector1 = vertex1.sub(center);
		Vector4f center_vector2 = vertex2.sub(center);
		Vector4f vector1_vector2 = vertex2.sub(vertex1);
		
		double angle = Math.acos((center_vector1.lengthSquared() + vector1_vector2.lengthSquared() - center_vector2.lengthSquared())
				/
				(2 * center_vector1.length() * center_vector2.length()));
		
		System.out.println(Math.toDegrees(angle));
		return Math.toDegrees(angle);
	}
	
	/**
	 * NEW PRE VIVA
	 * @param list assuming list has size 4 or more
	 * @return
	 */
	public static List<Vector4f> OrderVertex(List<Vector4f> list) {
		List<Vector4f> R = new ArrayList<Vector4f>();
		List<Vector4f> popL = list;
		
		Vector4f center = Object4DLoader.findCurrentOrigin(list);
		Vector4f v1 = list.get(0);
		
		// normal of a face
		Vector3f v1_3f = new Vector3f(list.get(0).x(), list.get(0).y(), list.get(0).z());
		Vector3f v2_3f = new Vector3f(list.get(1).x(), list.get(1).y(), list.get(1).z());
		Vector3f v3_3f = new Vector3f(list.get(2).x(), list.get(2).y(), list.get(2).z());
		Vector3f normal = new Vector3f();
		GeometryUtils.normal(v1_3f, v2_3f, v3_3f, normal);
		
		Vector4f c_v1 = new Vector4f();
		v1.sub(center, c_v1);
		
		R.add(v1);
		popL.remove(0);
		
		while (true) {
			
			Vector4f min_v = v1;
			float min_v_angle = 360.0f;
			for (int i = 0; i < popL.size(); i++) {
				
				// -LR = LEFT : +LR = RIGHT
				float LR = cross_product_magnitude_v4(center, v1, popL.get(i), normal);
				float angle = (float) AngleIn3D(center, v1, popL.get(i));
				
				if (LR < 0) {
					if (angle < min_v_angle) {
						min_v_angle = angle;
						min_v = popL.get(i);
					}
				} else if (LR > 0) {
					if (180 + (180 - angle) < min_v_angle) {
						min_v_angle = 180 + (180 - angle);
						min_v = popL.get(i);
					}
				} else if (LR == 0) {
					if (angle < min_v_angle) {
						min_v_angle = angle;
						min_v = popL.get(i);
					}
				}
				
			}
			
			R.add(min_v);
			popL.remove(min_v);
			
			if (popL.size() <= 0)
				break;
			
		}
		
		return R;
	}
	
	
	public static List<Vector4f> OrderVertexOLD2(List<Vector4f> input) {
		List<Vector4f> popingList = input;
		List<Vector4f> returnList = new ArrayList<Vector4f>();
		
		Vector4f center = Object4DLoader.findCurrentOrigin(input);
		
		Vector4f vertex_1 = input.get(0);
		popingList.remove(0);
		returnList.add(vertex_1);
		
		while (popingList.size() != 0) {
			
			int current_min_index = 0;
			for (int i = 1; i < popingList.size(); i++) {
				if (AngleIn3D(center, vertex_1, popingList.get(i)) < AngleIn3D(center, vertex_1, popingList.get(current_min_index))) {
					current_min_index = i;
				}
			}
			returnList.add(popingList.get(current_min_index));
			popingList.remove(current_min_index);
		}
		
		return returnList;
	}
		
	
	
	public static List<Vector4f> OrderVertexOLD(List<Vector4f> input) {
		List<Vector4f> returnList = new ArrayList<Vector4f>();
		
		Vector4f center = Object4DLoader.findCurrentOrigin(input);
		Vector4f firstVertex = input.get(0);
		returnList.add(firstVertex);
		
//		Vector4f CA = A.sub(center);
		
		while (true) {
			Vector4f currentMaxVector = null;
			double currentMaxVectorAngle = 0;
			
			for (int i = 1; i < input.size(); i++) {
	//			Vector4f CI = input.get(i).sub(center);
	//			Vector4f AI = input.get(i).sub(A);
				
				// values > 0 are on left and < 0 are on right relatively speaking to fitst->center
				float left_or_right = cross_product_magnitudeOLD(firstVertex, center, input.get(i));
				
				double angle = 0;
				if (left_or_right > 0) {
					angle = AngleIn3D(center, firstVertex, input.get(i));
				} else if (left_or_right < 0) {
					angle = 180 + AngleIn3D(center, firstVertex, input.get(i));
				}
				
				if (angle > currentMaxVectorAngle) {
					currentMaxVector = input.get(i);
					currentMaxVectorAngle = angle;
				}
			}
			
			if (input.size() == 1 && input.get(0) == returnList.get(0)) 
				break;
			
			returnList.add(currentMaxVector);
			input.remove(currentMaxVector);
		
		}
		
		return returnList;
	}
	
	
	/**
	 * This specific implementation of the Convex Hull Problem for 2D polygon in 3D space:
	 * More Specifically, utilising the Gift Wrapping or Jarvis March Algorithm
	 * 
	 * This implementation assumes that :
	 * - passed list of vector4f all have the same w() value
	 * - the passed list of vertex all lie on the convex hull perimeter/path (in random order)
	 * - all points passed exist on the same plane in 3D
	 * - the size of the pass list is 4 vertex or greater
	 * @param randomConvexHullVertexList Vertex on the convex hull but in random order
	 * @return the ordered vertex list
	 */
	public static List<Vector4f> ConvexHullOrder(List<Vector4f> input) {
		
		List<Vector4f> L = new ArrayList<Vector4f>(); // final list, L
		List<Vector4f> L_coll = new ArrayList<Vector4f>();
		
		Vector4f v_initial = input.get(0); // first element gets added
		L.add(v_initial);
		
		Vector4f current = v_initial;
		
		while (true) {
			// iterated and checks
			Vector4f next = input.get(L.size());
			// check on current max next
			Vector4f currentMAX = next;
			
			for (int i = 1; i < input.size(); i++) {
				if (input.get(i) == current)
					continue;
				
				// If the current check had a greater than the already mad
				if (cross_product_magnitudeOLD(current, next, input.get(i)) >= 
				cross_product_magnitudeOLD(current, next, currentMAX)) {
					if (!L.contains(input.get(i))) {
						next = input.get(i);
						currentMAX = input.get(i);
					}
				}
			}
			
			if (next == v_initial)
				break;
			
			L.add(next);
			current = next;
			break;
		}
		
//		while (true) {
//			Vector4f next = input.get(0);
//			
//			for (int i = 1; i < input.size(); i++) {
//				if (input.get(i) == current)
//					continue;
//				
//				float val = cross_product_magnitude(current, next, input.get(i));
////				System.out.println("val: " + val);
//				/**
//				 * values greater than 0 are on the left
//				 */
//				if (val > 0) {
//					next = input.get(i);
//				} else if (val == 0) {
//					next = input.get(i);
//				}
//			}
//			
//			if (next == v_initial)
//				break;
//			
//			L.add(next);
//			current = next;
//		}// while (next != v_initial);
		
		return L;
	}
	
	
	public static List<Vector4f> ConvexHullOrder2(List<Vector4f> input) {
		
		// orderedVertexList
		List<Vector4f> result = new ArrayList<Vector4f>(); // P, FINAL LIST
		Vector4f v_initial = input.get(0); // "leftmost" INITIAL pos
		result.add(v_initial);
		
		
		
		Vector4f v_current = input.get(result.size() - 1); // current/newest vector in result/hull
		Vector4f v_next = input.get(1); // to-be next Vector in result
		
		
		// Vcurrent --> Vnext : comparing vector
		Vector4f v_current_to_next = v_next.sub(v_current);
		
		// compare cross product of v_current_to_next x all vertex in input
		for (int i = 0; i < input.size(); i++) {
			
			// Vcurrent --> Vi
			Vector4f v_current_to_i = input.get(i).sub(v_current);
			
			/**
			 * calculate cross-product of v_current_to_next and v_current_to_i
			 */
			Vector3f cp = new Vector3f(v_current_to_next.x(), v_current_to_next.y(), v_current_to_next.z())
					.cross(v_current_to_i.x(), v_current_to_i.y(), v_current_to_i.z());
			
			/**
			 * calculate cross product magnitude using dot product or vp and current vector
			 */
			float cp_magnitude = new Vector3f(v_current.x(), v_current.y(), v_current.z()).dot(cp);
//			float cp_Vcn_Vci_magnitude = cross_product_magnitude();
			
		}
		
		return result;
	}
	
	public static List<Vector4f> ConvexHullOrder1(List<Vector4f> randomConvexHullVertexList) {
		List<Vector4f> orderedVertexList = new ArrayList<Vector4f>(); // P
		Vector4f v_initial = randomConvexHullVertexList.get(0); // "leftmost"
		
		Vector4f endpoint;
		do {
			orderedVertexList.add(v_initial);
			endpoint = randomConvexHullVertexList.get(0);
			
			for (int j = 0; j < randomConvexHullVertexList.size(); j++) {
				
			}
			
		} while (endpoint == orderedVertexList.get(0));
		
		
		return orderedVertexList;
	}
	
	
	/**
	 * Same as {@code Convert()} however utilises cells data
	 * @param object4D
	 * @return
	 */
	public static Object3D Convert_version2(Object4D object4D) {
		
		Object3D final_3D_representation_of_4D_shape = new Object3D();
		object4D.setVerticesList_compute_AppliedTransformation();
		
		float w_pos = object4D.getW();
		System.out.println("w_pos: " + w_pos);
		
		List<Vector3f> newVertexList = new ArrayList<Vector3f>();
		List<Vector3i> newPolygonList = new ArrayList<Vector3i>();
		
		// Go through every edge in a cell and add them to a list to be further analysed
		for (int[] cell: object4D.getCellList()) {
//			List<Vector4f> cellVertexList = new ArrayList<Vector4f>();
//			
//			/**
//			 * creates a temporary vertex list with the vertex of a cell
//			 */
//			// iterates through the face index in a cell
//			for (int i = 0; i < cell.length; i++) {
//				// face: containing the indexes of vertex that make up a face
//				int[] face = object4D.getFacesList().get(cell[i] - 1);
//				// iterates through the face and adding new vertex to the cellVertexList
//				for (int j = 0; j < face.length; j++) {
//					Vector4f vertex = object4D.getVerticesList_AppliedTransformation().get(face[j] - 1);
//					if (!cellVertexList.contains(vertex))
//						cellVertexList.add(vertex);
//				}
//			}
			
//			/**
//			 * Now calculate the 2D cross-section of the 3D object (that is the 4D face)
//			 */
			List<Vector4f> crossectionVertexList = new ArrayList<Vector4f>();
			/**
			 * For a given cell made up of faces, find the slice through each face and add vertex slice points
			 * to the crossectionVertexList
			 * Calculate all the intersection points in a cell = new face
			 */
			// for each face in the cell
			for (int f = 0; f < cell.length; f++) {
				// face in question
				int[] face = object4D.getFacesList().get(cell[f] - 1);
				int n = face.length;
//				List<Vector4f> crossectionVertexList_TEMP = new ArrayList<Vector4f>();
				
				for (int i = 0; i < n; i++) {
					Vector4f currentVertex = object4D.getVerticesList_AppliedTransformation().get(face[i % n] - 1);
					Vector4f nextVertex = object4D.getVerticesList_AppliedTransformation().get(face[(i + 1) % n] - 1);
					
					// if both points have an intersection or one or both are in the visable, add to 
					/**
					 * current vector is IN
					 */
					if (currentVertex.w() == w_pos) {
						if (!crossectionVertexList.contains(currentVertex))
							crossectionVertexList.add(currentVertex);
					}
					/**
					 * current and next vector are on either size of zyx hyperplane 
					 */
					if ((currentVertex.w() < w_pos && nextVertex.w() > w_pos) || 
							(currentVertex.w() > w_pos && nextVertex.w() < w_pos)) {
						if (!crossectionVertexList.contains(calc_intersection(currentVertex, nextVertex)))
							crossectionVertexList.add(calc_intersection(currentVertex, nextVertex));
					}
				}
			}
			
			/**
			 * Convert crossectionVertexList to ordered list
			 */
//			if (crossectionVertexList.size() >= 4) {
//				System.out.println("BEFORE_csVertexList size: " + crossectionVertexList.size());
//				crossectionVertexList = OrderVertex(crossectionVertexList);
//				System.out.println("AFTER_csVertexList size: " + crossectionVertexList.size());
//			}
			
			
			if (crossectionVertexList.size() >= 3) {
				
				int[] newFace = new int[crossectionVertexList.size()];
				for (int i = 1; i - 1 < crossectionVertexList.size(); i++)
					newFace[i - 1] = i;
				
//				System.out.println("newFace [] = " + Arrays.toString(newFace));
				
				newPolygonList.addAll(triangulate_polygons(newFace, newVertexList.size()));
				newVertexList.addAll(list4f_to_list3f(crossectionVertexList));
			}
		}
		
		final_3D_representation_of_4D_shape.setVerticesList(newVertexList);
		final_3D_representation_of_4D_shape.setPolygonList(newPolygonList);
		final_3D_representation_of_4D_shape.setPolygonCount(newPolygonList.size());
		
		return final_3D_representation_of_4D_shape;
	}
	
	
	/**
	 * Converts a 4D object to its 3D slice constituent
	 * @param object4D
	 * @return An {@link Object3D} whose values describe the final 3D representation of a 3D cross-section of given 4D shape 
	 */
	public static Object3D ConvertO(Object4D object4D) {
		
		Object3D final_3D_representation_of_4D_shape = new Object3D();
		
		object4D.setVerticesList_compute_AppliedTransformation();
		
		float w_pos = object4D.getW();
		System.out.println("w_pos: " + w_pos);
		
		List<Vector3f> newVertexList = new ArrayList<Vector3f>();
		List<Vector3i> newPolygonList = new ArrayList<Vector3i>();
		
		// for each face: consisting of indices at for the vertex in the objects4d vertex list
		for (int[] face: object4D.getFacesList()) {
			
//			System.out.println("preFace [] = " + Arrays.toString(face) + " | " + visualisable(face, object4D.getVerticesList(), w_pos));
			
			
			// only process a face if a face contains 3 or more vertex that are in the visable plane
			if (visualisable(face, object4D.getVerticesList_AppliedTransformation(), w_pos)) {
				
				int n = face.length;
				List<Vector4f> newVertexList_TEMP = new ArrayList<Vector4f>();
//				int newFacePolygonCount = 0;
				
				// adding new vertices to the new Vertex List
				// comparing every vertex with the next vertex
				for (int i = 0; i < n; i++) {
					
					/**
					 * TODO apply transformations/rotations to these vertex
					 */
					// Normal Vertex List (no transformations applied)
//					Vector4f currentVertex = object4D.getVerticesList().get(face[i % n] - 1);
//					Vector4f nextVertex = object4D.getVerticesList().get(face[(i + 1) % n] - 1);
					
					// Applied Vertex List (Rotation and Position transforamtions applied)
					Vector4f currentVertex = object4D.getVerticesList_AppliedTransformation().get(face[i % n] - 1);
					Vector4f nextVertex = object4D.getVerticesList_AppliedTransformation().get(face[(i + 1) % n] - 1);
					
					/**
					 * no vertex has been added yet
					 */
					if (newVertexList_TEMP.size() == 0) {
						/**
						 * current vector is IN
						 */
						if (currentVertex.w() == w_pos) {
							newVertexList_TEMP.add(currentVertex);
//							newFacePolygonCount++;
							
							/**
							 * next vector is IN
							 */
							if (nextVertex.w() == w_pos) {
								continue; // ignore vector
							} 
							/**
							 * next vector is OUT
							 */
							else {
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
//								newFacePolygonCount++;
							}
						}
						/**
						 * current vector is OUT
						 */
						else if (currentVertex.w() != w_pos) {
							/**
							 * next vector is IN
							 */
							if (nextVertex.w() == w_pos) {
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
//								newFacePolygonCount++;
							}
							/**
							 * next vector is OUT: but on the other side of xyz hyperplane
							 */
							else if ((currentVertex.w() >= w_pos && nextVertex.w() <= w_pos) || 
									(currentVertex.w() <= w_pos && nextVertex.w() >= w_pos)) {
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
							}
							/**
							 * next vector is OUT
							 */
							else {
								continue; // basically ignore
							}
						}
					} 
					/**
					 * vertex already exist
					 */
					else {
						/**
						 * current vector is IN
						 */
						if (currentVertex.w() == w_pos) {
							/**
							 * next vector is IN
							 */
							if (nextVertex.w() == w_pos) {
								newVertexList_TEMP.add(currentVertex);
//								newFacePolygonCount++;
							}
							/**
							 * next vector is OUT
							 */
							else {
								newVertexList_TEMP.add(currentVertex);
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
//								newFacePolygonCount+=2;
							}
						}
						/**
						 * current vector is OUT
						 */
						else {
							/**
							 * next vector is IN
							 */
							if (nextVertex.w() == w_pos) {
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
//								newFacePolygonCount++;
							}
							/**
							 * next vector is OUT: but on the other side of xyz hyperplane
							 */
							else if ((currentVertex.w() >= w_pos && nextVertex.w() <= w_pos) || 
									(currentVertex.w() <= w_pos && nextVertex.w() >= w_pos)) {
								newVertexList_TEMP.add(calc_intersection(currentVertex, nextVertex));
							}
							/**
							 * next vector is OUT
							 */
							else {
								continue; // ignore; this edge is not visible
							}
						}
					} // adding vertex end
				} // for END
				
//				System.out.println("The new Vertex List");
//				for (Vector4f v: newVertexList_TEMP)
//					System.out.println(v.toString());
				
				int[] newFace = new int[newVertexList_TEMP.size()];
				for (int i = 1; i - 1 < newVertexList_TEMP.size(); i++)
					newFace[i - 1] = i;
				
				System.out.println("newFace [] = " + Arrays.toString(newFace));
				
				newPolygonList.addAll(triangulate_polygons(newFace, newVertexList.size()));
				newVertexList.addAll(list4f_to_list3f(newVertexList_TEMP));
				
				
			} // if end
		} // END
		
		final_3D_representation_of_4D_shape.setVerticesList(newVertexList);
		final_3D_representation_of_4D_shape.setPolygonList(newPolygonList);
		final_3D_representation_of_4D_shape.setPolygonCount(newPolygonList.size());
		
		return final_3D_representation_of_4D_shape;
	}
	
	
	public static Object3D ConvertOLD3(Object4D object4D) {
		float w_pos = object4D.getW();
		System.out.println("w_pos: " + w_pos);
		
		List<Vector3f> newVertexList = new ArrayList<Vector3f>();
		List<Vector3i> newPolygonList = new ArrayList<Vector3i>();
		
		/**
		 * TODO numVerCounter should equal (newPolygonList * 3) at the very end if working correctly
		 * at this point using numVerCounter should be removed and can use (newPolygonList * 3) for total vertex count
		 */
		int newTotalVertexCounter = 0;
		
		// for each face: consisting of indices at for the vertex in the objects4d vertex list
		for (int[] face: object4D.getFacesList()) {
			
			if (visualisable(face, object4D.getVerticesList(), w_pos)) {
			
			
			int n = face.length; // n (for simplicity) equals face length
			
			List<Vector4f> newTempVertexList = new ArrayList<Vector4f>();
			int newVertexFace = 0;
			
			// comparing every vertex with the next vertex
			for (int i = 0; i < n - 1; i++) {
				Vector4f currentVector = object4D.getVerticesList().get(face[i % n] - 1);
				Vector4f nextVector = object4D.getVerticesList().get(face[(i + 1) % n] - 1);
				
				/**
				 * no vertex has been added yet
				 */
				if (newVertexFace == 0) {
					/**
					 * current vector is IN
					 */
					if (currentVector.w() == w_pos) {
						newTempVertexList.add(currentVector);
						newVertexFace++;
						
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							continue; // ignore vector
						} 
						/**
						 * next vector is OUT
						 */
						else {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							newVertexFace++;
						}
					}
					/**
					 * current vector is OUT
					 */
					else if (currentVector.w() != w_pos) {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							newVertexFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							continue; // basically ignore
						}
					}
				} 
				/**
				 * vertex already exist
				 */
				else {
					/**
					 * current vector is IN
					 */
					if (currentVector.w() == w_pos) {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(currentVector);
							newVertexFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							newTempVertexList.add(currentVector);
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							newVertexFace+=2;
						}
					}
					/**
					 * current vector is OUT
					 */
					else {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							newVertexFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							continue; // ignore; this edge is not visible
						}
					}
				} // adding vertex end
			} // iterating through vertex of a face end
			
			
			
			
			// 3 is the minimum number of vertex requires to render polygon/face
			if (newVertexFace >= 3) {
				int[] newFaceIndices = new int[newVertexFace + 1];

				// i iterated through a new face
				for (int i = 1; i <= newVertexFace + 1; i++) {
					// j is the new value (with accumulating value)
//					int j = i + newTotalVertexCounter;
					newFaceIndices[i - 1] = i;
				}
				
				// new triangulated indices
				List<Vector3i> newTriangulatedIndices = triangulate_polygons(newFaceIndices, newTotalVertexCounter);
				
				newVertexList.addAll(list4f_to_list3f(newTempVertexList));
				newPolygonList.addAll(newTriangulatedIndices);
				
				// after this is done, increase the total vertex count as we officially add this to the list
				newTotalVertexCounter += newVertexFace + 1;
			}
		}
		}
		
		return null;
		
//		final_3D_representation_of_4D_shape.setVerticesList(newVertexList);
//		final_3D_representation_of_4D_shape.setPolygonList(newPolygonList);
//		System.out.println("newPolygonList.size() * 3 = " + newPolygonList.size() * 3);
//		System.out.println("newTotalVertexCounter = " + newTotalVertexCounter);
////		final_3D_representation_of_4D_shape.setPolygonCount(newTotalVertexCounter);
//		final_3D_representation_of_4D_shape.setPolygonCount(newPolygonList.size() * 3);
//		
//		return final_3D_representation_of_4D_shape;
	}
	
	
	/**
	 * 
	 * @param object4D
	 * @return An {@link Object3D} whose values describe the final 3D representation of a 3D cross-section of given 4D shape 
	 */
	public static Object3D ConvertOLD2(Object4D object4D) {
		
		float w_pos = object4D.getW();
		System.out.println("w_pos: " + w_pos);
		
		List<Vector4f> newVertexList = new ArrayList<Vector4f>();
//		List<int[]> newFaceList = new ArrayList<int[]>();
		
		// 3-vertex polygon
		List<Vector3i> newPolygonList = new ArrayList<Vector3i>();
		
		int numVerCounter = 0;
		
		// for all faces, calculate the new faces
		for (int[] face: object4D.getFacesList()) {
//			System.out.println("input face: " + Arrays.toString(face));
			
			// specifies the number of vertices that make up the face
			int numVerPerFace = 0;
			
			
			List<Vector4f> newTempVertexList = new ArrayList<Vector4f>();
			
			// i iterator for all vertex in face[]
			for (int i = 0; i < face.length - 1; i++) {
//				float vCurrent = object4D.getVerticesList().get(face[i] - 1).w(); // current vertex
//				float vNext = object4D.getVerticesList().get(face[i + 1] - 1).w(); // next vertex to analyse

				Vector4f currentVector = object4D.getVerticesList().get(face[i] - 1);
				Vector4f nextVector = object4D.getVerticesList().get(face[i + 1] - 1);
				
				/**
				 * no vertex has been added yet
				 */
				if (numVerPerFace == 0) {
					/**
					 * current vector is IN
					 */
					if (currentVector.w() == w_pos) {
						newTempVertexList.add(currentVector);
						numVerPerFace++;
						
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							continue; // ignore vector
						} 
						/**
						 * next vector is OUT
						 */
						else {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							numVerPerFace++;
						}
					}
					/**
					 * current vector is OUT
					 */
					else if (currentVector.w() != w_pos) {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							numVerPerFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							continue; // basically ignore
						}
					}
				} 
				/**
				 * vertex already exist
				 */
				else {
					/**
					 * current vector is IN
					 */
					if (currentVector.w() == w_pos) {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(currentVector);
							numVerPerFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							newTempVertexList.add(currentVector);
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							numVerPerFace+=2;
						}
					}
					/**
					 * current vector is OUT
					 */
					else {
						/**
						 * next vector is IN
						 */
						if (nextVector.w() == w_pos) {
							newTempVertexList.add(calc_intersection(currentVector, nextVector));
							numVerPerFace++;
						}
						/**
						 * next vector is OUT
						 */
						else {
							continue; // ignore; this edge is not visible
						}
					}
				}
				
//				/**
//				 * current vector IS IN
//				 */
//				if (currentVector.w() == w_pos) {
//					// first entry vector
//					if (numVerPerFace == 0) {
//						newTempVertexList.add(currentVector);
//						numVerPerFace++;
//					} else {
//						/**
//						 * next vector IS IN
//						 */
//						if (nextVector.w() == w_pos) {
//							newTempVertexList.add(currentVector);
//							numVerPerFace++;
//						}
//						/**
//						 * next vector IS NOT IN
//						 */
//						else {
//							newTempVertexList.add(calc_intersection(currentVector, nextVector));
//							numVerPerFace++;
//						}
//					}
//				}
//				/**
//				 * current vector IS NOT IN
//				 */
//				else {
//					// first entry not yet been added
//					if (numVerPerFace == 0) {
//						newTempVertexList.add(calc_intersection(currentVector, nextVector));
//						numVerPerFace++;
//					} else {
//						/**
//						 * next vector IS IN
//						 */
//						if (nextVector.w() == w_pos) {
//							newTempVertexList.add(calc_intersection(currentVector, nextVector));
//							numVerPerFace++;
//						}
//						/**
//						 * next vector IS NOT IN
//						 */
//						else {
//							//skip
//							continue;
//						}
//					}
//				}
			}
			
			
			// if the new face had more than 3 vertex, then we can count it as a polygon and add it to the new list
			if (numVerPerFace >= 3) {
				int[] newFace = new int[numVerPerFace + 1];
	//			System.out.println("face ver size: " + (numVerPerFace + 1));
				
				// i iterated through a new face
				for (int i = 1; i <= numVerPerFace + 1; i++) {
					// j is the new value (with accumulating value)
	//				int j = i + numVerCounter;
					newFace[i - 1] = i + numVerCounter;
				}
				numVerCounter += numVerPerFace + 1;
				
//				newFaceList.add(newFace);
				newVertexList.addAll(newTempVertexList);
				newPolygonList.addAll(triangulate_polygonsOLD(newFace));
				
				// prints the newFace indices
//				System.out.println("newFace[]: " + Arrays.toString(newFace));
			}
			
			
		}
		System.out.println("New vertex list length: " + newVertexList.size());
		System.out.println("New triangulated vertex list length: " + newPolygonList.size());
		
		System.out.println("new vector4fs");
		for (Vector4f v: newVertexList)
			System.out.println("new vector:" + v);
			
		return null;
//		final_3D_representation_of_4D_shape.setVerticesList(list4f_to_list3f(newVertexList));
//		final_3D_representation_of_4D_shape.setPolygonList(newPolygonList);
//		final_3D_representation_of_4D_shape.setPolygonCount(newPolygonList.size());
//		return final_3D_representation_of_4D_shape;
	}
	
	
	/**
	 * 
	 * @param object4D
	 * @return An {@link Object3D} whose values describe the final 3D representation of a 3D cross-section of given 4D shape 
	 */
	public static Object3D ConvertOLD(Object4D object4D) {
		
		float w_pos = object4D.getW();
		

//		List<Vector3i> polygonList = new ArrayList<Vector3i>();
//		List<Vector3f> newVerticesList = new ArrayList<Vector3f>();
//		List<int[]> newFacesList = new ArrayList<int[]>();
		
		List<Vector4f> newFaceList = new ArrayList<Vector4f>();
		
		int totalVertexCount = 0;
		// for all faces, calculate the new faces
		for (int[] face: object4D.getFacesList()) {
			
			/**
			 * only need to calculate the final 3D cross-section, if three or more
			 * edges from a face are all in the same w position that is equal to the
			 * w position of the 4D Objects w.
			 * 
			 */
//			if ((face[0] == face[1]) && (face[1] == face[2]) && (face[0] == w_pos)) {
//				
//			}
			
			/**
			 * if only 1 vertex is in the current visible dimension (where w = 0)
			 * then that is enough for it to become rendered
			 */
//			if () {
//				
//			}
			//inside for loop
			
//			if (i == 0 && v1_wpos == w_pos) {
//				newFace.add(object4D.getVerticesList().get(face[i]));
//			} else {
//				if (v2_wpos == w_pos) {
//					
//				}
//			}
			
//			// checks weather to be rendered or not
//			/*
//			 * if an edge lies ons the w plane
//			 */
//			if ((v1_wpos == w_pos) && (v2_wpos == w_pos)) {
//				// add edge to vertes list
//				newFace.add(object4D.getVerticesList().get(face[i]));
//				newFace.add(object4D.getVerticesList().get(face[i + 1]));
//				
//			} 
//			/*
//			 * if one vertex equals objects w position and the other is not
//			 */
//			else if (((v1_wpos == w_pos) && (v2_wpos != w_pos)) || ((v1_wpos != w_pos) && (v2_wpos == w_pos))) {
//				
//			}
//			/**
//			 * if the ends of an edge each lie on opposite sides of the w plane
//			 */
//			else if (((v1_wpos > w_pos) && (v2_wpos < w_pos)) || ((v1_wpos < w_pos) && (v2_wpos > w_pos))) {
//				// calculate intersection, join correctly
//			}
			
			/**
			 * Edge = 2 connecting vertex v1, v2
			 * >>if both vertex in an edge equal the objects w position: render
			 * if one vertex == objects w pos, and the other is not: dont render
			 * if both vertex are on the same side (i.e. (both > w pos) or (both < wpos)): dont render
			 * >>if both are on opposite sides of the  (i.e. (one is > w pos) and (one is < w pos)): render up intersection
			 */
			
//			List<Vector4f> newFace = new ArrayList<Vector4f>();
			
			// specifies the number of verticies that make up the face
			int numVerPerFace = 0;
			
			
			// i iterator for all vertex in face[]
			for (int i = 0; i < face.length - 1; i++) {
				float vCurrent = object4D.getVerticesList().get(face[i]).w(); // current vertex
				float vNext = object4D.getVerticesList().get(face[i + 1]).w(); // next vertex to analyse
				
				/**
				 * current vertex is in
				 */
				if (vCurrent == w_pos) {
					/**
					 * next vertex is in
					 */
					if (vNext == w_pos) {
						// add current because next vertex will be added next iteration
						newFaceList.add(object4D.getVerticesList().get(face[i]));
						numVerPerFace++;
					} 
					/**
					 * next vertex is not in
					 */
					else {
						newFaceList.add(calc_intersection(object4D.getVerticesList().get(face[i]), 
								object4D.getVerticesList().get(face[i + 1])));
						numVerPerFace++;
					}
				} 
				/**
				 * current vertex is not in
				 */
				else {
					continue;
				}
			}
			totalVertexCount += numVerPerFace;
			
			int[] vertexPointer = new int[numVerPerFace];
			for (int j = 1; j <= numVerPerFace; j++) {
				vertexPointer[j - 1] = j;
			}
			
//			for (int i = 0; i < vertexPointer.length; i++)
			System.out.println(Arrays.toString(vertexPointer));
			
//			turn++;
		}
		
		
		return null;
	}
	
//	public static void main(String [] args) {
//		
//		Object4D o4d = Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape");
//		
//		Object3D o3d = Convert(o4d);
//		
//		List<Vector3f> verticesList = o3d.getVerticesList();
//		List<Vector3i> polygonList = o3d.getPolygonList();
//		int polygonCount = o3d.getPolygonCount();
//		
//		System.out.println("3D obj Vertex List: ");
//		for (Vector3f v: verticesList)
//			System.out.println(v.toString());
//		
//		System.out.println("3D obj Polygon List: ");
//		for (Vector3i p: polygonList)
//			System.out.println(p.toString());
//		
//		System.out.println("Polygon Count: " + polygonCount);
//	}
	
}