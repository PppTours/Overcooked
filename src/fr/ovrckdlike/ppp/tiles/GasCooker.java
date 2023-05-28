package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.CookerContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;


public class GasCooker extends Tile implements ContainerTile{
	private float cookingTime;
	private CookerContainer content;
	
	public Item getContent() {
		return content;
	}
	
	public GasCooker(Dot pos, CookerContainer onGas) {
		this.type = 3;
		this.space = new Rectangle(pos, size, size, 0f);
		this.cookingTime = 0f;
		this.content = onGas;
		this.content.setPos(pos);
		this.content.setMode(1);
	}
	
	public void cook() {
		if (content == null) return;
		if (content.isFilled()) {
			long dt = Time.get().getDt();
			content.cook(dt);
		}
		
	}
	
	public void use(Player player) {}

	public void render() {
		Renderer.drawTexture(space, Texture.gasCooker);
	}

	@Override
	public Item takeOrDrop(Item newContent) {
		if (newContent instanceof CookerContainer || newContent == null) {
			CookerContainer oldContent = content;
			content = (CookerContainer) newContent;
			cookingTime = 0f;
			if (content != null) {
				content.setMode(1);
				content.setPos(space.getPos());
			}
			if (oldContent != null) {
				oldContent.setMode(0);
			}
			return oldContent;
		}
		return newContent;
		
	}
}
