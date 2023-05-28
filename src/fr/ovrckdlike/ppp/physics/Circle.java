package fr.ovrckdlike.ppp.physics;

public class Circle {
	private Dot centre;
	private float ray;
	
	public Circle(Dot centre, float ray) {
		this.centre = centre;
		this.ray = ray;
	}
	
	public void setPos(Dot newPos) {
		centre = newPos;
	}
	
	public Dot getPos() {
		return centre;
	}
	
	public float getRay() {
		return ray;
	}
	
	public void collide(Circle obj, Dot move, boolean mobile) {		//move is the quantity of overlapping in each direction
		float movement = (float) Math.sqrt(move.getX()*move.getX()+move.getY()*move.getY());
		collide(obj, movement, mobile);
	}
	
	public void collide(Circle obj, float movement, boolean mobile) {
		
		float angle = centre.angleTo(obj.getPos());
		if (mobile) {		
			getPos().addToThis((float)((-movement) * Math.cos(angle)/2), (float)((movement * Math.sin(angle))/2));
			obj.getPos().addToThis((float)((movement) * Math.cos(angle)/2), (float)((-movement * Math.sin(angle))/2));
		}
		else {
			getPos().addToThis((float)((-movement) * Math.cos(angle)), (float)((movement * Math.sin(angle))));
		}
	}
	
	public void collide(Rectangle obj, float movement) {
		Circle substitute = new Circle(obj.nearestFromPos(getPos()), 1);
		
		// critical case where player is into the tile
		if (substitute.getPos().equals(getPos())) {
			movement = getPos().distanceTo(obj.getPos());
			substitute = new Circle(new Dot(obj.getPos()), 1);
		}
		
		collide(substitute, movement, false);
	}
	
	public void collide(Rectangle obj, Dot move) {
		Circle substitute = new Circle(obj.nearestFromPos(getPos()), 1);
		collide(substitute, move, false);
	}
	
	public Rectangle surroundBySquare(float angle) {
		return new Rectangle(centre, 2*ray, 2*ray, angle);
	}
	
	//create a new circle at the same pos
	public Circle resized(float newRay) {
		return new Circle(centre, newRay);
	}
	
	//resize the current circle
	public void resize(float newRay) {
		ray = newRay;
	}
}
