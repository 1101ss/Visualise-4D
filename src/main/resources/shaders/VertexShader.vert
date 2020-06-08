#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;

out vec3 vertexColor;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

// uniform mat4 transformationObject;
// uniform mat4 transformationWorld;
// uniform mat4 cameraProjection;

void main() {
	vertexColor = color;
	mat4 mvp = projection * view * model;
	gl_Position = mvp * vec4(position, 1.0);
	// gl_Position = cameraProjection * transformationWorld * transformationObject * vec4(position, 1.0);
	// gl_Position = vec4(position, 1.0);
}