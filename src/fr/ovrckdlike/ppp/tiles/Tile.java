package fr.ovrckdlike.ppp.tiles;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public abstract class Tile {
	Rectangle space;	// position en px
	protected int type;
	protected float size = 120;
	
	public Dot getPos() {
		return space.getPos();
	}
	
	public float getSize() {
		return space.getWidth();
	}
	
	public Rectangle getSpace() {
		return space;
	}
	
	public boolean isInTile(Dot pos) {
		return space.includes(pos);
	}
	
	public abstract void render();
	public abstract void use(Player player);
	
	public Dot nearestFromPos(Dot pos) {
		return space.nearestFromPos(pos);
	}
}
