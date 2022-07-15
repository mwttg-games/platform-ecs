package io.github.mwttg.games.platform.ecs.entity;

import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.SceneConfiguration;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteStatesAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.system.Timer;

public record PlayerEntity(long windowId,
                           TransformComponent transformComponent,
                           SpriteStatesAnimationComponent spriteStatesAnimationComponent,
                           PlayerInputComponent inputComponent,
                           VelocityComponent velocityComponent,
                           Timer timer,
                           PlayerConfiguration playerConfiguration,
                           SceneConfiguration sceneConfiguration) {

}
