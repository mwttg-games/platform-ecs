package io.github.mwttg.games.platform.ecs.component.input;

import io.github.mwttg.games.platform.ecs.system.Timer;
import java.util.Objects;
import java.util.StringJoiner;

public class KeyState {

  private final Timer timer;
  private boolean pressed;
  private float duration;

  public KeyState() {
    this.timer = new Timer();
    this.pressed = false;
    this.duration = 0.0f;
  }

  public void pressKey() {
    if (!pressed) {
      timer.reset();
      duration = 0.0f;
      pressed = true;
    }
  }

  public void releaseKey() {
    if (pressed) {
      duration = timer.getDeltaTime();
      pressed = false;
    }
  }

  public boolean isPressed() {
    return pressed;
  }

  public void setPressed(boolean pressed) {
    this.pressed = pressed;
  }

  public float getDuration() {
    return duration;
  }

  public void setDuration(float duration) {
    this.duration = duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyState keyState)) {
      return false;
    }
    return pressed == keyState.pressed && Float.compare(keyState.duration, duration) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pressed, duration);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", KeyState.class.getSimpleName() + "[", "]")
        .add("pressed=" + pressed)
        .add("duration=" + duration)
        .toString();
  }
}
