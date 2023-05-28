package fr.ovrckdlike.ppp.gui;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class RoundRobin {
	private Rectangle space;
	private List<SelectCard> elements;
	private int selected;
	private boolean moveReady;
	private boolean lock;
	
	public RoundRobin(Rectangle space) {
		this.space = space;
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
		Rectangle renderSpace1 = new Rectangle(space.getX()-space.getWidth()/4*3, space.getY(), space.getWidth()/2, 0);
		Rectangle renderSpace2 = new Rectangle(space.getX()+space.getWidth()/4*3, space.getY(), space.getWidth()/2, 0);
		Renderer.drawTexture(renderSpace1, Texture.arrowLeft);
		Renderer.drawTexture(renderSpace2, Texture.arrowRight);
		if (selected != -1) {
			elements.get(selected).render();
		}
	}
	
	
	

}
