package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Button;
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
import sdu.revolution.Main;
import sdu.revolution.engine.gui.panels.OptionPanel;

import java.util.Arrays;
import java.util.List;

import static sdu.revolution.engine.gui.GuiLibrary.Color.AQUA;
import static sdu.revolution.engine.gui.GuiLibrary.*;


// Todo: Reduce duplicate code

public class GUI extends Panel {
    public int width, height;
    private Button reloadButton;
    private List<Panel> panels;
    private Style panelStyle;
    private Panel subPanel;
    public OptionPanel optionPanel;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void prompt(String content, Runnable yesFunction, Runnable noFunction) {
        Panel promptPanel = new Panel();
        GuiLibrary.setAlign(promptPanel, VerticalAlign.MIDDLE, HorizontalAlign.CENTER);
        GuiLibrary.setPanelStyle(promptPanel);
        promptPanel.setSize(400, 200);
        promptPanel.setPosition((width / 2) - 200, (height / 2) - 100);
        Label title = new Label("Prompt");
        title.getStyle().setTextColor(GuiLibrary.getColor(AQUA));
        GuiLibrary.setFont(title, "Impact", 32f);
        GuiLibrary.setAlign(title, VerticalAlign.TOP, HorizontalAlign.CENTER);
        title.getStyle().setPaddingTop(new Length(20f, LengthType.PIXEL));
        title.setSize(promptPanel.getSize());
        promptPanel.add(title);
        Label val = new Label(content);
        val.getStyle().setTextColor(GuiLibrary.getColor(AQUA));
        val.getStyle().setFontSize(24f);
        val.getStyle().setFont("YaHei");
        GuiLibrary.setAlign(val, VerticalAlign.TOP, HorizontalAlign.CENTER);
        val.getStyle().setPaddingTop(new Length(72f, LengthType.PIXEL));
        val.setSize(promptPanel.getSize());
        promptPanel.add(val);
        float panelWidth = promptPanel.getSize().x;
        float panelHeight = promptPanel.getSize().y;
        Button close = new Button("\uE14C", panelWidth - 48, 18, 30, 30);
        GuiLibrary.setCloseButtonStyle(close, 24f);
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
        GuiLibrary.setButtonStyle(yesButton);
        GuiLibrary.setButtonStyle(noButton);
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
    private void createSubPanel() {
        subPanel = new Panel();
        GuiLibrary.setPanelStyle(subPanel);
        subPanel.setPosition(40, 40);
        subPanel.setSize(width - 80f, height - 80f);
        subPanel.getStyle().setVerticalAlign(VerticalAlign.TOP);
        subPanel.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        float panelWidth = subPanel.getSize().x;
        float panelHeight = subPanel.getSize().y;
        Label title = new Label("Sub Menu");
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
        buttons.forEach(GuiLibrary::setButtonStyle);
        buttons.forEach(subPanel::add);
        Button close = new Button("\uE14C", panelWidth - 60, 20, 40, 40);
        GuiLibrary.setCloseButtonStyle(close, 60f);
        close.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                runTransition(subPanel, 0, 0, 400,
                        TRANSITION_BACKGROUND | TRANSITION_FONT | TRANSITION_REMOVAL);
            }
        });
        subPanel.add(close);
    }

    private void createDebugPanel() {

        Label debug = new Label("Version Not Final, Does Not Represent Actual Game Footage.");
        debug.setSize(width, 200);
        debug.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        debug.getStyle().setVerticalAlign(VerticalAlign.TOP);
        debug.setPosition(0, 200);
        debug.getStyle().setFontSize(48F);
        debug.getStyle().setTextColor(new Vector4f(1.0f, 1.0f, 1.0f, 0.5f));
        debug.getStyle().setFont(FontRegistry.ROBOTO_REGULAR);
        this.add(debug);

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
                call(OptionPanel.class);
            }
        });
        this.add(optionButton);
    }

    public void call(Class<?> t) {
        if (t == OptionPanel.class) {
            this.optionPanel.call();
        }
    }
    public void close(Class<?> t) {
        if (t == OptionPanel.class) {
            this.optionPanel.close();
        }
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

    private void createCursor() {
        Label cursor = new Label("\uE145");
        cursor.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        cursor.getStyle().setFontSize(36.0f);
        cursor.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        cursor.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        cursor.getStyle().setTextColor(ColorConstants.white());
        cursor.setPosition(0, 0);
        cursor.setSize(width, height);
        this.add(cursor);
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
        FontRegistry.registerFont("YaHei", "resources/fonts/msyh.ttc");
        FontRegistry.registerFont("XinWei", "resources/fonts/STXINWEI.TTF");
        createStyle();
        createCursor();
        createPanels();
        createSubPanel();
        createDebugPanel();
        optionPanel = new OptionPanel();
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;

        reloadButton.setPosition(width - 100, height - 100);
    }

}