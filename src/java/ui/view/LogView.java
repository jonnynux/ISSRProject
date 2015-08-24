package ui.view;

import com.vaadin.data.Item;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.VerticalLayout;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.StoreManager;
import utils.AppContext;

/**
 * View for viewing log of a(all) store(s)
 *
 * @author jonny
 */
public class LogView extends AbstractView {

    Table textTable;
    String resultProp = "Esito";
    private final StoreManager storeManager;

    /**
     *
     * @param name Name of the channel we are creating
     */
    public LogView() {

        ApplicationContext ctx = AppContext.getApplicationContext();
        storeManager = (StoreManager) ctx.getBean("storeManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        textTable = new Table();
        textTable.setCurrentPageFirstItemId(textTable.lastItemId());
        textTable.setSizeFull();
        textTable.setPageLength(0);
        textTable.setImmediate(true);
        textTable.setSelectable(true);
        textTable.setCellStyleGenerator(new ColorCellStyleGenerator());
        storeManager.populateLogTable(executor, textTable, resultProp);

        VerticalLayout internalLayout = new VerticalLayout();
        internalLayout.setHeight("100%");
        internalLayout.setWidth("100%");
        internalLayout.setSpacing(true);
        internalLayout.setMargin(true);

        internalLayout.addComponent(textTable);
        internalLayout.setExpandRatio(textTable, 1);

        Panel panel = new Panel("Visualizzazione Log");
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

    /**
     *
     */
    private class ColorCellStyleGenerator implements CellStyleGenerator {

        @Override
        public String getStyle(Object itemId, Object propertyId) {
            if (propertyId == null) {
                Item item = textTable.getItem(itemId);
                Boolean positive = (Boolean) item.getItemProperty(resultProp).getValue();
                if (!positive) {
                    return "inactive";
                }
            }
            return null;
        }
    }
}
