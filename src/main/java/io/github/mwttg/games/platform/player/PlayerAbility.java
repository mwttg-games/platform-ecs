package io.github.mwttg.games.platform.player;

public class PlayerAbility {

  private boolean doubleJump;

  public PlayerAbility() {
    this.doubleJump = false;
  }

  public void activateDoubleJump() {
    doubleJump = true;
  }

  public boolean hasDoubleJump() {
    return doubleJump;
  }
}
