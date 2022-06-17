package io.github.mwttg.games.platform.ecs.system.draw;

import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

/**
 * Simple System for drawing a plane with a texture (a Sprite).
 */
public final class SpriteSystem extends AbstractDrawSystem {

  private SpriteSystem() {
  }

  public static void draw(final SpriteComponent component, final Matrix4f model, final Matrix4f view,
                          final Matrix4f projection) {
    GL41.glBindVertexArray(component.vertexArrayObjectId());
    GL41.glUseProgram(component.shaderProgramId());
    enableVertexAttribArray();
    component.uniforms().upload(model, view, projection, component.textureId());
    GL41.glDrawArrays(GL41.GL_TRIANGLES, 0, 6);
    disableVertexAttribArray();
  }
}
