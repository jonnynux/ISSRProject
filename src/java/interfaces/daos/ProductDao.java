package interfaces.daos;

import java.util.List;
import model.pojos.Product;
import model.pojos.Store;

/**
 * Dao for product operations
 *
 * @author jonny
 */
public interface ProductDao {

    public boolean isTypePresent(Store store, String brand, String type);

    public void createProductType(Store store, String brand, String type);

    public void resetTemporaryIndexes(Store store);

    public List<Product> getProducts(Store store);

    public List<Product> getAllProducts();

    public Product getProduct(int idProduct);

    public void updateStoredIndexes(Product product, int quantity);

    public void updateRequestedIndexes(Product product, int quantity);

    public void updateSoldIndexes(Product product, int realQuantity);

    public void updateExpiredIndexes(Product product, int quantity);

    public void updateReturnedIndexes(Product product, int quantity);
}
