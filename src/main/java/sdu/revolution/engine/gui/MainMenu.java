package sdu.revolution.engine.gui;

import com.spinyowl.legui.DefaultInitializer;
import com.spinyowl.legui.animation.Animator;
import com.spinyowl.legui.animation.AnimatorProvider;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.layout.LayoutManager;
import com.spinyowl.legui.system.renderer.Renderer;
import org.joml.Vector2i;
import sdu.revolution.Main;
import sdu.revolution.engine.main.IGuiInstance;
import sdu.revolution.engine.main.Window;
import sdu.revolution.engine.scene.Scene;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

public class MainMenu implements IGuiInstance {

    private int width, height;
    private Frame frame;
    private ExampleGui gui;
    private Context context;
    private Renderer renderer;
    private DefaultInitializer initializer;
    private Animator animator;

    private void createGuiElements(Frame frame, int w, int h) {
        gui = new ExampleGui(w, h);
        gui.setFocusable(false);
        gui.getStyle().setMinWidth(100F);
        gui.getStyle().setMinHeight(100F);
        gui.getStyle().getFlexStyle().setFlexGrow(1);
        gui.getStyle().setPosition(Style.PositionType.RELATIVE);
        gui.getStyle().getBackground().setColor(ColorConstants.transparent());

        frame.getContainer().getStyle().setDisplay(Style.DisplayType.FLEX);
        frame.getContainer().add(gui);
    }

    public MainMenu() {
        width = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor())).width();
        height = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor())).height();
        frame = new Frame(width, height);
        createGuiElements(frame, width, height);
        initializer = new DefaultInitializer(Main.getEngine().getWindow().getHandle(), frame);
        renderer = initializer.getRenderer();
        animator = AnimatorProvider.getAnimator();
        renderer.initialize();
        context = initializer.getContext();
    }

    @Override
    public void drawGui() { }

    @Override
    public boolean handleGuiInput(Scene scene, Window window) {
        return false;
    }

    @Override
    public void update() {
        context.updateGlfwWindow();
        Vector2i windowSize = context.getFramebufferSize();

        // We need to relayout components.
        if (gui.getGenerateEventsByLayoutManager().isChecked()) {
            LayoutManager.getInstance().layout(frame, context);
        } else {
            LayoutManager.getInstance().layout(frame);
        }

        // render frame
        renderer.render(frame, context);

        // poll events to callbacks
        animator.runAnimations();

        // Now we need to handle events. Firstly we need to handle system events.
        // And we need to know to which frame they should be passed.
        initializer.getSystemEventProcessor().processEvents(frame, context);

        // When system events are translated to GUI events we need to handle them.
        // This event processor calls listeners added to ui components
        initializer.getGuiEventProcessor().processEvents();

        // check toggle fullscreen flag and execute.

        updateElement();
    }

    private void updateElement() {
        if (context != null) {
            Component mouseTargetGui = context.getMouseTargetGui();
            gui.getMouseTargetLabel().getTextState().setText("-> " + (mouseTargetGui == null ? null : mouseTargetGui.getClass().getSimpleName()));

            Component focusedGui = context.getFocusedGui();
            gui.getFocusedGuiLabel().getTextState().setText("-> " + (focusedGui == null ? null : focusedGui.getClass().getSimpleName()));
        }
    }

    public void cleanup() {
        renderer.destroy();
    }

    public void resize(int w, int h) {
        width = w;
        height = h;
        gui.resize();
    }

}
