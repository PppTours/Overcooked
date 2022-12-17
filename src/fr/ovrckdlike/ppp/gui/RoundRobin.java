package fr.ovrckdlike.ppp.gui;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;

public class RoundRobin {
	private float[] pos;
	private float[] size;
	private List<SelectCard> elements;
	private int selected;
	private boolean moveReady;
	private boolean lock;
	
	public RoundRobin(float[] pos, float[] size) {
		this.size = size;
		this.pos = pos;
		elements = new ArrayList<SelectCard>();
		selected = -1;
		moveReady = true;
		lock = false;
	}
	
	public SelectCard getSelectedCard() {
		if (selected == -1) return null;
		else return elements.get(selected);
	}
	
	public void moveSelectionRight() {
		if (!moveReady || lock) return;
		if (selected == -1) return;
		selected++;
		if (selected == elements.size()) selected = 0;
		moveReady = false;
	}
	
	public void moveSelectionLeft() {
		if (!moveReady || lock) return;
		if (selected == -1) return;
		if (selected == 0) selected = elements.size();
		selected--;
		moveReady = false;
	}
	
	public void setLocked(boolean locked) {
		lock = locked;
	}
	
	public void resetMove() {
		moveReady = true;
	}
	
	public void addCard(SelectCard sc) {
		if (selected == -1) selected++;
		elements.add(sc);
	}
	
	public void render() {
		Renderer.drawTexture(pos[0]-size[1]/4-10, pos[1]+size[1]/4, size[1]/4, size[1]/2, 0, Texture.arrowLeft);
		Renderer.drawTexture(pos[0]+size[0]+10, pos[1]+size[1]/4, size[1]/4, size[1]/2, 0, Texture.arrowRight);
		if (selected != -1) {
			elements.get(selected).render();
		}
	}
	
	
	

}
