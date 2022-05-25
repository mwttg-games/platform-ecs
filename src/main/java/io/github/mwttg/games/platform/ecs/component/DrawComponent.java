package io.github.mwttg.games.platform.ecs.component;

import io.github.mwttg.games.opengl.basic.utilities.buffer.VertexArrayObject;
import io.github.mwttg.games.opengl.basic.utilities.shader.Shader;
import io.github.mwttg.games.opengl.basic.utilities.shader.ShaderProgram;
import io.github.mwttg.games.opengl.basic.utilities.uniform.TexturedUniforms;

public record DrawComponent(int vertexArrayObjectId, int textureId, int shaderProgramId, TexturedUniforms uniforms,
                            int amountOfPoints) {

  public static DrawComponent create(final float[] vertices, final float[] uvCoordinates, final int textureId) {
    final var amountOfPoints = vertices.length / 3;
    final var vertexArrayObjectId = VertexArrayObject.create(vertices, uvCoordinates);
    ShaderProgram.validate(Shader.DEFAULT);
    final var uniforms = new TexturedUniforms(Shader.DEFAULT);

    return new DrawComponent(vertexArrayObjectId, textureId, Shader.DEFAULT, uniforms, amountOfPoints);
  }

  // TODO for custom shaders
  // public static DrawComponent create(final float[] vertices, final float[] uvCoordinates, final int textureId,
  //                                    final int shaderProgramId)
}