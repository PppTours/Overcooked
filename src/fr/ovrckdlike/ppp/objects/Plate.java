package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.map.MapType;
import fr.ovrckdlike.ppp.physics.Dot;
import java.util.ArrayList;
import java.util.List;

/**
 * The class representing a plate. Which is an ingredient container.
 */
public class Plate extends Item implements IngredientContainer {
  /**
   * Content of the plate.
   */
  private boolean[] content = new boolean[15];
  private int[] soupContent = new int[3];

  private final Ingredient[] ingredients = new Ingredient[5];

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
      ingredients[ingCount] = ingredient;
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
    } else {
      return false;
    }
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
      ingredients[i] = null;
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
   * Set parameters for the ingredient visualizer.
   *
   * @param posX The x pos where the ingredient will be shown.
   * @param posY The y pos where the ingredient will be shown.
   * @param ing The ingredien to show.
   * @param scale The scale of the ingredient.
   * @param index The index of the ingredient in the list.
   * @param surroundedBySquare If the ingredient should be in a zone.
   */
  public void showIngredients(float posX, float posY, Ingredient ing, int scale, int index,
                              boolean surroundedBySquare) {
    ivList.get(index).setPos(posX, posY);
    ivList.get(index).setIngredient(ing);
    ivList.get(index).setVisible(true);
    ivList.get(index).setScale(scale);
    ivList.get(index).setSurroundedBySquare(surroundedBySquare);
  }

  private void renderRecipe() {
    MapType type = Map.get().getType();
    if (type == MapType.PIZZA) {
      renderPizza();
    } else if (type == MapType.BURGER) {
      renderBurger();
    } else if (type == MapType.SALAD) {
      renderSalad();
    } else if (type == MapType.NOODLES) {
      renderNoodles();
    } else if (type == MapType.SOUP) {
      renderSoup();
    }
  }

  private void renderSoup() {
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (ing.getType() == 2 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.potMushroom);
        } else if (ing.getType() == 1 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.potOnion);
        } else if (ing.getType() == 0 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.potTomato);
        }
      }
    }
  }

  private void renderPizza() {
    boolean hasPasta = false;
    boolean hasTomato = false;
    boolean hasCheese = false;
    boolean hasSausage = false;
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (ing.getType() == 8 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.flattenPizzaDough);
          hasPasta = true;
        }
        if (ing.getType() == 0 && ing.getPrepared() && hasPasta) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.pizza);
          hasTomato = true;
        }
        if (ing.getType() == 5 && ing.getPrepared() && hasTomato) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.slicedCheese);
          hasCheese = true;
          if (hasSausage) {
            Renderer.drawTexture(
                space.resized(2f * space.getRay()).surroundBySquare(0),
                Texture.CheeseSausagePizza);
          }
        }
        if (ing.getType() == 7 && ing.getPrepared() && hasTomato) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.sausagePizza);
          hasSausage = true;
          if (hasCheese) {
            Renderer.drawTexture(
                space.resized(2f * space.getRay()).surroundBySquare(0),
                Texture.CheeseSausagePizza);
          }
        }
      }
    }
  }

  private void renderBurger() {
    boolean hasBread = false;
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (ing.getType() == 9 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(1.5f * space.getRay()).surroundBySquare(0),
              Texture.slicedBread);
          hasBread = true;
        }
        if (ing.getType() == 1 && ing.getPrepared() && hasBread) {
          Renderer.drawTexture(
              space.resized(0.6f * space.getRay()).surroundBySquare(0),
              Texture.slicedSalad);
          Renderer.drawTexture(
              space.resized(0.6f * space.getRay()).surroundBySquare(30).move(new Dot(9, 10)),
              Texture.slicedSalad);
          Renderer.drawTexture(
              space.resized(0.6f * space.getRay()).surroundBySquare(130).move(new Dot(-10, -8)),
              Texture.slicedSalad);
          Renderer.drawTexture(
              space.resized(0.6f * space.getRay()).surroundBySquare(130).move(new Dot(-12, 10)),
              Texture.slicedSalad);
          Renderer.drawTexture(
              space.resized(0.6f * space.getRay()).surroundBySquare(130).move(new Dot(10, -12)),
              Texture.slicedSalad);
        }
        if (ing.getType() == 0 && ing.getPrepared() && hasBread) {
          Renderer.drawTexture(
              space.resized(0.4f * space.getRay()).surroundBySquare(30).move(new Dot(9, 10)),
              Texture.slicedTomato);
          Renderer.drawTexture(
              space.resized(0.4f * space.getRay()).surroundBySquare(130).move(new Dot(-10, -8)),
              Texture.slicedTomato);
          Renderer.drawTexture(
              space.resized(0.4f * space.getRay()).surroundBySquare(130).move(new Dot(-12, 10)),
              Texture.slicedTomato);
          Renderer.drawTexture(
              space.resized(0.4f * space.getRay()).surroundBySquare(130).move(new Dot(10, -12)),
              Texture.slicedTomato);
        }
        if (ing.getType() == 4 && ing.getPrepared() && hasBread) {
          Renderer.drawTexture(
              space.resized(1.5f * space.getRay()).surroundBySquare(0),
              Texture.cookedSteak);
        }
        if (ing.getType() == 5 && ing.getPrepared() && hasBread) {
          Renderer.drawTexture(
              space.resized(1.5f * space.getRay()).surroundBySquare(0),
              Texture.slicedCheese);
        }

      }
    }
  }

  private void renderSalad() {
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (ing.getType() == 1 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.slicedSalad);
        }
        if (ing.getType() == 0 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(1.7f * space.getRay()).surroundBySquare(0),
              Texture.slicedTomato);
        }
        if (ing.getType() == 2 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(1.7f * space.getRay()).surroundBySquare(0),
              Texture.slicedOnion);
        }
      }
    }
  }

  private void renderNoodles() {
    boolean hasPasta = false;
    boolean hasTomato = false;
    boolean hasCheese = false;
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (ing.getType() == 6 && ing.getPrepared()) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.pastaLayer);
          hasPasta = true;
        }
        if (ing.getType() == 0 && ing.getPrepared() && hasPasta && !hasCheese) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.tomatoLayer);
          hasTomato = true;
        }
        if (ing.getType() == 5 && hasPasta && !hasTomato) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.cheeseLayer);
          hasCheese = true;
        }
        if (ing.getType() == 4 && hasTomato) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.bolognese);
        }
        if (ing.getType() == 4 && hasCheese) {
          Renderer.drawTexture(
              space.resized(2f * space.getRay()).surroundBySquare(0),
              Texture.carbonara);
        }
      }
    }
  }

  public static boolean checkContent(ArrayList<Integer> possibilities,
                                     Ingredient[] ingredients,
                                     int nbIngredients) {
    if (possibilities.size() != nbIngredients) {
      return false;
    }
    for (Ingredient ing : ingredients) {
      if (ing != null) {
        if (!possibilities.contains(ing.getType())) {
          return false;
        }
      }
    }
    return true;
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
    Dot pos = space.getPos();

    switch (ingCount) {
      case 1:
        showIngredients(pos.getX(), pos.getY() - 40, ingredients[0], 13, 0, true);
        break;
      case 2:
        showIngredients(pos.getX() + 15, pos.getY() - 40, ingredients[0], 13, 0, true);
        showIngredients(pos.getX() - 15, pos.getY() - 40, ingredients[1], 13, 1, true);
        break;
      case 3:
        showIngredients(pos.getX(), pos.getY() - 40, ingredients[0], 13, 0, true);
        showIngredients(pos.getX() + 30, pos.getY() - 40, ingredients[1], 13, 1, true);
        showIngredients(pos.getX() - 30, pos.getY() - 40, ingredients[2], 13, 2, true);
        break;
      case 4:
        showIngredients(pos.getX() + 15, pos.getY() - 40, ingredients[0], 13, 0, true);
        showIngredients(pos.getX() - 15, pos.getY() - 40, ingredients[1], 13, 1, true);
        showIngredients(pos.getX() + 45, pos.getY() - 40, ingredients[2], 13, 2, true);
        showIngredients(pos.getX() - 45, pos.getY() - 40, ingredients[3], 13, 3, true);
        break;
      case 5:
        showIngredients(pos.getX(), pos.getY() - 40, ingredients[0], 13, 0, true);
        showIngredients(pos.getX() + 30, pos.getY() - 40, ingredients[1], 13, 1, true);
        showIngredients(pos.getX() - 30, pos.getY() - 40, ingredients[2], 13, 2, true);
        showIngredients(pos.getX() + 60, pos.getY() - 40, ingredients[3], 13, 3, true);
        showIngredients(pos.getX() - 60, pos.getY() - 40, ingredients[4], 13, 4, true);
        break;
      default:
        break;
    }
    // Render the recipe
    renderRecipe();

    for (IngredientVisualizer iv : ivList) {
      iv.render();
    }
  }
}
