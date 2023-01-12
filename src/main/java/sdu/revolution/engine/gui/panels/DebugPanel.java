package sdu.revolution.engine.gui.panels;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.style.font.FontRegistry;
import org.joml.Vector4f;
import sdu.revolution.Main;
import sdu.revolution.engine.gui.PanelInstance;

public class DebugPanel extends PanelInstance {
    public DebugPanel(Panel origin) {
        super();
        this.init(origin);
    }
    public void init(Panel origin) {
        // Debug Label
        Label debug = new Label("Version Not Final, Does Not Represent Actual Game Footage.");
        debug.setSize(width, 200);
        debug.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        debug.getStyle().setVerticalAlign(VerticalAlign.TOP);
        debug.setPosition(0, 200);
        debug.getStyle().setFontSize(48F);
        debug.getStyle().setTextColor(new Vector4f(1.0f, 1.0f, 1.0f, 0.5f));
        debug.getStyle().setFont(FontRegistry.ROBOTO_REGULAR);
        origin.add(debug);

        // Reload Button
        Button reloadButton = new Button("Reload");
        reloadButton.setSize(200, 80);
        reloadButton.setPosition(width - 200, height - 180);
        reloadButton.getStyle().setFont("Impact");
        reloadButton.getStyle().setFontSize(24f);
        reloadButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE)
                Main.menu.reload();
        });
        origin.add(reloadButton);

        // Switch Button
        Button switchButton = new Button("SubMenu");
        switchButton.setSize(100, 80);
        switchButton.setPosition(width - 300, height - 180);
        switchButton.getStyle().setFont("Impact");
        switchButton.getStyle().setFontSize(24f);
        switchButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                Main.menu.getGui().call(SubPanel.class);
            }
        });
        origin.add(switchButton);

        // Option Button
        Button optionButton = new Button("Option");
        optionButton.setSize(100, 80);
        optionButton.setPosition(width - 400, height - 180);
        optionButton.getStyle().setFont("Impact");
        optionButton.getStyle().setFontSize(24f);
        optionButton.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                Main.menu.getGui().call(OptionPanel.class);
            }
        });
        origin.add(optionButton);
    }
}