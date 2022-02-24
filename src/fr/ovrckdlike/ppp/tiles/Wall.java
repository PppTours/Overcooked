package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class Wall extends Tile{
	public Wall(float[] wallPos) {
		pos = wallPos;
		size = 120;
		type = 10;
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0f, Texture.wall);
	}

}
