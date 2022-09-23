package io.github.mwttg.games.platform.level;

import io.github.mwttg.games.platform.camera.CameraType;
import io.github.mwttg.games.platform.draw.SpriteComponent;
import io.github.mwttg.games.platform.entity.LevelDefinition;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import java.util.Map;
import org.joml.primitives.Rectanglef;

public record LevelComponent(LevelId levelId,
                             CameraType cameraType,
                             LevelDefinition.Dimension dimension,
                             SensorComponent sensorComponent,
                             Map<Rectanglef, Destination> levelConnections,
                             SpriteComponent spriteComponent,
                             SpriteComponent background) {
}
