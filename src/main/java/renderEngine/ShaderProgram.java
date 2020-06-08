package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {
	
	private final static String VERTEXSHADER_FILEEXTENSION = ".vert";
	private final static String FRAGMENTSHADER_FILEEXTENSION = ".frag";
	
	/**
	 * Specifies the VAO index locations for each VBO attribute
	 */
	public final static int BUFFER_OBJECT_VERTEX_ATTRIBUTE = 0;
	public final static int BUFFER_OBJECT_COLOUR_ATTRIBUTE = 1;
	
	private int shaderProgram;
	private int vertexShader;
	private int fragmentShader;
	
//	private int uniformMatrixProjection;
//	private int uniformMatrixTransformWorld;
//	private int uniformMatrixTransformObject;
	
	private int uniMat4Model;
	private int uniMat4View;
	private int uniMat4Projection;
	
	
	public ShaderProgram() {
		
	}
	
	public void create() {
		/**
		 * Vertex Shader and compile
		 */
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, GLSLReader("src/main/resources/shaders/VertexShader" + VERTEXSHADER_FILEEXTENSION));// "vs" before
		glCompileShader(vertexShader);
		
		// if shader was not compiled correctly, exception will be thrown
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) != GL_TRUE)
			throw new RuntimeException(glGetShaderInfoLog(vertexShader));
		
		/**
		 * Fragment Shader and compile
		 */
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, GLSLReader("src/main/resources/shaders/FragmentShader" + FRAGMENTSHADER_FILEEXTENSION));// "fs" vefore
		glCompileShader(fragmentShader);
			
		// if shader was not compiled correctly, exception will be thrown
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != GL_TRUE)
			throw new RuntimeException(glGetShaderInfoLog(fragmentShader));
		
		
		/**
		 * Shader Program and Link
		 */
		// creates the openGL shader program
		shaderProgram = glCreateProgram();
		// attaches vertex shader and fragment shader to to the specified program
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		
//		glBindFragDataLocation(shaderProgram, 0, "fragColor");
//		glBindFragDataLocation(shaderProgram, 0, "fragmentColor");
		// Position information will be attribute 0
        glBindAttribLocation(shaderProgram, 0, "position");
        // Color information will be attribute 1
        glBindAttribLocation(shaderProgram, 1, "color");
		
		// Links the ShaderProgram and checks weather this worked corrrectly
		glLinkProgram(shaderProgram);
		if (glGetProgrami(shaderProgram, GL_LINK_STATUS) != GL_TRUE)
			throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
		
		// Validates the ShaderProgram and checks weather this worked correctly
		glValidateProgram(shaderProgram);
		if (glGetProgrami(shaderProgram, GL_VALIDATE_STATUS) != GL_TRUE)
			throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
		
		/**
		 * Specifying Vertex Attributes
		 */
//		int floatSize = 4;
//		
//		int posAttrib = glGetAttribLocation(shaderProgram, "position");
//		glEnableVertexAttribArray(posAttrib);
//		glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * floatSize, 0);
//		
//		int colAttrib = glGetAttribLocation(shaderProgram, "color");
//		glEnableVertexAttribArray(colAttrib);
//		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * floatSize, 3 * floatSize);
		
//		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
//		glVertexAttribPointer(1, 3, GL_FLOAT, false, 3 * 4, 0);
		
		/**
		 * Set Uniform Variables 
		 */
		uniMat4Model = glGetUniformLocation(shaderProgram, "model");
//		Matrix4f model = new Matrix4f();
//		glUniformMatrix4fv(uniModel, false, model.get(vertices));
//		setUniform(uniMat4Model, model);

		uniMat4View = glGetUniformLocation(shaderProgram, "view");
//		Matrix4f view = new Matrix4f();
//		glUniformMatrix4fv(uniView, false, view.get(vertices));
//		setUniform(uniMat4View, view);
		
		uniMat4Projection = glGetUniformLocation(shaderProgram, "projection");
//		float ratio = 640f / 480f;
//		Matrix4f projection =  new Matrix4f();
//		projection.ortho(-ratio, ratio, -1f, 1f, -1f, 1f);
//		glUniformMatrix4fv(uniProjection, false, projection.get(vertices));
//		setUniform(uniMat4Projection, projection);
		
		// 2
//		uniformMatrixProjection = glGetUniformLocation(shaderProgram, "cameraProjection");
//		uniformMatrixTransformWorld= glGetUniformLocation(shaderProgram, "transformWorld");
//		uniformMatrixTransformObject = glGetUniformLocation(shaderProgram, "transformObject");
		
