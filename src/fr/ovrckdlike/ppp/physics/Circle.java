package fr.ovrckdlike.ppp.physics;


/**
 * A class to represent a circle. To be used in the physics engine.
 */
public class Circle {

  /**
   * centre is the centre of the circle.
   */
  private Dot centre;

  /**
   * ray is the radius of the circle.
   */
  private float ray;

  /**
   * Constructor of the class Circle.
   *
   * @param centre the centre of the circle.
   * @param ray the radius of the circle.
   */
  public Circle(Dot centre, float ray) {
    this.centre = centre;
    this.ray = ray;
  }

  /**
   * A setter for the centre of the circle.
   *
   * @param newPos the new position of the centre.
   */
  public void setPos(Dot newPos) {
    centre = new Dot(newPos);
  }

  /**
   * A getter for the centre of the circle.
   *
   * @return the centre of the circle.
   */
  public Dot getPos() {
    return centre;
  }

  /**
   * A getter for the radius of the circle.
   *
   * @return the radius of the circle.
   */
  public float getRay() {
    return ray;
  }

  /**
   * move is the quantity of overlapping in each direction.
   *
   * @param obj the circle to collide with.
   * @param move the quantity of overlapping in each direction.
   * @param mobile if the circle is mobile or not.
   */
  public void collide(Circle obj, Dot move, boolean mobile) {
    float movement = (float) Math.sqrt(move.getX() * move.getX() + move.getY() * move.getY());
    collide(obj, movement, mobile);
  }

  /**
   * A method to collide two circles.
   *
   * @param obj the circle to collide with.
   * @param movement the quantity of overlapping in each direction.
   * @param mobile if the circle is mobile or not.
   */
  public void collide(Circle obj, float movement, boolean mobile) {

    float angle = centre.angleTo(obj.getPos());
    if (mobile) {
      getPos().addToThis((float) ((-movement) * Math.cos(angle) / 2),
          (float) ((movement * Math.sin(angle)) / 2));
      obj.getPos().addToThis((float) ((movement) * Math.cos(angle) / 2),
          (float) ((-movement * Math.sin(angle)) / 2));
    } else {
      getPos().addToThis((float) ((-movement) * Math.cos(angle)),
          (float) ((movement * Math.sin(angle))));
    }
  }

  /**
   * A method to collide a circle with a rectangle.
   *
   * @param obj the rectangle to collide with.
   * @param movement the quantity of overlapping in each direction.
   */
  public void collide(Rectangle obj, float movement) {
    Circle substitute = new Circle(obj.nearestFromPos(getPos()), 1);

    // critical case where player is into the tile
    if (substitute.getPos().equals(getPos())) {
      movement = getPos().distanceTo(obj.getPos());
      substitute = new Circle(new Dot(obj.getPos()), 1);
    }

    collide(substitute, movement, false);
  }

  /**
   * A method to collide a circle with a rectangle.
   *
   * @param obj the rectangle to collide with.
   * @param move the quantity of overlapping in each direction.
   */
  public void collide(Rectangle obj, Dot move) {
    Circle substitute = new Circle(obj.nearestFromPos(getPos()), 1);
    collide(substitute, move, false);
  }

  /**
   * A method to define the hitbox of a circle.
   *
   * @param angle the angle of the hitbox.
   * @return the hitbox of the circle.
   */
  public Rectangle surroundBySquare(float angle) {
    return new Rectangle(centre, 2 * ray, 2 * ray, angle);
  }

  /**
   * A method to create a new circle at the same position.
   *
   * @param newRay the radius of the new circle.
   * @return the new circle.
   */
  public Circle resized(float newRay) {
    return new Circle(centre, newRay);
  }

  /**
   * A method to resize the current circle.
   *
   * @param newRay the new radius of the circle.
   */
  public void resize(float newRay) {
    ray = newRay;
  }
}
