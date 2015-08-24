package interfaces.daos;

import java.util.Date;
import java.util.List;
import model.pojos.Log;
import model.pojos.Store;

/**
 * Dao for log operations
 *
 * @author jonny
 */
public interface LogDao {

    public List<Log> getLogList(Store store);

    public void log(Store store, Date instantTime, String executorString, String activityString, boolean b);
}
