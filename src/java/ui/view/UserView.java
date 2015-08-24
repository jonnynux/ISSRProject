package ui.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.UserManager;
import utils.AppContext;

/**
 * View for viewing users of a(all) store(s)
 *
 * @author jonny
 */
public class UserView extends AbstractView {

    private final UserManager userManager;

    /**
     *
     * @param name Name of the channel we are creating
     */
    public UserView() {

        ApplicationContext ctx = AppContext.getApplicationContext();
        userManager = (UserManager) ctx.getBean("userManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final Table textTable = new Table();
        textTable.setCurrentPageFirstItemId(textTable.lastItemId());
        textTable.setSizeFull();
        textTable.setPageLength(0);
        textTable.setImmediate(true);
        textTable.setSelectable(true);
        final List<User> users = userManager.populateUserTable(executor, textTable);
        textTable.addListener(new Table.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (textTable.getValue() != null) {
                    userManager.openModifyUserSW(executor, users, (Integer) event.getProperty().getValue());
                }
            }
        });


        VerticalLayout internalLayout = new VerticalLayout();
        internalLayout.setHeight("100%");
        internalLayout.setWidth("100%");
        internalLayout.setSpacing(true);
        internalLayout.setMargin(true);
        internalLayout.addComponent(textTable);
        internalLayout.setExpandRatio(textTable, 1);

        Panel panel = new Panel("Visualizza Utenti");
        panel.setHeight("100%");
        panel.setWidth("100%");
        panel.setSizeFull();
        panel.setContent(internalLayout);

        VerticalLayout externalLayout = new VerticalLayout();
        externalLayout.setHeight("100%");
        externalLayout.setWidth("100%");
        externalLayout.setSpacing(true);
        externalLayout.setMargin(true);
        externalLayout.addComponent(panel);

        setCompositionRoot(externalLayout);

        setHeight("100%");
    }
}
