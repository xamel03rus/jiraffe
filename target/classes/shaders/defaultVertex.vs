#version 400

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 normal;

out vec2 outTexCoord;

uniform mat4 modelSpace;
uniform mat4 viewSpace;
uniform mat4 projectionSpace;

void main()
{
    vec4 worldPosition = modelSpace * vec4(position, 1.0);

    gl_Position = projectionSpace * viewSpace * worldPosition;
    outTexCoord = texCoord;
}
