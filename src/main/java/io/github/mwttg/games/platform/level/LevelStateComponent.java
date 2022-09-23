package io.github.mwttg.games.platform.level;

import java.util.Map;

public class LevelStateComponent {

  private LevelId levelId;
  private final Map<LevelId, LevelComponent> levelsById;

  public LevelStateComponent(final LevelId levelId, final Map<LevelId, LevelComponent> levelsById) {
    this.levelId = levelId;
    this.levelsById = levelsById;
  }

  public LevelComponent getLevelComponent() {
    return levelsById.get(levelId);
  }

  public void changeLevel(final Destination destination) {
    this.levelId = destination.levelId();
  }
}
