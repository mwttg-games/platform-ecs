package io.github.mwttg.games.platform.player.colision;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.mwttg.games.basic.utilities.files.JsonFile;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.joml.Vector2f;

public class GridComponent {

  private static final int WALKABLE = 0;

  private final List<List<Integer>> grid;
  private final int width;
  private final int height;

  public GridComponent(final String filename) {
    final var type = new TypeReference<List<List<Integer>>>() {
    };
    this.grid = JsonFile.readFrom(filename, type);
    this.height = grid.size();
    this.width = grid.get(0).size();
  }

  public GridComponent(final List<List<Integer>> grid) {
    this.grid = grid;
    this.height = grid.size();
    this.width = grid.get(0).size();
  }

  public boolean isBlocked(final Vector2f position) {
    final var x = (int) position.x();
    final var y = (int) position.y();
    if (x >= 0 && x < width && y >= 0 && y < height) {
      return grid.get(y).get(x) != WALKABLE;
    } else {
      return true;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GridComponent that)) {
      return false;
    }
    return width == that.width && height == that.height && grid.equals(that.grid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grid, width, height);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", GridComponent.class.getSimpleName() + "[", "]")
        .add("grid=" + grid)
        .add("width=" + width)
        .add("height=" + height)
        .toString();
  }
}
