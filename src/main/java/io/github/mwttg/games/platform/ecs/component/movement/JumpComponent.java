package io.github.mwttg.games.platform.ecs.component.movement;

import io.github.mwttg.games.platform.ecs.system.JumpTimer;
import lombok.Data;

@Data
public class JumpComponent {


  private boolean inAir = true;
  private boolean falling = true;
  private JumpTimer jumpTimer = new JumpTimer();
  private int jumpCount;
  private float riseDuration;
  private float gravityFactor;

}
