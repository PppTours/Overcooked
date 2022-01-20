package fr.ovrckdlike.ppp.objects;

public class Pot extends CookerContainer{
	private int[] content;
	
	
	public Pot(float[] pos) {
		this.pos = pos;
	}
	public void flush() {
		for (int i = 0; i < 3; i++) {
			this.content[i] = 0;
		}
	}
	
	public void render() {
		
	}

}
