package controllers;

import enums.Roles;
import exceptions.LoginErrorException;
import interfaces.LoginOperations;
import interfaces.daos.StoreDao;
import interfaces.daos.UserDao;
import model.pojos.Role;
import model.pojos.Store;
import model.pojos.User;

/**
 * Controller for login operations
 *
 * @author jonny
 */
public class LoginController implements LoginOperations {

    StoreDao storeDao;
    UserDao userDao;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setStoreDao(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    //</editor-fold>

    @Override
    public User login(String login, String password, int idStore) throws LoginErrorException {

        User user = userDao.getUser(login, password);

        if (login.equals("") && password.equals("")) {
            user = new User();
            user.setNamesurname("Visitatore");
            user.setRole(new Role(Roles.Visitatore.toString()));
        }

        if (user == null) {
            throw new LoginErrorException();
        }

        if (user.getStore() == null || user.getRole().isGeneraladministrator()) {
            if (idStore > 0) {
                Store store = storeDao.getStore(idStore);

                if (store == null) {
                    throw new LoginErrorException();
                }

                user.setStore((Store) store);
            } else {
                throw new LoginErrorException();
            }
        }

        return user;
    }
}
