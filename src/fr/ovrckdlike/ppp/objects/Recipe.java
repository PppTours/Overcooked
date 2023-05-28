package fr.ovrckdlike.ppp.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.GameScene;


public class Recipe {
	byte recipeSet;
	byte recipeId;
	float timer;		//timer is in seconds
	int[] ingredientList = new int[5];
	List<IngredientVisualizer> ivList;
	byte commandNumero;
	Rectangle space;
	TimeBar timeBar;
	
	
	public Recipe(byte recipeSet, byte commandNumero){
		this.recipeSet = recipeSet;
		this.commandNumero = commandNumero;
		space = new Rectangle(220*commandNumero, 0, 240, 140);
		ivList = new ArrayList();
		
		timer = 60f;
		Rectangle timeBarPos = new Rectangle(space.getPos().add(new Dot(0, -65)), 200, 15, 0);
		timeBar = new TimeBar(timeBarPos, timer);
		
		File recipeFile = new File("res/recipes/set"+recipeSet+".csv");
		try {
			Scanner scan = new Scanner(recipeFile);
			byte nbRecipe = 0;
			while(scan.hasNext()) {
				scan.next();
				nbRecipe++;
			}
			double rdm = Math.random();
			recipeId = (byte) (rdm*nbRecipe);
			scan.close();
			Scanner scan2 = new Scanner(recipeFile);
			String rawLine = new String();
			byte k=0;
			while (scan2.hasNext() && k <= recipeId) {
					rawLine = scan2.next();
					k++;
			}
			scan2.close();
			
			String[] splitedLine = rawLine.split(";");
			ingredientList = new int[splitedLine.length-1];
			k = -1;
			for(String str:splitedLine) {
				
				if (k >= 0) {
					ingredientList[k] = Integer.valueOf(str);
				}
				k++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Recipe file res/recipes/set"+recipeSet+".csv not found.");
		}
		byte ingIdx = 0;
		for (ingIdx = 0; ingIdx < ingredientList.length; ingIdx++) {
			int ingSize = 38;
			float xIng = space.getX() - ingSize*ingredientList.length/2f + ingSize*ingIdx + 5;
			float yIng = space.getY()+30;
			
			ivList.add(new IngredientVisualizer(ingredientList[ingIdx], new Dot(xIng, yIng)));
		
		
		}
	}
	
	public void reset() {
		timer = 60f;
	}
	
	public byte getComNo() {
		return commandNumero;
	}
	
	public void setComNo(byte numero) {
		commandNumero = numero;
		space.getPos().setX(220*commandNumero);
	}
	
	public float getTimer() {
		return timer;
	}
	
	public boolean checkValid(boolean[] content) {
		boolean[] actualContent = new boolean[15];
		if (recipeSet == 0) actualContent[14] = true;
		else actualContent[14] = false;
		if (recipeSet == 1) actualContent[13] = true;
		else actualContent[13] = false;
		int k;
		for (k = 0; k < 13; k++) {
			for (int i = 0; i < ingredientList.length; i++) {
				if (k == ingredientList[i]) 
					actualContent[k] = true;
			}
		}
		boolean flag = true;
		for (k = 0; k < 15; k++) {
			if (content[k] != actualContent[k]) flag = false;
		}
		return flag;
		
	}
	
	public void updateTimer() {
		timer -= Time.get().getDtS();
		if (timer < 0) timer = 0;
	}
	
	public int[] getIngredients() {
		return ingredientList;
	}
	
	
	public void render() {
		float alpha = .8f;
		Dot pos = space.getPos();
		float p1Dist = GameScene.getPlayers().get(0).distanceTo(pos);
		float p2Dist = GameScene.getPlayers().get(1).distanceTo(pos);
		float dist = Math.min(p1Dist, p2Dist);
		if (dist < space.getHeight()+50) {
			alpha = .6f * dist/(space.getHeight()+50)+ .2f;
		}
		Renderer.drawTextureTransparent(space, alpha, Texture.recipeBackground);
		
		
		for (IngredientVisualizer iv:ivList) {
			iv.render();
		}
		
	}
	
}
