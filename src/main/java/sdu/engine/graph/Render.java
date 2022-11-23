package sdu.engine.graph;

import org.lwjgl.opengl.GL;
import sdu.engine.Window;
import sdu.engine.scene.Scene;

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