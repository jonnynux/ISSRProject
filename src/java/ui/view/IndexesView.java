package ui.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import enums.IndexesType;
import java.util.ArrayList;
import java.util.List;
import model.Index;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import ui.MyApplication;
import ui.managers.ProductManager;
import utils.AppContext;

/**
 * View for viewing indexes of indexes of a(all) store(s)
 *
 * @author jonny
 */
public class IndexesView extends AbstractView {

    private static final String[] buttonTexts = {"Restituzione", "Successo", "Scadenza", "Gradimento"};
    private final ProductManager productManager;

    /**
     *
     * @param name Name of the channel we are creating
     */
    public IndexesView(final IndexesType indexesType) {

        ApplicationContext ctx = AppContext.getApplicationContext();
        productManager = (ProductManager) ctx.getBean("productManager");

        final User executor = MyApplication.getProject().getUiHandler().getUser();

        int numButtons = 4;
        String panelText = "";
        switch (indexesType) {
            case LOCAL:
                numButtons = 8;
                panelText = "Locali";
                break;
            case GLOBAL:
                numButtons = 4;
                panelText = "Globali";
                break;
        }

        final Table textTable = new Table();
        textTable.setCurrentPageFirstItemId(textTable.lastItemId());
        textTable.setSizeFull();
        textTable.setPageLength(0);
        textTable.setImmediate(true);
        textTable.setSelectable(true);
        final List<Index> indexes = productManager.populateIndexesTable(executor,
                textTable, indexesType);
        textTable.addListener(new Table.ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                if (textTable.getValue() != null) {
                    productManager.viewProductIndexes(indexes, indexesType,
                            (Integer) event.getProperty().getValue());
                }
            }
        });

        List<Button> buttons = new ArrayList();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponent(new Label("Master:"));

        for (int i = 0; i < numButtons; i++) {

            if (i == 4) {
                horizontalLayout.addComponent(new Label("\tTemporali:"));
            }

            final int buttonIndex = i;
            final String buttonText = buttonTexts[i % 4];
            final String subWindowText = buttonText + " " + panelText;
            Button button = new Button(buttonText, new Button.ClickListener() {

                @Override
                public void buttonClick(ClickEvent event) {

                    productManager.viewAllIndexes(indexes, buttonIndex, subWindowText);
                }
            });

            buttons.add(button);
            horizontalLayout.addComponent(button);
        }

        VerticalLayout internalLayout = new VerticalLayout();
        internalLayout.setHeight("100%");
        internalLayout.setWidth("100%");
        internalLayout.setSpacing(true);
        internalLayout.setMargin(true);

        internalLayout.addComponent(textTable);
        internalLayout.addComponent(horizontalLayout);
        internalLayout.setExpandRatio(textTable, 1);

        Panel panel = new Panel("Visualizzazione Indici " + panelText);
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
