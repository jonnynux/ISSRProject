package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.StoreManager;
import utils.AppContext;

/**
 * Subwindow for ordering products from the central whearhouse
 *
 * @author jonny
 */
public class OrderProductsSW extends Window {

    private final static String TITLE = "Ordina Prodotti";
    private final StoreManager storeManager;

    public OrderProductsSW() {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        storeManager = (StoreManager) ctx.getBean("storeManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final TextField brandField = new TextField("Inserisci marca");
        brandField.setRequired(true);

        final TextField typeField = new TextField("Inserisci tipo prodotto");
        typeField.setRequired(true);

        final TextField quantityField = new TextField("Inserisci quantità");
        quantityField.setRequired(true);

        final Window window = this;
        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                storeManager.orderProduct(executor, brandField, typeField, quantityField,
                        window);
            }
        });

        Button cancel = new Button("Annulla", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }
        });

        HorizontalLayout bl = new HorizontalLayout();
        bl.addComponent(confirm);
        bl.addComponent(cancel);

        VerticalLayout l = (VerticalLayout) this.getContent();
        l.setMargin(true);
        l.setSpacing(true);
        l.setSizeUndefined();
        l.addComponent(brandField);
        l.addComponent(typeField);
        l.addComponent(quantityField);
        l.addComponent(bl);
    }
}
