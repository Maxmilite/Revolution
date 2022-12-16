package sdu.revolution.engine.model;

import org.joml.Vector3f;
import sdu.revolution.engine.main.Window;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.scene.Entity;
import sdu.revolution.engine.scene.Scene;

public class Item {
    public boolean selected;
    public Vector3f defaultLocation;
    public Entity entity;
    public void init(Window window, Scene scene, Render render) {}
    public void input(Window window, Scene scene, long diffTimeMillis) {}
    public void update(Window window, Scene scene, long diffTimeMillis) {}
    public Vector3f getPosition() {
        return this.entity.getPosition();
    }
    public float getScale() {
        return entity.getScale();
    }

    public boolean isSelected() {
        return entity.isSelected();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        entity.setSelected(selected);
    }
}
