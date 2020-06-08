package renderEngine;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.windows.MOUSEINPUT;
import org.lwjgl.BufferUtils;
//import org.lwjgl.system.*;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import application.MemoryManager;
//import application.Matrix4f;
import application.application;
import utils.FourDtoThreeD;
import utils.Object3D;
import utils.Object3DLoader;
import utils.Object4D;
import utils.Object4DLoader;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.joml.AxisAngle4d;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2d;
import org.joml.Vector3f;
//import javax.vecmath.Vector3f;
import org.joml.Vector3i;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.GL_VERSION;
//import javax.management.RuntimeErrorException;
//import static org.lwjgl.opengl.GL11.*; // OpenGL version 1.1
//import static org.lwjgl.opengl.GL30.*; // OpenGL version 3.0 (2008); make sure GPU drivers are up to date
//import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL15.*;
//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

import static renderEngine.Camera.CAMERA_SPEED;

/**
 * 
 * @author Shonam
 *
 */
public class DisplayManager extends Thread {
	
	private static long window;
	private static int width_ = 960;
	private static int height_ = 720;
	private long targetFPS = 144;
	
//	private boolean changedWindowSize;
//	private GLFWWindowSizeCallback windowSizeCallback;
	
	private static double mouseSensitivity = 0.5;
	
	private MemoryManager memoryManager = MemoryManager.getInstance();
	private ShaderProgram shaderProgram;
	private Renderer renderer;
	
	/**
	 * This method starts up the display
	 */
	@Override
	public void run()
//	public void startDisplay()
	{
		initiliseDisplay();
		
//		System.out.println("OpenGL: " + glfwGetVersionString());
//	    System.out.println("LWJGL version " + org.lwjgl.system.getVersion());
//		int org.lwjgl.opengl.GL11C.GL_VERSION : 7938
//	    System.out.println("OpenGL version " + glGetString(GL_VERSION));
		
		loopDisplay();
		
		closeDisplay();
		
	}

	
	/**
	 * This method initialises the GLFW display
	 */
	private void initiliseDisplay() 
	{
		System.out.println("Initilizing GLFW Display");
		// Error callback in case something goes wrong. prints to System.err
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialising GLFW 
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		/**
		 * GLFW profile context config
		 * Strictly version OpenGL 3.2 or above
		 */
		// resets window hints to default
		glfwDefaultWindowHints();
		
		// Specifies to use OpenGL version 3.2
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		
		// other profile context config
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		// create GLFW window, window equals null if failed to do so (and will throw error)
		window = glfwCreateWindow(width_, height_, "3D Viewport", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Unable to create the GLFW window");
		}
		
		// will get the monitor resolution
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, ((videoMode.width() - width_) / 2) - 400, (videoMode.height() - height_) / 2);
		
		// Make the OpenGL context current
		// i.e. ONE thread can only have ONE OpenGL context current/window
		glfwMakeContextCurrent(window);
				
		// Enables V-sync
		glfwSwapInterval(1);
				
