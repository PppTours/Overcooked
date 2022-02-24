package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;


public class GasCooker extends ContainerTile{
	private CookerContainer onGas;
	
	
	public GasCooker(float[] pos, CookerContainer onGas) {
		this.type = 3;
		this.pos = pos;
		this.onGas = onGas;
		this.onGas.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
	}
	
	
	public void use(long dt) {
		this.onGas.cook(dt);
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.gasCooker);
	}
}
