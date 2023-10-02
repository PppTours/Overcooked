package fr.ovrckdlike.ppp.objects;

import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.physics.Dot;

public class Ingredient extends Item {
  private int type;
  private boolean prepared;
  private boolean cooked;
  private boolean cookable;

  public boolean isCooked() {
    return cooked;
  }

  public boolean isCookable() {
    return cookable;
  }

  public void cook() {
    cooked = true;
  }

  public int getType() {
    return this.type;
  }

  public Ingredient(int type) {
    super(new Dot(0f, 0f));
    this.type = type;
    prepared = this.type == 6 || this.type == 11;
    angle = 0;
    mode = 0;
    cooked = false;

    cookable = type == 0 || type == 4 || type == 6 || type == 10 || type == 11 || type == 12;
  }

  public static Texture getTexture(int ingType) {
    return switch (ingType) {
      case 0 -> Texture.tomato;
      case 1 -> Texture.salade;
      case 2 -> Texture.onion;
      case 3 -> Texture.mushroom;
      case 4 -> Texture.meat;
      case 5 -> Texture.cheese;
      case 6 -> Texture.pasta;
      case 7 -> Texture.sausage;
      case 8 -> Texture.pizzaDough;
      case 9 -> Texture.burgerBread;
      case 10 -> Texture.chicken;
      case 11 -> Texture.rice;
      case 12 -> Texture.potato;
      default -> null;
    };
  }

  public boolean getPrepared() {
    return prepared;
  }

  @Override
  public void flush() {
    Map.get().getItemList().remove(this);
  }

  public void prepare() {
    this.prepared = true;
  }

  public void render() {
    float zoom = space.getRay() * (mode + 1);
    switch (type) {
      case 0:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedTomato);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.tomato);
        }
        break;
      case 1:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedSalade);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.salade);
        }
        break;
      case 2:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedOnion);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.onion);
        }
        break;
      case 3:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedMushroom);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.mushroom);
        }
        break;
      case 4:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedMeat);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.meat);
        }
        break;
      case 5:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedCheese);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.cheese);
        }
        break;
      case 6:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pasta);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pasta);
        }
        break;
      case 7:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedSausage);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.sausage);
        }
        break;
      case 8:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pizzaDough);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.pizzaDough);
        }
        break;
      case 9:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedBread);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.burgerBread);
        }
        break;
      case 10:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedChicken);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.chicken);
        }
        break;
      case 11:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.rice);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.rice);
        }
        break;
      case 12:
        if (prepared) {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.slicedPotato);
        } else {
          Renderer.drawTexture(space.resized(zoom).surroundBySquare(angle), Texture.potato);
        }
        break;
      default:
        break;

    }
  }
}
