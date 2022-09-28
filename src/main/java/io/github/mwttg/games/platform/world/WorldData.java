package io.github.mwttg.games.platform.world;

public class WorldData {

  private final ActiveRespawn activeRespawn;

  public WorldData(final ActiveRespawn activeRespawn) {
    this.activeRespawn = activeRespawn;
  }

  public ActiveRespawn getActiveRespawn() {
    return activeRespawn;
  }
}
