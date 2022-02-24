package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;


public class Furnace extends ContainerTile{

	private Plate inFurnace;
	private float timeInFurnace;
	
	
	public Furnace(float[] pos) {
		type = 9;
		this.pos = pos;
		inFurnace = null;
		timeInFurnace = 0;
	}
	public Plate takeOrDrop(Plate newContent) {
		Plate oldContent = inFurnace;
		inFurnace = newContent;
		timeInFurnace = 0f;
		if (inFurnace != null) {
			inFurnace.setMode(2);
			inFurnace.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	
	public void use(long dt) {
		float s_dt = (float) (dt/1E9f);
		timeInFurnace += s_dt;
		
		if (inFurnace != null) {
			if (timeInFurnace > 10) inFurnace.cook();
			if (timeInFurnace > 20); //brulé
			
		}
		
	}
		
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.gasCooker);
	}
}


