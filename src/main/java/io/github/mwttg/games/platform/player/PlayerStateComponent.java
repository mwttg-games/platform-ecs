package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.PlayerState;
import io.github.mwttg.games.platform.player.states.air.PlayerFallDownLeftState;
import io.github.mwttg.games.platform.player.states.air.PlayerFallDownRightState;
import io.github.mwttg.games.platform.player.states.air.PlayerInAirState;
import io.github.mwttg.games.platform.player.states.air.PlayerJumpUpLeftState;
import io.github.mwttg.games.platform.player.states.air.PlayerJumpUpRightState;
import io.github.mwttg.games.platform.player.states.dead.PlayerDeadState;
import io.github.mwttg.games.platform.player.states.dead.PlayerReviveState;
import io.github.mwttg.games.platform.player.states.ground.PlayerIdleLeftState;
import io.github.mwttg.games.platform.player.states.ground.PlayerIdleRightState;
import io.github.mwttg.games.platform.player.states.ground.PlayerWalkLeftState;
import io.github.mwttg.games.platform.player.states.ground.PlayerWalkRightState;
import io.github.mwttg.games.platform.player.states.ladder.PlayerClimbUpLadder;
import io.github.mwttg.games.platform.player.states.ladder.PlayerIdleOnLadder;
import io.github.mwttg.games.platform.player.states.ladder.PlayerSlideDownLadder;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerStateComponent {

  private final PlayerIdleLeftState idleLeft;
  private final PlayerIdleRightState idleRight;
  private final PlayerWalkLeftState walkLeft;
  private final PlayerWalkRightState walkRight;
  private final PlayerFallDownLeftState fallDownLeft;
  private final PlayerFallDownRightState fallDownRight;
  private final PlayerJumpUpLeftState jumpUpLeft;
  private final PlayerJumpUpRightState jumpUpRight;
  private final PlayerClimbUpLadder climbUpLadder;
  private final PlayerSlideDownLadder slideDownLadder;
  private final PlayerIdleOnLadder idleOnLadder;
  private final PlayerDeadState dead;
  private final PlayerReviveState revive;

  private PlayerState previousState;
  private PlayerState currentState;

  public PlayerStateComponent(final Map<String, Drawable> drawableByName,
                              final PlayerEffectComponent playerEffectComponent,
                              final Matrix4f modelMatrix,
                              final PlayerData playerData) {
    this.idleLeft = new PlayerIdleLeftState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.idleRight = new PlayerIdleRightState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.walkLeft = new PlayerWalkLeftState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.walkRight = new PlayerWalkRightState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.fallDownLeft =
        new PlayerFallDownLeftState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.fallDownRight =
        new PlayerFallDownRightState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.jumpUpLeft = new PlayerJumpUpLeftState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.jumpUpRight =
        new PlayerJumpUpRightState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.climbUpLadder =
        new PlayerClimbUpLadder(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.slideDownLadder =
        new PlayerSlideDownLadder(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.idleOnLadder = new PlayerIdleOnLadder(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.dead = new PlayerDeadState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);
    this.revive = new PlayerReviveState(drawableByName, this, playerEffectComponent, modelMatrix, playerData);

    this.currentState = idleRight;
    this.previousState = currentState;  // to avoid npe
  }

  public PlayerState getPreviousState() {
    return previousState;
  }

  public PlayerState getCurrentState() {
    return currentState;
  }

  public void switchToIdleLeftState() {
    updateState(idleLeft);
  }

  public void switchToWalkRightState() {
    updateState(walkRight);
  }

  public void switchToIdleRightState() {
    updateState(idleRight);
  }

  public void switchToWalkLeftState() {
    updateState(walkLeft);
  }

  public void switchToFallDownLeftState() {
    updateState(fallDownLeft);
  }

  public void switchToFallDownLeftState(final float alreadyUsedAirTime) {
    updateState(fallDownLeft, alreadyUsedAirTime);
  }

  public void switchToFallDownRightState() {
    updateState(fallDownRight);
  }

  public void switchToFallDownRightState(final float alreadyUsedAirTime) {
    updateState(fallDownRight, alreadyUsedAirTime);
  }

  public void switchToJumpUpLeftState() {
    updateState(jumpUpLeft);
  }

  public void switchToJumpUpLeftState(final float alreadyUsedAirTime) {
    updateState(jumpUpLeft, alreadyUsedAirTime);
  }

  public void switchToJumpUpRightState() {
    updateState(jumpUpRight);
  }

  public void switchToJumpUpRightState(final float alreadyUsedAirTime) {
    updateState(jumpUpRight, alreadyUsedAirTime);
  }

  public void switchToIdleOnLadderState() {
    updateState(idleOnLadder);
  }

  public void switchToClimbUpLadderState() {
    updateState(climbUpLadder);
  }

  public void switchToSlideDownLadderState() {
    updateState(slideDownLadder);
  }

  public void switchToDeadState() {
    if (!(currentState instanceof PlayerDeadState)) {
      updateState(dead);
    }
  }

  public void switchToReviveState() {
    updateState(revive);
  }

  private void updateState(final PlayerState newState) {
    currentState.exit();

    previousState = currentState;
    currentState = newState;

    currentState.enter();
  }

  private void updateState(final PlayerInAirState inAirState, final float alreadyUsedAirTime) {
    if (!(currentState instanceof PlayerInAirState)) {
      throw new RuntimeException("Wrong player state (no player-'in air'-state)");
    }

    currentState.exit();

    previousState = currentState;
    currentState = inAirState;

    ((PlayerInAirState) currentState).enter(alreadyUsedAirTime);
  }
}
