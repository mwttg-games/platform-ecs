package io.github.mwttg.games.platform.player.states;

import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import org.joml.Matrix4f;

public interface PlayerState {

  // Constants (names for animations)
  String WALK_LEFT = "WALK_LEFT";
  String WALK_RIGHT = "WALK_RIGHT";
  String IDLE_LEFT = "IDLE_LEFT";
  String IDLE_RIGHT = "IDLE_RIGHT";
  String JUMP_RIGHT = "JUMP_RIGHT";
  String JUMP_LEFT = "JUMP_LEFT";
  String FALL_RIGHT = "FALL_RIGHT";
  String FALL_LEFT = "FALL_LEFT";
  String DUST_EFFECT = "DUST_EFFECT";
  String ON_LADDER = "ON_LADDER";

  void enter();

  void exit();

  void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection);

  void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent);

  void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent);
}
