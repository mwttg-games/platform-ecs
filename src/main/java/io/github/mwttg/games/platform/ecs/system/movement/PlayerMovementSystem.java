package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.SceneConfiguration;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.KinematicComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.system.Timer;
import io.github.mwttg.games.platform.ecs.system.input.PlayerInputSystem;

public class PlayerMovementSystem {

  public static void update(final long windowId,
                            final PlayerInputComponent playerInputComponent,
                            final KinematicComponent kinematicComponent,
                            final PlayerConfiguration playerConfiguration,
                            final SceneConfiguration sceneConfiguration,
                            final Timer timer,
                            final TransformComponent transformComponent) {

    PlayerInputSystem.update(windowId, playerInputComponent);
    PlayerKinematicSystem.update(windowId, playerInputComponent, kinematicComponent, playerConfiguration, sceneConfiguration);

    final var deltaX = kinematicComponent.getHorizontalVelocity() * timer.getDeltaTime();
    transformComponent.getModelMatrix().translate(deltaX, 0.0f, 0.0f);
  }
}
