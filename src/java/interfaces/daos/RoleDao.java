package interfaces.daos;

import java.util.List;
import model.pojos.Role;

/**
 * Dao for role operations
 *
 * @author jonny
 */
public interface RoleDao {

    public List<Role> getRoleList();

    public Role getRole(int idRole);
}
