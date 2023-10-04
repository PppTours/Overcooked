package fr.ovrckdlike.ppp.scene;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.SoundHandler;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.IngredientContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.Bin;
import fr.ovrckdlike.ppp.tiles.ContainerTile;
import fr.ovrckdlike.ppp.tiles.CuttingTable;
import fr.ovrckdlike.ppp.tiles.Dryer;
import fr.ovrckdlike.ppp.tiles.Furnace;
import fr.ovrckdlike.ppp.tiles.GasCooker;
import fr.ovrckdlike.ppp.tiles.IngredientRefiller;
import fr.ovrckdlike.ppp.tiles.PlateReturn;
import fr.ovrckdlike.ppp.tiles.Sink;
import fr.ovrckdlike.ppp.tiles.Table;
import fr.ovrckdlike.ppp.tiles.Tile;
import java.util.List;

public class GameScene extends Scene {
  private static GameScene game;

  private List<Item> itemList;
  private List<Tile> tileList;
  private List<Player> playerList;
  private RecipeScheduler recSch;

  private byte plateToReturn;

  private int mapNum;
  private Map map;


  public static GameScene get() {
    if (game == null) {
      game = new GameScene();
    }
    return game;
  }

  public void reset() {
    map.clearMap();
    recSch.reset();
    itemList = map.getItemList();
    tileList = map.getTileList();
    playerList = map.getPlayerList();

    this.mapNum = HyperParameters.get().getMap();
    this.map = Map.get();
    if (Map.buildMap(mapNum)) {
      System.out.println("Error while building the map");
    }

    Texture.loadForMapType(map.getType());


    recSch = RecipeScheduler.get();

  }

  public static void deleteItem(Item i) {
    game.itemList.remove(i);
  }

  public static List<Player> getPlayers() {
    return game.playerList;
  }

  public static void addPlateToReturn() {
    game.plateToReturn++;
  }

  private GameScene() {
    // change map here
    this.mapNum = HyperParameters.get().getMap();
    this.map = Map.get();
    if (Map.buildMap(mapNum)) {
      System.out.println("Error while building the map");
    }

    Texture.loadForMapType(map.getType());

    itemList = map.getItemList();
    tileList = map.getTileList();
    playerList = map.getPlayerList();

    recSch = RecipeScheduler.get();
    recSch.setRecSet(map.getType());

    plateToReturn = 0;
  }

  public void render() {
    for (Player p : playerList) {
      p.render();
    }
    for (Tile tile : tileList) {
      tile.render();
    }
    for (Item item : itemList) {
      if (!item.isOnTable()) {
        item.render();
      }
    }
    recSch.render();
  }

  public void run() {
    boolean flagDropP1 = true;
    boolean flagDropP2 = true;

    if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
      SceneManager.get().pauseGame();
      SoundHandler.play(SoundHandler.menu);
    }

    recSch.run();

    for (Tile tile : tileList) {
      if (tile instanceof GasCooker) {
        ((GasCooker) tile).cook();
      }
      if (tile instanceof Furnace) {
        ((Furnace) tile).cook();
      }
    }

    for (Player p : playerList) {
      p.updateKeyPressed();

      for (Tile tile : tileList) {

        //player can exchange items with this tile
        if (tile instanceof ContainerTile) {

          //player is in range, press the key and cooldown is ok
          if (tile.getSpace().includes(p.whereToDrop())
              && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
            p.resetLastPickDrop();
            Item item = p.getInHand();
            ContainerTile tileC = (ContainerTile) tile;

            //player wants to merge ingredient in hand with container in tile
            if (item instanceof Ingredient && (tileC).getContent() instanceof IngredientContainer) {
              if (((IngredientContainer) (tileC).getContent()).fill((Ingredient) item)) {
                //merge is successful (ingredients corresponds)
                itemList.remove(item);
                p.drop();
              } else {
                //merge aborted, player exchange the item in hand with the content of tile
                p.drop();
                item = (tileC).takeOrDrop(item);
                p.take(item);
              }
            } else if (item instanceof IngredientContainer
                && ((ContainerTile) tile).getContent() instanceof Ingredient) {
              //player wants to merge ingredient in tile with container in hand
              //successful
              if (((IngredientContainer) item).fill(((Ingredient) (tileC).getContent()))) {
                itemList.remove(tileC.takeOrDrop(null));
              } else {
                // aborted
                p.drop();
                item = (tileC).takeOrDrop(item);
                p.take(item);
              }
            } else {
              //player exchange item in hand with tile content
              p.drop();
              item = (tileC).takeOrDrop(item);
              p.take(item);
            }

          }
        }

        // player take a plate in the dryer
        if (tile instanceof Dryer && p.getInHand() == null) {
          if (tile.isInTile(p.whereToDrop())
              && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
            ((Dryer) tile).takePlate(p);
          }
        }

        //player drop a plate in sink
        if (tile instanceof Sink && p.getInHand() instanceof Plate) {
          if (tile.isInTile(p.whereToDrop())
              && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
            if (((Plate) p.getInHand()).getDirty()) {
              Sink sink = (Sink) tile;
              sink.addPlate();
              itemList.remove(p.getInHand());
              p.drop();
            }
          }
          if (tile.isInTile(p.whereToDrop()) && p.getInteract()) {
            tile.use(p);
          }
        }

        //player get plate in dryer
        if (plateToReturn > 0) {
          if (tile instanceof PlateReturn) {
            ((PlateReturn) tile).addPlate();
            plateToReturn--;
          }
        }
      }

      // player interact 
      for (Tile tile : tileList) {
        if (p.getPickDrop() && tile.isInTile(p.whereToDrop())) {
          flagDropP1 = false;
        }
        if (p.getInteract()) {
          if (tile.isInTile(p.whereToDrop())) {
            if (tile instanceof Sink) {
              SoundHandler.play(SoundHandler.washing);
            } else if (tile instanceof Furnace) {
              SoundHandler.play(SoundHandler.baking);
            } else if (tile instanceof GasCooker) {
              SoundHandler.play(SoundHandler.cooking);
            } else if (tile instanceof CuttingTable) {
              SoundHandler.play(SoundHandler.cutting);
            } else if (tile instanceof Dryer
                || tile instanceof Bin
                || tile instanceof Table) {
              SoundHandler.play(SoundHandler.putting);
            } else if (tile instanceof PlateReturn
                || tile instanceof IngredientRefiller) {
              SoundHandler.play(SoundHandler.taking);
            } else {
              SoundHandler.play(SoundHandler.click);
            }
            tile.use(p);
          }
        }
        if (!p.getInteract()) {
          p.unlockMove();

        }
      }

      if (p.getPickDrop() && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && flagDropP1) {
        p.resetLastPickDrop();
        if (p.getInHand() == null) {
          p.takeNearestItem(itemList);
        } else {
          p.drop();
        }
      }
    }
    for (Item item : itemList) {
      item.collideEntity(playerList);
      item.collideEntity(itemList);
      item.collideTile(tileList);
    }

    for (Player player : playerList) {
      player.collideTile(tileList);
      player.collidePlayer(playerList);
    }
  }
}
