package sdu.revolution.engine.graph;

import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Material {
    private Vector4f ambientColor;
    private float reflectance;
    private Vector4f specularColor;
    private List<Mesh> meshList;
    public static final Vector4f DEFAULT_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    private Vector4f diffuseColor;
    private String texturePath;

    public Material() {
        meshList = new ArrayList<>();
        diffuseColor = DEFAULT_COLOR;
        ambientColor = DEFAULT_COLOR;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }
    public float getReflectance() {
        return reflectance;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }

    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public void cleanup() {
        meshList.forEach(Mesh::cleanup);
    }

    public List<Mesh> getMeshList() {
        return meshList;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }
}
