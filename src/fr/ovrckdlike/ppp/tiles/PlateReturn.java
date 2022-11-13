package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;

public class PlateReturn extends Tile implements ContainerTile{
	int plateNb;
	
	
	public PlateReturn(float[] pos) {
		this.pos = pos;
		this.plateNb = 0;
		this.type = 8;
	}
	
	
	public void addPlate() {
		plateNb++;
	}
	
	
	@Override
	public void use(Player player) {}
	
	@Override
	public Item takeOrDrop(Item content) {
		System.out.println(plateNb);
		if (content != null) return content;
		if (plateNb > 0) {
			plateNb--;
			float[] platePos = {this.pos[0]+20, this.pos[1]+20};
			Plate newPlate = new Plate(platePos, true, 0);
			Map.get().getItemList().add(newPlate);
			return newPlate;
		}
		else return null;
	}
	
	
	public void render() {
		Renderer.drawTexture(this.pos[0], this.pos[1], this.size, this.size, 0, Texture.plateReturn);
		if (plateNb > 0) {
			Renderer.drawTexture(pos[0]+20, pos[1]+20, 80, 80, 0, Texture.dirtyPlate);
		}
	}


	@Override
	public Item getContent() {
		return null;
	}
}
