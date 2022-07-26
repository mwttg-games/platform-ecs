package io.github.mwttg.games.platform.ecs.system.movement;

class ActionWalk {

  private ActionWalk() {
  }

  static float getDeltaDistanceX(final float velocity, final float deltaTime) {
    return velocity * deltaTime;
  }
}
