package renderEngine;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Random;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;

import utils.Object3D;

public class ObjectColour {
	
	/** Long seed for generating random numbers with JOML */
	private static final long SEEDID = 1234567890;
//	private static final Random random = new Random(SEEDID);
	private static Random random = null;
	
	/** Whole object will be one colour */
	public static final int SET_ONE_COLOUR = 0;
	
	/** Whole object will be one random colour */
	public static final int GEN_ONE_COLOUR = 1;
	
	/** Every 3-Polygon / triangle will be different */
	public static final int GEN_EVERY_3POLY_DIFFERENT = 2;
	
	/** Every face will be a different colour */
	public static final int GEN_EVERY_FACE_DIFFERENT = 3;
	
	/** Every vertex will have a different colour */
	public static final int GEN_EVERY_VERTEX_DIFFERENT = 4;
	
	private ObjectColour() {
	}
	
	/**
	 * Generate One Colour with given r, g, b values
	 * @param vertexCount number of vertex for which to apply for
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 * @return
	 */
	public static FloatBuffer genOneColour(int vertexCount, float r, float g, float b) {
		random = new Random(SEEDID);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer returnColourBuffer = stack.mallocFloat(vertexCount * 3);
					
			for (int i = 0; i < vertexCount; i++) {
	//			System.out.printf("r: %s, g: %s, b:%s%n", r, g, b);
				returnColourBuffer.put(r).put(g).put(b);
			}
			
			returnColourBuffer.flip();
			MemoryStack.stackPop();
			
			return returnColourBuffer;
		}
	}
	
	/**
	 * Generate One Colour at Random
	 * @param vertexCount number of vertex for which to apply for
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 * @return
	 */
	public static FloatBuffer genOneColour(int vertexCount) {
		random = new Random(SEEDID);
				
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer returnColourBuffer = stack.mallocFloat(vertexCount * 3);
			
			float r = random.nextFloat();
			float b = random.nextFloat();
			float g = random.nextFloat();
			
			for (int i = 0; i < vertexCount; i++)
				returnColourBuffer.put(r).put(g).put(b);
			
			returnColourBuffer.flip();
			MemoryStack.stackPop();
			
			return returnColourBuffer;
		}
	}
	
	public static FloatBuffer genEvery3PolyDifferent(int vertexCount) {
		random = new Random(SEEDID);
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer returnColourBuffer = stack.mallocFloat(vertexCount * 3);
			
			float r = random.nextFloat();
			float b = random.nextFloat();
			float g = random.nextFloat();
			
	//		for () {
				
	//		}
			
			for (int i = 0; i < vertexCount; i+=3) {
				returnColourBuffer.put(r).put(g).put(b); // v1
				returnColourBuffer.put(r).put(g).put(b); // v2
				returnColourBuffer.put(r).put(g).put(b); // v3
				
				// new rgb
				r = random.nextFloat();
				b = random.nextFloat();
				g = random.nextFloat();
			}
			
			returnColourBuffer.flip();
			MemoryStack.stackPop();
			
			return returnColourBuffer;
		}
	}
	
	/**
	 * TODO in 4D to 3D conversion, create a new face list of the final cross-section
	 * @param size
	 * @return
	 */
	public static FloatBuffer genEveryFaceDifferent(List<int[]> facesList) {
		random = new Random(SEEDID);
		
		int count = 0;
		for (int[] face : facesList) {
			count += (face.length * 3);
		}
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer returnColourBuffer = stack.mallocFloat(count);
			
			float r = random.nextFloat();
			float b = random.nextFloat();
			float g = random.nextFloat();
			
			for (int[] face : facesList) {
				for (int i = 0; i < (face.length * 3); i++) {
					returnColourBuffer.put(r).put(g).put(b);
				}
				
				r = random.nextFloat();
				b = random.nextFloat();
				g = random.nextFloat();
			}
			returnColourBuffer.flip();
			MemoryStack.stackPop();
			
			return returnColourBuffer;
		}
	}

	
	public static FloatBuffer genVertexBufferColour(List<Object3D> objectList) {
		random = new Random(SEEDID);
		
		int count = 0;
		for (Object3D o3d : objectList) {
			count += (o3d.getPolygonCount() * 3 * 3);
		}
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer returnColourBuffer = stack.mallocFloat(count);
			
			for (Object3D o3d : objectList) {
				for (Vector3i polygon : o3d.getPolygonList()) {
					Vector3f v1 = o3d.getVerticesList().get(polygon.x - 1);
					Vector3f v2 = o3d.getVerticesList().get(polygon.y - 1);
					Vector3f v3 = o3d.getVerticesList().get(polygon.z - 1);
	
					returnColourBuffer.put(v1.x() * 1.1f).put(v1.y() * 1.1f).put(v1.z() * 1.1f);
					returnColourBuffer.put(v2.x() * 1.1f).put(v2.y() * 1.1f).put(v2.z() * 1.1f);
					returnColourBuffer.put(v3.x() * 1.1f).put(v3.y() * 1.1f).put(v3.z() * 1.1f);
				}
			}
			
			returnColourBuffer.flip();
			MemoryStack.stackPop();
			
			return returnColourBuffer;
		}
	}
	
	
	public static FloatBuffer genEveryVertexDifferent(int size) {
		random = new Random(SEEDID);
		
		FloatBuffer returnColourBuffer = MemoryStack.stackPush().mallocFloat(size);
		
		float r = random.nextFloat();
		float b = random.nextFloat();
		float g = random.nextFloat();
		
		for (int i = 0; i < size; i++) {
			returnColourBuffer.put(r).put(g).put(b);
			
			// new rgb
			r = random.nextFloat();
			b = random.nextFloat();
			g = random.nextFloat();
		}
		
		returnColourBuffer.flip();
		MemoryStack.stackPop();
		
		return returnColourBuffer;
	}
	
}
