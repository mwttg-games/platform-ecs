package io.github.mwttg.games.platform.player;

public class PlayerData {

  private FacingDirection facingDirection;
  private final TileSize tileSize;
  private int currentJumpCounter;
  // more to add like activated power ups, stats (in subclasses etc.)

  public PlayerData() {
    this.facingDirection = FacingDirection.RIGHT;
    this.tileSize = new TileSize(1.0f, 1.0f);
    this.currentJumpCounter = 0;
  }

  public FacingDirection getFacingDirection() {
    return facingDirection;
  }

  public void setFacingDirection(FacingDirection facingDirection) {
    this.facingDirection = facingDirection;
  }

  public TileSize getTileSize() {
    return tileSize;
  }

  public int getCurrentJumpCounter() {
    return currentJumpCounter;
  }

  public void resetCurrentJumpCounter() {
    currentJumpCounter = 0;
  }
}
