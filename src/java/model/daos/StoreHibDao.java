package model.daos;

import interfaces.daos.StoreDao;
import java.util.List;
import model.pojos.Store;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate dao for store operations
 *
 * @author jonny
 */
public class StoreHibDao extends HibernateDaoSupport implements StoreDao {

    @Override
    @Transactional(readOnly = true)
    public List<Store> getStoreList() {

        return getHibernateTemplate().find("from Store");
    }

    @Override
    @Transactional(readOnly = true)
    public Store getStore(int idStore) {

        return getHibernateTemplate().get(Store.class, idStore);
    }
}
