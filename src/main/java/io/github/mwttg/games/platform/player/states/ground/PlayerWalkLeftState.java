package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerWalkLeftState extends PlayerWalkState {

  public PlayerWalkLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
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
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    toIdleLeft(inputVector);
    toWalkRight(inputVector);
    toJumpUpLeft(inputVector);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownLeft(onGround);
  }

  private void toIdleLeft(final Vector2i inputVector) {
    if (inputVector.x() == 0) {
      getPlayerStateComponent().switchToIdleLeftState();
    }
  }

  private void toJumpUpLeft(final Vector2i inputVector) {
    if (inputVector.y() == 1) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }

  private void toFallDownLeft(final boolean onGround) {
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toWalkRight(final Vector2i inputVector) {
    if (inputVector.x() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }
}
