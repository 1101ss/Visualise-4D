package renderEngine;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * 
 * @author Shonam
 * 
 * Transformation Class
 * Transforms (position, rotation, scale) of a given object
 *
 */
public class Transformation {
	
	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	public Transformation() {
		position = new Vector3f();
		rotation = new Quaternionf();
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	/**
	 * 
	 * @return The Matrix4f transformation from a given position transform, rotation and scale
	 */
	public Matrix4f getTransformation() {
		Matrix4f returnMatrix4f = new Matrix4f();
		
		returnMatrix4f.translate(position);
		returnMatrix4f.rotate(rotation);
		returnMatrix4f.scale(scale);
		
		return returnMatrix4f;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Quaternionf getRotation() {
		return rotation;
	}

	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

}
