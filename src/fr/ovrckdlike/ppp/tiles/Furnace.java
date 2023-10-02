package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;


public class Furnace extends Tile implements ContainerTile, Burnable {

  private boolean burning;
  private Plate inFurnace;
  private int cookingTime = 10;
  private float timeInFurnace;
  private TimeBar timeBar;
  private Dot timeBarPos;


  public Furnace(Dot pos) {
    type = 9;
    this.space = new Rectangle(pos, size, size, 0f);
    timeBarPos = new Dot(space.getPos().getX(), pos.getY() + 48f);
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
      if (oldContent != null) {
        oldContent.setMode(0);
      }
      inFurnace = (Plate) newContent;
      if (burning) {
        inFurnace.burn();
      }
      if (inFurnace != null) {
        timeInFurnace = (inFurnace.getCooked()) ? cookingTime : 0f;
        inFurnace.setMode(1);
        inFurnace.setPos(space.getPos());
      }
      return oldContent;
    } else {
      return newContent;
    }
  }

  public void cook() {
    if (inFurnace == null) {
      return;
    }
    if (!inFurnace.isEmpty()) {
      long dt = Time.get().getDt();
      float sdt = dt / 1E9f;
      timeInFurnace += sdt;
      if (inFurnace != null) {
        if (timeInFurnace > cookingTime) {
          inFurnace.cook();
        }
        if (timeInFurnace > 2 * cookingTime) {
          setInFire();
        }

      }
    }
  }

  public void use(Player player) {}

  public void render() {
    Renderer.drawTexture(space, Texture.furnaceBack);

    if (inFurnace != null) {
      inFurnace.render();
    }

    Renderer.drawTexture(space, Texture.furnaceFront);
    timeBar.render(timeInFurnace, timeBarPos);

    if (burning) {
      Renderer.drawTexture(space, Texture.fire);
    }
  }

  @Override
  public boolean isBurning() {
    return burning;
  }

  @Override
  public void setInFire() {
    burning = true;
    inFurnace.burn();
  }

  @Override
  public void stopFire() {
    burning = false;
  }

}
