package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class representing a pot. Which is a cooker container and an ingredient container.
 */
public class Pot extends CookerContainer implements IngredientContainer {
  /**
   * The content of the pot.
   *
   * @see Ingredient
   */
  private final int[] content = new int[3];

  /**
   * The number of ingredients in the pot.
   */
  private int nbIng;

  /**
   * The visualizer of the ingredient in the pot.
   */
  private final List<IngredientVisualizer> ivList = new ArrayList<>();

  /**
   * The constructor of the pot.
   *
   * @param pos The position of the pot.
   */
  public Pot(Dot pos) {
    super(pos);
    angle = 0f;
    for (int i = 0; i < 3; i++) {
      this.content[i] = -1;
      Dot posiv = new Dot(0f, 0f);
      ivList.add(new IngredientVisualizer(0, posiv));
    }
    Dot timeBarPos = new Dot(space.getPos().getX(), space.getPos().getY() + 25 * mode);
    timebar = new TimeBar(timeBarPos, cookingTime);
    nbIng = 0;
  }

  
  public boolean isCooked() {
    return cooked;
  }

  /**
   * Clear the content of the pot.
   *
   * @see Pot#content
   */
  public void flush() {
    for (int i = 0; i < 3; i++) {
      this.content[i] = -1;
      ivList.get(i).setVisible(false);
    }
    nbIng = 0;
    currentCookingTime = 0f;
    stopFire();
    hasBurn = false;
  }

  /**
   * Check if the pot is filled.
   *
   * @return True if the pot is filled, false otherwise.
   */
  @Override
  public boolean isFilled() {
    return nbIng > 0;
  }

  /**
   * Fill the pot with an ingredient.
   *
   * @param ing The ingredient to fill the pot with.
   * @return True if the ingredient was added, false otherwise.
   */
  @Override
  public boolean fill(Ingredient ing) {
    if (nbIng > 2) {
      return false;
    }
    if (!ing.getPrepared()) {
      return false;
    }
    if (ing.getType() == 0 || ing.getType() == 2 || ing.getType() == 3) {
      content[nbIng] = ing.getType();
      nbIng++;
      currentCookingTime *= (float) (nbIng - 1) / (float) nbIng;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check for the content of the pot.
   * Mettre -1 pour vide.
   *
   * @param content Contenu à vérifier.
   * @return Nombre de fois que le contenu est présent.
   */
  public int checkForContent(int content) {
    int corresponding = 0;
    for (int i = 0; i < 3; i++) {
      if (this.content[i] == content) {
        corresponding++;
      }
    }
    return corresponding;
  }


  /**
   * Render the pot.
   */
  public void render() {
    int zoom = this.mode + 1;
    Rectangle printSurface = space.resized(zoom * space.getRay()).surroundBySquare(angle);

    Renderer.drawTexture(printSurface, Texture.pot);
    Renderer.drawTexture(printSurface, Texture.potEmpty);
    if (checkForContent(-1) != 3 && !isBurnt()) {
      float totalContent = 3 - this.checkForContent(-1);
      float alphaTomato = this.checkForContent(0) / totalContent;
      Renderer.drawTextureTransparent(printSurface, alphaTomato, Texture.potTomato);
      float alphaOnion = this.checkForContent(2) / totalContent;
      Renderer.drawTextureTransparent(printSurface, alphaOnion, Texture.potOnion);
      float alphaMushroom = this.checkForContent(3) / totalContent;
      Renderer.drawTextureTransparent(printSurface, alphaMushroom, Texture.potMushroom);
    }

    Dot pos = space.getPos();
    if (!hasBurn) {
      switch (nbIng) {
        case 1:
          ivList.get(0).setPos(pos.getX(), pos.getY() - 35);
          ivList.get(0).setIngredient(content[0]);
          ivList.get(0).setVisible(true);
          break;
        case 2:
          ivList.get(0).setPos(pos.getX() - 35, pos.getY());
          ivList.get(0).setIngredient(content[0]);
          ivList.get(0).setVisible(true);

          ivList.get(1).setPos(pos.getX() + 35, pos.getY());
          ivList.get(1).setIngredient(content[1]);
          ivList.get(1).setVisible(true);
          break;
        case 3:
          ivList.get(0).setPos(pos.getX(), pos.getY() - 35);
          ivList.get(0).setIngredient(content[0]);
          ivList.get(0).setVisible(true);

          ivList.get(1).setPos(pos.getX() + 30, pos.getY() + 18);
          ivList.get(1).setIngredient(content[1]);
          ivList.get(1).setVisible(true);

          ivList.get(2).setPos(pos.getX() - 30, pos.getY() + 18);
          ivList.get(2).setIngredient(content[2]);
          ivList.get(2).setVisible(true);
          break;
        default:
          break;
      }
    }
    for (IngredientVisualizer iv : ivList) {
      iv.render();
    }

    if (hasBurn) {
      Renderer.drawTexture(printSurface, Texture.burntPot);
      for (IngredientVisualizer iv : ivList) {
        iv.setVisible(false);
      }
    }

    Dot timeBarPos = new Dot(pos.getX(), pos.getY() + 30);
    timebar.render(currentCookingTime, timeBarPos);
  }


  public int[] getContent() {
    return content;
  }


  public int getNbIng() {
    return nbIng;
  }
}
