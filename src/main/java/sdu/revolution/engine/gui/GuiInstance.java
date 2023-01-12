package sdu.revolution.engine.gui;

import com.spinyowl.legui.component.Component;

public class GuiInstance extends Component {
    private boolean isWindow;
    public GuiInstance() {
        super();
        this.isWindow = false;
        this.init();
    }
    public GuiInstance(boolean isWindow) {
        super();
        this.isWindow = isWindow;
        this.init();
    }
    public void init() {}
    public boolean isWindow() {
        return isWindow;
    }

    public void setWindow(boolean window) {
        isWindow = window;
    }
    public void close() {}
}
