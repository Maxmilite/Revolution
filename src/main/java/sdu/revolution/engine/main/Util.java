package sdu.revolution.engine.main;

import org.lwjgl.Version;

public class Util {
    public static final String NAME = "Revolution";
    public static final String VERSION = "1.0.0";
    public static final String LWJGL_VERSION = Version.getVersion();

    public static String getTitle() {
        return NAME + " " + VERSION + " | Running on LWJGL " + LWJGL_VERSION;
    }
    public static String getResourceDir() { return System.getProperty("user.dir") + "/src/main/resources"; }
}
