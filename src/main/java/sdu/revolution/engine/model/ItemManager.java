package sdu.revolution.engine.model;

import sdu.revolution.engine.Window;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.model.items.CubeItem;
import sdu.revolution.engine.model.items.TitleItem;
import sdu.revolution.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static List<Item> list;
    public static void init() {
        list = new ArrayList<>();
        list.add(new CubeItem());
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
