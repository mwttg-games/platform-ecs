package io.github.mwttg.games.platform.ecs.system.draw;

import io.github.mwttg.games.platform.ecs.component.draw.SpriteStatesAnimationComponent;
import org.joml.Matrix4f;

public class SpriteStateAnimationSystem {

  public static void draw(final SpriteStatesAnimationComponent stateComponent, final Matrix4f model, final Matrix4f view,
                          final Matrix4f projection) {
    final var component = stateComponent.getCurrent();
    SpriteAnimationSystem.draw(component, model, view, projection);
  }
}
