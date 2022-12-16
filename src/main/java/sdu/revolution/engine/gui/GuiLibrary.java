package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.style.font.FontRegistry;
import org.apache.commons.collections4.BagUtils;
import org.joml.Vector4f;

public class GuiLibrary {
    public enum Color {
        AQUA
    }
    public static Vector4f getColor(Color color) {
        if (color == Color.AQUA) {
            return ColorUtil.rgba(0, 255, 255, 1f);
        }
        // White
        return ColorUtil.rgba(255, 255, 255, 1f);
    }
    public static void setAlign(Component component, VerticalAlign verticalAlign, HorizontalAlign horizontalAlign) {
        component.getStyle().setVerticalAlign(verticalAlign);
        component.getStyle().setHorizontalAlign(horizontalAlign);
    }
    public static void setFont(Component component, String s, float size) {
        component.getStyle().setFontSize(size);
        component.getStyle().setFont(s);
    }
    public static void setPanelStyle(Panel panel) {
        panel.getStyle().getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.75f));
        panel.getStyle().setBorder(new SimpleLineBorder(new Vector4f(1f, 1f, 1f, 1f), 1));
    }
    public static void setCloseButtonStyle(Button close, float size) {
        close.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        close.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        close.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        close.getStyle().setFontSize(size);
        close.getStyle().setFont(FontRegistry.MATERIAL_ICONS_REGULAR);
        close.getStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getHoveredStyle().setTextColor(new Vector4f(1f, 1f, 0f, 1f));
        close.getPressedStyle().getBackground().setColor(new Vector4f(ColorConstants.TRANSPARENT));
        close.getPressedStyle().setTextColor(new Vector4f(ColorConstants.WHITE));
    }

    public static void setButtonStyle(Button button) {
        button.getStyle().setTextColor(ColorUtil.rgba(0, 255, 255, 1f));
        button.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        button.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
        button.getStyle().setFontSize(20f);
        button.getStyle().setFont("Impact");
        button.getStyle().getBackground().setColor(0.3f, 0.3f, 0.3f, 1.0f);
    }
}
