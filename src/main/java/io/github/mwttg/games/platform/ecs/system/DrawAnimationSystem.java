package io.github.mwttg.games.platform.ecs.system;

import io.github.mwttg.games.platform.ecs.component.DrawAnimationComponent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

public final class DrawAnimationSystem extends AbstractDrawSystem {

  private DrawAnimationSystem() {
  }

  public static void draw(final DrawAnimationComponent component, final Matrix4f model, final Matrix4f view,
                          final Matrix4f projection) {
    GL41.glBindVertexArray(component.getVertexArrayObjectId());
    GL41.glUseProgram(component.getShaderProgramId());
    enableVertexAttribArray();
    component.getUniforms().upload(model, view, projection, component.getTextureId());

    final var first = component.getFirstVertexOfCurrentSpritePlane();
    GL41.glDrawArrays(GL41.GL_TRIANGLES, first, 6);

    disableVertexAttribArray();
  }
}
