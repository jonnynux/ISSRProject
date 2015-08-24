package ui.component;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.MyApplication;
import ui.controller.LoginHandler;

/**
 * Header (top region) of a web page
 *
 * @author jonny
 */
public class Header extends HorizontalLayout {

    // Header Components
    private final Embedded em = new Embedded("", new ThemeResource("images/vaadin.png"));
    private final LoginHandler loginhandler = new LoginHandler();

    public Header() {

        setWidth("100%");
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setScrollable(false);
        HorizontalLayout layout = new HorizontalLayout();

        panel.setHeight("180");

        // Create the visual components of the layout.
        layout.addComponent(em);
        layout.setComponentAlignment(em, Alignment.MIDDLE_LEFT);
        layout.setWidth("100%");

        // Adds the login-"box" to the header.
        layout.addComponent(loginhandler);
        layout.setComponentAlignment(loginhandler, Alignment.BOTTOM_RIGHT);
        panel.addComponent(layout);

        layout.setComponentAlignment(loginhandler, Alignment.MIDDLE_RIGHT);
        addComponent(panel);
    }

    /**
     * Method to change the visuals of the header when a user logs in.
     */
    public void userLoggedIn() {
        // If for some reason the loginhandler has not been initialized.
        try {
            loginhandler.setSizeFull();
            loginhandler.createLogoutComponents();
        } catch (final Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(Header.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to change the visuals of the header when a user logs out.
     */
    public void userLoggedOut() {
        // If for some reason the loginhandler has not been initialized.
        try {
            loginhandler.createLoginComponents();
        } catch (final Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(Header.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
