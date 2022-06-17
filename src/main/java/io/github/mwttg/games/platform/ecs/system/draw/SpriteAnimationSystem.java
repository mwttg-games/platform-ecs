package io.github.mwttg.games.platform.ecs.system.draw;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

/**
 * A system for drawing an animated Sprite. The {@link SpriteComponent} inside the {@link SpriteAnimationComponent} must contain
 * a plane for each frame as geometry data and one texture/image (where each frame is in it and each frame has the same size and
 * is the image is arranged/exported as one row). The geometry and uv-coordinates can be created with the {@link MeshFactory}.
 */
public final class SpriteAnimationSystem extends AbstractDrawSystem {

  private SpriteAnimationSystem() {
  }

  public static void draw(final SpriteAnimationComponent component, final Matrix4f model, final Matrix4f view,
                          final Matrix4f projection) {
    GL41.glBindVertexArray(component.getVertexArrayObjectId());
    GL41.glUseProgram(component.getShaderProgramId());
    enableVertexAttribArray();
    component.getUniforms().upload(model, view, projection, component.getTextureId());

    final var first = component.getFirstVertexOfCurrentSpriteFrame();
    GL41.glDrawArrays(GL41.GL_TRIANGLES, first, 6);

    disableVertexAttribArray();
  }
}
