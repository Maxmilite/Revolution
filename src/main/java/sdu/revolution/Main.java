package sdu.revolution;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import org.joml.Vector2f;
import org.joml.Vector3f;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.main.*;
import sdu.revolution.engine.model.ItemManager;
import sdu.revolution.engine.scene.Camera;
import sdu.revolution.engine.scene.Scene;
import sdu.revolution.engine.scene.SkyBox;
import sdu.revolution.engine.scene.lights.PointLight;
import sdu.revolution.engine.scene.lights.SceneLights;
import sdu.revolution.engine.scene.lights.SpotLight;

import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic, IGuiInstance {
    public static Main INSTANCE;
    private LightControls lightControls;
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private static final float SPRINT_AMPLIFIER = 2.0f;
    private static boolean isCursorDisabled;
    public static long start_time;
    private static boolean wasTabPressed;

    public static void main(String[] args) {
        isCursorDisabled = true;
        wasTabPressed = false;
        Logger.getGlobal().info("\n" + Util.getTitle() + "\nThanks for your playing.");
        ItemManager.init();
        start_time = System.currentTimeMillis();
        INSTANCE = new Main();
        Engine engine = new Engine(Util.getTitle(), new Window.WindowOptions(), INSTANCE);
        engine.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        ItemManager.init(window, scene, render);

        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.1f);
        sceneLights.getDirLight().setPosition(-0.9f, 0.6f, 0.4f);
        sceneLights.getDirLight().setIntensity(1f);
        scene.setSceneLights(sceneLights);
        SkyBox skyBox = new SkyBox(Util.getResourceDir() + "/models/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(500);
        scene.setSkyBox(skyBox);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        // Nothing to be done yet
        ItemManager.input(window, scene, diffTimeMillis);
        float move = diffTimeMillis * MOVEMENT_SPEED;
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) move *= SPRINT_AMPLIFIER;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_UP) || window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_DOWN) || window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move);
        }

        if (glfwGetKey(window.getHandle(), GLFW_KEY_TAB) == GLFW_PRESS) {
            wasTabPressed = true;
        }

        if (glfwGetKey(window.getHandle(), GLFW_KEY_TAB) == GLFW_RELEASE && wasTabPressed) {
            isCursorDisabled = !isCursorDisabled;
            wasTabPressed = false;
        }

        MouseInput mouseInput = window.getMouseInput();
        if (isCursorDisabled || window.getMouseInput().isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        ItemManager.update(window, scene, diffTimeMillis);
        if (isCursorDisabled) glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        else glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    @Override
    public void drawGui() {
        ImGui.newFrame();
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
//        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }

    @Override
    public boolean handleGuiInput(Scene scene, Window window) {
        ImGuiIO imGuiIO = ImGui.getIO();
        MouseInput mouseInput = window.getMouseInput();
        Vector2f mousePos = mouseInput.getCurrentPos();
        if (!isCursorDisabled) imGuiIO.setMousePos(mousePos.x, mousePos.y);
        imGuiIO.setMouseDown(0, mouseInput.isLeftButtonPressed());
        imGuiIO.setMouseDown(1, mouseInput.isRightButtonPressed());

        return imGuiIO.getWantCaptureMouse() || imGuiIO.getWantCaptureKeyboard();
    }
}
