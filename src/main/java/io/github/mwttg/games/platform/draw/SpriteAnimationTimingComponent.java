package io.github.mwttg.games.platform.draw;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A data container (or state) for Sprite animations. The delay of each frame is defined by the {@link List}
 * delaysInMs (The items in that list, the size, also defines how many frames are existing for this animation).
 */
public class SpriteAnimationTimingComponent {

  private final List<Integer> delaysInMs;
  private final int maxSprites;
  private int currentFrame;
  private long lastTick;

  public SpriteAnimationTimingComponent(final List<Integer> delaysInMs) {
    this(delaysInMs, 0);
  }

  public SpriteAnimationTimingComponent(final List<Integer> delaysInMs, final int currentFrame) {
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
    if (!(o instanceof SpriteAnimationTimingComponent that)) {
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
    return new StringJoiner(", ", SpriteAnimationTimingComponent.class.getSimpleName() + "[", "]")
        .add("delaysInMs=" + delaysInMs)
        .add("maxSprites=" + maxSprites)
        .add("currentFrame=" + currentFrame)
        .add("lastTick=" + lastTick)
        .toString();
  }
}
