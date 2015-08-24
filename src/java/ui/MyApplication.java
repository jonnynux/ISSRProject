package ui;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Window;

/**
 * Vaadin Application
 *
 * @author jonny
 */
public class MyApplication extends Application implements TransactionListener {

    protected static ThreadLocal<MyApplication> thisApplication = new ThreadLocal<MyApplication>();
    public UiHandler ui;

    /**
     * Set ThreadLocal application.
     *
     * @param MyApplication t
     */
    public static void setProject(MyApplication t) {
        thisApplication.set(t);
    }

    /**
     * Get ThreadLocal application.
     *
     * @return
     */
    public static MyApplication getProject() {
        return thisApplication.get();
    }

    @Override
    public void init() {

        // sets the current application to ThreadLocal.
        setProject(this);

        // Creates the Main Window and then hands over all UI work to the
        // UiHandler
        setMainWindow(new Window("Gestione Punto Vendita"));

        setTheme("my-chameleon");

        ui = new UiHandler(getMainWindow());

        // Adds a TransactionListener for this class.
        getContext().addTransactionListener(this);

        // Register user change listener for UiHandler.
        addListener(ui);
    }

    /**
     * Helper to return the uiHandler attached to a unique application.
     *
     * @return
     */
    public UiHandler getUiHandler() {
        return thisApplication.get().ui;
    }

    /**
     * For ThreadLocal pattern.
     *
     * @param application
     * @param transactionData
     */
    @Override
    public void transactionStart(Application application, Object transactionData) {
        // Same WebApplicationContext (session) may contain multiple different
        // Toolkit applications, here we are only interested of
        // TrainingApplication related transaction events.
        if (application == MyApplication.this) {
            thisApplication.set(this);
        }
    }

    /**
     * For ThreadLocal pattern, remove application reference
     *
     * @param application
     * @param transactionData
     */
    @Override
    public void transactionEnd(Application application, Object transactionData) {
        if (application == MyApplication.this) {
            thisApplication.set(null);
        }
    }
}
