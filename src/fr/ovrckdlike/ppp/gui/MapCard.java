package fr.ovrckdlike.ppp.gui;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.MapInfo;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import java.io.IOException;

// roulette ou boutons ?

/**
 * MapCard is a card that contains a preview of the map, the name of the map, the difficulty of the
 * map and the best score on the map.
 */
public class MapCard extends SelectCard {
  /**
   * The preview of the map.
   */
  private Texture preview;

  /**
   * The name of the map.
   */
  private Text name;

  /**
   * The difficulty of the map.
   */
  private Text difficulty;

  /**
   * The best score on the map.
   */
  private Text bestScore;

  /**
   * The information of the map.
   */
  private MapInfo info;

  /**
   * A constructor for the MapCard.
   *
   * @param pos the position of the card
   * @param map the number of the map
   */
  public MapCard(Dot pos, int map) {
    super(new Rectangle(pos, 800f, 700f, 0f));
    info = new MapInfo(map);
    name = new Text(info.getName(),
        new Rectangle(pos.getX(), pos.getY() - 300f, 700f, 50f), Color.black);
    difficulty = new Text("Difficulty : ",
        new Rectangle(pos.getX() - 200f, pos.getY() + 230, 320f, 40f), Color.black);
    bestScore = new Text("Best score : " + info.getMaxScore(),
        new Rectangle(pos.getX() - 100, pos.getY() + 300f, 520f, 40f), Color.black);

    try {
      preview = Texture.loadTexture("/maps/map" + map + "/preview.png", true);
    } catch (IOException e) {
      System.err.print("no map preview found for map " + map);
      e.printStackTrace();
    }
  }

  /**
   * Render the card.
   */
  @Override
  public void render() {
    Renderer.drawQuad(space, Color.gold);
    name.render();
    Renderer.drawTexture(
      new Rectangle(space.getX(), space.getY() - 50f, 750f, 422f), preview);
    difficulty.render();
    for (int i = 0; i < info.getDifficulty(); i++) {
      Renderer.drawTexture(
        new Rectangle(space.getX() + 150 + 70f * i, space.getY() + 230f,
          60f, 60f, 0f), Texture.pepper);
    }

    bestScore.render();
    //render the button with name, preview image, difficulty, high score and map type
  }

  /**
   * Get the choice of the user.
   *
   * @return the number of the map
   */
  @Override
  public Object getChoice() {
    return info.getMapNum();
  }
}
