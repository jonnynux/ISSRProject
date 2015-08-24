package ui.component;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import ui.MyApplication;

/**
 * Menu (left region) of a web page
 *
 * @author jonny
 */
public final class Menu extends VerticalLayout implements ValueChangeListener {

    private Tree menuTree;
    private Panel panel;

    public Menu() {

        // Visual modifiers of the menu.
        setHeight("100%");
        setMargin(true);

        // Add a tree.
        menuTree = new Tree();
        menuTree.setMultiSelect(false);

        // Instantly receive value update events.
        menuTree.setImmediate(true);

        // Add objects to the tree.
        populate(menuTree);

        panel = new Panel("Menù");
        panel.addComponent(menuTree);
        panel.setHeight("100%");

        addComponent(panel);

        menuTree.addListener((ValueChangeListener) this);
    }

    /**
     * Populates the tree with hard coded data.
     *
     * @param anyTree the tree we want
     */
    public void populate(Tree anyTree) {

        final Object[][] links = new Object[][]{
            new Object[]{"Pagina Principale"},
            new Object[]{"Gestione Utenti", "Crea Nuovo Utente", "Visualizza Utenti"},
            new Object[]{"Gestione Prodotti", "Crea Tipo Prodotto", "Elimina Tipo Prodotto",
                "Inserisci Lotto", "Vendi Prodotti", "Elimina Prodotti Scaduti", "Restituisci Prodotti"},
            new Object[]{"Visualizza Indici", "Indici Locali", "Indici Master"},
            new Object[]{"Gestione Punto Vendita", "Ordina Prodotti", "Crea Punto Vendita",
                "Visualizza Punti Vendita", "Visualizza Log"}
        };

        for (int i = 0; i < links.length; i++) {
            String temp = (String) (links[i][0]);
            anyTree.addItem(temp);

            if (links[i].length == 1) {
                anyTree.setChildrenAllowed(temp, false);
            } else {
                for (int j = 1; j < links[i].length; j++) {
                    String temp1 = (String) (links[i][j]);
                    anyTree.addItem(temp1);
                    anyTree.setParent(temp1, temp);
                    anyTree.setChildrenAllowed(temp1, false);
                }
                // Expand the subtree.
                anyTree.expandItemsRecursively(temp);
            }
        }
    }

    /**
     * Removes an item from menuTree.
     */
    public void removeCurrentTreeSelection() {

        if (!menuTree.isRoot(menuTree.getValue())) {
            Object id = menuTree.getParent(menuTree.getValue());
            Object id2 = menuTree.getValue();
            menuTree.select(id);
            menuTree.removeItem(id2);
        }
    }

    /**
     *
     * @return
     */
    public Tree getTree() {
        return menuTree;
    }

    /**
     *
     * @param event
     */
    @Override
    public void valueChange(ValueChangeEvent event) {

        if (menuTree.getValue() != null) {
            MyApplication.getProject().getUiHandler().switchView(menuTree.getValue().toString());
        }
    }
}
