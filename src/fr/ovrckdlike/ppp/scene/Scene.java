package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.physics.Time;

/**
 * This class manage the scenes.
 */
public abstract class Scene {
  /**
   * The cooldown when changing selection in round-robin.
   */
  private float changeCooldown;

  /**
   * The method that render the scene.
   */
  public abstract void render();

  /**
   * The method that run the scene.
   */
  public abstract void run();

  /**
   * The method that clock the cooldown.
   */
  public void clockCooldown() {
    if (changeCooldown > 0) {
      changeCooldown -= Time.get().getDtS();
    }
  }

  /**
   * The method that check if the cooldown is over.
   *
   * @return true if the cooldown is over, false otherwise.
   */
  public boolean noCooldown() {
    return (changeCooldown <= 0);
  }

  /**
   * The method that set the cooldown.
   *
   * @param time the time to set.
   */
  public void setCooldown(float time) {
    changeCooldown = time;
  }

  /**
   * The method that execute the scene.
   */
  public void execute() {
    run();
    render();
  }
}
