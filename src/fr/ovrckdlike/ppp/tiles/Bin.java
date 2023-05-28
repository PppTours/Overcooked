package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Bin extends Tile{
	
	public Bin(Dot pos) {
		this.type = 11;
		this.space = new Rectangle(pos, size, size, 0f);
	}
	
	public void use(Player player) {
		if (player.getInHand() != null) player.getInHand().flush();
		if (player.getInHand() instanceof Ingredient) {
			player.drop();
		}
		
	}
	
	public void render() {
		Renderer.drawTexture(space, Texture.bin);
	}
}
