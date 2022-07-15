package io.github.mwttg.games.platform.ecs.component.movement;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.mwttg.games.basic.utilities.files.JsonFile;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.joml.Vector2f;

public class SolidGridComponent {

  private static final int WALKABLE = 0;

  private final List<List<Integer>> grid;
  private final int width;
  private final int height;

  public SolidGridComponent(final String filename) {
    final var type = new TypeReference<List<List<Integer>>>() {
    };
    this.grid = JsonFile.readFrom(filename, type);
    this.height = grid.size();
    this.width = grid.get(0).size();
  }

  public SolidGridComponent(final List<List<Integer>> grid) {
    this.grid = grid;
    this.height = grid.size();
    this.width = grid.get(0).size();
  }

  private int get(final int x, final int y) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      return grid.get(y).get(x);
    } else {
      return -1; // hmm ...
    }
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

  public record BlockedNeighbours(boolean up, boolean down, boolean left, boolean right) {
  }

  public BlockedNeighbours getBlockedNeighboursOf(final int x, final int y) {
    final var up = get(x, y - 1) != WALKABLE;
    final var down = get(x, y + 1) != WALKABLE;
    final var left = get(x - 1, y) != WALKABLE;
    final var right = get(x + 1, y) != WALKABLE;

    return new BlockedNeighbours(up, down, left, right);
  }

  public List<List<Integer>> getGrid() {
    return grid;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SolidGridComponent that)) {
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
    return new StringJoiner(", ", SolidGridComponent.class.getSimpleName() + "[", "]")
        .add("grid=" + grid)
        .add("width=" + width)
        .add("height=" + height)
        .toString();
  }
}
