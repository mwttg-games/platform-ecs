package io.github.mwttg.games.platform.player.colision;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.mwttg.games.basic.utilities.files.JsonFile;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;

public class SensorComponent {

  private final List<List<TileType>> grid;
  private final int width;
  private final int height;
  private final ThinPlatformLock thinPlatformLock;

  public SensorComponent(final String filename) {
    final var type = new TypeReference<List<List<Integer>>>() {
    };
    final var temp = JsonFile.readFrom(filename, type);
    this.height = temp.size();
    this.width = temp.get(0).size();
    this.grid = createTileTypes(temp);
    this.thinPlatformLock = new ThinPlatformLock();
  }

  private List<List<TileType>> createTileTypes(final List<List<Integer>> source) {
    final var result = new ArrayList<List<TileType>>();
    for (int y = 0; y < height; y++) {
      final var row = new ArrayList<TileType>();
      for (int x = 0; x < width; x++) {
        final var tileIndex = source.get(y).get(x);
        row.add(TileType.createFromIndex(tileIndex));
      }
      result.add(row);
    }

    return result;
  }

  public TileType getTileType(final Vector2f position) {
    final var x = (int) position.x();
    final var y = (int) position.y();

    if (x >= 0 && x < width && y >= 0 && y < height) {
      return grid.get(y).get(x);
    } else {
      throw new RuntimeException("Outside of the GRID (position was x = '" + x + "' y = '" + y + "')");
    }
  }

  public void updateLock(final float deltaTime) {
    thinPlatformLock.update(deltaTime);
  }

  public boolean isThinPlatformLocked() {
    return thinPlatformLock.isLocked();
  }

  public void lockThinPlatform() {
    thinPlatformLock.lockPlatform();
  }
}
