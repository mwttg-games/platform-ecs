package io.github.mwttg.games.platform.scene.objects;

import org.joml.Matrix4f;

public interface SceneObject {

  void draw(final Matrix4f view, final Matrix4f projection);
}
