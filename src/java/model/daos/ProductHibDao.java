package model.daos;

import interfaces.daos.ProductDao;
import java.util.List;
import model.pojos.Product;
import model.pojos.Store;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate dao for Product operations
 *
 * @author jonny
 */
public class ProductHibDao extends HibernateDaoSupport implements ProductDao {

    @Override
    @Transactional(readOnly = true)
    public boolean isTypePresent(Store store, String brand, String type) {

        List<Product> products = getHibernateTemplate().
                find("from Product as p where p.brand='" + brand + "' and p.type='"
                + type + "' and p.store.idstore=" + store.getIdstore());

        return products.isEmpty()
                ? false
                : true;
    }

    @Override
    @Transactional
    public void createProductType(Store store, String brand, String type) {

        Product product = new Product();
        product.setStore(store);
        product.setBrand(brand);
        product.setType(type);

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    @Transactional
    public void resetTemporaryIndexes(Store store) {

        List<Product> products = getHibernateTemplate().find("from Product as p "
                + "where p.store.idstore=" + store.getIdstore());

        for (Product p : products) {

            p.setTempstored(0);
            p.setTemprequested(0);
            p.setTempsold(0);
            p.setTempexpired(0);
            p.setTempreturned(0);
        }

        getHibernateTemplate().saveOrUpdateAll(products);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts(Store store) {

        return getHibernateTemplate().find("from Product as p where p.store.idstore="
                + store.getIdstore() + " order by p.brand, p.type");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        return getHibernateTemplate().find("from Product as p order by p.brand, p.type");
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(int idProduct) {

        return getHibernateTemplate().get(Product.class, idProduct);
    }

    @Override
    @Transactional
    public void updateStoredIndexes(Product product, int quantity) {
        product.setTotstored(product.getTotstored() + quantity);
        product.setTempstored(product.getTempstored() + quantity);

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    @Transactional
    public void updateRequestedIndexes(Product product, int quantity) {
        product.setTotrequested(product.getTotrequested() + quantity);
        product.setTemprequested(product.getTemprequested() + quantity);

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    @Transactional
    public void updateSoldIndexes(Product product, int quantity) {
        product.setTotsold(product.getTotsold() + quantity);
        product.setTempsold(product.getTempsold() + quantity);

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    @Transactional
    public void updateExpiredIndexes(Product product, int quantity) {
        product.setTotexpired(product.getTotexpired() + quantity);
        product.setTempexpired(product.getTempexpired() + quantity);

        getHibernateTemplate().saveOrUpdate(product);
    }

    @Override
    @Transactional
    public void updateReturnedIndexes(Product product, int quantity) {
        product.setTotreturned(product.getTotreturned() + quantity);
        product.setTempreturned(product.getTempreturned() + quantity);

        getHibernateTemplate().saveOrUpdate(product);
    }
}