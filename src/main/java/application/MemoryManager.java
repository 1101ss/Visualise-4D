package application;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import renderEngine.ObjectColour;
import utils.FourDtoThreeD;
import utils.Object3D;
import utils.Object4D;

/**
 * MemoryManager manages the current list of Object4D's used in the Application.
 * All Instances of the MemoryManager are the same. Therefore altering one instance will affect all of MemoryManager
 * @author Shonam
 *
 */
public class MemoryManager {
	
	/** The Single Instance of MemoryManager */
	private static MemoryManager memoryManager;
	
	/**
	 * Memory Manager 4D Object's Array: List of all the 4D objects that will be visible in the display window
	 */
	private static List<Object4D> MM_object4DArray = new ArrayList<Object4D>();
	
	private List<Object3D> MM_object3DArray_CONVERTED = null;
	
	/**
	 * if the content of the MM_object4DArray have changed and still need to be updated in DisplayManager 
	 */
	private boolean MemoryManagerUpdatedStatus = false;
	
	/** Total number of vertex to render in a given scene */
	private int vertexCount = 0;
	
	/**
	 * Default Cons
	 */
	private MemoryManager() {
		System.out.println("Memory Manager Initilised");
	}
	
	
	/*
	 * creates and returns the single instance of the MemoryManager
	 */
	public static MemoryManager getInstance() {
		if (memoryManager == null)
			memoryManager = new MemoryManager();
			
		return memoryManager;
	}
	
	
	/**
	 * Loads a 4D Object to the Memory Manager
	 * @param object4D
	 */
	public void load4DintoMM(Object4D object4D) {
		
		MM_object4DArray.add(object4D);
//		Object3D object3D = FourDtoThreeD.Convert(object4D);
//		MM_object3DArray_CONVERTED.add(object3D);
//		vertexCount += object3D.getPolygonCount() * 3;
		
		MemoryManagerUpdatedStatus = true;
		
		System.out.println("Loading Object to MM; now size = " + MM_object4DArray.size());
	}
	
	
	/**
	 * Removes the first occurrence of the 4D Object from Memory Manager
	 * @param object4d
	 */
	public void remove4DfromMM(int index) {
		if (MM_object4DArray.size() > 0) {
//			vertexCount -= MM_object3DArray_CONVERTED.get(index).getPolygonCount() * 3;
			MM_object4DArray.remove(index);
//			MM_object3DArray_CONVERTED.remove(index);
			
			MemoryManagerUpdatedStatus = true;
			System.out.println("Removing Object to MM; now size = " + MM_object4DArray.size());
		} else {
			System.out.println("No Object to remove");
		}
	}

	
	/**
	 * 
	 * @return Memory Manager Updated state
	 */
	public boolean MemoryManagerUpdatedStatus() {
		return this.MemoryManagerUpdatedStatus;
	}
	
	/**
	 * After the contents in MemoryManager have been render, this should be called
	 * which sets the update status to false
	 */
	public void MemoryManagerContentsRendered() {
		this.MemoryManagerUpdatedStatus = false;
	}
	
	/**
	 * If an outside process needs to set update status to false, this should be called
	 */
	public void MemoryManagerContentsUnRendered() {
		this.MemoryManagerUpdatedStatus = true;
	}
	
	
	public FloatBuffer getMMVertexBuffer() {
		if (MemoryManagerUpdatedStatus == true)
			vertexCount = 0;
		
		System.out.println("MMvertexCount: " + vertexCount);
		
//		List<Object3D> convertedObjectsArray = new ArrayList<Object3D>();
		MM_object3DArray_CONVERTED = new ArrayList<Object3D>();
		
		for (Object4D object4D: MM_object4DArray) {
			// convert 4D object to its 3D counterpart
			Object3D object3D = FourDtoThreeD.Convert_version2(object4D);
			MM_object3DArray_CONVERTED.add(object3D);
			// each polygon has 3 vertex
			vertexCount += (object3D.getPolygonCount() * 3);
		}
		
		FloatBuffer vertexBuffer = MemoryStack.stackPush().mallocFloat(vertexCount * 3);
		for (Object3D object3D: MM_object3DArray_CONVERTED) {
			vertexBuffer.put(object3D.getVertexBuffer());
		}
		
		vertexBuffer.flip();
//		System.out.println("MMvertexCount: " + vertexCount);
		return vertexBuffer;
	}
	
	public FloatBuffer getMMColourBuffer() {
//		return ObjectColour.genOneColour(vertexCount, 1.0f, 1.0f, 1.0f);
//		return ObjectColour.genEvery3PolyDifferent(vertexCount);
		return ObjectColour.genVertexBufferColour(MM_object3DArray_CONVERTED);
	}
	
	public int getMMVertexCount() {
		return vertexCount;
	}
	
	
	/**
	 * Methods for retrieving an objects properties from the MemoryManager given the objects index in the MM
	 */
	public float getX(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getX();
	}
	
	public float getY(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getY();
	}
	
	public float getZ(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getZ();
	}
	
	public float getW(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getW();
	}

	public float getXY(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getXY();
	}
	
	public float getXW(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getXZ();
	}
	
	public float getXZ(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getXW();
	}
	
	public float getYZ(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getYZ();
	}
	
	public float getYW(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getYW();
	}
	
	public float getZW(int objectIndex) {
		return MM_object4DArray.get(objectIndex).getZW();
	}
	
	public Object4D getObject(int objectIndex) {
		return MM_object4DArray.get(objectIndex);
	}
	
	public int getObjectCount() {
		return MM_object4DArray.size();
	}
	
}
