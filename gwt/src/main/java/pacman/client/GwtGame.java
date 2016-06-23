package pacman.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import pacman.shared.MainLoop;

public class GwtGame implements EntryPoint {
    @Override public void onModuleLoad() {
        // the label can be used to show the score --v
        RootPanel.get().add(new Label("Main loop instantiated (so it works in js): " + new MainLoop(null, 1)));

        Button exit = new Button("Exit XD");
        exit.addClickHandler(click -> {
            // this can be used to show simple text dialogs
            Window.alert("not sure you can exit from a web page!");
        });
        RootPanel.get().add(exit);

        Canvas canvas = Canvas.createIfSupported();
        if (canvas == null) {
            Window.alert("Ups! your browser do not support canvas...");
            return;
        }
        canvas.setHeight("300px");
        canvas.setWidth("300px");
        Context2d c2d = canvas.getContext2d();
        c2d.setFillStyle("blue");
        c2d.fillRect(0,0,100,100);
        RootPanel.get().add(canvas);
    }
}
