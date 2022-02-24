package fr.ovrckdlike.ppp.objects;

public class Plate extends Item {
	private boolean[] content = new boolean[15];
	private boolean dirty;

	
	public Plate(float[] pos, boolean dirty) {
		this.dirty = dirty;
		this.pos = pos; 	//position du centre de l'objet
		this.mode = 1;
	}
	
	public boolean getDirty() {
		return this.dirty;
	}
	
	public void wash() {
		this.dirty = false;
	}
	
	public boolean isEmpty() {
		for (int i = 0; i < 13; i++) {
			if (content[i] == true) return false;
		}
		return true;
	}
	
	public void prepare() {}
	
	public void cook() {
		content[14] = true;
	}
	
	public boolean fill(Ingredient ingredient) {
		int count = 0;
		for (int i = 0; i < 13; i++) {
			if (this.content[i]) {
				count++;
			}
		}
		if (count > 5) return false;
		else {
			this.content[ingredient.getType()] = true;
			return true;
		}
	}
	
	public void flush() {
		for (int i = 0; i < 15; i++) {
			this.content[i] = false;
		}
	}
	
	public boolean merge(boolean[] food) {	//fusion du contenu de 2 assiettes
		if (dirty) return false;
		if (this.content[13]) return false;
		
		boolean[] afterMerge = new boolean[15];
		int nbIngredients = 0;
		for(int i = 0; i< 13; i++) {
			if (food[i] && this.content[i]) return false;
			afterMerge[i] = (food[i]||this.content[i]);
			nbIngredients++;
		}
		if (nbIngredients > 5) return false;
		else {
			this.content = afterMerge;
			return true;
		}
		
	}
	
	public void render() {
		int zoom = mode+1;
		if (mode == 2) return;
		
		
	}
}
