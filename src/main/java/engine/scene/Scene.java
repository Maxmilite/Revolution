package engine.scene;

import engine.graph.Mesh;

import java.util.HashMap;
import java.util.Map;

public class Scene {
    private final Map<String, Mesh> meshMap;
    public Scene() {
        this.meshMap = new HashMap<>();
    }

    public void addMesh(String id, Mesh mesh) {
        meshMap.put(id, mesh);
    }

    public void cleanup() {
        meshMap.values().forEach(Mesh::cleanup);
    }

    public Map<String, Mesh> getMeshMap() {
        return meshMap;
    }

    public boolean removeMesh(String id) {
        if (!meshMap.containsKey(id))
            return false;
        meshMap.remove(id);
        return true;
    }
}