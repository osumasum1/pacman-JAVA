package pacman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import pacman.shared.MainLoop;

public class GwtGame implements EntryPoint {
    @Override public void onModuleLoad() {
        RootPanel.get().add(new Label("Hello " + new MainLoop(null, 1)));
    }
}
