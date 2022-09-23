package io.github.mwttg.games.platform;


// todo transform to singleton - fields get init by config file... method calls where constants are used
public interface Configuration {

  float PLAYER_WALK_VELOCITY = 5.5f;
  float PLAYER_JUMP_VELOCITY = 10.0f;
  float PLAYER_CLIMB_VELOCITY = 4.0f;
  float JUMP_UP_GRAVITY = 20.0f;
  float FALL_DOWN_GRAVITY = 35.0f;
  float PLAYER_MAX_RISE_TIME = PLAYER_JUMP_VELOCITY / JUMP_UP_GRAVITY;
  int PLAYER_MAX_JUMP_AMOUNT = 2;
  float COYOTE_TIME = 0.1f;
  float THIN_PLATFORM_LOCK = 0.2f; // time in s
}
