package io.github.mwttg.games.platform.ecs.component.movement;

import lombok.Data;

@Data
public class VelocityComponent {

  private float horizontal = 0.0f;
  private float riseTime = 0.0f;
}