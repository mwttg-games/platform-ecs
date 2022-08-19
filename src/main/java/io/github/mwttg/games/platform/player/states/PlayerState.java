package io.github.mwttg.games.platform.player.states;

import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import org.joml.Matrix4f;

public interface PlayerState {

  // Constants (names for animations)
  String WALK_LEFT = "WALK_LEFT";
  String WALK_RIGHT = "WALK_RIGHT";
  String IDLE_LEFT = "IDLE_LEFT";
  String IDLE_RIGHT = "IDLE_RIGHT";

  void enter();

  void exit();

  void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection);

  void update(final float deltaTime, final PlayerInput playerInput, final SolidGridComponent solidGridComponent);

  void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent);
}
