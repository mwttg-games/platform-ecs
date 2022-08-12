package io.github.mwttg.games.platform.draw;

import io.github.mwttg.games.opengl.basic.utilities.uniform.TexturedUniforms;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A data container which is needed for drawing an 'animated' plane (2 triangles). This data container is used by the
 * {@link SpriteAnimationSystem}.
 *
 * @param spriteComponent the {@link SpriteComponent}
 * @param timingComponent the {@link SpriteAnimationTimingComponent}
 */
public record SpriteAnimationComponent(SpriteComponent spriteComponent, SpriteAnimationTimingComponent timingComponent) {

  public static SpriteAnimationComponent create(final float[] vertices, final float[] textureCoordinates, final int textureId,
                                                final List<Integer> delaysInMs) {
    final var drawComponent = SpriteComponent.create(vertices, textureCoordinates, textureId);
    final var timingComponent = new SpriteAnimationTimingComponent(delaysInMs);
    return new SpriteAnimationComponent(drawComponent, timingComponent);
  }

  public int getFirstVertexOfCurrentSpriteFrame() {
    return timingComponent.getCurrentFrame() * 6;
  }

  // Getter

  public int getVertexArrayObjectId() {
    return spriteComponent.vertexArrayObjectId();
  }

  public int getShaderProgramId() {
    return spriteComponent.shaderProgramId();
  }

  public int getTextureId() {
    return spriteComponent.textureId();
  }

  public TexturedUniforms getUniforms() {
    return spriteComponent.uniforms();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SpriteAnimationComponent that)) {
      return false;
    }
    return spriteComponent.equals(that.spriteComponent) && timingComponent.equals(that.timingComponent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(spriteComponent, timingComponent);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SpriteAnimationComponent.class.getSimpleName() + "[", "]")
        .add("spriteComponent=" + spriteComponent)
        .add("timingComponent=" + timingComponent)
        .toString();
  }
}
