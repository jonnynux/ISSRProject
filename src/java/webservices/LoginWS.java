package webservices;

import exceptions.LoginErrorException;
import interfaces.LoginOperations;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.pojos.User;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import utils.AppContext;

/**
 * Web service controller
 *
 * @author jonny
 */
@WebService(serviceName = "LoginWS")
public class LoginWS extends SpringBeanAutowiringSupport implements LoginOperations {

    ApplicationContext ctx = AppContext.getApplicationContext();
    LoginOperations loginOperations = (LoginOperations) ctx.getBean("loginOperations");

    @Override
    @WebMethod(operationName = "login")
    public User login(@WebParam(name = "login") String login,
            @WebParam(name = "password") String password,
            @WebParam(name = "idStore") int idStore)
            throws LoginErrorException {

        return loginOperations.login(login, password, idStore);
    }
}
