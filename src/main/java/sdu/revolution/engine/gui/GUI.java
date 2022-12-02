package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.style.length.Length;
import com.spinyowl.legui.style.length.LengthType;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import sdu.revolution.Main;

import java.util.Arrays;
import java.util.List;


public class GUI extends Panel {
    private int width, height;
    private Button reloadButton;
    private List<Panel> panels;
    private Style textStyle, panelStyle;
    private Panel subPanel;
    private final int TRANSITION_BACKGROUND = 1, TRANSITION_FONT = 2, TRANSITION_REMOVAL = 4;
    private Panel optionPanel;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void prompt(String content, Runnable yesFunction, Runnable noFunction) {
        Panel promptPanel = new Panel();
        promptPanel.getStyle().getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        promptPanel.getStyle().setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
        promptPanel.setSize(400, 200);
        promptPanel.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        promptPanel.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        promptPanel.setPosition((width / 2) - 200, (height / 2) - 100);
        Label title = new Label("Prompt");
        title.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        title.getStyle().setFontSize(32f);
        title.getStyle().setFont("Impact");
        title.getStyle().setVerticalAlign(VerticalAlign.TOP);
        title.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        title.getStyle().setPaddingTop(new Length(20f, LengthType.PIXEL));
        title.setSize(promptPanel.getSize());
        promptPanel.add(title);
        Label val = new Label(content);
        val.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        val.getStyle().setFontSize(24f);
        val.getStyle().setFont("Impact");
        val.getStyle().setVerticalAlign(VerticalAlign.TOP);
        val.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        val.getStyle().setPaddingTop(new Length(72f, LengthType.PIXEL));
        val.setSize(promptPanel.getSize());
        promptPanel.add(val);
        float panelWidth = promptPanel.getSize().x;
        float panelHeight = promptPanel.getSize().y;
        Button close = new Button("\uE14C", panelWidth - 48, 18, 30, 30);
        close.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        close.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        close.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        close.getStyle().setFontSize(24f);
        close.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        close.getStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().setTextColor(new Vector4f(1f, 1f, 0f, 1f));
        close.getPressedStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getPressedStyle().setTextColor(new Vector4f(ColorConstants.WHITE));
        close.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                runSlide(promptPanel, 100, new Vector2f(400, 0), new Vector2f((width / 2) - 200, (height / 2)));
                new Thread(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.remove(promptPanel);
                }).start();
            }
        });
        promptPanel.add(close);
        Button yesButton = new Button("Yes", panelWidth / 2 - 150f, panelHeight - 70f, 120f, 50f),
                noButton = new Button("No", panelWidth / 2 + 30f, panelHeight - 70f, 120f, 50f);
        yesButton.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        yesButton.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        yesButton.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        yesButton.getStyle().setFontSize(20f);
        yesButton.getStyle().setFont("Impact");
        yesButton.getStyle().getBackground().setColor(0.3f, 0.3f, 0.3f, 1.0f);
        noButton.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        noButton.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        noButton.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        noButton.getStyle().setFontSize(20f);
        noButton.getStyle().setFont("Impact");
        noButton.getStyle().getBackground().setColor(0.3f, 0.3f, 0.3f, 1.0f);
        yesButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                yesFunction.run();
                runSlide(promptPanel, 100, new Vector2f(400, 0), new Vector2f((width / 2) - 200, (height / 2)));
                new Thread(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.remove(promptPanel);
                }).start();
            }
        });
        noButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                noFunction.run();
                runSlide(promptPanel, 100, new Vector2f(400, 0), new Vector2f((width / 2) - 200, (height / 2)));
                new Thread(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.remove(promptPanel);
                }).start();
            }
        });
        promptPanel.add(yesButton);
        promptPanel.add(noButton);
        promptPanel.setSize(new Vector2f(400, 0));
        promptPanel.setPosition(new Vector2f((width / 2) - 200, (height / 2)));
        this.add(promptPanel);
        runSlide(promptPanel, 100, new Vector2f(400, 200), new Vector2f((width / 2) - 200, (height / 2) - 100));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void createOptionPanel() {
        optionPanel = new Panel();
        optionPanel.getStyle().getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        optionPanel.getStyle().setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
//        optionPanel.setSize(400, 600);
        optionPanel.setSize(400, 0);
        optionPanel.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        optionPanel.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
//        optionPanel.setPosition((width / 2) - 200, (height / 2) - 300);
        optionPanel.setPosition((width / 2) - 200, (height / 2));
        Label title = new Label("Game Options");
        title.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        title.getStyle().setFontSize(36f);
        title.getStyle().setFont("Impact");
        title.getStyle().setVerticalAlign(VerticalAlign.TOP);
        title.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        title.getStyle().setPaddingTop(new Length(20f, LengthType.PIXEL));
        title.setSize(optionPanel.getSize());
        optionPanel.add(title);
        float panelWidth = optionPanel.getSize().x;
        float panelHeight = optionPanel.getSize().y;
        Button close = new Button("\uE14C", panelWidth - 60, 18, 40, 40);
        close.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        close.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        close.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        close.getStyle().setFontSize(36f);
        close.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        close.getStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().setTextColor(new Vector4f(1f, 1f, 0f, 1f));
        close.getPressedStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getPressedStyle().setTextColor(new Vector4f(ColorConstants.WHITE));
        close.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                runSlide(optionPanel, 200, new Vector2f(400, 0), new Vector2f((width / 2) - 200, (height / 2)));
                new Thread(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.remove(optionPanel);
                }).start();
            }
        });
        optionPanel.add(close);
        List<Button> buttons = Arrays.asList(
                new Button("Settings", panelWidth / 2 - 90f, 10 + 1 * 80f, 180f, 60f),
                new Button("Info", panelWidth / 2 - 90f, 10 + 2 * 80f, 180f, 60f),
                new Button("Map", panelWidth / 2 - 90f, 10 + 3 * 80f, 180f, 60f),
                new Button("Players", panelWidth / 2 - 90f, 10 + 4 * 80f, 180f, 60f),
                new Button("Leave Server", panelWidth / 2 - 90f, 10 + 5 * 80f, 180f, 60f),
                new Button("Quit to Desktop", panelWidth / 2 - 90f, 10 + 6 * 80f, 180f, 60f)
        );
        buttons.get(5).getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                prompt("Are you sure to quit to desktop?", () -> GLFW.glfwSetWindowShouldClose(Main.getEngine().getWindow().getHandle(), true), () -> {});
