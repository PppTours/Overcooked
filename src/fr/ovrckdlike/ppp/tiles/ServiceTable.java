package fr.ovrckdlike.ppp.tiles;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.scene.GameScene;


public class ServiceTable extends Tile {
  private int direction;

  public ServiceTable(Dot pos, int dir) {
    this.type = 7;
    this.space = new Rectangle(pos, size, size, (float) (dir * (Math.PI / 2)));
    this.direction = dir;
  }

  public void use(Player player) {

    Item content = player.getInHand();
    if (content instanceof Plate) {
      boolean[] ingList = ((Plate) content).getContent();
      Map.get().getItemList().remove(content);
      RecipeScheduler.get().checkaContent(ingList);
      player.drop();
      GameScene.addPlateToReturn();
    }
  }



  public void render() {
    Renderer.drawTexture(space, Texture.serviceTable);
  }

}
