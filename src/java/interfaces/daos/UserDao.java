package interfaces.daos;

import java.util.Date;
import java.util.List;
import model.pojos.Role;
import model.pojos.Store;
import model.pojos.User;

/**
 * Dao for user operations
 *
 * @author jonny
 */
public interface UserDao {

    public User getUser(String login, String password);

    public User getUser(int idUser);

    public List<Role> getRoles(int idRole);

    public boolean loginIsPresent(String login);

    public boolean emailIsPresent(String email);

    public void createUpdateUser(int idUser, Store store, Role role, String login, String GeneratePassword, String namesurname, String email, Date pwexpiration, Date regexpiration, int sessionduration);

    public List<User> getUsers(Store store);

    public void removeUser(int idUser);
}
