package webservices;

import exceptions.*;
import interfaces.StoreOperations;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.pojos.Log;
import model.pojos.Store;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import utils.AppContext;

/**
 * Web service controller
 *
 * @author jonny
 */
@WebService(serviceName = "StoreWS")
public class StoreWS extends SpringBeanAutowiringSupport implements StoreOperations {

    ApplicationContext ctx = AppContext.getApplicationContext();
    StoreOperations storeOperations = (StoreOperations) ctx.getBean("storeOperations");

    @Override
    @WebMethod(operationName = "getStoreList")
    public List<Store> getStoreList(@WebParam(name = "executor") User executor)
            throws NoStoresException {

        return storeOperations.getStoreList(executor);
    }

    @Override
    @WebMethod(operationName = "orderProduct")
    public int orderProduct(@WebParam(name = "executor") User executor,
            @WebParam(name = "brand") String brand,
            @WebParam(name = "type") String type,
            @WebParam(name = "quantity") int quantity)
            throws OperationNotPermittedException, NoProductException,
            RequiredFieldEmptyException {

        return storeOperations.orderProduct(executor, brand, type, quantity);
    }

    @Override
    @WebMethod(operationName = "getLogList")
    public List<Log> getLogList(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoLogException {

        return storeOperations.getLogList(executor);
    }
}
