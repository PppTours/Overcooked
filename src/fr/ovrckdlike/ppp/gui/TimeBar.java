package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;

public class TimeBar {
	private float[] pos= new float[2];
	private float[] size= new float[2];
	private float maxTime;
	private float currentTime;
	
	public TimeBar(float[] pos, float maxTime) {
		this.pos = pos;
		this.size[0] = 50f;
		this.size[1] = 10f;
		this.maxTime = maxTime;
		this.currentTime = 0f;
	}
	
	public void render(float currentTime, float[] pos){
		this.pos = pos;
		if (currentTime < 2*maxTime && currentTime > 0) {
			this.currentTime = currentTime;
			Renderer.drawQuad(pos[0]-2, pos[1]-2, size[0]+4, size[1]+4, Color.white);
			if (currentTime < maxTime) {
				float fillingSize = this.currentTime / maxTime * size[0];
				Renderer.drawQuad(pos[0], pos[1], fillingSize, size[1], Color.darkGreen);
			}
			else if(currentTime < 1.3*maxTime) {
				Renderer.drawQuad(pos[0], pos[1], size[0], size[1], Color.darkGreen);
			}
			else {
				Renderer.drawQuad(pos[0], pos[1], size[0], size[1], Color.red);
			}
		}
	}
}
