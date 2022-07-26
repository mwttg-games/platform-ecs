package io.github.mwttg.games.platform.ecs.component.movement;

public class MovementStateComponent {

  private final Timings fallTimings;
  private final Timings jumpTimings;

  private boolean idle;
  private boolean falling;
  private boolean walkingLeft;
  private boolean walkingRight;
  private boolean jumping;

  public MovementStateComponent() {
    this.fallTimings = new Timings();
    this.jumpTimings = new Timings();

    this.idle = false;
    this.falling = true;
    this.walkingLeft = false;
    this.walkingRight = false;
    this.jumping = false;
  }

  public void updateFallTiming(final float deltaTime) {
    fallTimings.update(deltaTime);
  }

  public void updateJumpTiming(final float deltaTime) {
    jumpTimings.update(deltaTime);
  }

  public void activateJumping() {
    falling = false;
    if (!jumping) {
      jumping = true;
      jumpTimings.reset();
    }
  }

  public void activateFalling() {
    jumping = false;
    if (!falling) {
      falling = true;
      fallTimings.reset();
    }
  }

  public void deactivateFalling() {
    falling = false;
  }

  public void activateWalkingLeft() {
    walkingLeft = true;
    walkingRight = false;
  }

  public void activateWalkingRight() {
    walkingRight = true;
    walkingLeft = false;
  }

  public void deactivateWalking() {
    walkingLeft = false;
    walkingRight = false;
  }

  public Timings getFallTimings() {
    return fallTimings;
  }

  public Timings getJumpTimings() {
    return jumpTimings;
  }

  public boolean isIdle() {
    return idle;
  }

  public boolean isFalling() {
    return falling;
  }

  public boolean isWalkingLeft() {
    return walkingLeft;
  }

  public boolean isWalkingRight() {
    return walkingRight;
  }

  public boolean isJumping() {
    return jumping;
  }
}
