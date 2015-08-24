package ui.managers;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;
import enums.Activity;
import enums.IndexesType;
import exceptions.*;
import interfaces.ProductOperations;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Index;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.User;
import ui.MyApplication;
import ui.subwindow.*;
import ui.view.IndexesView;
import ui.view.ProductView;
import utils.Utility;

/**
 *
 * @author jonny
 */
public class ProductManager {

    ProductOperations productOperations;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setProductOperations(ProductOperations productOperations) {
        this.productOperations = productOperations;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Lot">
    /**
     *
     * @param productField
     * @param executor
     */
    public void populateProductField(User executor, ComboBox productField) {
        try {
            List<Product> productList = productOperations.getProductTypes(executor);

            for (Product i : productList) {
                productField.addItem(i.getIdproduct() + ": " + i.getBrand() + " " + i.getType());
            }
        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param brandField
     * @param typeField
     * @param window
     */
    public void createProductType(User executor, TextField brandField, TextField typeField,
            Window window) {
        try {

            if (productOperations.createProductType(executor, brandField.getValue().toString(),
                    typeField.getValue().toString())) {
                MyApplication.getProject().getMainWindow().showNotification("Tipo prodotto aggiunto con successo",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            } else {
                MyApplication.getProject().getMainWindow().showNotification("Tipo prodotto non aggiunto",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            }

            MyApplication.getProject().getMainWindow().removeWindow(window);

        } catch (RequiredFieldEmptyException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProductTypeAlreadyPresentException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(CreateProductTypeSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param productField
     * @param barcodeField
     * @param quantityField
     * @param dateField
     * @param window
     */
    public void insertLot(User executor, ComboBox productField, TextField barcodeField,
            TextField quantityField, PopupDateField dateField, Window window) {
        try {

            int idProduct = -1;
            if (productField.getValue() != null) {
                idProduct = Integer.parseInt(productField.getValue().toString().split(":")[0]);
            }

            int quantity = -1;
            if (quantityField.getValue() != "") {
                quantity = Integer.parseInt(quantityField.getValue().toString().split(":")[0]);
            }

            if (productOperations.insertLot(executor, idProduct, barcodeField.getValue().toString(),
                    quantity, (Date) dateField.getValue())) {
                MyApplication.getProject().getMainWindow().showNotification("Lotto aggiunto con successo",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            } else {
                MyApplication.getProject().getMainWindow().showNotification("Lotto non aggiunto",
                        Window.Notification.TYPE_TRAY_NOTIFICATION);
            }

            MyApplication.getProject().getMainWindow().removeWindow(window);

        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExpirationDateNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RequiredFieldEmptyException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BarcodeNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Formato numero errato", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(InsertLotSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SearchProduct">
    /**
     *
     * @param executor
     * @param activity
     * @return
     */
    public void populateProductTable(User executor, Table table, Activity activity) {
        try {
            IndexedContainer container = new IndexedContainer();

            String brandProp = "Marca";
            String typeProp = "Genere";
            String barcodeProp = "Codice a barre lotto";
            String quantityProp = "Quantità";
            String expirationProp = "Data di scadenza";

            container.addContainerProperty(brandProp, String.class, null);
            container.addContainerProperty(typeProp, String.class, null);
            container.addContainerProperty(barcodeProp, String.class, null);
            container.addContainerProperty(quantityProp, Integer.class, null);
            container.addContainerProperty(expirationProp, String.class, null);

            List<Lot> lots = null;
            switch (activity) {
                case SELL:
                    lots = productOperations.getSellProducts(executor);
                    break;
                case EXPIRED:
                    lots = productOperations.getExpiredProducts(executor);
                    break;
                case RETURN:
                    lots = productOperations.getReturnProducts(executor);
                    break;
            }

            if (!lots.isEmpty()) {
                for (Lot i : lots) {
                    Item item = container.addItem(i.getBarcode());
                    item.getItemProperty(brandProp).setValue(i.getProduct().getBrand());
                    item.getItemProperty(typeProp).setValue(i.getProduct().getType());
                    item.getItemProperty(barcodeProp).setValue(i.getBarcode());
                    item.getItemProperty(quantityProp).setValue(i.getQuantity());
                    item.getItemProperty(expirationProp).setValue(Utility.ConvertDate(i.getExpiration()));
                }
            }

            table.setContainerDataSource(container);

        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param activity
     * @param viewName
     */
    public void readBarcode(Activity activity, String viewName) {
        ReadBarcodeSW readBarcodeSW = new ReadBarcodeSW(activity, viewName);
        MyApplication.getProject().getMainWindow().addWindow(readBarcodeSW);
    }

    /**
     *
     * @param executor
     * @param activity
     * @param barcode
     * @param viewName
     * @param window
     */
    public void setBarcode(User executor, Activity activity, String barcode, String viewName,
            Window window) {
        try {
            Lot lot = productOperations.getLotByBarcode(executor, barcode);

            if (window != null) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }

            InsertQuantitySW insertQuantitySW = new InsertQuantitySW(lot, activity, barcode, viewName);
            MyApplication.getProject().getMainWindow().addWindow(insertQuantitySW);

        } catch (BarcodeNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param executor
     * @param activity
     * @param quantityField
     * @param barcode
     * @param viewName
     * @param window
     */
    public void setQuantity(User executor, Activity activity, TextField quantityField,
            String barcode, String viewName, Window window) {
        try {

            String quantityString = quantityField.getValue().toString();
            int quantity = -1;
            if (!quantityString.equals("")) {
                quantity = Integer.parseInt(quantityString);
            }

            String result = "";
            switch (activity) {
                case SELL:
                    result = productOperations.sellProducts(executor, barcode, quantity);
                    break;
                case EXPIRED:
                    result = productOperations.removeExpiredProducts(executor, barcode, quantity);
                    break;
                case RETURN:
                    result = productOperations.returnProducts(executor, barcode, quantity);
                    break;
            }

            MyApplication.getProject().getMainWindow().removeWindow(window);

            MyApplication.getProject().getUiHandler().switchView(viewName);

            ResultSW resultSW = new ResultSW(result);
            MyApplication.getProject().getMainWindow().addWindow(resultSW);

        } catch (SellingExpiredProduct ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemovingNotExpiredProduct ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BarcodeNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (QuantityNotValidException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Formato numero errato", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Indexes">
    /**
     *
     * @param executor
     * @param indexesType
     * @return
     */
    public List<Index> populateIndexesTable(User executor, Table table, IndexesType indexesType) {

        List<Index> indexes = null;

        try {

            IndexedContainer container = new IndexedContainer();

            String brandProp = "Marca";
            String typeProp = "Genere";
            String returnedProp = "M_Rest.";
            String successProp = "M_Succ.";
            String expiredProp = "M_Scad.";
            String approvalProp = "M_Grad.";
            String returnedTempProp = "T_Rest.";
            String successTempProp = "T_Succ.";
            String expiredTempProp = "T_Scad.";
            String approvalTempProp = "T_Grad.";

            container.addContainerProperty(brandProp, String.class, null);
            container.addContainerProperty(typeProp, String.class, null);

            container.addContainerProperty(returnedProp, Double.class, null);
            container.addContainerProperty(successProp, Double.class, null);
            container.addContainerProperty(expiredProp, Double.class, null);
            container.addContainerProperty(approvalProp, Double.class, null);

            switch (indexesType) {
                case LOCAL:
                    container.addContainerProperty(returnedTempProp, Double.class, null);
                    container.addContainerProperty(successTempProp, Double.class, null);
                    container.addContainerProperty(expiredTempProp, Double.class, null);
                    container.addContainerProperty(approvalTempProp, Double.class, null);

                    indexes = productOperations.getLocalIndexes(executor);
                    break;
                case GLOBAL:
                    indexes = productOperations.getGlobalIndexes(executor);
                    break;
            }

            if (!indexes.isEmpty()) {
                for (int i = 0; i < indexes.size(); i++) {
                    Item item = container.addItem(i);
                    item.getItemProperty(brandProp).setValue(indexes.get(i).
                            getBrand());
                    item.getItemProperty(typeProp).setValue(indexes.get(i).
                            getType());

                    item.getItemProperty(returnedProp).setValue(Utility.Trunc(
                            indexes.get(i).getReturnedIndex()));
                    item.getItemProperty(successProp).setValue(Utility.Trunc(
                            indexes.get(i).getSuccessIndex()));
                    item.getItemProperty(expiredProp).setValue(Utility.Trunc(
                            indexes.get(i).getExpiredIndex()));
                    item.getItemProperty(approvalProp).setValue(Utility.Trunc(
                            indexes.get(i).getApprovalIndex()));

                    if (indexesType == IndexesType.LOCAL) {
                        item.getItemProperty(returnedTempProp).setValue(Utility.Trunc(
                                indexes.get(i).getReturnedTempIndex()));
                        item.getItemProperty(successTempProp).setValue(Utility.Trunc(indexes.get(i).getSuccessTempIndex()));
                        item.getItemProperty(expiredTempProp).setValue(Utility.Trunc(
                                indexes.get(i).getExpiredTempIndex()));
                        item.getItemProperty(approvalTempProp).setValue(Utility.Trunc(
                                indexes.get(i).getApprovalTempIndex()));
                    }
                }
            }

            table.setContainerDataSource(container);

        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(IndexesView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(IndexesView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(IndexesView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return indexes;
    }

    /**
     *
     * @param indexesType
     * @param productIndex
     */
    public void viewProductIndexes(List<Index> indexes, IndexesType indexesType, Integer productIndex) {
        BarChartSW indexChartSW = new BarChartSW(indexesType, indexes.get(productIndex));
        MyApplication.getProject().getMainWindow().addWindow(indexChartSW);
    }

    /**
     *
     * @param buttonIndex
     * @param buttonText
     */
    public void viewAllIndexes(List<Index> indexes, int buttonIndex, String buttonText) {
        if (indexes != null) {
            PieChartSW pieChartSW = new PieChartSW(indexes, buttonIndex, buttonText);
            MyApplication.getProject().getMainWindow().addWindow(pieChartSW);
        }
    }
    //</editor-fold>
}
