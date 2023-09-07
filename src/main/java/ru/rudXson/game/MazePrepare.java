package ru.rudXson.game;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;

public class MazePrepare extends GameApplication {
    static ArrayList<ArrayList<Object>> preBoxes = new ArrayList<>() {};
    static ArrayList<String> boxes = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Walls setup");
        settings.setVersion("");
        settings.setAppIcon("");

    }
    @Override
    protected void initInput() {
        onBtnDown(MouseButton.PRIMARY, this::shoot);
        onBtnDown(MouseButton.SECONDARY, this::shoot);
        onKey(KeyCode.ENTER, this::launchMainGame);
    }

    private void launchMainGame() {
        try {
            Runtime.getRuntime().exec(
                    "java -jar \"C:\\Users\\user\\IdeaProjects\\lab9\\target\\Maze-1.0-jar-with-dependencies.jar\" \"" + String.join("\" \"", boxes) + "\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new MazePrepareFactory());

        initScreenBounds();
        initBorderGround();
        spawnBoxes();
        spawn("finish", 275, 65);
        spawn("sphere", 280, 480);
    }

    private void initScreenBounds() {
        entityBuilder().buildScreenBoundsAndAttach(100);
    }


    private void initBorderGround() {
        spawn("ground", 50, 50);
    }

    private void shoot() {
        spawn("box", getInput().getMouseXWorld() - 20, getInput().getMouseYWorld() - 20);
        boxes.add("ruddnev");
        boxes.add(String.valueOf((getInput().getMouseXWorld() - 300) * 0.040));
        boxes.add(String.valueOf((getInput().getMouseYWorld() - 320) * -0.040));
        boxes.add(String.valueOf(Math.sqrt(40) / 3.9));
        spawn("sphere", 300 - 20, 500 - 20);
        spawn("finish", 300 - 25, 90 - 25);
    }


    protected void initUI() {
        Text scoreText = getUIFactoryService().newText("Press Enter to launch", Color.BLACK, 24);
        addUINode(scoreText, 170, 35);
    }


    private void spawnBoxes() {
        for (ArrayList<Object> box : preBoxes) {
            SpawnData data = new SpawnData((double) box.get(1) - (double) box.get(3) / 2, (double) box.get(2) - (double) box.get(3) / 2);
            data.put("name", box.get(0));
            data.put("areaSquared", box.get(3));
            spawn("box", data);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length / 4; i++) {
            int pos = i * 4;
            double x = Double.parseDouble(args[pos + 1]);
            double y = Double.parseDouble(args[pos + 2]);
            double area = Double.parseDouble(args[pos + 3]);


            x = (x * 3.5) + 270;
            y = (y * 3.5) + 315;
            area = Math.sqrt(area) * 3.5;

            boxes.add(args[pos]);
            boxes.add(String.valueOf((x - 300) * 0.040));
            boxes.add(String.valueOf((y - 300) * -0.040));
            boxes.add(String.valueOf(Math.sqrt(area) / 3.9));
            ArrayList<Object> el = new ArrayList<>();
            el.add(args[pos]);
            el.add(x);
            el.add(y);
            el.add(area);
            preBoxes.add(el);
        }
        launch(args);
    }


}
