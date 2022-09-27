package io.github.mwttg.games.platform.draw;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Timing {

  private final List<Integer> delaysInMs;
  private final int maxSprites;
  private int currentFrame;
  private long lastTick;

  public Timing(final List<Integer> delaysInMs) {
    this.delaysInMs = delaysInMs;
    this.currentFrame = 0;
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
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final Timing timing)) {
      return false;
    }
    return maxSprites == timing.maxSprites && currentFrame == timing.currentFrame && lastTick == timing.lastTick
        && delaysInMs.equals(timing.delaysInMs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delaysInMs, maxSprites, currentFrame, lastTick);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Timing.class.getSimpleName() + "[", "]")
        .add("delaysInMs=" + delaysInMs)
        .add("maxSprites=" + maxSprites)
        .add("currentFrame=" + currentFrame)
        .add("lastTick=" + lastTick)
        .toString();
  }
}
