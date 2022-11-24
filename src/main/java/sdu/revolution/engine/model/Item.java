package sdu.revolution.engine.model;

import sdu.revolution.engine.Window;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.scene.Scene;

public interface Item {
    void init(Window window, Scene scene, Render render);
    void input(Window window, Scene scene, long diffTimeMillis);
    void update(Window window, Scene scene, long diffTimeMillis);
}
