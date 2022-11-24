package sdu.revolution.engine.main;

import sdu.revolution.engine.scene.Scene;

public interface IGuiInstance {
    void drawGui();

    boolean handleGuiInput(Scene scene, Window window);
}