#version 330 core

in vec2 i_position;

uniform vec4 u_color;
uniform vec4 u_camera;
uniform vec4 u_position;

void main() {
	vec2 world = i_position*u_position.zw+u_position.xy;
	vec2 view = (world-u_camera.xy)/u_camera.zw;
	vec2 screen = view*2-1;
	screen.y = -screen.y;
	gl_Position = vec4(screen, 0, 1);
}
