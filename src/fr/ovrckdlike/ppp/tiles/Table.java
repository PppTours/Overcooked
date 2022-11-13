package fr.ovrckdlike.ppp.tiles;

import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.scene.GameScene;

public class Table extends Tile implements ContainerTile {
	private Item content;
	
	public Table(float[] pos, boolean withPlate) {
		this.pos = pos;
		if (withPlate) {
			float[] platePos = new float[2];
			platePos[0] = this.pos[0] + size/2;
			platePos[1] = this.pos[1] + size/2;
			content = new Plate(platePos, false);
			Map.get().getItemList().add(content);
			
		}
		else content = null;
		this.type = 1;
	}
	
	@Override
	public Item getContent() {
		return content;
	}
	
	@Override
	public Item takeOrDrop(Item newContent) {
		Item oldContent = this.content;
		this.content = newContent;
		if (this.content != null) {
			this.content.setMode(1);
			this.content.setPos(this.pos[0]+size/2, this.pos[1]+size/2);
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	public void use(Player player) {}
	
	public void render() {
		Renderer.drawTexture(pos[0], pos[1], size, size, 0, Texture.table);
	}
	
}
