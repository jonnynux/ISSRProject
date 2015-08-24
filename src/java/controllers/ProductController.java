package controllers;

import enums.Roles;
import exceptions.*;
import interfaces.ProductOperations;
import interfaces.daos.LotDao;
import interfaces.daos.ProductDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Index;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.Store;
import model.pojos.User;
import utils.Utility;

/**
 * Controller for all product operations
 *
 * @author jonny
 */
public class ProductController implements ProductOperations {

    LotDao lotDao;
    ProductDao productDao;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setLotDao(LotDao lotDao) {
        this.lotDao = lotDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
    //</editor-fold>

    @Override
    public boolean createProductType(User executor, String brand, String type)
            throws OperationNotPermittedException, ProductTypeAlreadyPresentException,
            RequiredFieldEmptyException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (type.equals("")) {
            throw new RequiredFieldEmptyException();
        }

        Store store = executor.getStore();

        if (!productDao.isTypePresent(store, brand, type)) {
            productDao.createProductType(store, brand, type);
            productDao.resetTemporaryIndexes(store);
            return true;
        } else {
            throw new ProductTypeAlreadyPresentException();
        }
    }

    @Override
    public List<Product> getProductTypes(User executor) throws OperationNotPermittedException,
            NoProductException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Magazziniere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        List<Product> products = productDao.getProducts(store);
        if (!products.isEmpty()) {
            return products;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public boolean insertLot(User executor, int idProduct, String barcode, int quantity, Date expiration)
            throws OperationNotPermittedException, RequiredFieldEmptyException,
            BarcodeNotValidException, ExpirationDateNotValidException, NoProductException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Magazziniere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        if (!(idProduct > 0) || barcode.equals("") || !(quantity > 0) || (expiration == null)) {
            throw new RequiredFieldEmptyException();
        }

        Lot alreadyPresent = lotDao.getLotByBarcode(store, barcode);

        if (!Utility.BarcodeIsValid(barcode) || alreadyPresent != null) {
            throw new BarcodeNotValidException();
        }

        Date today = new Date();
        if (!expiration.after(today)) {
            throw new ExpirationDateNotValidException();
        }

        Product product = productDao.getProduct(idProduct);
        if (product == null) {
            throw new NoProductException();
        }

        lotDao.insertLot(product, barcode, quantity, expiration);
        productDao.updateStoredIndexes(product, quantity);

        return true;
    }

