package io.github.mwttg.games.platform.player;

import java.util.Objects;
import java.util.StringJoiner;

public class PlayerData {

  private FacingDirection facingDirection;
  private final TileSize tileSize;
  // more to add like activated power ups, stats (in subclasses etc.)


  public PlayerData() {
    this.facingDirection = FacingDirection.RIGHT;
    this.tileSize = new TileSize(1.0f, 1.0f);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerData that)) {
      return false;
    }
    return facingDirection == that.facingDirection && tileSize.equals(that.tileSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facingDirection, tileSize);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PlayerData.class.getSimpleName() + "[", "]")
        .add("facingDirection=" + facingDirection)
        .add("tileSize=" + tileSize)
        .toString();
  }
}
