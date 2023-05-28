package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Table extends Tile implements ContainerTile {
	private Item content;
	
	public Table(Dot pos, boolean withPlate) {
		this.space = new Rectangle(pos, size, size, 0f);
		if (withPlate) {

			content = new Plate(new Dot(pos), false);
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
			this.content.setPos(space.getPos());
		}
		if (oldContent != null) {
			oldContent.setMode(0);
		}
		return oldContent;
	}
	
	public void use(Player player) {}
	
	public void render() {
		Renderer.drawTexture(space, Texture.table);
	}
	
}