    @Override
    public List<Lot> getSellProducts(User executor) throws OperationNotPermittedException,
            NoProductException {
        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Cassiere.toString())
                || roleName.equals(Roles.Visitatore.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        List<Lot> from = lotDao.getLots(store);
        List<Lot> to = new ArrayList();

        Date today = new Date();
        for (Lot i : from) {
            if (i.getExpiration().after(today) && i.getQuantity() > 0) {
                to.add(i);
            }
        }

        if (!to.isEmpty()) {
            return to;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public List<Lot> getExpiredProducts(User executor) throws OperationNotPermittedException,
            NoProductException {
        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Magazziniere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        List<Lot> from = lotDao.getLots(store);
        List<Lot> to = new ArrayList();

        Date today = new Date();
        for (Lot i : from) {
            if (!i.getExpiration().after(today) && i.getQuantity() > 0) {
                to.add(i);
            }
        }

        if (!to.isEmpty()) {
            return to;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public List<Lot> getReturnProducts(User executor) throws OperationNotPermittedException,
            NoProductException {
        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Addetto_Reclami.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        List<Lot> lots = lotDao.getLots(store);

        if (!lots.isEmpty()) {
            return lots;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public Lot getLotByBarcode(User executor, String barcode) throws OperationNotPermittedException,
            NoProductException, BarcodeNotValidException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Addetto_Reclami.toString())
                || roleName.equals(Roles.Magazziniere.toString())
                || roleName.equals(Roles.Cassiere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (!Utility.BarcodeIsValid(barcode)) {
            throw new BarcodeNotValidException();
        }

        Store store = executor.getStore();

        Lot lot = lotDao.getLotByBarcode(store, barcode);

        if (lot != null) {
            return lot;
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public String sellProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, SellingExpiredProduct {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Cassiere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (!Utility.BarcodeIsValid(barcode)) {
            throw new BarcodeNotValidException();
        }

        if (quantity <= 0) {
            throw new QuantityNotValidException();
        }

        Store store = executor.getStore();

        Lot lot = lotDao.getLotByBarcode(store, barcode);

        if (lot == null) {
            throw new NoProductException();
        }

        Date today = new Date();
        if (!lot.getExpiration().after(today)) {
            throw new SellingExpiredProduct();
        }

        List<Lot> lotsSold = new ArrayList();
        lotsSold.add(lot);
        if (lot.getQuantity() < quantity) {
            lotsSold.addAll(lotDao.getLots(lot));
        }


        for (Lot i : lotsSold) {
            if (!i.getExpiration().after(today)) {
            }
        }

        String result = "";
        int realQuantity = 0;
        int remainingQuantity = quantity;
        for (Lot i : lotsSold) {

            int quantityRemoved = lotDao.removeProducts(i, remainingQuantity);
            remainingQuantity -= quantityRemoved;
            realQuantity += quantityRemoved;

            result += "Prelevato: " + quantityRemoved + " da lotto " + i.getBarcode() + "\n";

            if (remainingQuantity <= 0) {
                break;
            }
        }

        result += "Totale prelevato: " + realQuantity + " (" + quantity + ") "
                + lot.getProduct().getType() + " " + lot.getProduct().getBrand();

        Product product = lot.getProduct();

        productDao.updateRequestedIndexes(product, quantity);
        productDao.updateSoldIndexes(product, realQuantity);

        return result;
    }

    @Override
    public String removeExpiredProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, RemovingNotExpiredProduct {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Magazziniere.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (!Utility.BarcodeIsValid(barcode)) {
            throw new BarcodeNotValidException();
        }

        if (quantity <= 0) {
            throw new QuantityNotValidException();
        }

        Store store = executor.getStore();

        Lot lot = lotDao.getLotByBarcode(store, barcode);

        if (lot == null) {
            throw new NoProductException();
        }

        Date today = new Date();
        if (lot.getExpiration().after(today)) {
            throw new RemovingNotExpiredProduct();
        }

        Product product = lot.getProduct();

        int realQuantity = lotDao.removeProducts(lot, quantity);
        productDao.updateExpiredIndexes(product, realQuantity);

        return "Rimosso scaduto: " + realQuantity + " (" + quantity + ") "
                + lot.getProduct().getType() + " " + lot.getProduct().getBrand();
    }

    @Override
    public String returnProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Addetto_Reclami.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (!Utility.BarcodeIsValid(barcode)) {
            throw new BarcodeNotValidException();
        }

        if (quantity <= 0) {
            throw new QuantityNotValidException();
        }

        Store store = executor.getStore();

        Lot lot = lotDao.getLotByBarcode(store, barcode);

        if (lot == null) {
            throw new NoProductException();
        }

        Product product = lot.getProduct();
        productDao.updateReturnedIndexes(product, quantity);

        return "Restituito: " + quantity + " " + lot.getProduct().getType() + " "
                + lot.getProduct().getBrand();
    }

    @Override
    public List<Index> getLocalIndexes(User executor) throws OperationNotPermittedException,
            NoProductException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Analista_Indici.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        List<Product> products = productDao.getProducts(store);

        if (!products.isEmpty()) {
            return Utility.CalculateLocalIndexes(products, false);
        } else {
            throw new NoProductException();
        }
    }

    @Override
    public List<Index> getGlobalIndexes(User executor) throws OperationNotPermittedException,
            NoProductException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        List<Product> products = productDao.getAllProducts();

        if (!products.isEmpty()) {
            return Utility.CalculateGlobalIndexes(products);
        } else {
            throw new NoProductException();
        }
    }
}
