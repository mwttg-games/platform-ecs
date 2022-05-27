package io.github.mwttg.games.platform.ecs.system;

import io.github.mwttg.games.platform.ecs.component.DrawComponent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

public final class DrawSystem extends AbstractDrawSystem {

  private DrawSystem() {
  }

  public static void draw(final DrawComponent component, final Matrix4f model, final Matrix4f view, final Matrix4f projection) {
    GL41.glBindVertexArray(component.vertexArrayObjectId());
    GL41.glUseProgram(component.shaderProgramId());
    enableVertexAttribArray();
    component.uniforms().upload(model, view, projection, component.textureId());
    GL41.glDrawArrays(GL41.GL_TRIANGLES, 0, component.amountOfPoints());
    disableVertexAttribArray();
  }
}
