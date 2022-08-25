package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.PlayerState;
import io.github.mwttg.games.platform.player.states.air.PlayerFallDownLeftState;
import io.github.mwttg.games.platform.player.states.air.PlayerFallDownRightState;
import io.github.mwttg.games.platform.player.states.air.PlayerInAirState;
import io.github.mwttg.games.platform.player.states.air.PlayerJumpUpLeftState;
import io.github.mwttg.games.platform.player.states.air.PlayerJumpUpRightState;
import io.github.mwttg.games.platform.player.states.ground.PlayerIdleLeftState;
import io.github.mwttg.games.platform.player.states.ground.PlayerIdleRightState;
import io.github.mwttg.games.platform.player.states.ground.PlayerWalkLeftState;
import io.github.mwttg.games.platform.player.states.ground.PlayerWalkRightState;
import io.github.mwttg.games.platform.player.states.ladder.PlayerOnLadderState;
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
  private final PlayerOnLadderState onLadderState;

  private PlayerState previousState;
  private PlayerState currentState;

  public PlayerStateComponent(final Map<String, SpriteAnimationComponent> animationComponentByName,
                              final PlayerEffectComponent playerEffectComponent,
                              final Matrix4f modelMatrix,
                              final PlayerData playerData) {
    this.idleLeft = new PlayerIdleLeftState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.idleRight = new PlayerIdleRightState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.walkLeft = new PlayerWalkLeftState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.walkRight = new PlayerWalkRightState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.fallDownLeft =
        new PlayerFallDownLeftState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.fallDownRight =
        new PlayerFallDownRightState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.jumpUpLeft = new PlayerJumpUpLeftState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.jumpUpRight =
        new PlayerJumpUpRightState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);
    this.onLadderState =
        new PlayerOnLadderState(animationComponentByName, this, playerEffectComponent, modelMatrix, playerData);

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

  public void switchToOnLadderState() {
    updateState(onLadderState);
  }

  private void updateState(final PlayerState newState) {
    currentState.exit();

    previousState = currentState;
    currentState = newState;

    System.out.println(String.format("previous: %-25s now: %-25s",
        previousState.getClass().getSimpleName(),
        currentState.getClass().getSimpleName()));

    currentState.enter();
  }

  private void updateState(final PlayerInAirState inAirState, final float alreadyUsedAirTime) {
    if (!(currentState instanceof PlayerInAirState)) {
      throw new RuntimeException("Wrong player state (no player-'in air'-state)");
    }

    currentState.exit();

    previousState = currentState;
    currentState = inAirState;

    System.out.println(String.format("*previous: %-25s now: %-25s",
        previousState.getClass().getSimpleName(),
        currentState.getClass().getSimpleName()));

    ((PlayerInAirState) currentState).enter(alreadyUsedAirTime);
  }
}
