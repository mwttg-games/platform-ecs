package io.github.mwttg.games.platform.ecs;

public record SceneConfiguration(float verticalUpGravity, float verticalDownGravity) {

  public String prettyPrint() {
    return """
            + Scene configuration
                Vertical Gravity (up) ........... %s
                Vertical Gravity (down) ......... %s
        """.formatted(verticalUpGravity, verticalDownGravity);
  }
}
