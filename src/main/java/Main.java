import engine.Engine;
import engine.IAppLogic;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Render;
import engine.scene.Scene;

public class Main implements IAppLogic {
    public static Main INSTANCE;
    public static long start_time;

    public static void main(String[] args) {
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
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f,
                0.5f, 0.5f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
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
        float padding = -0.4f;
        float height = 0.02f;
        float length = (System.currentTimeMillis() - start_time) * 0.0002f;
        if (length > 1.5f)
            length = 1.5f;
        float[] pos = new float[]{
                -0.75f, height + padding, 0.0f,
                -0.75f, -height + padding, 0.0f,
                -0.75f + length, height + padding, 0.0f,
                -0.75f + length, height + padding, 0.0f,
                -0.75f, -height + padding, 0.0f,
                -0.75f + length, -height + padding, 0.0f,
        };
//        scene.removeMesh("quad");
//        scene.addMesh("quad", new Mesh(pos, 6));
    }
}
