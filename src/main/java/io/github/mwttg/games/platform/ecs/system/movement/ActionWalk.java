package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;

class ActionWalk {

  static void execute(final TransformComponent transformComponent, final float velocity, final float deltaTime) {
    final var deltaDistance = velocity * deltaTime;
    transformComponent.getModelMatrix().translate(deltaDistance ,0.0f, 0.0f);
  }
}
