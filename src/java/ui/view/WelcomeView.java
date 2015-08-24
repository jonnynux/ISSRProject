package ui.view;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Welcome Page.
 *
 * @author jonny
 */
public class WelcomeView extends AbstractView {

    public WelcomeView() {

        setHeight("100%");

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);

        layout.setMargin(false);
        layout.setHeight("100%");

        // Main panel to hold information shown to the unlogged user.
        Panel panel = new Panel("Benvenuto nel Punto Vendita");
        panel.setSizeFull();
        panel.setStyleName("default-panel");

        VerticalLayout textLayout = new VerticalLayout();

        Label lbl = new Label("Benvenuto nel punto vendita.");
        Label lbl2 = new Label("Per effettuare il login inserire nome utente e "
                + "password in alto a destra.");
        Label lbl3 = new Label("In caso di accesso con l'amministratore generale "
                + "si deve selezionare anche il punto vendita dalla combobox.");
        Label lbl4 = new Label("Dopo il login si accederà alle funzionalità del sistema.");

        textLayout.addComponent(lbl);
        textLayout.addComponent(lbl2);
        textLayout.addComponent(lbl3);
        textLayout.addComponent(lbl4);
        textLayout.setWidth("50%");
        textLayout.setHeight("100%");
        panel.addComponent(textLayout);

        layout.addComponent(panel);
    }
}
