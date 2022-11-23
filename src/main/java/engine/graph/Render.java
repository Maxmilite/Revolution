package engine.graph;

import org.lwjgl.opengl.GL;
import engine.Window;
import engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private SceneRender sceneRender;

    public Render() {
        GL.createCapabilities();
        sceneRender = new SceneRender();
    }

    public void cleanup() {
        sceneRender.cleanup();
    }

    public void render(Window window, Scene scene) {
        sceneRender.render(scene);
    }
}