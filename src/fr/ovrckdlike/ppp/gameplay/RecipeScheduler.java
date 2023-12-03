package fr.ovrckdlike.ppp.gameplay;


import fr.ovrckdlike.ppp.map.MapType;
import fr.ovrckdlike.ppp.objects.Recipe;
import fr.ovrckdlike.ppp.physics.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manage the commands and update score.
 */
public class RecipeScheduler {

  /**
   * The singleton instance.
   */
  private static RecipeScheduler rs;

  /**
   * The list of recipes.
   */
  private List<Recipe> recipeList;

  /**
   * The time since the last recipe was added.
   */
  private float timeSinceLastAdd;

  /**
   * The set of recipes.
   */
  private MapType recipeSet;

  /**
   * The constructor of the class.
   * It is private because it is a singleton.
   */
  private RecipeScheduler() {
    recipeList = new ArrayList<Recipe>();
  }

  /**
   * Reset the list of recipes.
   */
  public void reset() {
    recipeList.clear();
  }

  /**
   * Get the singleton instance.
   *
   * @return the singleton instance
   */
  public static RecipeScheduler get() {
    if (rs == null) {
      rs = new RecipeScheduler();
    }
    return rs;
  }

  /**
   * Set the set of recipes.
   *
   * @param newSet the new set of recipes
   */
  public void setRecSet(MapType newSet) {
    recipeSet = newSet;
  }

  /**
   * This method manage the commands.
   * It adds a command every 45 seconds.
   */
  public void run() {
    if (recipeList.size() < 2) {
      recipeList.add(new Recipe(recipeSet, (byte) recipeList.size()));
      timeSinceLastAdd = 0;
    }
    timeSinceLastAdd += Time.get().getDtS();
    if (timeSinceLastAdd >= 45 && recipeList.size() < 11) {
      recipeList.add(new Recipe(recipeSet, (byte) recipeList.size()));
      timeSinceLastAdd = 0;
    }
    for (Recipe r : recipeList) {
      r.updateTimer();
      if (r.getTimer() <= 0) {
        r.reset();
        r.getIngredients();
        Score.get().penalty();
      }
    }
  }

  /**
   * Check if the recipe is valid for the command.
   *
   * @param content the content of the recipe made.
   */
  public void checkContent(boolean[] content) {
    boolean flag = true;
    /*
    if (!recipeList.isEmpty()) {
      for (Recipe r : recipeList) {
        if (r.checkValid(content, recipeList) && flag) {
          flag = false;
          completeRecipe(r);
        }
      }
      if (flag) {
        Score.get().error();
      }
    }
     */

  }

  /**
   * Update the score if the recipe is completed.
   *
   * @param completed the completed recipe
   */
  public void completeRecipe(Recipe completed) {
    // on ne peut pas acceder aux recettes, il faut une liste d'ingrédients
    int idx = recipeList.indexOf(completed);
    if (idx == -1) {
      return;
    }
    Score.get().supply();
    float timeLeft = recipeList.get(idx).getTimer();
    recipeList.remove(completed);
    for (int k = idx; k < recipeList.size(); k++) {
      recipeList.get(k).setComNo((byte) k);
    }
  }

  /**
   * Render the commands.
   */
  public void render() {
    for (Recipe r : recipeList) {
      r.render();
    }
  }
}
