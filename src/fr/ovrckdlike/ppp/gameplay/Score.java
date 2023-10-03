package fr.ovrckdlike.ppp.gameplay;

/**
 * The Score class is a singleton that contains the score of the player.
 */
public class Score {
  /**
   * The singleton instance.
   */
  private static Score s;

  /**
   * The score.
   */
  private int score;

  /**
   * The number of penalty.
   */
  private int nbPenalty;

  /**
   * The multiplier.
   */
  private float multiplier;

  /**
   * The number of command supplied
   */
  private int nbSupply;

  /**
   * The number of error during command preparation.
   */
  private int nbError;

  /**
   * The constructor.
   */
  private Score() {
    score = 0;
    nbPenalty = 0;
    nbSupply = 0;
    nbError = 0;
    multiplier = 1f;
  }

  /**
   * The getter of the singleton.
   *
   * @return the singleton instance.
   */
  public static Score get() {
    if (s == null) {
      s = new Score();
    }
    return s;
  }

  /**
   * The getter of the score.
   *
   * @return the score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Apply a penalty.
   */
  public void penalty() {
    score -= 30;
    nbPenalty++;
    multiplier = 1f;
  }

  /**
   * Apply the bonus when the player supply a command.
   */
  public void supply() {
    nbSupply++;
    score += (int) (10f * multiplier);
    multiplier *= 1.25;
  }

  /**
   * Apply the penalty when the player make an error during command preparation.
   */
  public void error() {
    score -= 15;
    nbError++;
  }

}
