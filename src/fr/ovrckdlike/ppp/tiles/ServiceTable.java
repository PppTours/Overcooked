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
	
	public int serve(Plate plate, List<Item> itemList, List<Recipe> recipeList, PlateReturn pr) {
		pr.addPlate();
		int score = 0;
		boolean[] plateContent = plate.getContent();
		ArrayList<Recipe> validRecipe = new ArrayList<Recipe>();
		
		if (plateContent[13]) {
			for (Recipe rec : recipeList) {
				int[] ingList = rec.getIngredients();
				if(plateContent[ingList[0]]) {
					validRecipe.add(rec);
				}
				
			}
		}
		else {
			int nbIngInPlate = 0;
			for (int i = 0; i < 13; i++) {
				if(plateContent[i]){
					nbIngInPlate++;
				}
			}
			for (Recipe rec : recipeList) {
				int[] ingList = rec.getIngredients();
				boolean isValid = true;
				for (int ing : ingList) {
					if (! plateContent[ing]) {
						isValid = false;
					}
				}
				if (isValid && ingList.length == nbIngInPlate) {
					validRecipe.add(rec);
				}
			}
			if (validRecipe.isEmpty()) {
				return score;
			}
			else {
				Recipe chosenRecipe = validRecipe.get(0);
				float lowerTimer = validRecipe.get(0).getTimer();
				for (Recipe rec : validRecipe) {
					if (rec.getTimer() < lowerTimer) {
						lowerTimer = rec.getTimer();
						chosenRecipe = rec;
					}
				}
				score = (int) (10+chosenRecipe.getTimer());
				byte chosenNo = chosenRecipe.getComNo();
				recipeList.remove(chosenRecipe);
				for (Recipe rec : recipeList) {
					if (rec.getComNo() > chosenNo) {
						rec.setComNo((byte)(rec.getComNo()- 1));
					}
				}
			}
		}
		
		
		itemList.remove(plate);
		return score;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, (float)(this.direction*(Math.PI/2)), Texture.serviceTable);
	}

}
