package io.github.mwttg.games.platform.ecs.component;

import io.github.mwttg.games.opengl.basic.utilities.buffer.VertexArrayObject;
import io.github.mwttg.games.opengl.basic.utilities.shader.Shader;
import io.github.mwttg.games.opengl.basic.utilities.shader.ShaderProgram;
import io.github.mwttg.games.opengl.basic.utilities.uniform.TexturedUniforms;
import io.github.mwttg.games.platform.ecs.system.SpriteSystem;

/**
 * A data container which is needed for drawing a plane (2 triangles) with a texture on it (a Sprite). This data container is
 * used by the {@link SpriteSystem}.
 *
 * @param vertexArrayObjectId the OpenGL id of the geometry and texture information
 * @param textureId           the OpenGL id of the texture
 * @param shaderProgramId     the OpenGL shader program id which is used for drawing the plane
 * @param uniforms            the locations of the uploaded data (model-, view- and projection matrix, texture-sampler)
 */
public record SpriteComponent(int vertexArrayObjectId, int textureId, int shaderProgramId, TexturedUniforms uniforms) {

  public static SpriteComponent create(final float[] vertices, final float[] uvCoordinates, final int textureId) {
    final var vertexArrayObjectId = VertexArrayObject.create(vertices, uvCoordinates);
    ShaderProgram.validate(Shader.DEFAULT);
    final var uniforms = new TexturedUniforms(Shader.DEFAULT);

    return new SpriteComponent(vertexArrayObjectId, textureId, Shader.DEFAULT, uniforms);
  }

  // TODO for custom shaders
  // public static DrawComponent create(final float[] vertices, final float[] uvCoordinates, final int textureId,
  //                                    final int shaderProgramId)
}