package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import static renderEngine.ShaderProgram.BUFFER_OBJECT_VERTEX_ATTRIBUTE;
import static renderEngine.ShaderProgram.BUFFER_OBJECT_COLOUR_ATTRIBUTE;

public class Renderer {
	
	/** Size of float in bytes */
	public static final int floatSize = 4;
	
	/** OpenGL render mode when rendering polygon. default is GL_LINE mode */
	private static int gl_polygon_mode = GL_LINE;
	
	/** Total number of vertex to render in a given scene */
	private int vertexCount;
	
	private ShaderProgram shaderProgram;
	
	/** Vertex Array Object */
	private int vao;
	
	/** Vertex Buffer Object */
	private int vbo;
	
	/** Colour Buffer Object */
	private int cbo;
	
	
	public Renderer(ShaderProgram shaderProgram) {
		this.shaderProgram = shaderProgram;
	}
	
	
	/**
	 * Initialises the Render by creating a VAO and 2 VBO for the data we need being:
	 * Vertex / position data in VBO_0 and colour of vertex data in VBO_1
	 * NOTE: VBO is initialised and not specified by any data structures.
	 */
	public void initRenderer() {
		/** VAO */
		// Creates VAO
		vao = glGenVertexArrays();
		// Binds VAO
		glBindVertexArray(vao);
		
		/** VBO_0: Vertex / Position Data */
		// Creates Buffer Object
		vbo = glGenBuffers();
		// Binds VBO
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		// Attribute pointer (How the data is Interpreted)
//		glVertexAttribPointer(BUFFER_OBJECT_VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, 3 * floatSize, 0);
		// Enbales VBO_0
//		glEnableVertexAttribArray(BUFFER_OBJECT_VERTEX_ATTRIBUTE);
		// Unbinds the current Buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		/** VBO_1: Colour Data */
		// Creates Buffer Object
		cbo = glGenBuffers();
		// Binds VBO
		glBindBuffer(GL_ARRAY_BUFFER, cbo);
		// Attribute pointer (How the data is Interpreted)
//		glVertexAttribPointer(BUFFER_OBJECT_COLOUR_ATTRIBUTE, 3, GL_FLOAT, false, 3 * floatSize, 0);
		// Enbales VBO_1
//		glEnableVertexAttribArray(BUFFER_OBJECT_COLOUR_ATTRIBUTE);
		// Unbinds the current Buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		/** Unbind VAO */
		glBindVertexArray(0);
		
		// Depth test. Otherwise some polygons further away render on-top of closer ones
		glEnable (GL_DEPTH_TEST);
	}
	
	
	/**
	 * Updates the VAO by updating the data in both VBO's and also specifying the data structure
	 * @param vertex_data Vertex buffer data; Tightly Packed with stride = 3; [x,y,z, x,y,z, x,y,z ...]
	 * @param colour_data Colour buffer data; Tightly Packed with stride = 3; [r,g,b, r,g,b, r,g,b ...]
	 * @param vertexCount number of vertex
	 */
	public void updateRenderData(FloatBuffer vertex_data, FloatBuffer colour_data, int vertexCount) {
		this.vertexCount = vertexCount;
		
		/** VAO */
		// Binds VAO
		glBindVertexArray(vao);
		
		/** VBO_0: Vertex / Position Data */
		// Binds VBO
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		// submits the buffer data to the GPU
		glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
		// Attribute pointer (How the data is Interpreted)
		glVertexAttribPointer(BUFFER_OBJECT_VERTEX_ATTRIBUTE, 3, GL_FLOAT, false, 3 * floatSize, 0);
		// Enbales VBO_0
		glEnableVertexAttribArray(BUFFER_OBJECT_VERTEX_ATTRIBUTE);		
		// Unbinds the current Buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		/** VBO_1: Colour Data */
		// Binds VBO
		glBindBuffer(GL_ARRAY_BUFFER, cbo);
		// submits the buffer data to the GPU
		glBufferData(GL_ARRAY_BUFFER, colour_data, GL_STATIC_DRAW);
		// Attribute pointer (How the data is Interpreted)
		glVertexAttribPointer(BUFFER_OBJECT_COLOUR_ATTRIBUTE, 3, GL_FLOAT, false, 3 * floatSize, 0);
		// Enbales VBO_0
		glEnableVertexAttribArray(BUFFER_OBJECT_COLOUR_ATTRIBUTE);
		// Unbinds the current Buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		/** Unbind VAO */
		glBindVertexArray(0);
	}
	
	
	public void renderData(int mode, Camera camera, Transformation transformation) {
		// Bind VAO
		glBindVertexArray(vao);
		// Bind VBO
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		// Enbales VBO_0
		glEnableVertexAttribArray(BUFFER_OBJECT_VERTEX_ATTRIBUTE);
		// Enbales VBO_1
		glEnableVertexAttribArray(BUFFER_OBJECT_COLOUR_ATTRIBUTE);
		
		// Uses Shader with given camera and transformations
		shaderProgram.useShader();
		shaderProgram.setCamera(camera);
		shaderProgram.setTransform(transformation);
		
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_FRONT);
		
		// rendering polygon mode
		glPolygonMode(GL_FRONT_AND_BACK, gl_polygon_mode);
		// Draws the vertes to the screen
		glDrawArrays(mode, 0, vertexCount * 3);
		
		// Disables VBO_0
		glDisableVertexAttribArray(BUFFER_OBJECT_VERTEX_ATTRIBUTE);
		// Disables VBO_1
		glDisableVertexAttribArray(BUFFER_OBJECT_COLOUR_ATTRIBUTE);
		// Unbind VBO then VAO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	public static int getRendererPolygonMode() {
		return gl_polygon_mode;
	}
	
	public static void setRendererPolygonMode(int polygon_mode) {
		gl_polygon_mode = polygon_mode;
	}

}
