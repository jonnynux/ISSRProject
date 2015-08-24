package ui.managers;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import exceptions.NoLogException;
import exceptions.NoProductException;
import exceptions.OperationNotPermittedException;
import exceptions.RequiredFieldEmptyException;
import interfaces.StoreOperations;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pojos.Log;
import model.pojos.User;
import ui.MyApplication;
import ui.subwindow.OrderProductsSW;
import ui.subwindow.ResultSW;
import ui.view.LogView;
import utils.Utility;

/**
 *
 * @author jonny
 */
public class StoreManager {

    StoreOperations storeOperations;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setStoreOperations(StoreOperations storeOperations) {
        this.storeOperations = storeOperations;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OrderProduct">
    /**
     *
     * @param executor
     * @param brandField
     * @param typeField
     * @param quantityField
     * @param window
     */
    public void orderProduct(User executor, TextField brandField, TextField typeField,
            TextField quantityField, Window window) {
        try {

            int quantity = -1;
            if (quantityField.getValue() != "") {
                quantity = Integer.parseInt(quantityField.getValue().toString());
            }

            int obtained = storeOperations.orderProduct(executor, brandField.getValue().toString(),
                    typeField.getValue().toString(), quantity);

            String result = "Ordinato " + obtained + " (" + quantity
                    + ") " + typeField.getValue().toString() + " " + brandField.getValue().toString();

            MyApplication.getProject().getMainWindow().removeWindow(window);

            ResultSW resultSW = new ResultSW(result);
            MyApplication.getProject().getMainWindow().addWindow(resultSW);


        } catch (RequiredFieldEmptyException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(OrderProductsSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(OrderProductsSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoProductException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(OrderProductsSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification("Formato numero errato", Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(OrderProductsSW.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(OrderProductsSW.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    public void populateLogTable(User executor, Table table, String resultProp) {

        try {
            IndexedContainer container = new IndexedContainer();
            String idLogProp = "ID";
            String timeProp = "Istante";
            String executorProp = "Esecutore";
            String activityProp = "Attività";


            container.addContainerProperty(idLogProp, Integer.class, null);
            container.addContainerProperty(timeProp, String.class, null);
            container.addContainerProperty(executorProp, String.class, null);
            container.addContainerProperty(activityProp, String.class, null);
            container.addContainerProperty(resultProp, Boolean.class, null);

            List<Log> logs = storeOperations.getLogList(executor);
            if (!logs.isEmpty()) {
                for (Log i : logs) {
                    Item item = container.addItem(i.getIdlog());
                    item.getItemProperty(idLogProp).setValue(i.getIdlog());
                    item.getItemProperty(timeProp).setValue(Utility.ConvertDateTime(i.getInstanttime()));
                    item.getItemProperty(executorProp).setValue(i.getExecutor());
                    item.getItemProperty(activityProp).setValue(i.getActivity());
                    item.getItemProperty(resultProp).setValue(i.isResult());
                }
            }

            table.setContainerDataSource(container);

        } catch (OperationNotPermittedException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LogView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoLogException ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LogView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            MyApplication.getProject().getUiHandler().getApplication().getMainWindow().
                    showNotification(ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            Logger.getLogger(LogView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
