#version 330 core

in vec3 vertexColor;

out vec4 fragmentColor;

void main() {
    fragmentColor = vec4(vertexColor, 1.0);
    // fragmentColor = vec4(0.0, 0.2, 1.0, 1.0);
    // fragmentColor = vec4(0.2, 0.6, 0.0, 0.5);
}