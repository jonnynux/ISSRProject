package interfaces;

import exceptions.*;
import java.util.Date;
import java.util.List;
import model.pojos.Role;
import model.pojos.User;

/**
 * Interface for all the store operations
 *
 * @author jonny
 */
public interface UserOperations {

    public List<Role> getRoles(User executor) throws OperationNotPermittedException,
            NoRolesException;

    public boolean createUpdateUser(User executor, int idUser, int idStore, int idRole,
            String login, String namesurname, String email, Date pwexpiration, Date regexpiration,
            int sessionduration) throws OperationNotPermittedException, RequiredFieldEmptyException,
            LoginNotValidException, EmailNotValidException, RoleNotValidException, NoStoresException;

    public List<User> getUserList(User executor) throws OperationNotPermittedException, NoUsersException;

    public boolean removeUser(User executor, int idUser) throws OperationNotPermittedException,
            RoleNotValidException, NoUsersException;
}
