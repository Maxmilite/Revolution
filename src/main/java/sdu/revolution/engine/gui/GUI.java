package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.style.Background;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.font.FontRegistry;
import org.joml.Vector4f;
import sdu.revolution.Main;

import java.util.Arrays;
import java.util.List;


public class GUI extends Panel {
    private int width, height;
    private Button reloadButton;
    private List<Panel> panels;
    private Style textStyle, panelStyle;

    private void createStyle() {
        textStyle = new Style();
        textStyle.setFont("Impact");
        textStyle.setTextColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
        textStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        textStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        textStyle.setFontSize(24f);
        panelStyle = new Style();
        panelStyle.getBackground().setColor(new Vector4f(1.0f, 1.0f, 1.0f, 0.3f));
    }

    private void createPanels() {
        Main.Logger.info(this, "Window width: " + width + " || Window Height: " + height);
        panels = Arrays.asList(
                new Panel(0, 0, 100, 100), // Top Left Panel
                new Panel(100, 0, width - 300, 40), // Top Panel
                new Panel(width - 200, 0, 200, 40), // Top Right (Config) Panel
                new Panel(0, height - 100, width - 200, 100), // Bottom Panel
                new Panel(width - 200, height - 100, 200, 100) // Phase Panel
        );
        List<Label> labels = Arrays.asList(
                new Label("Map & Info"),
                new Label("Status"),
                new Label("Config"),
                new Label("Soldier"),
                new Label("Phase Control")
        );
        labels.forEach((e) -> {
            e.getStyle().setTextColor(ColorUtil.rgba(0, 0, 0, 1f));
            e.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
            e.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
            e.getStyle().setFontSize(20f);
            e.getStyle().setFont("Impact");
        });
        panels.forEach((e) -> {
//            e.setStyle(panelStyle);
            e.getStyle().getBackground().setColor(new Vector4f(1.0f, 1.0f, 1.0f, 0.75f));
            e.getStyle().setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
        });
        for (int i = 0; i < 5; i++) {
            labels.get(i).setSize(panels.get(i).getSize());
            panels.get(i).add(labels.get(i));
        }
        panels.forEach(this::add);
    }

    private void createUpdateButton() {
        reloadButton = new Button("Reload");
        reloadButton.setSize(100, 60);
        reloadButton.setPosition(width - 300, height - 300);
        reloadButton.getStyle().setFont("Impact");
        reloadButton.getStyle().setFontSize(24f);
        reloadButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
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
        createUpdateButton();
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        createPanels();
        createUpdateButton();
        this.remove(reloadButton);
        reloadButton.setPosition(width - 100, height - 100);
        this.add(reloadButton);
    }

}