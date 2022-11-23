package sdu.engine.models;

import sdu.engine.Window;
import sdu.engine.graph.Render;
import sdu.engine.scene.Scene;

public interface Item {
    void init(Window window, Scene scene, Render render);
    void input(Window window, Scene scene, long diffTimeMillis);
    void update(Window window, Scene scene, long diffTimeMillis);
}
