package io.github.mwttg.games.platform.ecs.component.draw;

import java.util.HashMap;
import java.util.Map;

public class SpriteStatesAnimationComponent {

  private final Map<String, SpriteAnimationComponent> animationsByState;
  private String currentState;

  public SpriteStatesAnimationComponent() {
    this.animationsByState = new HashMap<>();
  }

  public void addState(final String stateName, final SpriteAnimationComponent component) {
    animationsByState.put(stateName, component);
  }

  public SpriteAnimationComponent getCurrent() {
    return animationsByState.get(currentState);
  }

  public void setCurrentState(final String state) {
    currentState = state;
  }
}
