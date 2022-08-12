package io.github.mwttg.games.platform.player.states;

import io.github.mwttg.games.platform.player.SolidGridComponent;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public interface PlayerState {

  // Constants (names for animations)
  String WALK_LEFT = "WALK_LEFT";
  String WALK_RIGHT = "WALK_RIGHT";
  String IDLE_LEFT = "IDLE_LEFT";
  String IDLE_RIGHT = "IDLE_RIGHT";

  void enter();

  void exit();

  void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection);

  void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent);

  void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent);
}
