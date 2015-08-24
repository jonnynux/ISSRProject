package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;

/**
 * Subwindow for inserting new lots
 *
 * @author jonny
 */
public class InsertLotSW extends Window {

    private final static String TITLE = "Inserisci Lotto";
    private final ProductManager productManager;

    public InsertLotSW() {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final ComboBox productField = new ComboBox("Seleziona Prodotto");
        productField.setRequired(true);
        productManager.populateProductField(executor, productField);

        final TextField barcodeField = new TextField("Inserisci codice a barre");
        barcodeField.setRequired(true);

        final TextField quantityField = new TextField("Inserisci quantità");
        quantityField.setRequired(true);

        final PopupDateField dateField = new PopupDateField("Seleziona data di scadenza");
        dateField.setValue(new java.util.Date());
        dateField.setResolution(PopupDateField.RESOLUTION_DAY);
        dateField.setRequired(true);

        final Window window = this;

        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                productManager.insertLot(executor, productField, barcodeField,
                        quantityField, dateField, window);
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
        l.addComponent(productField);
        l.addComponent(barcodeField);
        l.addComponent(quantityField);
        l.addComponent(dateField);
        l.addComponent(bl);
    }
}
