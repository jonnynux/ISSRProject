package model.daos;

import interfaces.daos.LogDao;
import java.util.Date;
import java.util.List;
import model.pojos.Log;
import model.pojos.Store;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate dao for log operations
 *
 * @author jonny
 */
public class LogHibDao extends HibernateDaoSupport implements LogDao {

    @Override
    public List<Log> getLogList(Store store) {

        return store == null
                ? getHibernateTemplate().find("from Log as l order by l.instanttime")
                : getHibernateTemplate().find("from Log as l where l.store.idstore="
                + store.getIdstore() + " order by l.instanttime");
    }

    @Override
    public void log(Store store, Date instantTime, String executorString, String activityString, boolean result) {
        if (activityString.length() > 150) {
            activityString = activityString.substring(0, 150);
        }

        Log log = new Log();
        log.setStore(store);
        log.setInstanttime(instantTime);
        log.setExecutor(executorString);
        log.setActivity(activityString);
        log.setResult(result);

        getHibernateTemplate().saveOrUpdate(log);
    }
}
