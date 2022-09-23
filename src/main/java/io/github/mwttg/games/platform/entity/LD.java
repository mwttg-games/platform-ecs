package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.platform.level.CameraType;
import io.github.mwttg.games.platform.level.Destination;
import io.github.mwttg.games.platform.level.LevelId;
import java.io.File;
import java.util.Map;
import org.joml.Vector2f;
import org.joml.primitives.Rectanglef;

public class LD {

  public static void main(String[] args) {
    final var x  = new LevelDefinition(
        new LevelId("CAVE", "SCENE002"),
        new LevelDefinition.Dimension(20, 10),
        CameraType.FIXED,
        "./data/p007/solid-grid.json",
        "./data/p007/level.png",
        "./data/...",
        Map.of(
            new Rectanglef(2.0f, 0.0f, 0.0f, 1.0f),
            new Destination(
                new LevelId("FOREST", "SCENE001"),
                new Vector2f(1.0f, 1.0f)),
            new Rectanglef(21.0f, 14.0f, 19.0f, 15.0f),
            new Destination(
                new LevelId("CAVE", "SCENE002"),
                new Vector2f(20.0f, 15.0f))
        )
    );

    JsonFile.writeTo(x, new File("test.json"));
    JsonFile.writeTo(new Rectanglef(1.0f, 2.0f, 3.0f, 4.0f), new File("test3.json"));

  }
}
