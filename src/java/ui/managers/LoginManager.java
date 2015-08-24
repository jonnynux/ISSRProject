package ui.managers;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;
import exceptions.LoginErrorException;
import exceptions.NoStoresException;
import interfaces.LoginOperations;
import interfaces.StoreOperations;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pojos.Store;
import model.pojos.User;
import ui.MyApplication;

/**
 *
 * @author jonny
 */
public class LoginManager {

    LoginOperations loginOperations;
    StoreOperations storeOperations;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setLoginOperations(LoginOperations loginOperations) {
        this.loginOperations = loginOperations;
    }

    public void setStoreOperations(StoreOperations storeOperations) {
        this.storeOperations = storeOperations;
    }
    //</editor-fold>

    /**
     *
     * @param storeField
     */
    public void populateStoreField(ComboBox storeField) {
        try {
            List<Store> storeList = storeOperations.getStoreList(null);

            for (Store i : storeList) {
                storeField.addItem(i.getIdstore() + ": " + i.getName()
                        + " (" + i.getAddress() + ")");
            }
        } catch (NoStoresException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param username
     * @param password
     * @param idStore
     * @return
     */
    public User login(String username, String password, int idStore) {
        try {
            return loginOperations.login(username, password, idStore);
        } catch (LoginErrorException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
