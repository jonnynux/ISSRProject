package ui.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import enums.Activity;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;

/**
 * View for viewing products to sell/return/remove expired
 *
 * @author jonny
 */
public class ProductView extends AbstractView {

    private final ProductManager productManager;

    /**
     *
     * @param name Name of the channel we are creating
     */
    public ProductView(final Activity activity, final String viewName) {

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        final Table textTable = new Table();
        textTable.setCurrentPageFirstItemId(textTable.lastItemId());
        textTable.setSizeFull();
        textTable.setPageLength(0);
        textTable.setImmediate(true);
        textTable.setSelectable(true);

        textTable.addListener(new Table.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (textTable.getValue() != null) {
                    productManager.setBarcode(executor, activity, event.getProperty().getValue().toString(),
                            viewName, null);
                }
            }
        });
        productManager.populateProductTable(executor, textTable, activity);

        final Button readBarcode = new Button("Leggi codice a barre", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                productManager.readBarcode(activity, viewName);
            }
        });

        VerticalLayout internalLayout = new VerticalLayout();
        internalLayout.setHeight("100%");
        internalLayout.setWidth("100%");
        internalLayout.setSpacing(true);
        internalLayout.setMargin(true);

        internalLayout.addComponent(textTable);
        internalLayout.addComponent(readBarcode);

        internalLayout.setComponentAlignment(readBarcode, Alignment.BOTTOM_LEFT);
        internalLayout.setExpandRatio(textTable, 1);

        String panelText = "";
        switch (activity) {
            case SELL:
                panelText = "Vendi Prodotti";
                break;
            case EXPIRED:
                panelText = "Elimina Prodotti Scaduti";
                break;
            case RETURN:
                panelText = "Restituisci Prodotti";
                break;
        }

        Panel panel = new Panel(panelText);
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
