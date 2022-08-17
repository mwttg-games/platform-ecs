package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.FallDown;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerFallDownLeftState extends PlayerFallDownState {

  public PlayerFallDownLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                 final PlayerStateComponent playerStateComponent,
                                 final Matrix4f transform,
                                 final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return IDLE_LEFT;
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
    if (inputVector.x() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);

    handleStateTransitions(inputVector, onGround);

    if (!onGround && inputVector.x() == 1) {
      getPlayerStateComponent().switchToFallDownRightState(getInAirTime());
    }
  }
}
