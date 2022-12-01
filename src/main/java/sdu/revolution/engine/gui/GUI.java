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
import org.joml.Vector4f;
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

    private void runTransition(Component component, int fadeInTime, int duration, int fadeOutTime, int opts) {
        component.getChildComponents().forEach((e) -> {
            runTransition(e, fadeInTime, duration, fadeOutTime, opts & 3);
        });
        if ((opts & TRANSITION_BACKGROUND) != 0) {
            Vector4f vec = new Vector4f(component.getStyle().getBackground().getColor());
            float limit = vec.w;
            new Thread(() -> {
                try {
                    for (int i = 1; i <= fadeInTime / 10; ++i) {
                        vec.w = (10.0f * i / fadeInTime) * limit;
                        component.getStyle().getBackground().setColor(vec);
                        Thread.sleep(10);
                    }
                    Thread.sleep(duration);
                    for (int i = 1; i <= fadeOutTime / 10; ++i) {
                        vec.w = (10.0f * (fadeOutTime / 10 - i) / fadeOutTime) * limit;
                        component.getStyle().getBackground().setColor(vec);
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
            }).start();
        }
        if ((opts & TRANSITION_FONT) != 0) {
            Vector4f vec = new Vector4f(component.getStyle().getTextColor());
            float limit = vec.w;
            Main.Logger.info(this, Float.toString(limit));
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

        Button switchButton = new Button("Switch");
        switchButton.setSize(200, 80);
        switchButton.setPosition(width - 400, height - 180);
        switchButton.getStyle().setFont("Impact");
        switchButton.getStyle().setFontSize(24f);
        switchButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                this.add(subPanel);
                runTransition(subPanel, 400, 0, 0, TRANSITION_BACKGROUND | TRANSITION_FONT);
            }
        });
        this.add(switchButton);

    }

    private void createStyle() {
        panelStyle = new Style();
        panelStyle.getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        panelStyle.setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
    }

    private void createPanels() {
        Main.Logger.info(this, "Window width: " + width + " || Window Height: " + height);
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
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        createPanels();
        createDebugPanel();
        reloadButton.setPosition(width - 100, height - 100);
    }

}