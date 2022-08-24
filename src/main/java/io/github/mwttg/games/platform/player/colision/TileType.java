package io.github.mwttg.games.platform.player.colision;

public enum TileType {

  SPACE(0),

  SOLID(1),

  LADDER_TOP(2),

  LADDER(3);

  TileType(final int index) {
  }

  public static TileType createFromIndex(final int index) {
    return switch (index) {
      case 0 -> SPACE;
      case 1 -> SOLID;
      case 2 -> LADDER_TOP;
      case 3 -> LADDER;
      default -> throw new IllegalArgumentException("no TileType found for index: " + index);
    };
  }
}
