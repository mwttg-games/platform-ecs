package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerWalkRightState extends PlayerWalkState {

  public PlayerWalkRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                              final PlayerStateComponent playerStateComponent,
                              final Matrix4f transform,
                              final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_RIGHT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.RIGHT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    toIdleRight(inputVector);
    toWalkLeft(inputVector);
    toJumpUpRight(inputVector);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownRight(onGround);
  }

  private void toIdleRight(final Vector2i inputVector) {
    if (inputVector.x() == 0) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }

  private void toJumpUpRight(final Vector2i inputVector) {
    if (inputVector.y() == 1) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void toFallDownRight(final boolean onGround) {
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toWalkLeft(final Vector2i inputVector) {
    if (inputVector.x() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }
}
