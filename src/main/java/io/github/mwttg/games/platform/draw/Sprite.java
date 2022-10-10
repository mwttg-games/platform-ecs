package io.github.mwttg.games.platform.draw;

import io.github.mwttg.games.opengl.basic.utilities.buffer.VertexArrayObject;
import io.github.mwttg.games.opengl.basic.utilities.shader.Shader;
import io.github.mwttg.games.opengl.basic.utilities.shader.ShaderProgram;
import io.github.mwttg.games.opengl.basic.utilities.uniform.TexturedUniforms;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

public record Sprite(int vertexArrayObjectId,
                     int textureId,
                     int shaderProgramId,
                     TexturedUniforms uniforms) implements Drawable {

  public static Sprite create(final float[] vertices, final float[] uvCoordinates, final int textureId) {
    final var vertexArrayObjectId = VertexArrayObject.create(vertices, uvCoordinates);
    ShaderProgram.validate(Shader.DEFAULT);
    final var uniforms = new TexturedUniforms(Shader.DEFAULT);

    return new Sprite(vertexArrayObjectId, textureId, Shader.DEFAULT, uniforms);
  }

  @Override
  public void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection) {
    GL41.glBindVertexArray(vertexArrayObjectId);
    GL41.glUseProgram(shaderProgramId);
    uniforms.upload(model, view, projection, textureId);
    GL41.glDrawArrays(GL41.GL_TRIANGLES, 0, 6);
  }
}
