package fr.ovrckdlike.ppp.tiles;

/**
 * Interface for burnable tiles.
 */
public interface Burnable {
  /**
   * Returns true if the tile is burning.
   */
  public boolean isBurning();

  /**
   * Sets the tile on fire.
   */
  public void setInFire();

  /**
   * Stops the fire.
   */
  public void stopFire();
}
