package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.ground.PlayerOnGroundState;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerFallDownState extends PlayerInAirState {

  public PlayerFallDownState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final PlayerEffectComponent playerEffectComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  public void enter() {
    super.enter();
  }

  @Override
  public void exit() {
    super.exit();
  }

  protected boolean grabLadder(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var onLadder = SensorSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    return onLadder && playerInput.yAxis() == 1;
  }

  protected boolean doubleJump(final PlayerInput playerInput) {
    return playerInput.jump()
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT;
  }

  protected boolean coyoteTime(final PlayerInput playerInput) {
    return playerInput.jump()
        && getPlayerData().getJumpCounter() == 0
        && getInAirTime() <= Configuration.COYOTE_TIME
        && getPlayerStateComponent().getPreviousState() instanceof PlayerOnGroundState;
  }

  protected boolean onGround(final SensorComponent sensorComponent) {
    return SensorSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), sensorComponent);
  }
}