		// Displays the window
		glfwShowWindow(window);
		
	}
	
	
	/**
	 * THE MAIN RENDER LOOP
	 * 
	 * This loop is the responsible for everything that happens
	 * within the GLFW window e.g. displaying scene objects
	 */
	private void loopDisplay()
	{
		System.out.println("Starting GLFW Display Main Loop");
		
		GL.createCapabilities();
	    System.out.println("OpenGL Version " + glGetString(GL_VERSION));
		
//		Object3D o3d = Object3DLoader.loadObj3D("src/main/resources/3Dmodels/testShape");
//		Object3D o3d = Object3DLoader.loadObj3D("src/main/resources/3Dmodels/testShape2");
		
	    
	    /**
	     * TODO before game loop, load from objects from MM
	     */
//	    Object4D o4d = Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape");
//	    Object3D o3d = FourDtoThreeD.Convert(o4d);
	    
//	    Object4D o4d_2 = Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape2");
//	    Object3D o3d_2 = FourDtoThreeD.Convert(o4d_2);
	    
//		o3d.loadVertexBuffer();
//		o3d2.loadVertexBuffer();
		
		// Creates VAO
//		int vao = glGenVertexArrays();
		
		// Binds VAO
//		glBindVertexArray(vao);
		
		// Creates VBO buffer
//		int vbo = glGenBuffers();
		
		// Binds VBO
//		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		// uploads vertices Buffer to the GUP
//		glBufferData(GL_ARRAY_BUFFER, o3d.getVertexBuffer(), GL_STATIC_DRAW);
//		glBufferData(GL_ARRAY_BUFFER, o3d_2.getVertexBuffer(), GL_STATIC_DRAW);
		
		// Not needed to call manually unless there is no try{} block
		// MemorySatck implements AutoClosable class so in the try{} block it will auto close the resource
//		MemoryStack.stackPop();
	    
	    shaderProgram = new ShaderProgram();
	    renderer = new Renderer(shaderProgram);
	    
//	    renderer.initData(o3d.getVertexBuffer(), 
//	    		ObjectColour.genOneColour(o3d.getPolygonCount() * 3, 0.0f, 0.2f, 1.0f), 
//	    		o3d.getPolygonCount() * 3);
	    
//	    application.memoryManager.load4DintoMM(Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape"));
	    
//	    renderer.initData(application.memoryManager.getMMVertexBuffer(), 
//	    		application.memoryManager.getMMColourBuffer(), 
//	    		application.memoryManager.getMMVertexCount());
	    
	    renderer.initRenderer();
	    shaderProgram.create();
	    
//	    renderer.RenderData(o3d.getVertexBuffer(), 
//				ObjectColour.setOneColour(o3d.getPolygonCount() * 3, 1.0f, 1.0f, 1.0f),
//				o3d.getPolygonCount() * 3);
		
//		shaderProgram = new ShaderProgram();
//		shaderProgram.create();
		
		Camera camera = new Camera();
		// initial x,y,x camera position
		float cX = 0.0f;
		float cY = 0.4f;
		float cZ = 3.0f;
		Transformation transformation = new Transformation();
		
		camera.setPerspectiveProjection((float) Math.toRadians(90), width_, height_, 0.01f, Float.POSITIVE_INFINITY);
		camera.setPosition(new Vector3f(cX, cY, cZ));// camera position
		camera.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(0), new Vector3f(0.0f, 0.0f, 0.0f))));// camera rotation
		
//		transformation.setRotation(transformation.getRotation().rotateAxis((float) Math.toRadians(1.0f), 0.0f, 1.0f, 0.0f););
//		transformation.getRotation().rotateAxis((float) Math.toRadians(40.0f), 1.0f, 0.0f, 0.0f);
//		System.out.println("Transformation Rotation" + transformation.getRotation().toString());
		
		
		/**
		 * #####################################
		 * NEW END
		 * #####################################
		 */
		
		
		// how long one frame lasts i.e. Time Period = 1 / Frequency
		double frameTime = 1.0d / (double) (targetFPS);
		
		// keeps track of the loop timer, in ms by x1000
		double loopStartTime;
		
		// keeps track of the seconds passing for a fps counter
		double secondsTimer = glfwGetTime();
		// keeps track of the current fps by adding 1 every frame. resets to 0 after 1s
		int fps = 0;
		
		// get the courser position
		DoubleBuffer xPos = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yPos = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer xPos_previous = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yPos_previous = BufferUtils.createDoubleBuffer(1);
		
		DoubleBuffer xPos_release = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yPos_release = BufferUtils.createDoubleBuffer(1);
		
		
		// scroll stuff
		Vector2d scrollWheel = new Vector2d();
		Vector2d scrollWheel_change = new Vector2d();
		glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
			scrollWheel.set(xoffset, yoffset);
			});
		
		
		/*
		 * MAIN LOOP
		 */
		while (!glfwWindowShouldClose(window)) {
			loopStartTime = glfwGetTime() * 1000;
			
			// Clears the framebuffers
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//			glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			
//			glEnable(GL_BLEND);
//			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			/**
			 * ###############################################
			 * NEW
			 * ###############################################
			 */
//			glDrawArrays(GL_TRIANGLES, 0, o3d.getPolygonCount() * 3 * 3 * 3);
//			glBindVertexArray(vao);
//			renderer.render(GL_TRIANGLES);
//			renderer.renderData(GL_TRIANGLES, camera, transformation);
			
			// if true, contents in MM have been updated/changed and need rendering
			if (memoryManager.MemoryManagerUpdatedStatus() == true) {
				renderer.updateRenderData(memoryManager.getMMVertexBuffer(), 
						memoryManager.getMMColourBuffer(), 
						memoryManager.getMMVertexCount());
				
				memoryManager.MemoryManagerContentsRendered();
			}
			renderer.renderData(GL_TRIANGLES, camera, transformation);
			
			
			// if the mouse is over window
			if (glfwGetWindowAttrib(window, GLFW_HOVERED) == GLFW_TRUE) {
//				System.out.println("mouse over window");
				
				scrollWheel_change.set(0.0d, 0.0d);
				scrollWheel_change.set(scrollWheel.x, scrollWheel.y);
				scrollWheel.set(0.0d, 0.0d);
			}
			
			// MOUSE EVENTS
			if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE || scrollWheel_change.y > 0) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cZ -= CAMERA_SPEED * 10;
				} else {
					cZ -= CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE || scrollWheel_change.x < 0) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cX -= CAMERA_SPEED * 10;
				} else {
					cX -= CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE || scrollWheel_change.y < 0) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cZ += CAMERA_SPEED * 10;
				} else {
					cZ += CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE || scrollWheel_change.x > 0) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cX += CAMERA_SPEED * 10;
				} else {
					cX += CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cY += CAMERA_SPEED * 10;
				} else {
					cY += CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			if (glfwGetKey(window, GLFW_KEY_C) == GL_TRUE) {
				if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GL_TRUE) {
					cY -= CAMERA_SPEED * 10;
				} else {
					cY -= CAMERA_SPEED;
				}
				camera.setPosition(new Vector3f(cX, cY, cZ));
			}
			
			// Left Mouse Button
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GL_FALSE) {
				glfwGetCursorPos(window, xPos_previous, yPos_previous);
			}
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
				glfwGetCursorPos(window, xPos, yPos);
				
