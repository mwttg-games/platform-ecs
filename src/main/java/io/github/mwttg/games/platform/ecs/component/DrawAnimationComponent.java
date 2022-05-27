package io.github.mwttg.games.platform.ecs.component;

import io.github.mwttg.games.opengl.basic.utilities.uniform.TexturedUniforms;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public record DrawAnimationComponent(DrawComponent drawComponent, AnimationTimingComponent timingComponent) {

  public static DrawAnimationComponent create(final float[] vertices, final float[] textureCoordinates, final int textureId,
                                              final List<Integer> delaysInMs) {
    final var drawComponent = DrawComponent.create(vertices, textureCoordinates, textureId);
    final var timingComponent = new AnimationTimingComponent(delaysInMs);
    return new DrawAnimationComponent(drawComponent, timingComponent);
  }

  public int getFirstVertexOfCurrentSpritePlane() {
    return timingComponent.getCurrentFrame() * 6;
  }

  // Getter

  public int getVertexArrayObjectId() {
    return drawComponent.vertexArrayObjectId();
  }

  public int getShaderProgramId() {
    return drawComponent.shaderProgramId();
  }

  public int getTextureId() {
    return drawComponent.textureId();
  }

  public int getAmountOfPoints() {
    return drawComponent.amountOfPoints();
  }

  public TexturedUniforms getUniforms() {
    return drawComponent.uniforms();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DrawAnimationComponent that)) {
      return false;
    }
    return drawComponent.equals(that.drawComponent) && timingComponent.equals(that.timingComponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(drawComponent, timingComponent);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DrawAnimationComponent.class.getSimpleName() + "[", "]")
        .add("drawComponent=" + drawComponent)
        .add("timingComponent=" + timingComponent)
        .toString();
  }
}
