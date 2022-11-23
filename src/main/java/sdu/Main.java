package sdu;

import sdu.engine.Engine;
import sdu.engine.IAppLogic;
import sdu.engine.Window;
import sdu.engine.graph.Mesh;
import sdu.engine.graph.Render;
import sdu.engine.scene.Scene;

import java.util.logging.Logger;

public class Main implements IAppLogic {
    public static Main INSTANCE;
    public static long start_time;

    public static void main(String[] args) {
        Logger.getGlobal().info("\n" + Util.getTitle() + "\nThanks for your using.");
        start_time = System.currentTimeMillis();
        INSTANCE = new Main();
        Engine engine = new Engine(Util.getTitle(), new Window.WindowOptions(), INSTANCE);
        engine.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                -0.2f, 0.2f, -1.0f,
                -0.2f, -0.2f, -1.0f,
                0.2f, -0.2f, -1.0f,
                0.2f, 0.2f, -1.0f,
        };
        float[] colors = new float[]{
                1.0f, 0.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2
        };
        Mesh mesh = new Mesh(positions, colors, indices);
        scene.addMesh("quad", mesh);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        // Nothing to be done yet
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        float padding = -0.5f;
        float height = 0.02f;
        float length = (System.currentTimeMillis() - start_time) * 0.0002f;
        if (length > 1.5f)
            length = 1.5f;
        float[] positions = new float[]{
                -0.75f + length, height + padding, -1.0f,
                -0.75f + length, padding, -1.0f,
                -0.75f, height + padding, -1.0f,
                -0.75f, padding, -1.0f,
        };
        float[] colors = new float[]{
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f
        };
        int[] indices = new int[]{
                0, 1, 2, 1, 2, 3
        };
        scene.removeMesh("loading-bar");
        scene.addMesh("loading-bar", new Mesh(positions, colors, indices));
    }
}
