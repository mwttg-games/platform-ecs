package io.github.mwttg.games.platform.ecs.component;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class AnimationTimingComponent {

  private final List<Integer> delaysInMs;
  private final int maxSprites;
  private int currentFrame;
  private long lastTick;

  public AnimationTimingComponent(final List<Integer> delaysInMs) {
    this(delaysInMs, 0);
  }

  public AnimationTimingComponent(final List<Integer> delaysInMs, final int currentFrame) {
    this.delaysInMs = delaysInMs;
    this.currentFrame = currentFrame;
    this.maxSprites = delaysInMs.size();
    this.lastTick = System.currentTimeMillis();
  }

  public int getCurrentFrame() {
    final var now = System.currentTimeMillis();

    if (now - lastTick > delaysInMs.get(currentFrame)) {
      lastTick = System.currentTimeMillis();
      incCurrentFrame();
    }

    return currentFrame;
  }

  private void incCurrentFrame() {
    currentFrame++;
    if (currentFrame >= maxSprites) {
      currentFrame = 0;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AnimationTimingComponent that)) {
      return false;
    }
    return maxSprites == that.maxSprites && currentFrame == that.currentFrame && lastTick == that.lastTick
        && delaysInMs.equals(that.delaysInMs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delaysInMs, maxSprites, currentFrame, lastTick);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AnimationTimingComponent.class.getSimpleName() + "[", "]")
        .add("delaysInMs=" + delaysInMs)
        .add("maxSprites=" + maxSprites)
        .add("currentFrame=" + currentFrame)
        .add("lastTick=" + lastTick)
        .toString();
  }
}
