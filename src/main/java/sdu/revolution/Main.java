package sdu.revolution;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import org.joml.Vector2f;
import sdu.revolution.engine.graph.Model;
import sdu.revolution.engine.graph.Render;
import sdu.revolution.engine.main.*;
import sdu.revolution.engine.model.ItemManager;
import sdu.revolution.engine.scene.*;
import sdu.revolution.engine.scene.lights.SceneLights;
import sdu.revolution.engine.scene.SkyBox;

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
    private static AnimationData animationData;

    public static void main(String[] args) {
        isCursorDisabled = true;
        Logger.getGlobal().info("\n" + Utils.getTitle() + "\nThanks for your playing.");
        ItemManager.init();
        start_time = System.currentTimeMillis();
        INSTANCE = new Main();
        Window.WindowOptions options = new Window.WindowOptions();
        Engine engine = new Engine(Utils.getTitle(), options, INSTANCE);
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
        sceneLights.getAmbientLight().setIntensity(0.3f);
        sceneLights.getAmbientLight().setColor(0.5f, 0.5f, 0.5f);
        sceneLights.getDirLight().setPosition(-0.9f, 0.6f, 0.4f);
        sceneLights.getDirLight().setIntensity(0.6f);
        scene.setSceneLights(sceneLights);

        String bobModelId = "bobModel";
        Model bobModel = ModelLoader.loadModel(bobModelId, "resources/models/bob/boblamp.md5mesh",
                scene.getTextureCache(), true);
        scene.addModel(bobModel);
        Entity bobEntity = new Entity("bobEntity", bobModelId);
        bobEntity.setScale(0.05f);
        bobEntity.updateModelMatrix();
        animationData = new AnimationData(bobModel.getAnimationList().get(0));
        bobEntity.setAnimationData(animationData);
        scene.addEntity(bobEntity);

        SkyBox skyBox = new SkyBox(Utils.getResourceDir() + "/models/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(500);
        scene.setSkyBox(skyBox);
        scene.setGuiInstance(this);
        glfwSetCursorPos(window.getHandle(), window.getWidth() >> 1, window.getHeight() >> 1);
        scene.getCamera().setPosition(1f, 1f, 1f);
        Logger.getGlobal().info("Graphics System initialized.");
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

        isCursorDisabled = glfwGetKey(window.getHandle(), GLFW_KEY_LEFT_ALT) != GLFW_PRESS;

        MouseInput mouseInput = window.getMouseInput();
        if (isCursorDisabled) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(displVec.y * MOUSE_SENSITIVITY));
        }
        if (!isCursorDisabled && window.getMouseInput().isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }

    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        animationData.nextFrame();
        ItemManager.update(window, scene, diffTimeMillis);
        if (isCursorDisabled) {
            if (glfwGetInputMode(window.getHandle(), GLFW_CURSOR) == GLFW_CURSOR_NORMAL) {
                glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            }
        }
        else {
            if (glfwGetInputMode(window.getHandle(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED) {
                glfwSetInputMode(window.getHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                glfwSetCursorPos(window.getHandle(), window.getWidth() >> 1, window.getHeight() >> 1);
            }
        }
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
