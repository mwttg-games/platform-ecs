package io.github.mwttg.games.platform.ecs.component.movement;

public class Timings {

  // time for a loop (one cycle in main loop)
  private float deltaTime;

  // time from a point of start and now
  private float currentDuration;

  public Timings() {
    reset();
  }

  public void reset() {
    this.deltaTime = 0.0f;
    this.currentDuration = 0.0f;
  }

  public void update(final float delta) {
    deltaTime = delta;
    currentDuration = currentDuration + delta;
  }

  public float getDeltaTime() {
    return deltaTime;
  }

  public float getCurrentDuration() {
    return currentDuration;
  }

  // duration from the cycle before (used for e.g: player jumping timings)
  public float getLastDuration() {
    return currentDuration - deltaTime;
  }
}
