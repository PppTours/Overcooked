package fr.ovrckdlike.ppp.tiles;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.objects.Recipe;
import fr.ovrckdlike.ppp.scene.GameScene;


public class ServiceTable extends Tile {
	private int direction;
	
	
	public ServiceTable(float[] pos, int dir) {
		this.type = 7;
		this.pos = pos;
		this.direction = dir;
	}
	
	public void use(Player player) {
		
		Item content = player.getInHand();
		if (content instanceof Plate) {
			boolean[] ingList = ((Plate) content).getContent();
			Map.get().getItemList().remove(content);
			player.drop();
			GameScene.addPlateToReturn();
			RecipeScheduler.get().checkAContent(ingList);
		}
		else return;
	}

	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(this.direction*(Math.PI/2)), Texture.serviceTable);
	}

}
