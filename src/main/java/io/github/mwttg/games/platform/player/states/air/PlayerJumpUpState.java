package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerJumpUpState extends PlayerInAirState {

  public PlayerJumpUpState(final Map<String, Drawable> drawableByName,
                           final PlayerStateComponent playerStateComponent,
                           final PlayerEffectComponent playerEffectComponent,
                           final Matrix4f transform,
                           final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().incJumpCounter();
    getPlayerEffectComponent().startEffect(DUST_EFFECT, getTransform(), 300);
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

  protected boolean topBlocked(final SensorComponent sensorComponent) {
    return SensorSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), sensorComponent);
  }

  protected boolean jumpHighReached() {
    return getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME;
  }
}
