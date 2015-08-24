package webservices;

import exceptions.*;
import interfaces.UserOperations;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.pojos.Role;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import utils.AppContext;

/**
 * Web service controller
 *
 * @author jonny
 */
@WebService(serviceName = "UserWS")
public class UserWS extends SpringBeanAutowiringSupport implements UserOperations {

    ApplicationContext ctx = AppContext.getApplicationContext();
    UserOperations userOperations = (UserOperations) ctx.getBean("userOperations");

    @Override
    @WebMethod(operationName = "getRoles")
    public List<Role> getRoles(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoRolesException {

        return userOperations.getRoles(executor);
    }

    @Override
    @WebMethod(operationName = "createUpdateUser")
    public boolean createUpdateUser(@WebParam(name = "executor") User executor,
            @WebParam(name = "idUser") int idUser,
            @WebParam(name = "idStore") int idStore,
            @WebParam(name = "idRole") int idRole,
            @WebParam(name = "login") String login,
            @WebParam(name = "namesurname") String namesurname,
            @WebParam(name = "email") String email,
            @WebParam(name = "pwexpiration") Date pwexpiration,
            @WebParam(name = "regexpiration") Date regexpiration,
            @WebParam(name = "sessionduration") int sessionduration)
            throws OperationNotPermittedException, RequiredFieldEmptyException,
            LoginNotValidException, EmailNotValidException, RoleNotValidException,
            NoStoresException {

        return userOperations.createUpdateUser(executor, idUser, idStore, idRole,
                login, namesurname, email, pwexpiration, regexpiration, sessionduration);
    }

    @Override
    @WebMethod(operationName = "getUserList")
    public List<User> getUserList(@WebParam(name = "executor") User executor)
            throws OperationNotPermittedException, NoUsersException {

        return userOperations.getUserList(executor);
    }

    @Override
    @WebMethod(operationName = "removeUser")
    public boolean removeUser(@WebParam(name = "executor") User executor,
            @WebParam(name = "idUser") int idUser)
            throws OperationNotPermittedException, RoleNotValidException, NoUsersException {

        return userOperations.removeUser(executor, idUser);
    }
}
