package sdu.revolution.engine.main;

import org.lwjgl.Version;

import java.util.List;

public class Utils {
    public static final String NAME = "Revolution";
    public static final String VERSION = "1.0.0";
    public static final String LWJGL_VERSION = Version.getVersion();

    public static String getTitle() {
        return NAME + " " + VERSION + " | Running on LWJGL " + LWJGL_VERSION;
    }
    public static String getResourceDir() { return "resources"; }

    public static float[] listFloatToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }

    public static int[] listIntToArray(List<Integer> list) {
        return list.stream().mapToInt((Integer v) -> v).toArray();
    }
}
