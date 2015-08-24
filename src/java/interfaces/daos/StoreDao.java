package interfaces.daos;

import java.util.List;
import model.pojos.Store;

/**
 * Dao for store operations
 *
 * @author jonny
 */
public interface StoreDao {

    public List<Store> getStoreList();

    public Store getStore(int idStore);
}
