package fr.ovrckdlike.ppp.objects;

import static fr.ovrckdlike.ppp.objects.Plate.checkContent;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.IngredientVisualizer;
import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.map.MapType;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.GameScene;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A class to manage commands.
 */
public class Recipe {
  /**
   * The set of recipes.
   */
  MapType recipeSet;

  /**
   * The id of the recipe.
   */
  byte recipeId;

  /**
   * The timer (in seconds) of the recipe.
   */
  float timer;

  /**
   * The list of ingredients.
   */
  int[] ingredientList = new int[5];

  /**
   * The list of ingredient visualizers.
   *
   * @see IngredientVisualizer
   */
  List<IngredientVisualizer> ivList;

  /**
   * The command number.
   */
  byte commandNumero;

  /**
   * The rectangle to show the command to the players.
   */
  Rectangle space;

  /**
   * The time bar.
   */
  TimeBar timeBar;

  /**
   * The constructor of the class.
   *
   * @param recipeSet The set of recipes.
   * @param commandNumero The command number.
   */
  public Recipe(MapType recipeSet, byte commandNumero) {
    this.recipeSet = recipeSet;
    this.commandNumero = commandNumero;
    space = new Rectangle(220 * commandNumero + 120, 70, 240, 140);
    ivList = new ArrayList<>();

    timer = 60f;
    Rectangle timeBarPos = new Rectangle(space.getPos().add(new Dot(0, -65)), 200, 15, 0);
    timeBar = new TimeBar(timeBarPos, timer);



    File recipeFile = new File("res/recipes/set" + recipeSet.toString() + ".csv");
    try {
      Scanner scan = new Scanner(recipeFile);
      byte nbRecipe = 0;
      while (scan.hasNext()) {
        scan.next();
        nbRecipe++;
      }
      double rdm = Math.random();
      recipeId = (byte) (rdm * nbRecipe);
      scan.close();
      Scanner scan2 = new Scanner(recipeFile);
      String rawLine = "";
      byte k = 0;
      while (scan2.hasNext() && k <= recipeId) {
        rawLine = scan2.next();
        k++;
      }
      scan2.close();
      String[] splitedLine = rawLine.split(";");
      ingredientList = new int[splitedLine.length - 1];
      k = -1;
      for (String str : splitedLine) {
        if (k >= 0) {
          ingredientList[k] = Integer.parseInt(str);
        }
        k++;
      }
    } catch (FileNotFoundException e) {
      System.out.println("Recipe file res/recipes/set" + recipeSet + ".csv not found.");
    }
    byte ingIdx;
    for (ingIdx = 0; ingIdx < ingredientList.length; ingIdx++) {
      int ingSize = 38;
      float xing = space.getX() - ingSize * (ingredientList.length - 1) / 2f + ingSize * ingIdx;
      float ying = space.getY() + 50;

      ivList.add(new IngredientVisualizer(ingredientList[ingIdx], new Dot(xing, ying)));
    }
  }

  /**
   * Reset the timer.
   */
  public void reset() {
    timer = 60f;
  }

  /**
   * Get the command number.
   *
   * @return The command number.
   */
  public byte getComNo() {
    return commandNumero;
  }

  /**
   * Set the command number.
   *
   * @param numero The command number.
   */
  public void setComNo(byte numero) {
    commandNumero = numero;
    space.getPos().setX(220 * commandNumero);
  }

  /**
   * Get the timer.
   *
   * @return The timer.
   */
  public float getTimer() {
    return timer;
  }

  /**
   * Check if the command is valid.
   *
   * @param content The content of the command.
   * @return True if the command is valid, false otherwise.
   */
  public boolean checkValid(boolean[] content, List<Recipe> recipeList) {
    System.out.println(Arrays.toString(ingredientList));
    boolean flag = true;
    if (recipeList.contains(this)) {
      for (int ing : ingredientList) {
        if (!content[ing]) {
          flag = false;
          break;
        }
      }
    }

    return flag;
  }


  /**
   * Update the timer.
   */
  public void updateTimer() {
    timer -= Time.get().getDtS();
    if (timer < 0) {
      timer = 0;
    }
  }

  /**
   * Get the list of ingredients.
   *
   * @return The list of ingredients.
   */
  public int[] getIngredients() {
    return ingredientList;
  }

  /**
   * Render the command at the top of the screen.
   */
  public void render() {
    float alpha = .8f;
    Dot pos = space.getPos();
    float p1Dist = GameScene.getPlayers().get(0).distanceTo(pos);
    float p2Dist = GameScene.getPlayers().get(1).distanceTo(pos);
    float dist = Math.min(p1Dist, p2Dist);
    if (dist < space.getHeight() + 50) {
      alpha = .6f * dist / (space.getHeight() + 50) + .2f;
    }
    Renderer.drawTextureTransparent(space, alpha, Texture.recipeBackground);


    for (IngredientVisualizer iv : ivList) {
      iv.setVisible(true);
      iv.render();
    }
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i : ingredientList) {
      str.append(i).append(" ");
    }
    return str.toString();
  }

}
