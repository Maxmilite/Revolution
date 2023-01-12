package sdu.revolution.engine.model;

import org.joml.Vector2d;
import org.joml.Vector3f;
import sdu.revolution.Main;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.main.Window;
import sdu.revolution.engine.model.items.DirtBlock;
import sdu.revolution.engine.model.items.GrassBlock;
import sdu.revolution.engine.model.terrain.Plain;
import sdu.revolution.engine.scene.CameraBoxSelectionDetector;
import sdu.revolution.engine.scene.MouseBoxSelectionDetector;
import sdu.revolution.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class ItemManager {
    public static final int MAP_SIZE = 10;
    public static final int LOWEST_HEIGHT = -1;
    public static List<Item> list;
    public static Plain plain;
    public static MouseBoxSelectionDetector mouseDetector;
    public static CameraBoxSelectionDetector cameraDetector;
    public static void init() {
        list = new ArrayList<>();
        plain = new Plain(MAP_SIZE);
        for (int i = -MAP_SIZE; i <= MAP_SIZE; ++i)
            for (int j = -MAP_SIZE; j <= MAP_SIZE; ++j)  {
                int height = plain.getHeight(i, j);
                list.add(new GrassBlock(new Vector3f(i, height, j)));
                for (int k = height - 1; k >= LOWEST_HEIGHT; --k) {
                    list.add(new DirtBlock(new Vector3f(i, k, j)));
                }
            }
        plain.cleanup();
        cameraDetector = new CameraBoxSelectionDetector();
        mouseDetector = new MouseBoxSelectionDetector();
    }
    public static void init(Window window, Scene scene, Render render) {
        for (var i : list) {
            i.init(window, scene, render);
        }
    }
    public static void input(Window window, Scene scene, long diffTimeMillis) {
        for (var i : list) {
            i.input(window, scene, diffTimeMillis);
        }
    }
    public static void update(Window window, Scene scene, long diffTimeMillis) {
        double[] arrX = new double[1];
        double[] arrY = new double[1];
        glfwGetCursorPos(window.getHandle(), arrX, arrY);
        Vector2d currentPos = new Vector2d(arrX[0], arrY[0]);
        if (Main.isControlled) {
            cameraDetector.selectItem(list, Main.getEngine().getScene().getCamera());
        } else if (Main.getEngine().getWindow().isMouseInWindow()) {
            mouseDetector.selectItem(list, window, currentPos, Main.getEngine().getScene().getCamera());
        }
        for (var i : list) {
            i.update(window, scene, diffTimeMillis);
        }
    }
}