//                GLFW.glfwSetWindowShouldClose(Main.getEngine().getWindow().getHandle(), true);
            }
        });
        buttons.forEach((e) -> {
            e.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
            e.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
            e.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            e.getStyle().setFontSize(20f);
            e.getStyle().setFont("Impact");
            e.getStyle().getBackground().setColor(0.3f, 0.3f, 0.3f, 1.0f);
        });
        buttons.forEach(optionPanel::add);
    }

    private void runSlide(Component component, int duration, Vector2f targetSize, Vector2f targetPosition) {
//        component.getChildComponents().forEach((e) -> runSlide(e, duration, targetSize, targetPosition));
        float targetWidth = targetSize.x, targetHeight = targetSize.y;
        float targetPositionX = targetPosition.x, targetPositionY = targetPosition.y;
        float currentWidth = component.getSize().x, currentHeight = component.getSize().y;
        float currentPositionX = component.getPosition().x, currentPositionY = component.getPosition().y;
        new Thread(() -> {
            try {
                Vector2f deltaSize = new Vector2f(
                        (10.0f / duration) * (targetWidth - currentWidth),
                        (10.0f / duration) * (targetHeight - currentHeight)
                );
                Vector2f deltaPosition = new Vector2f(
                        (10.0f / duration) * (targetPositionX - currentPositionX),
                        (10.0f / duration) * (targetPositionY - currentPositionY)
                );
                for (int i = 1; i <= duration / 10; ++i) {
                    component.setSize(component.getSize().add(deltaSize));
                    component.setPosition(component.getPosition().add(deltaPosition));
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void runTransition(Component component, int fadeInTime, int duration, int fadeOutTime, int opts) {
        component.getChildComponents().forEach((e) -> runTransition(e, fadeInTime, duration, fadeOutTime, opts & 3));
        if ((opts & TRANSITION_BACKGROUND) != 0) {
            Vector4f vec = new Vector4f(component.getStyle().getBackground().getColor());
            var ref = new Object() {
                float thickness = -1.0F;
            };
            if (component.getStyle().getBorder() != null) {
                ref.thickness = ((SimpleLineBorder) component.getStyle().getBorder()).getThickness();
            }
            float limit = vec.w;
            new Thread(() -> {
                try {
                    for (int i = 1; i <= fadeInTime / 10; ++i) {
                        vec.w = (10.0f * i / fadeInTime) * limit;
                        component.getStyle().getBackground().setColor(vec);
                        if (component.getStyle().getBorder() != null)
                            ((SimpleLineBorder) component.getStyle().getBorder()).setThickness((10.0f * i / fadeInTime) * ref.thickness);
                        Thread.sleep(10);
                    }
                    Thread.sleep(duration);
                    for (int i = 1; i <= fadeOutTime / 10; ++i) {
                        float v = 10.0f * (fadeOutTime / 10 - i) / fadeOutTime;
                        vec.w = v * limit;
                        component.getStyle().getBackground().setColor(vec);
                        if (component.getStyle().getBorder() != null)
                            ((SimpleLineBorder) component.getStyle().getBorder()).setThickness(
                                    v * ref.thickness
                            );
                        Thread.sleep(10);
                        if (i == fadeOutTime / 10 && (opts & TRANSITION_REMOVAL) != 0) {
                            this.remove(component);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vec.w = limit;
                component.getStyle().getBackground().setColor(vec);
                if (component.getStyle().getBorder() != null)
                    ((SimpleLineBorder) component.getStyle().getBorder()).setThickness(ref.thickness);
            }).start();
        }
        if ((opts & TRANSITION_FONT) != 0) {
            Vector4f vec = new Vector4f(component.getStyle().getTextColor());
            float limit = vec.w;
            new Thread(() -> {
                try {
                    for (int i = 1; i <= fadeInTime / 10; ++i) {
                        vec.w = (10.0f * i / fadeInTime) * limit;
                        component.getStyle().setTextColor(vec);
                        Thread.sleep(10);
                    }
                    Thread.sleep(duration);
                    for (int i = 1; i <= fadeOutTime / 10; ++i) {
                        vec.w = (10.0f * (fadeOutTime / 10 - i) / fadeOutTime) * limit;
                        component.getStyle().setTextColor(vec);
                        Thread.sleep(10);
                        if (i == fadeOutTime / 10 && (opts & TRANSITION_REMOVAL) != 0) {
                            this.remove(component);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                vec.w = limit;
                component.getStyle().setTextColor(vec);
            }).start();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void createSubPanel() {
        subPanel = new Panel();
        subPanel.getStyle().getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        subPanel.getStyle().setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
        subPanel.setPosition(40, 40);
        subPanel.setSize(width - 80f, height - 80f);
        subPanel.getStyle().setVerticalAlign(VerticalAlign.TOP);
        subPanel.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        float panelWidth = subPanel.getSize().x;
        float panelHeight = subPanel.getSize().y;
        Label title = new Label("Title");
        title.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        title.getStyle().setFontSize(48f);
        title.getStyle().setFont("Impact");
        title.getStyle().setVerticalAlign(VerticalAlign.TOP);
        title.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        title.getStyle().setPaddingTop(new Length(20f, LengthType.PIXEL));
        title.setSize(subPanel.getSize());
        subPanel.add(title);
        List<Button> buttons = Arrays.asList(
                new Button(panelWidth / 2 - 640f, panelHeight - 100f, 180f, 60f),
                new Button(panelWidth / 2 - 420f, panelHeight - 100f, 180f, 60f),
                new Button(panelWidth / 2 - 200f, panelHeight - 100f, 180f, 60f),
                new Button(panelWidth / 2 + 20f, panelHeight - 100f, 180f, 60f),
                new Button(panelWidth / 2 + 240f, panelHeight - 100f, 180f, 60f),
                new Button(panelWidth / 2 + 460f, panelHeight - 100f, 180f, 60f)
        );
        buttons.forEach((e) -> {
            e.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
            e.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
            e.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            e.getStyle().setFontSize(20f);
            e.getStyle().setFont("Impact");
            e.getStyle().getBackground().setColor(0.3f, 0.3f, 0.3f, 1.0f);
        });
        buttons.forEach(subPanel::add);
        Button close = new Button("\uE14C", panelWidth - 60, 20, 40, 40);
        close.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        close.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        close.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        close.getStyle().setFontSize(60f);
        close.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        close.getStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().setTextColor(new Vector4f(1f, 1f, 0f, 1f));
        close.getPressedStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getPressedStyle().setTextColor(new Vector4f(ColorConstants.WHITE));
        close.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                runTransition(subPanel, 0, 0, 400,
                        TRANSITION_BACKGROUND | TRANSITION_FONT | TRANSITION_REMOVAL);
            }
        });
        subPanel.add(close);
    }

    private void createDebugPanel() {
        createReloadButton();

        Button switchButton = new Button("SubMenu");
        switchButton.setSize(100, 80);
        switchButton.setPosition(width - 300, height - 180);
        switchButton.getStyle().setFont("Impact");
        switchButton.getStyle().setFontSize(24f);
        switchButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                this.add(subPanel);
                runTransition(subPanel, 400, 0, 0, TRANSITION_BACKGROUND | TRANSITION_FONT);
            }
        });
        this.add(switchButton);

        Button optionButton = new Button("Option");
        optionButton.setSize(100, 80);
        optionButton.setPosition(width - 400, height - 180);
        optionButton.getStyle().setFont("Impact");
        optionButton.getStyle().setFontSize(24f);
        optionButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                this.add(optionPanel);
                runSlide(optionPanel, 200, new Vector2f(400, 600), new Vector2f((width / 2) - 200, (height / 2) - 300));
            }
        });
        this.add(optionButton);
    }

    private void createStyle() {
        panelStyle = new Style();
        panelStyle.getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        panelStyle.setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
    }

    private void createPanels() {
        panels = Arrays.asList(
                new Panel(0, 0, 128, 128), // Top Left Panel
                new Panel(128, 0, width - 328, 64), // Top Panel
                new Panel(width - 200, 0, 200, 64), // Top Right (Config) Panel
                new Panel(0, height - 100, width - 200, 100), // Bottom Panel
                new Panel(width - 200, height - 100, 200, 100) // Phase Panel
        );
        List<Label> labels = Arrays.asList(
                new Label("Map & Info"),
                new Label("Status"),
                new Label("Config"),
                new Label("Soldier"),
                new Label("Phase")
        );
        labels.forEach((e) -> {
            e.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
            e.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
            e.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            e.getStyle().setFontSize(24f);
            e.getStyle().setFont("Impact");
        });
        panels.forEach((e) -> e.setStyle(panelStyle));
        for (int i = 0; i < 5; i++) {
            labels.get(i).setSize(panels.get(i).getSize());
            panels.get(i).add(labels.get(i));
        }
        panels.forEach(this::add);
    }

    private void createReloadButton() {
        reloadButton = new Button("Reload");
        reloadButton.setSize(200, 80);
        reloadButton.setPosition(width - 200, height - 180);
        reloadButton.getStyle().setFont("Impact");
        reloadButton.getStyle().setFontSize(24f);
        reloadButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE)
                Main.menu.reload();
        });
        this.add(reloadButton);
    }

    public GUI(int width, int height) {
        super(0, 0, width, height);
        this.width = width;
        this.height = height;
        FontRegistry.registerFont("YaHei Mono", "resources/fonts/Microsoft YaHei Mono.ttf");
        FontRegistry.registerFont("Impact", "resources/fonts/impact.ttf");
        createStyle();
        createPanels();
        createSubPanel();
        createDebugPanel();
        createOptionPanel();
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        createPanels();
        createDebugPanel();
        reloadButton.setPosition(width - 100, height - 100);
    }

}