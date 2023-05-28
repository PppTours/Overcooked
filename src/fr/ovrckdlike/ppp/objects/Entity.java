package fr.ovrckdlike.ppp.objects;

import java.util.List;

import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.tiles.Tile;

public abstract class Entity {
	protected Circle space;	
	
	public abstract <T extends Entity> void collideEntity(List<T> objList);
	
	public abstract void collideTile(List<Tile> tileList);

}
