package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.SceneConfiguration;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.KinematicComponent;

public class PlayerKinematicSystem {

  public static void update(final long windowId, final PlayerInputComponent playerInputComponent,
                            final KinematicComponent kinematicComponent, final PlayerConfiguration playerConfiguration,
                            final SceneConfiguration sceneConfiguration) {
    var leftVelocityAddend = 0.0f;
    var rightVelocityAddend = 0.0f;

    if (playerInputComponent.getLeft().isPressed()) {
      leftVelocityAddend = -playerConfiguration.horizontalVelocity();
    }

    if (playerInputComponent.getRight().isPressed()) {
      rightVelocityAddend = playerConfiguration.horizontalVelocity();
    }

    kinematicComponent.setHorizontalVelocity(leftVelocityAddend + rightVelocityAddend);
  }
}
