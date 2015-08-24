package webservices;

import exceptions.*;
import interfaces.ProductOperations;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.Index;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import utils.AppContext;

/**
 * Web service controller
 *
 * @author jonny
 */
@WebService(serviceName = "ProductWS")
public class ProductWS extends SpringBeanAutowiringSupport implements ProductOperations {

    ApplicationContext ctx = AppContext.getApplicationContext();
    ProductOperations productOperations = (ProductOperations) ctx.getBean("productOperations");

    @Override
    @WebMethod(operationName = "createProductType")
    public boolean createProductType(@WebParam(name = "executor") User executor,
            @WebParam(name = "brand") String brand,
            @WebParam(name = "type") String type)
            throws OperationNotPermittedException, ProductTypeAlreadyPresentException,
            RequiredFieldEmptyException {

        return productOperations.createProductType(executor, brand, type);
    }

    @Override
    @WebMethod(operationName = "getProductTypes")
    public List<Product> getProductTypes(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getProductTypes(executor);
    }

    @Override
    @WebMethod(operationName = "insertLot")
    public boolean insertLot(@WebParam(name = "executor") User executor,
            @WebParam(name = "idProduct") int idProduct,
            @WebParam(name = "barcode") String barcode,
            @WebParam(name = "quantity") int quantity,
            @WebParam(name = "expiration") Date expiration)
            throws OperationNotPermittedException, RequiredFieldEmptyException,
            BarcodeNotValidException, ExpirationDateNotValidException,
            NoProductException {

        return productOperations.insertLot(executor, idProduct, barcode, quantity, expiration);
    }

    @Override
    @WebMethod(operationName = "getSellProducts")
    public List<Lot> getSellProducts(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getSellProducts(executor);
    }

    @Override
    @WebMethod(operationName = "getExpiredProducts")
    public List<Lot> getExpiredProducts(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getExpiredProducts(executor);
    }

    @Override
    @WebMethod(operationName = "getReturnProducts")
    public List<Lot> getReturnProducts(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getReturnProducts(executor);
    }

    @Override
    @WebMethod(operationName = "getLotByBarcode")
    public Lot getLotByBarcode(@WebParam(name = "executor") User executor,
            @WebParam(name = "barcode") String barcode)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException {

        return productOperations.getLotByBarcode(executor, barcode);
    }

    @Override
    @WebMethod(operationName = "sellProducts")
    public String sellProducts(@WebParam(name = "executor") User executor,
            @WebParam(name = "barcode") String barcode,
            @WebParam(name = "quantity") int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, SellingExpiredProduct {

        return productOperations.sellProducts(executor, barcode, quantity);
    }

    @Override
    @WebMethod(operationName = "removeExpiredProducts")
    public String removeExpiredProducts(@WebParam(name = "executor") User executor,
            @WebParam(name = "barcode") String barcode,
            @WebParam(name = "quantity") int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException, RemovingNotExpiredProduct {

        return productOperations.removeExpiredProducts(executor, barcode, quantity);
    }

    @Override
    @WebMethod(operationName = "returnProducts")
    public String returnProducts(@WebParam(name = "executor") User executor,
            @WebParam(name = "barcode") String barcode,
            @WebParam(name = "quantity") int quantity)
            throws OperationNotPermittedException, NoProductException,
            BarcodeNotValidException, QuantityNotValidException {

        return productOperations.returnProducts(executor, barcode, quantity);
    }

    @Override
    @WebMethod(operationName = "getLocalIndexes")
    public List<Index> getLocalIndexes(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getLocalIndexes(executor);
    }

    @Override
    @WebMethod(operationName = "getGlobalIndexes")
    public List<Index> getGlobalIndexes(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoProductException {

        return productOperations.getGlobalIndexes(executor);
    }
}
