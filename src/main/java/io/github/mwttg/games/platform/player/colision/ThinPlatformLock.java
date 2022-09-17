package io.github.mwttg.games.platform.player.colision;

import io.github.mwttg.games.platform.Configuration;

/**
 * This is needed, when pressing down and jump, to move from a thin platform and land on
 * another thin platform after it. Because Player State will change from onGround -> falling -> onGround.
 * If this platform isn't locked right after switching from onGround to falling the state will be onGround
 * again. (Because the player will be still in the sensor positions)
 */
public class ThinPlatformLock {

  private float duration;
  private boolean lock;

  public ThinPlatformLock() {
    duration = 0;
    lock = false;
  }

  // platform is no longer 'ground', so the player can fall through
  public void lockPlatform() {
    duration = 0;
    lock = true;
  }

  public void update(final float deltaTime) {
    if (lock) {
      duration = duration + deltaTime;

      if (duration > Configuration.THIN_PLATFORM_LOCK) {
        lock = false;
        duration = 0;
      }
    }
  }

  public boolean isLocked() {
    return lock;
  }
}
