package io.github.mwttg.games.platform.ecs.system;

public class Timer {

  private long lastTick;

  public Timer() {
    this.lastTick = System.currentTimeMillis();
  }

  public void reset() {
    this.lastTick = System.currentTimeMillis();
  }

  // get delta time in seconds AND reset lastTick
  public float getDeltaTime() {
    final var currentTick = System.currentTimeMillis();
    final var delta = (currentTick - lastTick) / 1000.0f;
    lastTick = currentTick;
    return delta;
  }
}
