package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import enums.Activity;
import model.pojos.Lot;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;
import utils.Utility;

/**
 * Subwindow for inserting quantity about selling/returning/removing expired
 * product
 *
 * @author jonny
 */
public class InsertQuantitySW extends Window {

    private final static String TITLE = "Inserimento quantità";
    private final ProductManager productManager;

    public InsertQuantitySW(final Lot lot, final Activity activity, final String barcode,
            final String viewName) {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final Label brandField = new Label("Marca: " + lot.getProduct().getBrand());
        final Label typeField = new Label("Genere: " + lot.getProduct().getType());
        final Label barcodeField = new Label("Codice a barre: " + lot.getBarcode());
        final Label expirationField = new Label("Scadenza: " + Utility.ConvertDate(lot.getExpiration()));

        final TextField quantityField = new TextField("Inserisci quantità");
        quantityField.setValue(lot.getQuantity());
        quantityField.setRequired(true);

        final Window window = this;

        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                productManager.setQuantity(executor, activity, quantityField,
                        barcode, viewName, window);
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
        l.addComponent(barcodeField);
        l.addComponent(expirationField);
        l.addComponent(quantityField);
        l.addComponent(bl);
    }

    public InsertQuantitySW(Lot lot) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
