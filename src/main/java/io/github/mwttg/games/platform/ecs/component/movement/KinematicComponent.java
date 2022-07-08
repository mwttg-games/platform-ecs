package io.github.mwttg.games.platform.ecs.component.movement;

import java.util.Objects;
import java.util.StringJoiner;

public class KinematicComponent {

  private float horizontalVelocity;
  private float verticalVelocity;

  public KinematicComponent() {
    this.horizontalVelocity = 0.0f;
    this.verticalVelocity = 0.0f;
  }

  public float getHorizontalVelocity() {
    return horizontalVelocity;
  }

  public void setHorizontalVelocity(float horizontalVelocity) {
    this.horizontalVelocity = horizontalVelocity;
  }

  public float getVerticalVelocity() {
    return verticalVelocity;
  }

  public void setVerticalVelocity(float verticalVelocity) {
    this.verticalVelocity = verticalVelocity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KinematicComponent that)) {
      return false;
    }
    return Float.compare(that.horizontalVelocity, horizontalVelocity) == 0
        && Float.compare(that.verticalVelocity, verticalVelocity) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(horizontalVelocity, verticalVelocity);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", KinematicComponent.class.getSimpleName() + "[", "]")
        .add("horizontalVelocity=" + horizontalVelocity)
        .add("horizontalVelocity=" + verticalVelocity)
        .toString();
  }
}
