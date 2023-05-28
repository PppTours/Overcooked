package fr.ovrckdlike.ppp.tiles;


import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Dryer extends Tile{
	private int nbPlate;
	private int direction;
	
	public Dryer(Dot pos, int direction) {
		this.space = new Rectangle(pos, size, size, (float)(-Math.PI*direction/2));
		this.type = 5;
		this.nbPlate = 0;
		this.direction = direction;
	}
	
	
	public void addPlate() {
		nbPlate++;
	}
	
	
	public void use(Player player) {}
	
	
	public void takePlate(Player player) {
		if (nbPlate > 0) {
			if (player.getInHand() == null) {
				Dot tempPos = new Dot(-50f, -50f);
				Plate plate = new Plate(tempPos, false, 0);
				Map.get().getItemList().add(plate);
				player.setInHand(plate);
				nbPlate--;
			}
		}
	}
	
	public void render() {
		Renderer.drawTexture(space, Texture.dryer);
		
	}
}
