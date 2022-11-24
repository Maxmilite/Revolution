package sdu.revolution.engine.model.items;

import org.joml.Vector3f;
import org.joml.Vector4f;
import sdu.revolution.Util;
import sdu.revolution.engine.Window;
import sdu.revolution.engine.graph.*;
import sdu.revolution.engine.model.Item;
import sdu.revolution.engine.scene.AssimpLoader;
import sdu.revolution.engine.scene.Entity;
import sdu.revolution.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;

public class CubeItem implements Item {
    public static Entity cubeEntity;
    public static float cubeRotation;
    public static Vector4f displInc = new Vector4f();

    public void init(Window window, Scene scene, Render render) {
        Model cubeModel = AssimpLoader.loadModel("cube-model", Util.getResourceDir() + "/models/cube/cube.obj",
                scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);
    }

    public void input(Window window, Scene scene, long diffTimeMillis) {
        displInc.zero();
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            displInc.y = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displInc.y = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            displInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            displInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            displInc.w = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            displInc.w = 1;
        }

        displInc.mul(diffTimeMillis / 1000.0f);

        Vector3f entityPos = cubeEntity.getPosition();
        cubeEntity.setPosition(displInc.x + entityPos.x, displInc.y + entityPos.y, displInc.z + entityPos.z);
        cubeEntity.setScale(cubeEntity.getScale() + displInc.w);
    }

    public void update(Window window, Scene scene, long diffTimeMillis) {
        cubeRotation += 1.5;
        if (cubeRotation > 360) {
            cubeRotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(cubeRotation));
        cubeEntity.updateModelMatrix();
    }
}
