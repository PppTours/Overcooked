package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.objects.Item;

public abstract class ContainerTile extends Tile{
	protected Item content;
	
	
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
	

}
