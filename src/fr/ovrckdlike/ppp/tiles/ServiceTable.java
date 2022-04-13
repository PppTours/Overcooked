package fr.ovrckdlike.ppp.tiles;

import java.util.List;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.objects.Recipe;
import fr.ovrckdlike.ppp.graphics.Renderer;


public class ServiceTable extends Tile {
	private int direction;
	
	
	public ServiceTable(float[] pos, int dir) {
		this.type = 7;
		this.pos = pos;
		this.direction = dir;
	}
	
	public void use(Player player) {}
	
	public int serve(Plate plate, List<Item> itemList, List<Recipe> recipeList, PlateReturn pr) {
		pr.addPlate();
		int score = 0;
		for (Recipe rec : recipeList) {
			if (true) {
				
			}
		}
		itemList.remove(plate);
		return score;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(this.direction*(Math.PI/2)), Texture.serviceTable);
	}

}