//		return shaderProgram;
	}
	
	public void setUniform(int location, Matrix4f value) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(4 * 4);
			value.get(buffer);
			glUniformMatrix4fv(location, false, buffer);
        }
	}
	
	public void cleanUp() {
//		glDeleteVertexArrays(vao);
//		glDeleteBuffers(vbo);
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(shaderProgram);
	}
	
	public void useShader() {
		glUseProgram(shaderProgram);
	}
	
	public void setCamera(Camera camera) {
		if (uniMat4Projection != -1) {
			float matrix4x4[] = new float[16];
			camera.getProjection().get(matrix4x4);
			glUniformMatrix4fv(uniMat4Projection, false, matrix4x4);
		}
		
		if (uniMat4View != -1) {
			float matrix4x4[] = new float[16];
			camera.getTransformation().get(matrix4x4);
			glUniformMatrix4fv(uniMat4View, false, matrix4x4);
		}
		
	}
	
	public void setTransform(Transformation transformation) {
		if (uniMat4Model != -1) {
			float matrix4x4[] = new float[16];
			transformation.getTransformation().get(matrix4x4);
			glUniformMatrix4fv(uniMat4Model, false, matrix4x4);
		}
	}
	
//	public int getAttributeLocation(String attributeName) {
//		return glGetAttribLocation(shaderProgram, attributeName);
//	}
//	
//	public int getUniformLocation(String attributeName) {
//		return glGetUniformLocation(shaderProgram, attributeName);
//	}
	
	/**
	 * 
	 * @param fileLocation file url location of the GLSL file that needs reading
	 * @return returns the GLSH file / Shader code as a String sequence
	 */
	public static String GLSLReader(String fileLocation) {
		String glsl = "";
//		StringBuilder outGLSL = new StringBuilder();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(new File(fileLocation));
			bufferedReader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			System.err.printf("File (%s) failed to load.%n", fileLocation);
			e.printStackTrace();
		}
		
		// Loading in GLSL file data
		String data = null;
		try {
			while ((data = bufferedReader.readLine()) != null) {
//				System.out.print(data + "\n");
				glsl += data + "\n";
			}
			
		} catch (IOException e1) {
			System.out.println("GLSL Data Loading Finished");
//			e1.printStackTrace();
		}
		// end
		try {
			fileReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to Close GLSLReader");
		}
		
		return glsl;
	}
	
//	private final CharSequence vs = "#version 330\n"
//			+ "\n"
//			+ "layout (location = 0) in vec3 position;\n"
//			+ "\n"
//			+ "void main() {\n"
//			+ "    gl_Position = vec4(position, 1);\n"
//			+ "}";
//	
//	private final CharSequence vs2 = "#version 330\n"
//			+ "\n"
//			+ "layout (location = 0) in vec3 position;\n"
//			+ "\n"
//			+ "uniform mat4 transformWorld;\n"
//			+ "uniform mat4 transformOnject;\n"
//			+ "uniform mat4 camersProjection;\n"
//			+ "\n"
//			+ "void main() {\n"
//			+ "    gl_Position = camersProjection * transformWorld * transformOnject * vec4(position, 1);\n"
//			+ "}";
//	
//	private final CharSequence fs = "#version 330"
//			+ "\n"
//			+ "out vec4 fragmentColor;\n"
//			+ "\n"
//			+ "void main() {\n"
//			+ "    fragmentColor = vec4(1,1,1,0);\n"
//			+ "}";
//	
//	private final CharSequence vertexSource = "#version 150 core\n"
//		+ "\n"
//		+ "in vec3 position;\n"
//		+ "in vec3 color;\n"
//		+ "\n"
//		+ "out vec3 vertexColor;\n"
//		+ "\n"
//		+ "uniform mat4 model;\n"
//		+ "uniform mat4 view;\n"
//		+ "uniform mat4 projection;\n"
//		+ "\n"
//		+ "void main() {\n"
//		+ "    vertexColor = color;\n"
//		+ "    mat4 mvp = projection * view * model;\n"
//		+ "    gl_Position = mvp * vec4(position, 1.0);\n"
//		+ "}";
//
//	private final CharSequence fragmentSource = "#version 150 core\n"
//		+ "\n"
//		+ "in vec3 vertexColor;\n"
//		+ "\n"
//		+ "out vec4 fragColor;\n"
//		+ "\n"
//		+ "void main() {\n"
//		+ "    fragColor = vec4(vertexColor, 1.0);\n"
//		+ "}";

}
