package ui.controller;

import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.LoginManager;
import utils.AppContext;

/**
 * Controller for the login view
 *
 * @author jonny
 */
public final class LoginHandler extends VerticalLayout implements Action.Handler {

    // The login "Form".
    private final TextField userField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ComboBox storeField = new ComboBox();
    private final Button loginButton = new Button("Login",
            new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent e) {
                    login();
                }
            });
    private final Button logoutButton = new Button("Logout",
            new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent e) {

                    logout();
                }
            });
    // User object retrieved from the database.
    private User user;
    // Allow input with the enter button.
    private final Action ACTION_LOGIN = new ShortcutAction("Login ",
            ShortcutAction.KeyCode.ENTER, null);
    private final LoginManager loginManager;

    /**
     * Handles the login box on the page.
     */
    public LoginHandler() {

        ApplicationContext ctx = AppContext.getApplicationContext();
        loginManager = (LoginManager) ctx.getBean("loginManager");

        // Style the login box.
        setMargin(true);
        setWidth("250");
        setHeight("150");

        createLoginComponents();

        // Adds keyboard listener
        MyApplication.getProject().getMainWindow().addActionHandler(this);
    }

    /**
     * Inserts the login-"Form" for the user.
     */
    public void createLoginComponents() {
        try {
            // Clears all current components in case the user already logged in.
            removeAllComponents();

            // Adds the "Form" for the login event.
            userField.setDescription("Username");
            addComponent(userField);
            setComponentAlignment(userField, Alignment.TOP_RIGHT);

            passwordField.setDescription("Password");
            addComponent(passwordField);
            setComponentAlignment(passwordField, Alignment.TOP_RIGHT);

            loginManager.populateStoreField(storeField);
            storeField.setDescription("Punto Vendita");
            addComponent(storeField);
            setComponentAlignment(storeField, Alignment.TOP_RIGHT);

            addComponent(loginButton);
            setComponentAlignment(loginButton, Alignment.TOP_RIGHT);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the login information box and the logout button.
     */
    public void createLogoutComponents() {

        // User name
        Label welcome = new Label("Benvenuto, " + user.getNamesurname());
        Label role = new Label("[" + user.getRole().getName() + "]");
        Label store = new Label(user.getStore().getName()
                + " (" + user.getStore().getAddress() + ")");

        // Clears all current components.
        removeAllComponents();

        VerticalLayout vert = new VerticalLayout();
        vert.setWidth("200");
        vert.addComponent(welcome);
        vert.addComponent(role);
        vert.addComponent(store);
        addComponent(vert);
        setComponentAlignment(vert, Alignment.TOP_RIGHT);

        addComponent(logoutButton);
        setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
    }

    /*
     * Try to log in and set credentials.
     *
     * @param loginname login name value retrieved from the form @param password
     * login password value retrieved from the form
     */
    private void login() {
        try {
            String username = userField.getValue().toString();
            String password = passwordField.getValue().toString();

            // Empty the text boxes.
            userField.setValue("");
            passwordField.setValue("");

            int idStore = -1;
            if (storeField.getValue() != null) {
                idStore = Integer.parseInt(storeField.getValue().toString().split(":")[0]);
            }

            // Empty the text boxes.
            userField.setValue("");
            passwordField.setValue("");
            storeField.setValue(null);

            // Try to authenticate the user.
            user = loginManager.login(username, password, idStore);

            if (user != null) {
                // Sets the application user and sends out an event to inform that
                // the user has logged in. UiHandler will receive this event.
                MyApplication.getProject().setUser((Object) user.getLogin() + " ");
                MyApplication.getProject().getUiHandler().setUser(user);

                // Remove the action handler to the Enter key.
                MyApplication.getProject().getMainWindow().removeActionHandler(this);
            }
        } catch (NumberFormatException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Formato numero errato", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LoginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Used to inform the application that a user has logged off.
     */
    public void logout() {

        // Sets the current user as NULL and fires an UserChangeEvent.
        MyApplication.getProject().setUser(null);
        user = null;

        // Add back the ActionHandler for the Enter key.
        MyApplication.getProject().getMainWindow().addActionHandler(this);
    }

    /**
     *
     * @param target
     * @param sender
     * @return
     */
    @Override
    public Action[] getActions(Object target, Object sender) {
        return new Action[]{ACTION_LOGIN};
    }

    /**
     *
     * @param action
     * @param sender
     * @param target
     */
    @Override
    public void handleAction(Action action, Object sender, Object target) {
        // Handle only events that originate from Login window

        if (action == ACTION_LOGIN) {
            login();
        }
    }
}
