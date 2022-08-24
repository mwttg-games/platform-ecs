package io.github.mwttg.games.platform.player.colision;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.mwttg.games.basic.utilities.files.JsonFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.joml.Vector2f;

public class GridComponent {

  private final List<List<TileType>> grid;
  private final int width;
  private final int height;

  public GridComponent(final String filename) {
    final var type = new TypeReference<List<List<Integer>>>() {
    };
    final var temp = JsonFile.readFrom(filename, type);
    this.height = temp.size();
    this.width = temp.get(0).size();
    this.grid = createTileTypes(temp);
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
      throw new RuntimeException("Outside of the GRID");
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
