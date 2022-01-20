#version 330 core

layout(location=0) out vec4 color;

in vec2 v_texCoord;

uniform sampler2D u_texture;
uniform vec4 u_alphaColor;

void main() {
	color = texture(u_texture, v_texCoord)*u_alphaColor;
}
