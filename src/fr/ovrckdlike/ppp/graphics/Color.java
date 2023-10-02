package fr.ovrckdlike.ppp.graphics;

public class Color {

  public static Color white = new Color(255, 255, 255);
  public static Color black = new Color(0, 0, 0);
  public static Color lightGrey = new Color(200, 200, 200);
  public static Color grey = new Color(150, 150, 150);
  public static Color red = new Color(255, 0, 0);
  public static Color yellow = new Color(255, 255, 0);
  public static Color pink = new Color(255, 86, 226);
  public static Color purple = new Color(174, 35, 255);
  public static Color darkGreen = new Color(0, 128, 0);
  public static Color darkGreenSelec = new Color(0, 128, 0, 100);
  public static Color transparentWhite = new Color(255, 255, 255, 50);
  public static Color transparentGrey = new Color(50, 50, 50, 170);
  public static Color gold = new Color(255, 191, 0);


  public float valR;
  public float valG;
  public float valB;
  public float valA;

  public Color(int valR, int valG, int valB, int valA) {
    this.valR = valR / 255f;
    this.valG = valG / 255f;
    this.valB = valB / 255f;
    this.valA = valA / 255f;
  }

  public Color(int valR, int valG, int valB) {
    this(valR, valG, valB, 255);
  }
}