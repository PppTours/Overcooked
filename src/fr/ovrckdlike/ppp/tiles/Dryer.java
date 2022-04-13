package fr.ovrckdlike.ppp.tiles;


import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.scene.GameScene;

public class Dryer extends Tile{
	private int nbPlate;
	private int direction;
	
	public Dryer(float[] pos, int direction) {
		this.pos = pos;
		this.type = 5;
		this.nbPlate = 0;
		this.direction = direction;
	}
	
	
	public void add(Plate plate) {
		nbPlate++;
	}
	
	public void use(Player player) {}
	
	
	public void takePlate(Player player) {
		if (nbPlate > 0) {
			if (player.getInHand() == null) {
				float[] tempPos = {-50f, -50f};
				Plate plate = new Plate(tempPos, false);
				GameScene.getItemList().add(plate);
				player.setInHand(plate);
				nbPlate--;
			}
		}
	}
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(Math.PI*this.direction/2), Texture.dryer);
		
	}
}
