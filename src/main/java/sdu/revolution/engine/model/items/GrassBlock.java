package sdu.revolution.engine.model.items;

import org.joml.Vector3f;
import sdu.revolution.engine.main.Utils;
import sdu.revolution.engine.main.Window;
import sdu.revolution.engine.graph.Model;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.model.Item;
import sdu.revolution.engine.scene.ModelLoader;
import sdu.revolution.engine.scene.Entity;
import sdu.revolution.engine.scene.Scene;

public class GrassBlock implements Item {
    public Entity cubeEntity;
    public Vector3f defaultLocation;

    public void init(Window window, Scene scene, Render render) {
        Model cubeModel = ModelLoader.loadModel("cube-model" + defaultLocation, Utils.getResourceDir() + "/models/cube/grass.obj",
                scene.getTextureCache(), false);
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity" + defaultLocation, cubeModel.getId());
        cubeEntity.setPosition(defaultLocation);
        cubeEntity.updateModelMatrix();
        scene.addEntity(cubeEntity);
    }
    public GrassBlock(Vector3f vec) {
        this.defaultLocation = vec;
    }
    public GrassBlock() { this(new Vector3f(0, 0, 0)); }
    public void input(Window window, Scene scene, long diffTimeMillis) { }

    public void update(Window window, Scene scene, long diffTimeMillis) {
    }
}
