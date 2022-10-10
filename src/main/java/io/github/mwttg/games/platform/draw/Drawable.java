package io.github.mwttg.games.platform.draw;

import org.joml.Matrix4f;

public interface Drawable {

  void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection);

}
