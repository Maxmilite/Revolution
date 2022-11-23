package sdu.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long handle;
    private int width;
    private int height;
    private final Callable<Void> resizeFunc;

    public static class WindowOptions {
        public boolean compatibleProfile;
        public int fps;
        public int width;
        public int height;
        public int ups = Engine.TARGET_UPS;
    }

    private void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resizeFunc.call();
        } catch (Exception excp) {
            Logger.getGlobal().severe("Error calling resize callback");
        }
    }

    public Window(String title, WindowOptions options, Callable<Void> resizeFunc) {
        this.resizeFunc = resizeFunc;
        if (!glfwInit()) { // Destroy when failed to init
            throw new IllegalStateException("Unable to initialize Revolution");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);
        glfwWindowHint(GLFW_RESIZABLE, 1);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 1);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        if (options.compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, 1);
        }

        if (options.width > 0 && options.height > 0) {
            this.width = options.width;
            this.height = options.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, 1);
            GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (mode == null) {
                throw new IllegalStateException("Failed to get the primary monitor's information");
            }
            width = mode.width();
            height = mode.height();
        }
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL) {
            throw new RuntimeException("Failed to create window.");
        }

        glfwSetFramebufferSizeCallback(handle, (window, w, h) -> resized(w, h));
        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                Logger.getGlobal().severe("Error occurred.")
        );
        glfwSetKeyCallback(handle, (window, key, code, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
        glfwMakeContextCurrent(handle);

        if (options.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        glfwShowWindow(handle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(handle, arrWidth, arrHeight);
        width = arrWidth[0];
        height = arrHeight[0];

    }

    public void cleanup() {
        glfwFreeCallbacks(handle);
        glfwDestroyWindow(handle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(handle, keyCode) == GLFW_PRESS;
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void update() {
        glfwSwapBuffers(handle);
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(handle);
    }

}
