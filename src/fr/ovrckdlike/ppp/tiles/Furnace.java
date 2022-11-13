package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;


public class Furnace extends Tile implements ContainerTile{

	private Plate inFurnace;
	private int cookingTime = 10;
	private float timeInFurnace;
	private TimeBar timeBar;
	private float[] timeBarPos = {this.pos[0]+35, this.pos[1]+108};
	
	
	public Furnace(float[] pos) {
		type = 9;
		this.pos = pos;
		inFurnace = null;
		timeInFurnace = 0;
		timeBar = new TimeBar(timeBarPos, cookingTime);
	}
	
	public Item getContent() {
		return inFurnace;
	}
	
	public Item takeOrDrop(Item newContent) {
		if (newContent instanceof Plate || newContent == null) {
			Plate oldContent = inFurnace;
			inFurnace = (Plate) newContent;
			timeInFurnace = 0f;
			if (inFurnace != null) {
				inFurnace.setMode(1);
				inFurnace.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
			}
			if (oldContent != null) {
				oldContent.setMode(0);
			}
			return oldContent;
		}
		else return newContent;
	}

	public void use(Player player) {
		long dt = Time.get().getDt();
		float s_dt = (float) (dt/1E9f);
		timeInFurnace += s_dt;
		
		if (inFurnace != null) {
			if (timeInFurnace > cookingTime) inFurnace.cook();
			if (timeInFurnace > 2*cookingTime); //brulé
			
		}
		
	}
		
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.furnaceBack);
		
		if (inFurnace != null) {
			inFurnace.render();
		}
		
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.furnaceFront);
		timeBar.render(timeInFurnace, timeBarPos);
		
	}
	
}


