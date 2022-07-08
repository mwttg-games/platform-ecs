package io.github.mwttg.games.platform.ecs;

public record SceneConfiguration(float verticalUpVelocity, float verticalDownVelocity) {

  public String prettyPrint() {
    return """
            + Scene configuration
                Vertical Up Velocity ............ %s
                Vertical Down Velocity .......... %s
        """.formatted(verticalUpVelocity, verticalDownVelocity);
  }
}
