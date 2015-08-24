package ui.managers;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import exceptions.*;
import interfaces.StoreOperations;
import interfaces.UserOperations;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pojos.Role;
import model.pojos.Store;
import model.pojos.User;
import ui.MyApplication;
import ui.subwindow.CreateModifyUserSW;
import ui.view.UserView;
import utils.Utility;

/**
 *
 * @author jonny
 */
public class UserManager {

    UserOperations userOperations;
    StoreOperations storeOperations;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setUserOperations(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    public void setStoreOperations(StoreOperations storeOperations) {
        this.storeOperations = storeOperations;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ViewUser">
    /**
     *
     * @param table
     * @param executor
     */
    public List<User> populateUserTable(User executor, Table table) {

        List<User> users = null;

        try {
            IndexedContainer container = new IndexedContainer();
            String idUserProp = "Id Utente";
            String loginProp = "Login";
            String namesurnameProp = "Nome Cognome";
            String emailProp = "Email";
            String roleProp = "Ruolo";
            String storeProp = "Punto vendita";
            String regExpirationProp = "Scadenza registrazione";
            String passExpirationProp = "Scadenza password";
            String sessionDurationProp = "Durata sessione";

            container.addContainerProperty(idUserProp, Integer.class, null);
            container.addContainerProperty(loginProp, String.class, null);
            container.addContainerProperty(namesurnameProp, String.class, null);
            container.addContainerProperty(emailProp, String.class, null);
            container.addContainerProperty(roleProp, String.class, null);
            container.addContainerProperty(storeProp, String.class, null);
            container.addContainerProperty(regExpirationProp, String.class, null);
            container.addContainerProperty(passExpirationProp, String.class, null);
            container.addContainerProperty(sessionDurationProp, Integer.class, null);

            users = userOperations.getUserList(executor);

            if (!users.isEmpty()) {
                for (User i : users) {
                    Item item = container.addItem(i.getIduser());
                    item.getItemProperty(idUserProp).setValue(i.getIduser());
                    item.getItemProperty(loginProp).setValue(i.getLogin());
                    item.getItemProperty(namesurnameProp).setValue(i.getNamesurname());
                    item.getItemProperty(emailProp).setValue(i.getEmail());
                    item.getItemProperty(roleProp).setValue(i.getRole().getName());
                    if (i.getStore() != null) {
                        item.getItemProperty(storeProp).setValue(i.getStore().getIdstore() + ": "
                                + i.getStore().getName() + " (" + i.getStore().getAddress() + ")");
                    }
                    if (i.getRegexpiration() != null) {
                        item.getItemProperty(regExpirationProp).setValue(Utility.ConvertDate(i.getRegexpiration()));
                    }
                    if (i.getPwexpiration() != null) {
                        item.getItemProperty(passExpirationProp).setValue(Utility.ConvertDate(i.getPwexpiration()));
                    }
                    item.getItemProperty(sessionDurationProp).setValue(i.getSessionduration());
                }
            }

            table.setContainerDataSource(container);

        } catch (NoUsersException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }
    //</editor-fold>

    /**
     *
     * @param executor
     * @param users
     * @param idUser
     */
    public void openModifyUserSW(User executor, List<User> users, Integer idUser) {

        User user = null;
        for (User i : users) {
            if (i.getIduser() == idUser) {
                user = i;
                break;
            }
        }

        CreateModifyUserSW createModifyUserSW = new CreateModifyUserSW();
        createModifyUserSW.populateUserWindow(executor, user);
        MyApplication.getProject().getMainWindow().addWindow(createModifyUserSW);
    }

    /**
     *
     * @param executor
     * @param roleField
     */
    public void populateRoleField(User executor, ComboBox roleField) {
        try {
            List<Role> roleList = userOperations.getRoles(executor);

            for (Role i : roleList) {
                roleField.addItem(i.getIdrole() + ": " + i.getName());
            }
        } catch (NoRolesException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param storeField
     */
    public void populateStoreField(User executor, ComboBox storeField) {
        try {
            List<Store> storeList = storeOperations.getStoreList(executor);

            for (Store i : storeList) {
                storeField.addItem(i.getIdstore() + ": " + i.getName() + " (" + i.getAddress() + ")");
            }
        } catch (NoStoresException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param roleField
     * @param storeField
     * @param loginField
     * @param namesurnameField
     * @param emailField
     * @param regExpirationField
     * @param passExpirationField
     * @param sessionDurationField
     * @param idUser
     * @param window
     */
    public void createUpdateUser(User executor, ComboBox roleField, ComboBox storeField,
            TextField loginField, TextField namesurnameField, TextField emailField,
            PopupDateField regExpirationField, PopupDateField passExpirationField,
            TextField sessionDurationField, int idUser, Window window) {
        try {

            int idRole = -1;
            if (roleField.getValue() != null) {
                idRole = Integer.parseInt(roleField.getValue().toString().split(":")[0]);
            }

            int idStore = -1;
            if (storeField.getValue() != null) {
                idStore = Integer.parseInt(storeField.getValue().toString().split(":")[0]);
            }

            int sessionDuration = -1;
            if (sessionDurationField.getValue() != null) {
                sessionDuration = Integer.parseInt((sessionDurationField.getValue().toString()));
            }

            if (userOperations.createUpdateUser(executor, idUser, idStore, idRole,
                    loginField.getValue().toString(), namesurnameField.getValue().toString(),
                    emailField.getValue().toString(), (Date) passExpirationField.getValue(),
                    (Date) regExpirationField.getValue(), sessionDuration)) {

                MyApplication.getProject().getMainWindow().showNotification("Utente aggiunto con successo",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);

                MyApplication.getProject().getMainWindow().removeWindow(window);
                if (idUser != -1) {
                    MyApplication.getProject().getUiHandler().switchView("VisualizzaUtenti");
                }
            } else {

                MyApplication.getProject().getMainWindow().showNotification("Utente non aggiunto",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            }
        } catch (LoginNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmailNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RoleNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoStoresException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RequiredFieldEmptyException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Formato numero errato", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param idUser
     * @param window
     */
    public void removeUser(User executor, int idUser, Window window) {
        try {
            if (userOperations.removeUser(executor, idUser)) {

                MyApplication.getProject().getMainWindow().showNotification("Utente eliminato con successo",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);

                MyApplication.getProject().getMainWindow().removeWindow(window);
                MyApplication.getProject().getUiHandler().switchView("VisualizzaUtenti");
            } else {

                MyApplication.getProject().getMainWindow().showNotification("Utente non eliminato",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            }
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoUsersException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RoleNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateModifyUserSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
