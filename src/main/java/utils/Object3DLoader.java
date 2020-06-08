package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
//import javax.vecmath.Vector4f;
import org.joml.Vector3i;

//import com.sun.javafx.geom.Vec4f;

/**
 * This class is responsible for reading and loading / importing object3D (.obj file with a .obj3D extension) files
 * into the MemoryManager
 * @author Shonam
 *
 */
public class Object3DLoader {
	
	public final static String OBJECT3D_FILEEXTENSION = "obj3D";
	
	public static Object3D loadObj3D(String fileLocation) {
		
		int vertices = 0;
		int faces = 0;
		int edges = 0;
		ArrayList<Vector3f> verticesList = new ArrayList<Vector3f>();
		List<int[]> facesList = new ArrayList<int[]>();
		List<int[]> edgeList = new ArrayList<int[]>();
		
		/**
		 * NEW 
		 * faces are made from 3-polygons (triangles) as oppose polygons >= 3
		 * </br>
		 * List of Vector3i or 3 integers x,y,z which are the three indices of the vertices from {@code verticesList}, not
		 * to be confused with literal x,y,z coordinate values
		 */
		int polygonCount = 0;
		List<Vector3i> polygonList = new ArrayList<Vector3i>();
		
		Object3D object3D;// = new Object4D();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(new File(fileLocation + "." + OBJECT3D_FILEEXTENSION));
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			System.err.printf("File (%s) failed to load. Make sure you are using a \"%s\" file extension.%n", 
					fileLocation, 
					"." + OBJECT3D_FILEEXTENSION
					);
			e.printStackTrace();
		}
		
		// 3D Object data loading
		String data = null;
		try {
			while ((data = bufferedReader.readLine()) != null) {
//				data = bufferedReader.readLine();
				String[] readLine = data.split(" ");
//				for (int i = 0; i < readLine.length; i++) {
//					System.out.println(readLine[i]);
//				}
				
				/*
				 * Vertices
				 */
				if (data.startsWith("v ")) {
					Vector3f vec4f = new Vector3f(
							Float.parseFloat(readLine[1]), // x
							Float.parseFloat(readLine[2]), // y
							Float.parseFloat(readLine[3]) // z
							);
					
					vertices++;
					verticesList.add(vec4f);
					
				}
				/*
				 * Faces
				 */
				else if (data.startsWith("f ")) {
					
					/*
					 * FaceList
					 */
					// intArray of vertices that make up a face
					int[] intArray = new int[readLine.length - 1];
					for (int i = 0; i < readLine.length - 1; i++) {
						intArray[i] = Integer.parseInt(readLine[i + 1]);
					}
					
					faces++;
					facesList.add(intArray);
					
					/*
					 * Triangle PolygonList
					 */
					// split faces into triangle polygons a add them to polygonList
					// if a face has more than 3 vertices
					if (readLine.length - 1 > 3) {
						// starts at 2, this is v2 since we have (f, v2, v2, v3, ...)
						for (int i = 1; i <= readLine.length - 3; i++) {
							polygonList.add(new Vector3i(Integer.parseInt(readLine[1]), // v1 always at index 1
									Integer.parseInt(readLine[i + 1]),
									Integer.parseInt(readLine[i + 2])
									));
							polygonCount++;
						}
						
					} else {
						polygonList.add(new Vector3i(Integer.parseInt(readLine[1]), //v1
								Integer.parseInt(readLine[2]), // v2
								Integer.parseInt(readLine[3]) // v3
								));
						polygonCount++;
					}
					
					
					/*
					 * Edges
					 */
					int[] edge;// = new int[1]  two vertices make an edge
					
					// if edge list is empty, just add the edges
					if (edgeList.isEmpty()) {
						for (int i = 0; i <= intArray.length - 2; i++) {
							edge = new int[2];
							edge[0] = intArray[i]; // vertex 1
							edge[1] = intArray[i + 1]; // vertex 2
							
//							System.out.printf("importing edge (%s, %s)%n", edge[0], edge[1]);
							edges++;
							edgeList.add(edge);
						}
						
						
					} else {
						for (int i = 0; i <= intArray.length - 2; i++) {
							// check if egde is already present in edgeList
							edge = new int[2];
							edge[0] = intArray[i]; // vertex 1
							edge[1] = intArray[i + 1]; // vertex 2
							
							// adds an edge to edgeList if not already containing that edge
							// Vertices [1,2] == [2,1] as 1 and 2 both connect to form an edge
//							System.out.printf("checking edge (%s, %s)%n", edge[0], edge[1]);
//							System.out.printf("contains (%s, %s) = %s%n", edge[0], edge[1], edgeList.contains(edge));
//							System.out.printf("contains reversed(%s, %s) = %s%n", edge[0], edge[1], edgeList.contains(swapEdgeVertices(edge)));
							if (!existsEdge(edgeList, edge)) {
								
//								System.out.printf("importing edge (%s, %s)%n", edge[0], edge[1]);
								edges++;
								edgeList.add(edge);
							}
						}
					}
					
				}
			}
			
			
		} catch (IOException e1) {
			System.out.println("Object3D Data Loading Finished");
//			e1.printStackTrace();
		}
		
