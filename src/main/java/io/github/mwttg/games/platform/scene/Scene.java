package io.github.mwttg.games.platform.scene;

import io.github.mwttg.games.platform.camera.CameraType;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import java.util.Map;
import org.joml.primitives.Rectanglef;

public record Scene(SceneId sceneId,
                    CameraType cameraType,
                    SceneDefinition.Dimension dimension,
                    SensorComponent sensorComponent,
                    Map<Rectanglef, Destination> levelConnections,
                    Drawable sprite,
                    Drawable background) {
}
