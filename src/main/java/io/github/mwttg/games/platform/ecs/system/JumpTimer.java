package io.github.mwttg.games.platform.ecs.system;

public class JumpTimer {

  private long startTick;
  private long lastTick;

  public JumpTimer() {
    this.startTick = System.currentTimeMillis();
    this.lastTick = startTick;
  }

  public void reset() {
    this.startTick = System.currentTimeMillis();
    this.lastTick = startTick;
  }

  public float getDuration() {
    final var currentTick = System.currentTimeMillis();
    lastTick = currentTick;
    return (currentTick - startTick) / 1000.0f;
  }

  // hopefully called before #getDuration
  public float getLastDuration() {
    return (lastTick - startTick) / 1000.0f;
  }
}