//				System.out.println("xpos: " + xPos.get(0) + ", ypos: " + yPos.get(0));
//				System.out.println("xpos diff: " + (xPos.get(0) - xPos_previous.get(0)) + ", ypos diff: " + (yPos.get(0) - yPos_previous.get(0)));
				
//				camera.rotateYBy((float) (mouseSensitivity * (xPos.get(0) - xPos_previous.get(0))));
//				camera.rotateXBy((float) (mouseSensitivity * (yPos.get(0) - yPos_previous.get(0))));
				
//				camera.setRotation(camera.getRotation().rotateAxis((float) Math.toRadians(mouseSensitivity * (xPos.get(0) - xPos_previous.get(0))), 0.0f, 1.0f, 0.0f));
//				camera.setRotation(camera.getRotation().rotateAxis((float) Math.toRadians(mouseSensitivity * (yPos.get(0) - yPos_previous.get(0))), 1.0f, 0.0f, 0.0f));
				
				transformation.setRotation(transformation.getRotation().rotateAxis((float) Math.toRadians(mouseSensitivity * (xPos.get(0) - xPos_previous.get(0))), 0.0f, 1.0f, 0.0f));
				transformation.setRotation(transformation.getRotation().rotateAxis((float) Math.toRadians(mouseSensitivity * (yPos.get(0) - yPos_previous.get(0))), 1.0f, 0.0f, 0.0f));
				
				glfwGetCursorPos(window, xPos_previous, yPos_previous);
			}
			// Left Mouse Button
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_FALSE) {
				glfwGetCursorPos(window, xPos_release, yPos_release);
			}
			if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
				glfwGetCursorPos(window, xPos, yPos);
				
				float dx = (float) ((xPos_release.get(0) - xPos.get(0)) * 0.01d);
				float dy = (float) ((yPos_release.get(0) - yPos.get(0)) * 0.01d);
				cX += dx;
				cY -= dy;
				
				camera.setPosition(new Vector3f(cX, cY,	cZ));
				
				glfwGetCursorPos(window, xPos_release, yPos_release);
			}
			
			
			
			// END MOUSE EVENTS
			
//			alpha += 0.2f;
//			transformation.setPosition(new Vector3f((float)Math.sin(Math.toRadians(alpha)), 0.0f, 0.0f));
//			transformation.getRotation().rotateAxis((float) Math.toRadians(1.0f), 0.0f, 1.0f, 0.0f);
			
//			shaderProgram.useShader();
//			shaderProgram.setCamera(camera);
//			shaderProgram.setTransform(transformation);
//			renderer.render(GL_TRIANGLES);
//			renderer.renderData(GL_TRIANGLES, camera, transformation);
			/**
			 * ######################################################
			 * END NEW
			 * ####################################################
			 */
			
			// Updates frame contents
			glfwSwapBuffers(window);
			glfwPollEvents();
			
			
			/** FPS RELATED */
			// V-Sync (fps of monitor)
