package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.physics.Circle;
import fr.ovrckdlike.ppp.physics.Dot;

/**
 * A class to visualize an ingredient.
 */
public class IngredientVisualizer {
  /**
   * The space occupied by the ingredient.
   */
  private Circle space;

  /**
   * The type of the ingredient.
   *
   * @see Ingredient
   */
  private int ingType;

  /**
   * Whether the ingredient is visible or not.
   */
  private boolean visible;

  /**
   * Creates a new ingredient visualizer.
   *
   * @param ingType The type of the ingredient.
   * @param pos The position of the ingredient.
   */
  public IngredientVisualizer(int ingType, Dot pos) {
    this.ingType = ingType;
    this.space = new Circle(pos, 15);
    visible = false;
  }

  /**
   * Set the visibility of the ingredient.
   *
   * @param param A boolean to set visibility.
   */
  public void setVisible(boolean param) {
    visible = param;
  }

  /**
   * Set the position of the ingredient.
   *
   * @param newX The new x coordinate.
   * @param newY The new y coordinate.
   */
  public void setPos(float newX, float newY) {
    space.setPos(new Dot(newX, newY));
  }

  /**
   * Set the position of the ingredient.
   *
   * @param newPos The new position.
   */
  public void setPos(Dot newPos) {
    space.setPos(newPos);
  }

  /**
   * Set the ingredient type.
   *
   * @param ingType The new ingredient type.
   */
  public void setIngredient(int ingType) {
    this.ingType = ingType;
  }

  /**
   * Render the ingredient.
   */
  public void render() {
    if (visible) {
      Renderer.drawTexture(space.surroundBySquare(0), Texture.circle);
      Renderer.drawTexture(space.resized(13).surroundBySquare(0), Ingredient.getTexture(ingType));
    }
  }

}
