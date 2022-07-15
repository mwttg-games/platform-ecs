package io.github.mwttg.games.platform.ecs;

public record PlayerConfiguration(float walkVelocity, float initialJumpVelocity) {

  public String prettyPrint() {
    return """
            + Player configuration
                Walk Velocity ................... %s
                Initial Jump Velocity ........... %s
        """.formatted(walkVelocity, initialJumpVelocity);
  }
}
