package io.github.mwttg.games.platform.ecs.component.input;

import java.util.Objects;
import java.util.StringJoiner;

public class PlayerInputComponent {

  private final KeyState jump;
  private final KeyState left;
  private final KeyState right;

  public PlayerInputComponent() {
    this.jump = new KeyState();
    this.left = new KeyState();
    this.right = new KeyState();
  }

  public KeyState getJump() {
    return jump;
  }

  public KeyState getLeft() {
    return left;
  }

  public KeyState getRight() {
    return right;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerInputComponent that)) {
      return false;
    }
    return jump.equals(that.jump) && left.equals(that.left) && right.equals(that.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jump, left, right);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PlayerInputComponent.class.getSimpleName() + "[", "]")
        .add("jump=" + jump)
        .add("left=" + left)
        .add("right=" + right)
        .toString();
  }
}
