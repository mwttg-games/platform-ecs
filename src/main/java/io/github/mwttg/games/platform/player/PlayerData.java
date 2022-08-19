package io.github.mwttg.games.platform.player;

public class PlayerData {

  private FacingDirection facingDirection;
  private final TileSize tileSize;
  private final PlayerAbility playerAbility;
  private int jumpCounter;
  // more to add like activated power ups, stats (in subclasses etc.)

  public PlayerData() {
    this.facingDirection = FacingDirection.RIGHT;
    this.tileSize = new TileSize(1.0f, 1.0f);
    this.jumpCounter = 0;
    this.playerAbility = new PlayerAbility();
  }

  public FacingDirection getFacingDirection() {
    return facingDirection;
  }

  public void setFacingDirection(final FacingDirection facingDirection) {
    this.facingDirection = facingDirection;
  }

  public TileSize getTileSize() {
    return tileSize;
  }

  public void incJumpCounter() {
    jumpCounter = jumpCounter + 1;
  }

  public void resetJumpCounter() {
    jumpCounter = 0;
  }

  public int getJumpCounter(){
    return jumpCounter;
  }

  public PlayerAbility getPlayerAbility() {
    return playerAbility;
  }
}
