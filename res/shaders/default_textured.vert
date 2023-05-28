#version 330 core

in vec2 i_position;

out vec2 v_texCoord;

uniform vec4 u_camera;
uniform vec4 u_position;
uniform vec4 u_tex;
uniform float u_theta;

mat2 rot(float theta){
	return mat2(cos(theta), sin(theta),
				-sin(theta), cos(theta));
}

void main() {
	vec2 i_moved = rot(u_theta) * (i_position*u_position.zw- u_position.zw/2);

	vec2 world = i_moved+u_position.xy;
	vec2 view = (world-u_camera.xy)/u_camera.zw;
	vec2 screen = view*2-1;
	gl_Position = vec4(screen, 0, 1);
	v_texCoord = i_position*u_tex.zw+u_tex.xy;
}
