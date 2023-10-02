package fr.ovrckdlike.ppp.gameplay;

public class Score {
  private static Score s;
  private int score;
  private int nbPenalty;
  private float multiplier;
  private int nbSupply;
  private int nbError;

  private Score() {
    score = 0;
    nbPenalty = 0;
    nbSupply = 0;
    nbError = 0;
    multiplier = 1f;
  }

  public static Score get() {
    if (s == null) {
      s = new Score();
    }
    return s;
  }

  public int getScore() {
    return score;
  }

  public void penalty() {
    score -= 30;
    nbPenalty++;
    multiplier = 1f;
  }

  public void supply() {
    nbSupply++;
    score += (int) (10f * multiplier);
    multiplier *= 1.25;
  }

  public void error() {
    score -= 15;
    nbError++;
  }

}
