package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;

import java.util.List;
import java.util.ArrayList;

public class Bin extends Tile{
	
	public Bin(float[] pos) {
		this.type = 11;
		this.pos = pos;
	}
	
	public void use(Player player) {
		if (player.getInHand() != null) player.getInHand().flush();
		if (player.getInHand() instanceof Ingredient) {
			player.drop();
		}
		
	}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.bin);
	}
}
