package sdu.engine.models;

import org.joml.Vector3f;
import sdu.Util;
import sdu.engine.Window;
import sdu.engine.graph.*;
import sdu.engine.scene.Entity;
import sdu.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;

public class TitleItem implements Item {
    public Entity titleEntity;
    public void init(Window window, Scene scene, Render render) {
        Texture texture = scene.getTextureCache().createTexture(Util.getResourceDir() + "/textures/title.png");
        Material material = new Material();
        material.setTexturePath(texture.getTexturePath());
        List<Material> materialList = new ArrayList<>();
        materialList.add(material);
        float rate = 0.5f;
        float padding = 0.4f;
        float[] positions = new float[]{
                // V0
                -1.0f * rate, 0.5f * rate + padding, 0.5f,
                // V1
                -1.0f * rate, -0.5f * rate + padding, 0.5f,
                // V2
                1.0f * rate, -0.5f * rate + padding, 0.5f,
                // V3
                1.0f * rate, 0.5f * rate + padding, 0.5f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2
        };
        Mesh mesh = new Mesh(positions, textCoords, indices);
        material.getMeshList().add(mesh);
        Model titleModel = new Model("title-model", materialList);
        scene.addModel(titleModel);
        titleEntity = new Entity("title-entity", titleModel.getId());
        titleEntity.setPosition(0, 0, -2f);
        scene.addEntity(titleEntity);
        titleEntity.updateModelMatrix();
    }

    public void input(Window window, Scene scene, long diffTimeMillis) {
    }
    public void update(Window window, Scene scene, long diffTimeMillis) {
    }
}
