package controllers;

import enums.Roles;
import exceptions.*;
import interfaces.StoreOperations;
import interfaces.daos.LogDao;
import interfaces.daos.StoreDao;
import java.util.List;
import legacy.LegacyMagaz;
import model.pojos.Log;
import model.pojos.Store;
import model.pojos.User;

/**
 * Controller for all store operations
 *
 * @author jonny
 */
public class StoreController implements StoreOperations {

    LogDao logDao;
    StoreDao storeDao;
    LegacyMagaz legacyMagaz;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setLogDao(LogDao logDao) {
        this.logDao = logDao;
    }

    public void setStoreDao(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public void setLegacyMagaz(LegacyMagaz legacyMagaz) {
        this.legacyMagaz = legacyMagaz;
    }
    //</editor-fold>

    @Override
    public List<Store> getStoreList(User executor) throws NoStoresException {

        List<Store> stores = storeDao.getStoreList();

        if (!stores.isEmpty()) {
            return stores;
        } else {
            throw new NoStoresException();
        }
    }

    @Override
    public int orderProduct(User executor, String brand, String type, int quantity)
            throws OperationNotPermittedException, RequiredFieldEmptyException, NoProductException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (brand.equals("") || type.equals("") || quantity <= 0) {
            throw new RequiredFieldEmptyException();
        }

        int obtained = legacyMagaz.orderProduct(brand, type, quantity);

        if (obtained > 0) {
            return obtained;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public List<Log> getLogList(User executor) throws OperationNotPermittedException, NoLogException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        if (executor.getRole().isGeneraladministrator()) {
            store = null;
        }

        List<Log> logs = logDao.getLogList(store);

        if (!logs.isEmpty()) {
            return logs;
        } else {
            throw new NoLogException();
        }
    }
}
