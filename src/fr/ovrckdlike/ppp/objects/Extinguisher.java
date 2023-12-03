package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.SoundHandler;
import fr.ovrckdlike.ppp.tiles.Burnable;
import fr.ovrckdlike.ppp.tiles.Furnace;
import fr.ovrckdlike.ppp.tiles.GasCooker;
import fr.ovrckdlike.ppp.tiles.Tile;

/**
 * A class to represent an extinguisher.
 */
public class Extinguisher extends Item {

  private float currentExtinguishTime;
  private final int extinguishTime;
  private TimeBar timeBar;
  private Dot timeBarPos;
  private boolean needTimeBar;

  public Extinguisher(Dot pos) {
    super(pos);
    setMode(1);
    this.extinguishTime = 2;
    this.timeBarPos = new Dot(0, 0);
    this.timeBar = new TimeBar(timeBarPos, extinguishTime);
    needTimeBar = false;
  }

  @Override
  public void render() {
    float zoom = space.getRay() * (mode + 1);
    Renderer.drawTexture(space.resized(zoom).surroundBySquare(0), Texture.extinguisher);
    Dot moveTimeBar = new Dot(0, 50f);
    if (needTimeBar) {
      timeBar.render(currentExtinguishTime, this.getPos().add(moveTimeBar));
      System.out.println("render");
    }
  }

  @Override
  public void prepare() {}

  @Override
  public void flush() {}

  public void use(Tile tileFront) {
    SoundHandler.play(SoundHandler.extinguish);
    if (tileFront instanceof Burnable && ((Burnable) tileFront).isBurning()) {
      needTimeBar = true;
      System.out.println("use");
      long dt = Time.get().getDt();
      float sdt = dt / 1E9f;
      currentExtinguishTime += sdt;
      if (currentExtinguishTime >= extinguishTime) {
        currentExtinguishTime = 0;
        ((Burnable) tileFront).stopFire();
        if (tileFront instanceof GasCooker) {
          ((GasCooker) tileFront).stopFire();
        } else if (tileFront instanceof Furnace) {
          ((Furnace) tileFront).stopFire();
        }
      }


    }
  }

  // Ajouter l'affichage du sprite
}
