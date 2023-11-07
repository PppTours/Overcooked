package fr.ovrckdlike.ppp.objects;

import java.util.ArrayList;
import java.util.List;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.objects.Pot;
import fr.ovrckdlike.ppp.physics.Dot;

/**
 * The class representing a plate. Which is an ingredient container.
 */
public class Plate extends Item implements IngredientContainer {
  /**
   * Content of the plate.
   */
  private boolean[] content = new boolean[15];
  private int[] soupContent = new int[3];

  /**
   * If the plate is dirty.
   */
  private boolean dirty;

  /**
   * If the content is cooked.
   */
  private final boolean cooked;

  /**
   * If the content is burnt.
   */
  private boolean burnt;

  /**
   * Number of ingredients in the plate.
   */
  private byte ingCount;

  /**
   * List of the ingredient visualizer.
   */
  private final List<IngredientVisualizer> ivList = new ArrayList<>();


  /**
   * Constructor of the plate.
   *
   * @param pos Position of the plate.
   * @param dirty If the plate is dirty.
   */
  public Plate(Dot pos, boolean dirty) {
    this(pos, dirty, 1);
  }

  /**
   * Constructor of the plate.
   *
   * @param pos Position of the plate.
   * @param dirty If the plate is dirty.
   * @param mode The mode of the plate.
   */
  public Plate(Dot pos, boolean dirty, int mode) {
    super(pos);
    this.dirty = dirty;
    cooked = false;
    burnt = false;
    this.mode = mode;

    for (int i = 0; i < 5; i++) {
      Dot posCopy = new Dot(pos);
      ivList.add(i, new IngredientVisualizer(0, posCopy));
    }

    ingCount = 0;
  }

  /**
   * Get the content of the plate.
   *
   * @return The content of the plate.
   */
  public boolean[] getContent() {
    return content;
  }

  /**
   * Get if the content of the plate is cooked.
   *
   * @return If the content of the plate is cooked.
   */
  public boolean getCooked() {
    return cooked;
  }

  /**
   * Get if the plate is dirty.
   *
   * @return If the plate is dirty.
   */
  public boolean getDirty() {
    return this.dirty;
  }

  /**
   * Wash the plate.
   */
  public void wash() {
    this.dirty = false;
  }

  /**
   * Get if the plate is empty.
   *
   * @return True if the plate is empty, false otherwise.
   */
  public boolean isEmpty() {
    return (ingCount == 0);
  }

  /**
   * Burn the plate.
   */
  public void burn() {
    flush();
    burnt = true;
  }

  /**
   * Prepare the plate.
   */
  public void prepare() {}

  /**
   * Cook the plate.
   */
  public void cook() {
    content[14] = true;
  }

  /**
   * Fill the plate with an ingredient.
   *
   * @param ingredient Ingredient to add in the plate.
   * @return True if the ingredient could be in the plate, false otherwise.
   */
  @Override
  public boolean fill(Ingredient ingredient) {
    if (!ingredient.getPrepared()) {
      return false;
    }
    if (dirty || burnt) {
      return false;
    }

    if (this.content[ingredient.getType()]) {
      return false;
    }
    if (ingCount > 5) {
      return false;
    } else {
      this.content[ingredient.getType()] = true;
      ingCount++;
      return true;
    }
  }

  public boolean fillSoup(Pot p) {
	if (p.isCooked() && p.getNbIng() == 3) {
	  for (int i = 0; i < p.getContent().length; i++) {
		  soupContent[i] = p.getContent()[i];
	  }
	  
	  content[13] = true;
	  return true;
	}
	else return false;
  }
  
  /**
   * Flush the content of the plate.
   */
  public void flush() {
    ingCount = 0;
    burnt = false;
    for (IngredientVisualizer iv : ivList) {
      iv.setVisible(false);
    }
    for (int i = 0; i < 15; i++) {
      this.content[i] = false;
    }
  }

