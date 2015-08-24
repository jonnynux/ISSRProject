package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.UserManager;
import utils.AppContext;
import utils.XMLGCC;

/**
 * Subwindow for creating/modifing a user
 *
 * @author jonny
 */
public class CreateModifyUserSW extends Window {

    int idUser = -1;
    final TextField loginField;
    final TextField namesurnameField;
    final TextField emailField;
    final TextField sessionDurationField;
    final PopupDateField regExpirationField;
    final PopupDateField passExpirationField;
    final ComboBox roleField;
    final ComboBox storeField;
    HorizontalLayout bl;
    private final static String TITLE = "Crea Nuovo Utente";
    final UserManager userManager;

    public CreateModifyUserSW() {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        userManager = (UserManager) ctx.getBean("userManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        loginField = new TextField("Inserisci username");
        loginField.setRequired(true);

        namesurnameField = new TextField("Inserisci nome e cognome");

        emailField = new TextField("Inserisci email");
        emailField.setRequired(true);

        roleField = new ComboBox("Seleziona ruolo");
        roleField.setRequired(true);
        userManager.populateRoleField(executor, roleField);

        storeField = new ComboBox("Seleziona punto vendita");
        userManager.populateStoreField(executor, storeField);

        regExpirationField = new PopupDateField("Seleziona scadenza registrazione");
        regExpirationField.setValue(new java.util.Date());
        regExpirationField.setResolution(PopupDateField.RESOLUTION_DAY);

        passExpirationField = new PopupDateField("Seleziona scadenza password");
        passExpirationField.setValue(new java.util.Date());
        passExpirationField.setResolution(PopupDateField.RESOLUTION_DAY);

        sessionDurationField = new TextField("Inserisci durata sessione");

        final Window window = this;

        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                userManager.createUpdateUser(executor, roleField, storeField,
                        loginField, namesurnameField, emailField, regExpirationField,
                        passExpirationField, sessionDurationField, idUser, window);
            }
        });

        Button cancel = new Button("Annulla", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }
        });

        bl = new HorizontalLayout();
        bl.addComponent(confirm);
        bl.addComponent(cancel);

        VerticalLayout l = (VerticalLayout) this.getContent();
        l.setMargin(true);
        l.setSpacing(true);
        l.setSizeUndefined();

        l.addComponent(loginField);
        l.addComponent(namesurnameField);
        l.addComponent(emailField);
        l.addComponent(roleField);
        l.addComponent(storeField);
        l.addComponent(regExpirationField);
        l.addComponent(passExpirationField);
        l.addComponent(sessionDurationField);

        l.addComponent(bl);
    }

    public void populateUserWindow(final User executor, User user) {
        super.setCaption("Modifica Utente");

        idUser = user.getIduser();
        loginField.setValue(user.getLogin());
        namesurnameField.setValue(user.getNamesurname());
        emailField.setValue(user.getEmail());
        roleField.setValue(user.getRole().getIdrole() + ": " + user.getRole().getName());
        if (user.getStore() != null) {
            storeField.setValue(user.getStore().getIdstore() + ": " + user.getStore().getName()
                    + " (" + user.getStore().getAddress() + ")");
        }
        regExpirationField.setValue(XMLGCC.convertDate(user.getRegexpiration()));
        passExpirationField.setValue(XMLGCC.convertDate(user.getPwexpiration()));
        sessionDurationField.setValue(user.getSessionduration());

        final Window window = this;

        Button remove = new Button("Elimina", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                userManager.removeUser(executor, idUser, window);
            }
        });

        bl.addComponent(remove);
    }
}
