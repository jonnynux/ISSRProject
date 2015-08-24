package interfaces;

import exceptions.*;
import java.util.List;
import model.pojos.Log;
import model.pojos.Store;
import model.pojos.User;

/**
 * Interface for all the store operations
 *
 * @author jonny
 */
public interface StoreOperations {

    public List<Store> getStoreList(User executor) throws NoStoresException;

    public int orderProduct(User executor, String brand, String type, int quantity)
            throws OperationNotPermittedException, NoProductException, RequiredFieldEmptyException;

    public List<Log> getLogList(User executor) throws OperationNotPermittedException,
            NoLogException;
}
