package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.style.Style;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.font.FontRegistry;
import org.joml.Vector4f;
import sdu.revolution.engine.gui.panels.DebugPanel;
import sdu.revolution.engine.gui.panels.OptionPanel;
import sdu.revolution.engine.gui.panels.SubPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Todo: Reduce duplicate code
// Finished on 2023-01-12 22:33

public class GUI extends Panel {
    public int width, height;
    private Style panelStyle;
    public SubPanel subPanel;
    public OptionPanel optionPanel;
    public DebugPanel debugPanel;
    public final List<PanelInstance> panelStack;
    public boolean lock;

    public boolean call(Class<?> t) {
        if (t == OptionPanel.class) {
            this.optionPanel.call();
            panelStack.add(optionPanel);
            return true;
        } else if (t == SubPanel.class) {
            this.subPanel.call();
            panelStack.add(subPanel);
            return true;
        }
        return false;
    }

    public boolean close(Class<?> t) {
        if (t == OptionPanel.class) {
            this.optionPanel.close();
            panelStack.remove(optionPanel);
            return true;
        } else if (t == SubPanel.class) {
            this.subPanel.close();
            panelStack.remove(subPanel);
            return true;
        }
        return false;
    }

    public void close(PanelInstance instance) {
        if (close(instance.getClass()))
            return;
        instance.close();
        panelStack.remove(instance);
    }

    private void createStyle() {
        panelStyle = new Style();
        panelStyle.getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        panelStyle.setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
    }

    private void createUtilityPanels() {
        // Top Left Panel
        // Top Panel
        // Top Right (Config) Panel
        // Bottom Panel
        // Phase Panel
        List<Panel> panels = Arrays.asList(new Panel(0, 0, 128, 128), // Top Left Panel
                new Panel(128, 0, width - 328, 64), // Top Panel
                new Panel(width - 200, 0, 200, 64), // Top Right (Config) Panel
                new Panel(0, height - 100, width - 200, 100), // Bottom Panel
                new Panel(width - 200, height - 100, 200, 100) // Phase Panel
        );
        List<Label> labels = Arrays.asList(new Label("Map & Info"), new Label("Status"), new Label("Config"), new Label("Soldier"), new Label("Phase"));
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

    public GUI(int width, int height) {
        super(0, 0, width, height);
        this.width = width;
        this.height = height;
        this.panelStack = new ArrayList<>();
        FontRegistry.registerFont("YaHei Mono", "resources/fonts/Microsoft YaHei Mono.ttf");
        FontRegistry.registerFont("Impact", "resources/fonts/impact.ttf");
        FontRegistry.registerFont("YaHei", "resources/fonts/msyh.ttc");
        FontRegistry.registerFont("XinWei", "resources/fonts/STXINWEI.TTF");
        createStyle();
        createCursor();
        createUtilityPanels();
        debugPanel = new DebugPanel(this);
        subPanel = new SubPanel();
        optionPanel = new OptionPanel();
    }

    public void resize(int w, int h) {
        this.width = w;
        this.height = h;
        GuiLibrary.width = w;
        GuiLibrary.height = h;
        this.setSize(w, h);
    }

    public boolean pause() {
        if (lock)
            return false;
        if (panelStack.isEmpty()) {
            call(OptionPanel.class);
            return true;
        } else {
            PanelInstance instance = panelStack.get(panelStack.size() - 1);
            close(instance);
            return false;
        }
    }

}