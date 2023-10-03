package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.gui.TimeBar;
import fr.ovrckdlike.ppp.physics.Dot;

/**
 * An abstract class to represent a cooker container.
 */
public abstract class CookerContainer extends Item {
  protected final int cookingTime = 15;
  protected float currentCookingTime;
  protected boolean cooked;
  protected boolean burnt;
  protected TimeBar timebar;

  /**
   * Constructor of the class CookerContainer.
   *
   * @param pos the position of the cooker container.
   */
  public CookerContainer(Dot pos) {
    super(pos);
  }

  public void prepare() {}

  /**
   * A method to render the cooker container.
   */
  public abstract void render();

  /**
   * A method to flush the cooker container.
   */
  public abstract void flush();

  /**
   * A method to check if the cooker container is filled.
   *
   * @return true if the cooker container is filled, false otherwise.
   */
  public abstract boolean isFilled();

  /**
   * A setter to fire the cooker container.
   */
  public void burn() {
    burnt = true;
  }

  /**
   * A getter to check if the cooker container is burnt.
   *
   * @return true if the cooker container is burnt, false otherwise.
   */
  public boolean isBurnt() {
    return burnt;
  }

  /**
   * A method to use the cooker container.
   *
   * @param dt the cooking time.
   */
  public void cook(long dt) {
    float sdt = dt / 1E9f;
    currentCookingTime += sdt;
    if (currentCookingTime > cookingTime) {
      cooked = true;
    }
    if (currentCookingTime > 2 * cookingTime) {
      burnt = true;
    }
  }
}
