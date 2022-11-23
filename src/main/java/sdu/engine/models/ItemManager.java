package sdu.engine.models;

import sdu.engine.Window;
import sdu.engine.graph.Render;
import sdu.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static List<Item> list;
    public static void init() {
        list = new ArrayList<>();
//        list.add(new CubeItem());
        list.add(new TitleItem());
    }
    public static void init(Window window, Scene scene, Render render) {
        for (var i : list) {
            i.init(window, scene, render);
        }
    }
    public static void input(Window window, Scene scene, long diffTimeMillis) {
        for (var i : list) {
            i.input(window, scene, diffTimeMillis);
        }
    }
    public static void update(Window window, Scene scene, long diffTimeMillis) {
        for (var i : list) {
            i.update(window, scene, diffTimeMillis);
        }
    }
}