  /**
   * Fusion du contenu de 2 assiettes.
   *
   * @param food Si l'assiette est sale ou non.
   * @return Si la fusion a réussi.
   */
  public boolean merge(boolean[] food) {
    if (dirty) {
      return false;
    }
    if (this.content[13]) {
      return false;
    }

    boolean[] afterMerge = new boolean[15];
    int nbIngredients = 0;
    for (int i = 0; i < 13; i++) {
      if (food[i] && this.content[i]) {
        return false;
      }
      afterMerge[i] = (food[i] || this.content[i]);
      nbIngredients++;
    }
    if (nbIngredients > 5) {
      return false;
    } else {
      ingCount = (byte) nbIngredients;
      this.content = afterMerge;
      return true;
    }
  }

  /**
   * Render the plate.
   */
  public void render() { //TODO affichage soup
    int zoom = mode + 1;
    if (mode == 2) {
      return;
    }
    if (!dirty) {
      Renderer.drawTexture(space.resized(zoom * space.getRay()).surroundBySquare(0),
          Texture.plate);
    } else {
      Renderer.drawTexture(space.resized(zoom * space.getRay()).surroundBySquare(0),
          Texture.dirtyPlate);
    }
    int[] ingIdx = new int[5];
    byte i = 0;
    for (int k = 0; k < 13; k++) {
      if (content[k]) {
        ingIdx[i] = k;
        i++;
      }
    }
    Dot pos = space.getPos();

    switch (ingCount) {
      case 1:
        ivList.get(0).setPos(pos.getX(), pos.getY() - 35);
        ivList.get(0).setIngredient(ingIdx[0]);
        ivList.get(0).setVisible(true);
        break;
      case 2:
        ivList.get(0).setPos(pos.getX() - 35, pos.getY());
        ivList.get(0).setIngredient(ingIdx[0]);
        ivList.get(0).setVisible(true);

        ivList.get(1).setPos(pos.getX() + 35, pos.getY());
        ivList.get(1).setIngredient(ingIdx[1]);
        ivList.get(1).setVisible(true);
        break;
      case 3:
        ivList.get(0).setPos(pos.getX(), pos.getY() - 35);
        ivList.get(0).setIngredient(ingIdx[0]);
        ivList.get(0).setVisible(true);

        ivList.get(1).setPos(pos.getX() + 30, pos.getY() + 18);
        ivList.get(1).setIngredient(ingIdx[1]);
        ivList.get(1).setVisible(true);

        ivList.get(2).setPos(pos.getX() - 30, pos.getY() + 18);
        ivList.get(2).setIngredient(ingIdx[2]);
        ivList.get(2).setVisible(true);
        break;
      case 4:
        ivList.get(0).setPos(pos.getX() - 25, pos.getY() - 25);
        ivList.get(0).setIngredient(ingIdx[0]);
        ivList.get(0).setVisible(true);

        ivList.get(1).setPos(pos.getX() + 25, pos.getY() - 25);
        ivList.get(1).setIngredient(ingIdx[1]);
        ivList.get(1).setVisible(true);

        ivList.get(2).setPos(pos.getX() + 25, pos.getY() + 25);
        ivList.get(2).setIngredient(ingIdx[2]);
        ivList.get(2).setVisible(true);

        ivList.get(3).setPos(pos.getX() - 25, pos.getY() + 25);
        ivList.get(3).setIngredient(ingIdx[3]);
        ivList.get(3).setVisible(true);
        break;
      case 5:
        ivList.get(0).setPos(pos.getX(), pos.getY() - 35);
        ivList.get(0).setIngredient(ingIdx[0]);
        ivList.get(0).setVisible(true);

        ivList.get(1).setPos(pos.getX() + 33, pos.getY() - 11);
        ivList.get(1).setIngredient(ingIdx[1]);
        ivList.get(1).setVisible(true);

        ivList.get(2).setPos(pos.getX() + 21, pos.getY() + 28);
        ivList.get(2).setIngredient(ingIdx[2]);
        ivList.get(2).setVisible(true);

        ivList.get(3).setPos(pos.getX() - 21, pos.getY() + 28);
        ivList.get(3).setIngredient(ingIdx[3]);
        ivList.get(3).setVisible(true);

        ivList.get(4).setPos(pos.getX() - 33, pos.getY() - 11);
        ivList.get(4).setIngredient(ingIdx[4]);
        ivList.get(4).setVisible(true);
        break;
      default:
        break;
    }
    for (IngredientVisualizer iv : ivList) {
      iv.render();
    }
  }
}
