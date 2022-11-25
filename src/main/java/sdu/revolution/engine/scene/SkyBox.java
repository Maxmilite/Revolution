package sdu.revolution.engine.scene;

import sdu.revolution.engine.graph.Model;
import sdu.revolution.engine.graph.TextureCache;

public class SkyBox {

    private final Entity skyBoxEntity;
    private final Model skyBoxModel;

    public SkyBox(String skyBoxModelPath, TextureCache textureCache) {
        skyBoxModel = ModelLoader.loadModel("skybox-model", skyBoxModelPath, textureCache, false);
        skyBoxEntity = new Entity("skyBoxEntity-entity", skyBoxModel.getId());
    }

    public Entity getSkyBoxEntity() {
        return skyBoxEntity;
    }

    public Model getSkyBoxModel() {
        return skyBoxModel;
    }
}