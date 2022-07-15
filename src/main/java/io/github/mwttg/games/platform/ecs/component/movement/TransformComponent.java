package io.github.mwttg.games.platform.ecs.component.movement;

import java.util.Objects;
import java.util.StringJoiner;
import org.joml.Matrix4f;

public class TransformComponent {

  private final Matrix4f modelMatrix;

  public TransformComponent(final Matrix4f modelMatrix) {
    assert modelMatrix != null : "ModelMatrix is not allowed to be 'null'.";
    this.modelMatrix = modelMatrix;
  }

  public TransformComponent(final float x, final float y) {
    this.modelMatrix = new Matrix4f().translate(x, y, 0.0f);
  }

  public TransformComponent(final float x, final float y, final float z) {
    this.modelMatrix = new Matrix4f().translate(x, y, z);
  }

  public Matrix4f getModelMatrix() {
    return modelMatrix;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TransformComponent that)) {
      return false;
    }
    return modelMatrix.equals(that.modelMatrix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelMatrix);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TransformComponent.class.getSimpleName() + "[", "]")
        .add("modelMatrix=" + modelMatrix)
        .toString();
  }
}
