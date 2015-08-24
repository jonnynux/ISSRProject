package interfaces;

import exceptions.LoginErrorException;
import model.pojos.User;

/**
 * Interface for all the store operations
 *
 * @author jonny
 */
public interface LoginOperations {

    public User login(String login, String password, int idStore) throws LoginErrorException;
}
