package sdu.revolution.engine.main;

import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.scene.Scene;

public interface IAppLogic {
    void cleanup();

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

    void update(Window window, Scene scene, long diffTimeMillis);
}
