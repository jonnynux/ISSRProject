package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import enums.Activity;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;

/**
 * Subwindow for reading barcodes for selling/returning/removing expired product
 *
 * @author jonny
 */
public class ReadBarcodeSW extends Window {

    private final static String TITLE = "Lettura codice a barre";
    private final ProductManager productManager;

    public ReadBarcodeSW(final Activity activity, final String viewName) {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final TextField barcodeField = new TextField("Inserisci codice a barre");
        barcodeField.setRequired(true);

        final Window window = this;

        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                productManager.setBarcode(executor, activity, barcodeField.getValue().toString(),
                        viewName, window);
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
        l.addComponent(barcodeField);
        l.addComponent(bl);
    }
}
