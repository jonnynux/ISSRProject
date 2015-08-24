package interfaces.daos;

import java.util.Date;
import java.util.List;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.Store;

/**
 * Dao for lot operations
 *
 * @author jonny
 */
public interface LotDao {

    public void insertLot(Product product, String barcode, int quantity, Date expiration);

    public List<Lot> getLots(Store store);

    public Lot getLotByBarcode(Store store, String barcode);

    public int removeProducts(Lot lot, int quantity);

    public List<Lot> getLots(Lot lot);
}
