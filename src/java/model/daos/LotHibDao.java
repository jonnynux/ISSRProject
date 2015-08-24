package model.daos;

import interfaces.daos.LotDao;
import java.util.Date;
import java.util.List;
import model.pojos.Lot;
import model.pojos.Product;
import model.pojos.Store;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate dao for lot operations
 *
 * @author jonny
 */
public class LotHibDao extends HibernateDaoSupport implements LotDao {

    @Override
    @Transactional
    public void insertLot(Product product, String barcode, int quantity, Date expiration) {
        Lot lot = new Lot();
        lot.setProduct(product);
        lot.setBarcode(barcode);
        lot.setQuantity(quantity);
        lot.setExpiration(expiration);

        getHibernateTemplate().saveOrUpdate(lot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lot> getLots(Store store) {

        return getHibernateTemplate().find("from Lot as l where "
                + "l.product.store.idstore=" + store.getIdstore()
                + " order by l.product.brand, l.product.type");
    }

    @Override
    @Transactional(readOnly = true)
    public Lot getLotByBarcode(Store store, String barcode) {
        List<Lot> lots = getHibernateTemplate().find("from Lot as l where l.barcode='"
                + barcode + "' and l.product.store.idstore=" + store.getIdstore());

        return lots.isEmpty()
                ? null
                : lots.get(0);
    }

    @Override
    @Transactional
    public int removeProducts(Lot lot, int quantity) {

        int toRemove = (quantity > lot.getQuantity()) ? lot.getQuantity() : quantity;

        lot.setQuantity(lot.getQuantity() - toRemove);

        getHibernateTemplate().saveOrUpdate(lot);

        return toRemove;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lot> getLots(Lot lot) {

        return getHibernateTemplate().find("from Lot as l where l.product="
                + lot.getProduct().getIdproduct() + " and idlot!="
                + lot.getIdlot());
    }
}
