package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.scene.GameScene;
import java.util.ArrayList;
import java.util.List;

/**
 * Pan is a CookerContainer and an IngredientContainer.
 */
public class Pan extends CookerContainer implements IngredientContainer {
  /**
   * Content of the pan.
   */
  Ingredient content;

  /**
   * List of the ingredient visualizer.
   */
  List<IngredientVisualizer> ivList;

  /**
   * Constructor of the pan.
   *
   * @param pos Position of the pan.
   */
  public Pan(Dot pos) {
    super(pos);
    content = null;
    ivList = new ArrayList<>();
    Dot timeBarPos = new Dot(pos.getX() - 25, pos.getY() + 25 * mode);
    timebar = new TimeBar(timeBarPos, cookingTime);
  }


  /**
   * Fill the pan with an ingredient.
   *
   * @param ing Ingredient to add in the pan.
   * @return True if the ingredient is cookable, false otherwise.
   */
  @Override
  public boolean fill(Ingredient ing) {
    if (ing.isCookable()) {
      content = ing;
      return true;
    } else {
      return false;
    }
  }

  /**
   * If the pan is filled.
   *
   * @return True if the pan is filled, false otherwise.
   */
  @Override
  public boolean isFilled() {
    return content != null;
  }

  /**
   * Flush the content of the pan.
   */
  @Override
  public void flush() {
    GameScene.deleteItem(content);
    content = null;
    currentCookingTime = 0f;
    stopFire();
    hasBurn = false;
  }

  /**
   * Render the pan.
   */
  @Override
  public void render() {
    float zoom = space.getRay() * (mode + 1);
    if (!hasBurn) {
      Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pan);
    } else {
      Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.panBurnt);
    }
  }
}
