package ui.view;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * View that appears after login
 *
 * @author jonny
 */
public class LoggedView extends AbstractView {

    private VerticalLayout layout;

    /**
     * Styles the User View.
     */
    public LoggedView() {

        setHeight("100%");

        layout = new VerticalLayout();
        setCompositionRoot(layout);

        layout.setMargin(true);
        layout.setHeight("100%");

        //setStyleName("view");

        Panel panel = new Panel("Grazie per aver effettuato l'accesso");
        panel.setSizeFull();

        panel.addComponent(new Label(
                "Benvenuto nella pagina principale del punto vendita. "
                + "Per favore seleziona un'opzione sulla sinistra"));

        layout.addComponent(panel);
    }
}
