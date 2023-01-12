package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Component;

public class GuiInstance extends Component {
    private boolean isWindow;
    public GuiInstance() {
        this.init();
    }
    public GuiInstance(boolean isWindow) {
        this.isWindow = isWindow;
        this.init();
    }
    private void init() {}
    public boolean isWindow() {
        return isWindow;
    }

    public void setWindow(boolean window) {
        isWindow = window;
    }
    public void close() {}
    public void
}
