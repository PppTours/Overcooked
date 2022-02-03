package fr.ovrckdlike.ppp.graphics;

public class Color {
	
	public static Color white = new Color(255, 255, 255);
	public static Color black = new Color(0, 0, 0);
	public static Color lightGrey = new Color(200, 200, 200);
	public static Color red = new Color(255, 0, 0);
	public static Color yellow = new Color(255, 255, 0);
	public static Color pink = new Color(255, 86, 226);
	public static Color purple = new Color(174, 35, 255);
	public static Color darkGreen = new Color(0, 128, 0);
	public static Color darkGreenSelec = new Color(0, 128, 0, 100);
	

	public float r, g, b, a;

	public Color(int r, int g, int b, int a) {
		this.r = r/255f;
		this.g = g/255f;
		this.b = b/255f;
		this.a = a/255f;
	}
	
	public Color(int r, int g, int b) {
		this(r, g, b, 256);
	}
}