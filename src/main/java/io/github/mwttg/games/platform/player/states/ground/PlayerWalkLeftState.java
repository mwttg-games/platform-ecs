package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerWalkLeftState extends PlayerWalkState {

  public PlayerWalkLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final PlayerEffectComponent playerEffectComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_LEFT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (idle(playerInput)) {
      getPlayerStateComponent().switchToIdleLeftState();
    } else if (inputRight(playerInput)) {
      getPlayerStateComponent().switchToWalkRightState();
    } else if (jumpUp(playerInput)) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    } else if (fallDown(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownLeftState();
    } else if (grabLadder(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    } else if (goLadderDown(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    }
  }
}
