package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.internal.Texture;

public class HyperParameters {
	private static HyperParameters instance;
	int map;
	Texture skinP1;
	Texture skinP2;
	
	public static HyperParameters get() {
		if (instance == null) instance = new HyperParameters();
		return instance;
	}
	
	private HyperParameters() {
		map = 0;
		skinP1 = Texture.catSkin;
		skinP2 = Texture.dragonSkin;
	}
	
	public void setMap(int newMap) {
		map = newMap;
	}
	
	public void setSkinP1(Texture skin) {
		skinP1 = skin;
	}
	
	public void setSkinP2(Texture skin) {
		skinP2 = skin;
	}
	
	public int getMap() {
		return map;
	}
	
	public Texture getSkinP1() {
		return skinP1;
	}
	
	public Texture getSkinP2() {
		return skinP2;
	}
	
}
