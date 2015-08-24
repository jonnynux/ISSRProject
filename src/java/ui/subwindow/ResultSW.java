package ui.subwindow;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import ui.MyApplication;

/**
 * Subwindow for viewing selling/returning/removing expired product result
 *
 * @author jonny
 */
public class ResultSW extends Window {

    private final static String TITLE = "Risultato";

    public ResultSW(String result) {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        final Window window = this;

        Button close = new Button("Chiudi", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }
        });

        VerticalLayout l = (VerticalLayout) this.getContent();
        l.setMargin(true);
        l.setSpacing(true);
        l.setSizeUndefined();

        String[] results = result.split("\n");
        for (String i : results) {
            Label resultField = new Label(i);
            resultField.setSizeUndefined();
            l.addComponent(resultField);
        }

        l.addComponent(close);
    }
}
