package ru.rudXson.game;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class MazePrepareFactory implements EntityFactory {

    @Spawns("ground")
    public Entity newGround(SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypePrepare.SPHERE)
                .view(new Circle(250, 270, 250, Color.DARKGOLDENROD))
                .build();
    }


    @Spawns("sphere")
    public Entity newSphere(SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypePrepare.SPHERE)
                .view(new Circle(20, 20, 20, Color.LIMEGREEN))
                .build();
    }

    @Spawns("box")
    public Entity newBox(SpawnData data) {
        double areaSquared;
        String name;
        if (data.hasKey("areaSquared")) {
            name = data.get("name");
            areaSquared = data.get("areaSquared");
        } else {
            name = "ruddnev";
            areaSquared = 40;
        }
        return entityBuilder(data)
                .type(EntityTypePrepare.BOX)
                .viewWithBBox(new Rectangle(areaSquared, areaSquared, encodeStringToColor(name)))
                .build();
    }

    @Spawns("finish")
    public Entity newFinish(SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypePrepare.FINISH)
                .viewWithBBox(new Rectangle(50, 10, Color.LIME))
                .build();
    }

    public static Color encodeStringToColor(String input) {
        int hashCode = input.hashCode();
        int red = (hashCode & 0xFF0000) >> 16;
        int green = (hashCode & 0xFF00) >> 8;
        int blue = hashCode & 0xFF;
        return Color.rgb(red, green, blue);
    }

}
