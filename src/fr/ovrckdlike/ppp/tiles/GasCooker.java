package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;


public class GasCooker extends Tile{
	private CookerContainer onGas;
	
	
	public GasCooker(float[] pos, CookerContainer onGas) {
		this.type = 3;
		this.pos = pos;
		this.onGas = onGas;
	}
	
	public CookerContainer dropOrTake(CookerContainer newOnGas) {
		CookerContainer oldOnGas = this.onGas;
		this.onGas = newOnGas;
		if (this.onGas != null) {
			this.onGas.setPos(this.pos[0]+20, this.pos[1]+20);
			this.onGas.setMode(1);
		}
		return oldOnGas;
	}
	
	public void use(long dt) {
		this.onGas.cook(dt);
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.gasCooker);
	}
}
