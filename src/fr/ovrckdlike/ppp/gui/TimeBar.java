package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class TimeBar {
	private Rectangle space;
	private float maxTime;
	private float currentTime;

	public TimeBar(Dot pos, float maxTime) {
		space = new Rectangle(pos, 50f, 10f, 0f);
		this.maxTime = maxTime;
		this.currentTime = 0f;
	}
	
	public TimeBar(Rectangle rect , float maxTime) {
		this.space = rect;
		this.currentTime = maxTime;
		this.maxTime = maxTime;
	}

	public void render(float currentTime) {
		if (currentTime < 2 * maxTime && currentTime > 0) {
			this.currentTime = currentTime;
			Renderer.drawQuad(space.resized(4), Color.white);
			if (currentTime < maxTime) {
				float fillingSize = this.currentTime / maxTime * space.getWidth();
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, fillingSize, space.getHeight(), 0f), Color.darkGreen);
			} else if (currentTime < 1.3 * maxTime) {
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, space.getWidth(), space.getHeight(), 0f),
						Color.darkGreen);
			} else {
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, space.getWidth(), space.getHeight(), 0f), Color.red);
			}
		}
	}

	public void render(float currentTime, Dot pos) {
		space.setPos(pos);

		if (currentTime < 2 * maxTime && currentTime > 0) {
			this.currentTime = currentTime;
			Renderer.drawQuad(space.resized(4), Color.white);
			if (currentTime < maxTime) {
				float fillingSize = this.currentTime / maxTime * space.getWidth();
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, fillingSize, space.getHeight(), 0f), Color.darkGreen);
			} else if (currentTime < 1.3 * maxTime) {
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, space.getWidth(), space.getHeight(), 0f),
						Color.darkGreen);
			} else {
				Renderer.drawQuad(Rectangle.fromCorner(space.getX() - space.getWidth() / 2,
						space.getY() - space.getHeight() / 2, space.getWidth(), space.getHeight(), 0f), Color.red);
			}
		}
	}
}
