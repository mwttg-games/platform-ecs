package io.github.mwttg.games.platform.draw;

import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

public record AnimatedSprite(Sprite spriteFrames, Timing timing) implements Drawable {

  public static AnimatedSprite create(final float[] vertices,
                                      final float[] textureCoordinates,
                                      final int textureId,
                                      final List<Integer> delaysInMs) {
    final var frames = Sprite.create(vertices, textureCoordinates, textureId);
    final var timing = new Timing(delaysInMs);
    return new AnimatedSprite(frames, timing);
  }

  private int getFirstVertexOfCurrentSpriteFrame() {
    return timing.getCurrentFrame() * 6;
  }

  @Override
  public void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection) {
    GL41.glBindVertexArray(spriteFrames.vertexArrayObjectId());
    GL41.glUseProgram(spriteFrames().shaderProgramId());
    spriteFrames.uniforms().upload(model, view, projection, spriteFrames().textureId());

    final var first = getFirstVertexOfCurrentSpriteFrame();
    GL41.glDrawArrays(GL41.GL_TRIANGLES, first, 6);
  }
}