//			glfwSwapInterval(1);
			
			// FPS of the window
			try {
				
				fps++;
				Thread.sleep((long) ((frameTime * 1000) - ((glfwGetTime() * 1000) - loopStartTime)));
				
			} catch (InterruptedException e) {
//				e.printStackTrace();
			} 
			catch (IllegalArgumentException e) {
//				e.printStackTrace();
			}
			
			// if 1 second has passed, show fps count, reset to 0, reset timer
			if (glfwGetTime() > secondsTimer + 1.0d) {
				// prints FPS:
//				System.out.println("FPS: " + fps);
				glfwSetWindowTitle(window, "3D Viewport | FPS: " + fps);
				
				// resets FPS counter
				fps = 0;
				
				// resets fps seconds timer to current time
				secondsTimer = glfwGetTime();
			}
			/** END FPS RELATED */
		} /** END RENDER LOOP */
		
	}
	
	
	private void closeDisplay()
	{
		System.out.println("Closing GLFW Display");
		memoryManager.MemoryManagerContentsUnRendered();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
	}
	
	public void terminateDisplay() {
		glfwTerminate();
	}
	
//	private void drawTriangle() 
//	{
//		// creates and VAO and binds it
//		int vao = glGenVertexArrays();
//		glBindVertexArray(vao);
//		
////		try (MemoryStack stack = MemoryStack.stackPush()) {
//			// creates a triangle (vertex and colour data)
//			MemoryStack stack = MemoryStack.stackPush();
//			FloatBuffer vertices = stack.mallocFloat(3 * 6);
//			vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
//			vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
//			vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
//			vertices.flip();
//			
//			// upload a VBO the GPU
//			int vbo = glGenBuffers();
//			glBindBuffer(GL_ARRAY_BUFFER, vbo);
//			glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
//			MemoryStack.stackPop();
//			
//			/*
//			 * SHADER
//			 */
//			// vertex shader
//			int vertexShader = glCreateShader(GL_VERTEX_SHADER);
//			glShaderSource(vertexShader, vertexSource);
//			glCompileShader(vertexShader);
//
//			int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
//			glShaderSource(fragmentShader, fragmentSource);
//			glCompileShader(fragmentShader);
//			
//			// Shader programme
//			int shaderProgram = glCreateProgram();
//			glAttachShader(shaderProgram, vertexShader);
//			glAttachShader(shaderProgram, fragmentShader);
//			glBindFragDataLocation(shaderProgram, 0, "fragColor");
//			glLinkProgram(shaderProgram);
//			
//			
//			// Specify Vertex Attribute
//			int floatSize = 4;
//
//			int posAttrib = glGetAttribLocation(shaderProgram, "position");
//			glEnableVertexAttribArray(posAttrib);
//			glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * floatSize, 0);
//
//			int colAttrib = glGetAttribLocation(shaderProgram, "color");
//			glEnableVertexAttribArray(colAttrib);
//			glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * floatSize, 3 * floatSize);
//
//			// Setting uniform variables
//			int uniModel = glGetUniformLocation(shaderProgram, "model");
//			Matrix4f model = new Matrix4f();
//			glUniformMatrix4fv(uniModel, false, vertices);
//
//			int uniView = glGetUniformLocation(shaderProgram, "view");
//			Matrix4f view = new Matrix4f();
//			glUniformMatrix4fv(uniView, false, vertices);
//
//			int uniProjection = glGetUniformLocation(shaderProgram, "projection");
//			float ratio = 640f / 480f;
//			Matrix4f projection = Matrix4f.orthographic(-ratio, ratio, -1f, 1f, -1f, 1f);
//			glUniformMatrix4fv(uniProjection, false, vertices);
//	}
	
//	private final CharSequence vertexSource
//		= "#version 150 core\n"
//			+ "\n"
//			+ "in vec3 position;\n"
//			+ "in vec3 color;\n"
//			+ "\n"
//			+ "out vec3 vertexColor;\n"
//			+ "\n"
//			+ "uniform mat4 model;\n"
//			+ "uniform mat4 view;\n"
//			+ "uniform mat4 projection;\n"
//			+ "\n"
//			+ "void main() {\n"
//			+ "    vertexColor = color;\n"
//			+ "    mat4 mvp = projection * view * model;\n"
//			+ "    gl_Position = mvp * vec4(position, 1.0);\n"
//			+ "}";
//	
//	private final CharSequence fragmentSource
//		= "#version 150 core\n"
//			+ "\n"
//			+ "in vec3 vertexColor;\n"
//			+ "\n"
//			+ "out vec4 fragColor;\n"
//			+ "\n"
//			+ "void main() {\n"
//			+ "    fragColor = vec4(vertexColor, 1.0);\n"
//			+ "}";
	
}
