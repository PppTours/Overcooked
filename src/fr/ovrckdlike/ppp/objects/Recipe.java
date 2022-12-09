package fr.ovrckdlike.ppp.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.GameScene;


public class Recipe {
	byte recipeSet;
	byte recipeId;
	float timer;		//timer is in seconds
	int[] ingredientList;
	byte commandNumero;
	float[] pos = new float[2];
	float[] size = new float[2];
	
	
	
	public Recipe(byte recipeSet, byte commandNumero){
		this.recipeSet = recipeSet;
		this.commandNumero = commandNumero;
		
		timer = 60f;
		size[0] = 180;
		size[1] = 120;
		pos[0] = (size[0]-20) * commandNumero;
		pos[1] = 0;
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
	}
	
	public void reset() {
		timer = 60f;
	}
	
	public byte getComNo() {
		return commandNumero;
	}
	
	public void setComNo(byte numero) {
		commandNumero = numero;
		pos[0] = (size[0]-20) * commandNumero;
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
		float[] middlePos = {pos[0] + size[0]/2, pos[1] + size[1]/2};
		float p1Dist = GameScene.getPlayers().get(0).distanceTo(middlePos);
		float p2Dist = GameScene.getPlayers().get(1).distanceTo(middlePos);
		float dist = Math.min(p1Dist, p2Dist);
		if (dist < size[0]+50) {
			alpha = .6f * dist/(size[0]+50)+ .2f;
		}
		Renderer.drawTextureTransparent(pos[0], pos[1], size[0], size[1], 0, alpha, Texture.recipeBackground);
		
		byte ingIdx = 0;
		for (ingIdx = 0; ingIdx < ingredientList.length; ingIdx++) {
			int ingSize = 30;
			float xIng = middlePos[0] - ingSize*ingredientList.length/2f + ingSize*ingIdx;
			
			Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.circle);
			switch (ingredientList[ingIdx]) {
			case 0 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.tomato);
				break;
			case 1 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.salade);
				break;
			case 2 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.onion);
				break;
			case 3 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.mushroom);
				break;
			case 4 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.meat);
				break;
			case 5 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.cheese);
				break;
			case 6 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.pasta);
				break;
			case 7 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.sausage);
				break;
			case 8 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.pizzaDough);
				break;
			case 9 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.burgerBread);
				break;
			case 10 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.chicken);
				break;
			case 11 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.rice);
				break;
			case 12 :
				Renderer.drawTextureTransparent(xIng, pos[1]+80, 28, 28, 0, alpha, Texture.potato);
				break;
			default :
				break;
			}	
		}
		Renderer.drawQuad(pos[0]+30, pos[1]+5, 120, 15, Color.black);
		
		float timeBarLength = 2*timer;
		
		if (timer > 40f) {
			Renderer.drawQuad(pos[0]+30, pos[1]+5, timeBarLength, 15, Color.darkGreen);
		}
		else if (timer < 20f) {
			Renderer.drawQuad(pos[0]+30, pos[1]+5, timeBarLength, 15, Color.red);
		}
		else Renderer.drawQuad(pos[0]+30, pos[1]+5, timeBarLength, 15, Color.yellow);
	}
	
}