		// end
		try {
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to Close Object3DLoader");
		}
		
		object3D = new Object3D(vertices, faces, edges, verticesList, facesList, polygonCount, polygonList);
		return object3D;
	}
	
	// TODO remove for Object4DLoader
//	private static final int[] swapEdgeVertices(int[] edge) {
//		int[] reversedEdge = new int[2];
//		reversedEdge[0] = edge[1];
//		reversedEdge[1] = edge[0];
//		
//		System.out.printf("Edge Before: (%s, %s)%n", edge[0], edge[1]);
//		System.out.printf("Edge After: (%s, %s)%n", reversedEdge[0], reversedEdge[1]);
//		
//		return reversedEdge;
//	}
	
	// TODO same for Object4DLoader
	/*
	 * Checks weather an edge exists,
	 * Vertices [1,2] == [2,1] as 1 and 2 both connect to form an edge therefore no need to reverse edge
	 */
	private static final boolean existsEdge(List<int[]> edgeList, int[] edge) {
		for (int[] e : edgeList) {
			if ((e[0] == edge[0] && e[1] == edge[1]) || (e[0] == edge[1] && e[1] == edge[0])) {
				return true;
			}
		}
		
		return false;
	}

	
	/*
	 * Object 3D Loader test
	 * just to test if this loader loads everything from the .obj4D file
	 * 
	 */
//	public static void main(String[] args) {
//		
//		Object3D o3d = Object3DLoader.loadObj3D("src/main/resources/3Dmodels/testShape");
//		
//		System.out.printf("Verticies: %s%nFaces: %s%nEdges: %s%n", 
//				o3d.getVertices(),
//				o3d.getFaces(),
//				o3d.getEdges());
//		
//		List<Vector3f> verticesList = o3d.getVerticesList();
//		List<int[]> facesList = o3d.getFacesList();
//		
//		// prints vertices list
//		for (int i = 0; i < verticesList.size(); i++) {
//			System.out.printf("Vertex List %s: %s%n", (i + 1), verticesList.get(i));
//		}
//		
//		// prints facelist
//		for (int i = 0; i < facesList.size(); i++) {
//			System.out.printf("Face List %s: ", (i + 1));
//			for (int j = 0; j < facesList.get(i).length; j++) {
//				System.out.print(facesList.get(i)[j]);
//				
//				if (j < (facesList.get(i).length - 1))
//					System.out.print(", ");
//			}
//			System.out.println();
//		}
//		
//		for (Vector3i polygon: o3d.getPolygonList()) {
//			System.out.println("polygon :" + polygon.x + " " + polygon.y + " " + polygon.z);
//		}
//		
//		System.out.println("Polygon Count: " + o3d.getPolygonCount());
//		System.out.println("PolygonList Length: " + o3d.getPolygonList().size());
//		
//	}

}
