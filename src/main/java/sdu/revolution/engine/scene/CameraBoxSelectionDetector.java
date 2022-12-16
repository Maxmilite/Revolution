package sdu.revolution.engine.scene;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import sdu.revolution.engine.model.Item;

import java.util.List;

public class CameraBoxSelectionDetector {

    private Vector3f max;

    private Vector3f min;

    private Vector2f nearFar;
    private Vector3f dir;

    public CameraBoxSelectionDetector() {
        dir = new Vector3f();
        min = new Vector3f();
        max = new Vector3f();
        nearFar = new Vector2f();
    }

    public void selectItem(List<Item> items, Camera camera) {
        dir = camera.getViewMatrix().positiveZ(dir).negate();
        selectItem(items, camera.getPosition(), dir);
    }

    public void selectItem(List<Item> items, Vector3f center, Vector3f dir) {
        Item selectedItem = null;
        float closestDistance = Float.POSITIVE_INFINITY;
        for (Item item : items) {
            item.setSelected(false);
            min.set(item.getPosition());
            max.set(item.getPosition());
            min.add(-item.getScale() / 2, -item.getScale() / 2, -item.getScale() / 2);
            max.add(item.getScale() / 2, item.getScale() / 2, item.getScale() / 2);
            if (Intersectionf.intersectRayAab(center, dir, min, max, nearFar) && nearFar.x < closestDistance) {
                closestDistance = nearFar.x;
                selectedItem = item;
            }
        }

        if (selectedItem != null) {
            selectedItem.setSelected(true);
        }
    }
}
