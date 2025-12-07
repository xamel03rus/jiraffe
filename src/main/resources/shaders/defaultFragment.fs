#version 400

in vec2 outTexCoord;

out vec4 fragColor;

struct Material {
    vec4 color;
};

uniform sampler2D sampler;
uniform Material material;

vec4 color;

void main()
{
    fragColor = vec4(material.color.x, 0, 1, 1);
}