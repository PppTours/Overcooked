package fr.ovrckdlike.ppp.gameplay;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.map.MapType;
import fr.ovrckdlike.ppp.objects.Recipe;
import fr.ovrckdlike.ppp.physics.Time;

public class RecipeScheduler {
	private static RecipeScheduler rs;
	private List<Recipe> recipeList;
	private float timeSinceLastAdd;
	private MapType recipeSet;
	
	private RecipeScheduler() {
		recipeList = new ArrayList<Recipe>();
	}
	
	public void reset() {
		recipeList.clear();
	}
	
	public static RecipeScheduler get() {
		if(rs==null) {
			rs = new RecipeScheduler();
		}
		return rs;
	}
	
	public void setRecSet(MapType newSet) {
		recipeSet = newSet;
	}
	
	public void run() {
		if (recipeList.size() < 2) {
			recipeList.add(new Recipe(recipeSet, (byte) recipeList.size()));
			timeSinceLastAdd = 0;
		}
		timeSinceLastAdd += Time.get().getDtS();
		if (timeSinceLastAdd >= 45 && recipeList.size() < 11) {
			recipeList.add(new Recipe(recipeSet, (byte) recipeList.size()));
			timeSinceLastAdd = 0;
		}
		for (Recipe r:recipeList) {
			r.updateTimer();
			if (r.getTimer() <= 0) {
				r.reset();
				r.getIngredients();
				Score.get().penalty();
			}
		}
	}
	
	public void checkAContent(boolean[] content) {
		boolean flag = true;
		for (Recipe r:recipeList) {
			if (r.checkValid(content)&& flag) {
				flag = false;
				completeRecipe(r);
			}
		}
		if (flag) {
			Score.get().error();
		}
	}
	
	
	public float completeRecipe(Recipe completed) {		// on ne peut pas acceder aux recettes, il faut une liste d'ingrédients
		int idx = recipeList.indexOf(completed);
		if (idx == -1) return 0f;
		float timeLeft = recipeList.get(idx).getTimer();
		Score.get().supply();
		recipeList.remove(completed);
		for (int k = idx; k < recipeList.size(); k++) {
			recipeList.get(k).setComNo((byte) k);
		}
		return timeLeft;
	}
	
	public void render() {
		for (Recipe r:recipeList) {
			r.render();
		}
	}
}
