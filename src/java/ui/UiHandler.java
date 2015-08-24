package ui;

import com.vaadin.Application.UserChangeEvent;
import com.vaadin.Application.UserChangeListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pojos.User;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import ui.component.Header;
import ui.component.Menu;
import ui.view.AbstractView;
import ui.view.LoggedView;
import ui.view.WelcomeView;
import utils.AppContext;

/**
 * Controller for all the UI
 *
 * @author jonny
 */
public class UiHandler extends VerticalLayout implements UserChangeListener {

    // Portions of the application that exist at all times.
    private Header header;
    private WelcomeView defaultView;
    // The views that are shown to logged users.
    private LoggedView userView;
    //private ChannelView javaView;
    //private ChannelView ubuntuView;
    // Components shown to logged users.
    private Menu menu;
    private HorizontalSplitPanel menusplit;
    // Used to keep track of the current main view.
    //private HashMap<String, AbstractView> viewList = new HashMap<String, AbstractView>();
    private User executor;

    /**
     * Constructor of the handler. Creates and styles all default components.
     *
     * @param window main window of the application
     */
    public UiHandler(Window window) {
        header = new Header();
        defaultView = new WelcomeView();

        setSizeFull();
        setMargin(true);

        // Add the main components for the UI.
        addComponent(header);
        addComponent(defaultView);

        setComponentAlignment(header, Alignment.TOP_CENTER);
        setComponentAlignment(defaultView, Alignment.TOP_CENTER);

        setExpandRatio(defaultView, 1);
        this.setSpacing(true);

        window.setContent(this);//.setLayout(this);
    }

    /**
     * All the UI logic in the event that a user logs in.
     */
    public void userLoggedIn() {

        // initialization of the user specific portions of the UI and
        // add a split panel to differentiate between menu and main view.
        //userView = new UserView();
        menu = new Menu();
        //javaView = new ChannelView("#java");
        //ubuntuView = new ChannelView("#ubuntu");

        // Hard code adding of the user specific views to the view list.
        //viewList.put("#java", javaView);
        //viewList.put("#ubuntu", ubuntuView);
        //viewList.put("User View", userView);

        menusplit = new HorizontalSplitPanel();//SplitPanel.ORIENTATION_HORIZONTAL);

        // Adds the splitbar to the layout.
        addComponent(menusplit, 1);

        // Visual modifiers of the splitpanel.
        setExpandRatio(menusplit, 1);
        menusplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);

        // Remove the default view and show the user specifc one.
        removeComponent(defaultView);

        ApplicationContext ctx = AppContext.getApplicationContext();
        userView = (LoggedView) ctx.getBean("PaginaPrincipale");
        addComponent(userView);

        // Set the menu on the left side of the split.
        menusplit.setFirstComponent(menu);

        // Set the Welcome View on the right side.
        setMainView(userView);

        // Inform the different parts of the UI that the user has logged in.
        header.userLoggedIn();

        MyApplication.getProject().getMainWindow().showNotification(
                "Accesso Effettuato", Window.Notification.TYPE_TRAY_NOTIFICATION);
    }

    /**
     * Reverts back to the default view and removes all the user specific
     * content.
     */
    public void userLoggedOut() {

        // If components exist.
        try {
            // Remove the split panel including menu and userView.
            removeComponent(menusplit);

            // Add the default view.
            addComponent(defaultView, 1);
            setExpandRatio(defaultView, 1);

            // Tell the other UI components that the user logged off.
            header.userLoggedOut();

        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UiHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the main view when the user clicks an item in the menu.
     *
     * @param viewName Event
     */
    public void switchView(String viewName) {
        viewName = viewName.replace(" ", "");

        ApplicationContext ctx = AppContext.getApplicationContext();

        try {
            AbstractView view = (AbstractView) ctx.getBean(viewName);
            setMainView(view);
        } catch (ClassCastException ex) {
            Window subWindow = (Window) ctx.getBean(viewName);
            MyApplication.getProject().getMainWindow().addWindow(subWindow);
        } catch (NoSuchBeanDefinitionException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Funzione non abilitata", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UiHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UiHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Helper method for setting the main view.
     *
     * @param c View that we want to display
     */
    public void setMainView(AbstractView c) {
        menusplit.setSecondComponent(c);
    }

    /**
     * Closes the view and removes it from the menu.
     *
     * @throws Exception
     */
    public void closeCurrentView() throws Exception {
        // Remove the view from the view list.
        //viewList.remove(menu.getTree().getValue().toString());
        setMainView(userView);
        menu.removeCurrentTreeSelection();
    }

    /**
     * Logging
     *
     * @param event
     */
    @Override
    public void applicationUserChanged(UserChangeEvent event) {
        if (event.getNewUser() == null) {
            userLoggedOut();
        } else {
            userLoggedIn();
        }
    }

    public void setUser(User user) {
        this.executor = user;
    }

    public User getUser() {
        return executor;
    }
}
