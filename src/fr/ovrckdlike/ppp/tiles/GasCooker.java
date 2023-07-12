package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.CookerContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;


public class GasCooker extends Tile implements ContainerTile, Burnable{
	private CookerContainer content;
	private boolean burning;
	
	public Item getContent() {
		return content;
	}
	
	public GasCooker(Dot pos, CookerContainer onGas) {
		this.type = 3;
		this.space = new Rectangle(pos, size, size, 0f);
		this.content = onGas;
		this.content.setPos(pos);
		this.content.setMode(1);
		burning = false;
	}
	
	public void cook() {
		if (content == null) return;
		if (content.isFilled()) {
			long dt = Time.get().getDt();
			content.cook(dt);
			if (content.isBurnt()) setInFire();
		}
	}
	
	public void use(Player player) {}

	public void render() {
		Renderer.drawTexture(space, Texture.gasCooker);
		if (content != null) content.render();
		
		if (burning) Renderer.drawTexture(space, Texture.fire);
	}

	@Override
	public Item takeOrDrop(Item newContent) {
		if (newContent instanceof CookerContainer || newContent == null) {
			CookerContainer oldContent = content;
			content = (CookerContainer) newContent;
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

	@Override
	public boolean isBurning() {
		return burning;
	}

	@Override
	public void setInFire() {
		burning = true;
		content.burn();
	}

	@Override
	public void stopFire() {
		burning = false;
		
	}
}
