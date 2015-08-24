package interfaces;

import exceptions.*;
import java.util.Date;
import java.util.List;
import model.Index;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.User;

/**
 * Interface for all the store operations
 *
 * @author jonny
 */
public interface ProductOperations {

    public boolean createProductType(User executor, String brand, String type)
            throws OperationNotPermittedException, ProductTypeAlreadyPresentException,
            RequiredFieldEmptyException;

    public List<Product> getProductTypes(User executor) throws OperationNotPermittedException,
            NoProductException;

    public boolean insertLot(User executor, int idProduct, String barcode, int quantity,
            Date expiration) throws OperationNotPermittedException, RequiredFieldEmptyException,
            BarcodeNotValidException, ExpirationDateNotValidException, NoProductException;

    public List<Lot> getSellProducts(User executor) throws OperationNotPermittedException,
            NoProductException;

    public List<Lot> getExpiredProducts(User executor) throws OperationNotPermittedException,
            NoProductException;

    public List<Lot> getReturnProducts(User executor) throws OperationNotPermittedException,
            NoProductException;

    public Lot getLotByBarcode(User executor, String barcode) throws OperationNotPermittedException,
            NoProductException, BarcodeNotValidException;

    public String sellProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, SellingExpiredProduct;

    public String removeExpiredProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, RemovingNotExpiredProduct;

    public String returnProducts(User executor, String barcode, int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException;

    public List<Index> getLocalIndexes(User executor) throws OperationNotPermittedException,
            NoProductException;

    public List<Index> getGlobalIndexes(User executor) throws OperationNotPermittedException,
            NoProductException;
}
