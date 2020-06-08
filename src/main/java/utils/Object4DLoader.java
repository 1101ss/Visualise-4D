package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import com.sun.javafx.geom.Vec4f;

/**
 * This class is responsible for reading and loading / importing object4D (.obj4D) files
 * into the MemoryManager
 * @author Shonam
 *
 */
public class Object4DLoader {
	
	public final static String OBJECT4D_FILEEXTENSION = "obj4D";
	
	public static Object4D loadObj4D(String fileLocation) {
		
		int vertices = 0;
		int faces = 0;
		int cells = 0;
		int edges = 0;
		List<Vector4f> verticesList = new ArrayList<Vector4f>();
//		List<List<Vec4f>> facesList = new ArrayList<List<Vec4f>>();
		List<int[]> facesList = new ArrayList<int[]>();
		List<int[]> edgeList = new ArrayList<int[]>();
		List<int[]> cellList = new ArrayList<int[]>();
		
		Object4D object4D;// = new Object4D();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(new File(fileLocation + "." + OBJECT4D_FILEEXTENSION));
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			System.err.printf("File (%s) failed to load. Make sure you are using a \"%s\" file extension.%n", 
					fileLocation, 
					"." + OBJECT4D_FILEEXTENSION
					);
			e.printStackTrace();
		}
		
		// 4D Object data loading
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
					Vector4f vec4f = new Vector4f(
							Float.parseFloat(readLine[1]), // x
							Float.parseFloat(readLine[2]), // y
							Float.parseFloat(readLine[3]), // z
							Float.parseFloat(readLine[4]) // w (4th axis coordinate)
							);
//					System.out.println("4DLoader, v: " + vec4f);
					vertices++;
					verticesList.add(vec4f);
					
				}
				/*
				 * Faces
				 */
				else if (data.startsWith("f ")) {
					int[] intArray = new int[readLine.length - 1];
					for (int i = 0; i < readLine.length - 1; i++) {
						intArray[i] = Integer.parseInt(readLine[i + 1]);
					}
					
					faces++;
					facesList.add(intArray);
					
					/*
					 * Edges
					 */
					int[] edge;// = new int[1]  two vertices make an edge
					
					if (edgeList.isEmpty()) {
						for (int i = 0; i < intArray.length - 2; i++) {
							edge = new int[2];
							edge[0] = intArray[i]; // vertex 1
							edge[1] = intArray[i + 1]; // vertex 2
							
							edges++;
							edgeList.add(edge);
						}
						
						
					} else {
						for (int i = 0; i < intArray.length - 2; i++) {
							// check if egde is already present in edgeList
							edge = new int[2];
							edge[0] = intArray[i]; // vertex 1
							edge[1] = intArray[i + 1]; // vertex 2
							
							// adds an edge to edgeList if not already containing that edge
							// Vertices [1,2] == [2,1] as 1 and 2 both connect to form an edge
							if (!edgeList.contains(edge) || !edgeList.contains(swapEdgeVertices(edge))) {
								
								edges++;
								edgeList.add(edge);
							}
						}
					}
					
				}
				/**
				 * NEW 8/05/20
				 * Cells
				 */
				else if (data.startsWith("c ")) {
					int[] intArray = new int[readLine.length - 1];
					for (int i = 0; i < readLine.length - 1; i++) {
						intArray[i] = Integer.parseInt(readLine[i + 1]);
					}
					
					cellList.add(intArray);
				}
				/**
				 * END NEW 8/05/20
				 */
			}
			
			/*
			 * Cells
			 * 
			 * Knowing the fact: Euler's Poincaré/polyhedron Formula
			 * x = V - E + F
			 * where x = The Euler Characteristic, V = vertices, E = edges and F = faces
			 * The Euler Characteristic: 1 - (-1)^n , where n id n-dimension
			 * we can calculate the cells of the 4D polytype
			 * More precisely V - E + F - C = 0, where C = cells
			 * 
			 */
			cells = Math.abs(vertices - edges + faces);
			System.out.println("Object4D Data Loading Finished");
			
		} catch (IOException e1) {
			System.out.println("Object4D Data Loading Finished");
//			e1.printStackTrace();
		}
		
		// end
		try {
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to Close Object4DLoader");
		}
		
//		verticesList = centeraliseVertexList(verticesList, findCurrentOrigin(verticesList));
		
		object4D = new Object4D(vertices, faces, cells, edges, verticesList, facesList, cellList);
		return object4D;
	}
	
	private static final int[] swapEdgeVertices(int[] edge) {
		int tempVerticie = edge[0];
		edge[0] = edge[1];
		edge[1] = tempVerticie;
		
		return edge;
	}
	
	public static Vector4f findCurrentOrigin(List<Vector4f> verticesList) {
		Vector4f vectorOrigin = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
		
		for (Vector4f vertex: verticesList) {
			vectorOrigin.add(vertex);
		}
		
		vectorOrigin.div((float) verticesList.size());
		
		System.out.println("Imported Origin (" + vectorOrigin.x + ", " + vectorOrigin.y + ", "+ vectorOrigin.z + ", " + vectorOrigin.w + ")");
		
		return vectorOrigin;
	}
	
	public static List<Vector4f> centeraliseVertexList(List<Vector4f> verticesList, Vector4f currentOrigin) {
		List<Vector4f> newCenteralisedVertexList = new ArrayList<Vector4f>();
		
		for (Vector4f vertex: verticesList) {
			newCenteralisedVertexList.add(new Vector4f(vertex.sub(currentOrigin)));
		}
		
		return newCenteralisedVertexList;
	}
	
	

	
	/*
	 * Object 4D Loader test
	 * just to test if this loader loads everything from the .obj4D file
	 * 
	public static void main(String[] args) {
		
		Object4D o4d = Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape");
		
		System.out.printf("Verticies: %s%nFaces: %s%nEdges: %s%nCells: %s%n", 
				o4d.getVertices(),
				o4d.getFaces(),
				o4d.getEdges(),
				o4d.getCells());
		
//		Vec4f v4f = new Vec4f(3.1f, 2.0f, 100.3f, 8f);
		
//		System.out.print(v4f);
		List<Vec4f> verticesList = o4d.getVerticesList();
		List<int[]> facesList = o4d.getFacesList();
		
		// prints vertices list
		for (int i = 0; i < verticesList.size(); i++) {
			System.out.printf("Vertex List %s: %s%n", (i + 1), verticesList.get(i));
		}
		
		// prints facelist
		for (int i = 0; i < facesList.size(); i++) {
			System.out.printf("Face List %s: ", (i + 1));
			for (int j = 0; j < facesList.get(i).length; j++) {
				System.out.print(facesList.get(i)[j]);
				
				if (j < (facesList.get(i).length - 1))
					System.out.print(", ");
			}
			System.out.println();
		}
		
	}
	 
	 */

}
