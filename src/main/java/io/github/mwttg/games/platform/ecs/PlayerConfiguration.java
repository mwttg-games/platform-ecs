package io.github.mwttg.games.platform.ecs;

public record PlayerConfiguration(float horizontalVelocity) {

  public String prettyPrint() {
    return """
            + Player configuration
                Horizontal Velocity ............. %s
        """.formatted(horizontalVelocity);
  }
}
