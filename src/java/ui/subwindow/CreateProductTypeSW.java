package ui.subwindow;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;

/**
 * Subwindow for cretin new product type
 *
 * @author jonny
 */
public class CreateProductTypeSW extends Window {

    private final static String TITLE = "Crea Tipo Prodotto";
    private final ProductManager productManager;

    public CreateProductTypeSW() {
        super(TITLE);

        this.setModal(true);
        this.setReadOnly(true);

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final ComboBox productField = new ComboBox("Tipi già presenti");
        productManager.populateProductField(executor, productField);

        final TextField brandField = new TextField("Inserisci marca");

        final TextField typeField = new TextField("Inserisci tipo prodotto");
        typeField.setRequired(true);

        final Window window = this;
        Button confirm = new Button("Conferma", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                productManager.createProductType(executor, brandField, typeField, window);
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
        l.addComponent(brandField);
        l.addComponent(typeField);
        l.addComponent(bl);
    }
}
