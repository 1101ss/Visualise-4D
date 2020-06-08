package renderEngine;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Matrix4f;

/**
 * 
 * @author Shonam
 * </br>
 * Camera Class provides transformations for a camera in 3D space.
 * When we provide a transformation, here we are transforming the scene as oppose to the camera.
 * This achieves the same effect as if the scene were stationary and the camera was moving in space.
 */
public class Camera {
	
	public static final float CAMERA_SPEED = 0.025f;
	
	private Vector3f position;
	private Quaternionf rotation;
	private Matrix4f projection;
	
	public Camera() {
		position = new Vector3f();
		rotation = new Quaternionf();
		projection = new Matrix4f();
	}
	
	/**
	 * 
	 * @return Matrix4f The transformation for the scene; as if the camera were to make the transformations itself
	 */
	public Matrix4f getTransformation() {
		Matrix4f returnMatrix4f = new Matrix4f();
		
		// The scene is moving as oppose to the camers, therefore we apply the rotations followed by translations
		returnMatrix4f.rotate(rotation.conjugate(new Quaternionf()));
		returnMatrix4f.translate(position.mul(-1, new Vector3f()));
		
		return returnMatrix4f;
	}

	/**
	 * Orthographic Projection</br>
	 * A Projection type for flat 2D projections with no depth
	 * 
	 * @param left The distance from the center to the left edge
	 * @param right The distance from the center to the right edge
	 * @param bottom The distance from the center to the bottom edge
	 * @param top The distance from the center to the top edge
	 */
	public void setOrthographicProjection(float left, float right, float bottom, float top) {
		projection.setOrtho2D(left, right, bottom, top);
	}
	
	/**
	 * PerspectiveProjection</br>
	 * A Projection type for perfective projections, 3D projections
	 * 
	 * @param fov Field of View
	 * @param width window width
	 * @param height window height
	 * @param zNear The distance of the near clipping distance
	 * @param zFar The distance of the far clipping distance
	 */
	public void setPerspectiveProjection(float fov, float width, float height, float zNear, float zFar) {
		projection.setPerspective(fov, (width / height), zNear, zFar);
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
	
	/**
	 * other rotations features
	 * Mouse input rotations
	 */
	public void rotateXBy(float angle) {
//		if (this.getRotation().x() == 1.0f) {
//			this.rotation.rotateX(-1.0f);
//		} else if (this.getRotation().x() == -1.0f) {
//			this.rotation.rotateX(1.0f);
//		}else {
			this.rotation.rotateX(this.getRotation().x() + angle);
//		}
	}
	
	public void rotateYBy(float angle) {
//		if (this.getRotation().y() == 1.0f) {
//			this.rotation.rotateY(-1.0f);
//		} else if (this.getRotation().y() == -1.0f) {
//			this.rotation.rotateY(1.0f);
//		} else {
			this.rotation.rotateY(this.getRotation().y() + angle);
//		}
	}
	
	

	public Matrix4f getProjection() {
		return projection;
	}

//	public void setProjection(Matrix4f projection) {
//		this.projection = projection;
//	}

}
