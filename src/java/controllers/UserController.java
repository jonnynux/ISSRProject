package controllers;

import enums.Roles;
import exceptions.*;
import interfaces.UserOperations;
import interfaces.daos.RoleDao;
import interfaces.daos.StoreDao;
import interfaces.daos.UserDao;
import java.util.Date;
import java.util.List;
import model.pojos.Role;
import model.pojos.Store;
import model.pojos.User;
import utils.Utility;

/**
 * Controller for all users operations
 *
 * @author jonny
 */
public class UserController implements UserOperations {

    RoleDao roleDao;
    StoreDao storeDao;
    UserDao userDao;

    //<editor-fold defaultstate="collapsed" desc="setter">
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setStoreDao(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    //</editor-fold>

    @Override
    public List<Role> getRoles(User executor) throws OperationNotPermittedException,
            NoRolesException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Gestore_Credenziali.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        int idRole = executor.getRole().getIdrole();

        List<Role> roles = userDao.getRoles(idRole);

        if (!roles.isEmpty()) {
            return roles;
        } else {
            throw new NoRolesException();
        }
    }

    @Override
    public boolean createUpdateUser(User executor, int idUser, int idStore, int idRole,
            String login, String namesurname, String email, Date pwexpiration, Date regexpiration,
            int sessionduration) throws OperationNotPermittedException, RequiredFieldEmptyException,
            LoginNotValidException, EmailNotValidException, RoleNotValidException, NoStoresException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Gestore_Credenziali.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        if (login.equals("") || email.equals("") || !(idRole > 0)) {
            throw new RequiredFieldEmptyException();
        }

        String lowerCaseLogin = login.toLowerCase();
        if (!Utility.LoginIsValid(lowerCaseLogin) || (userDao.loginIsPresent(lowerCaseLogin) && idUser <= 0)) {
            throw new LoginNotValidException();
        }

        if (!Utility.EmailIsValid(email) || (userDao.emailIsPresent(email) && idUser <= 0)) {
            throw new EmailNotValidException();
        }

        Store store = executor.getStore();

        if (executor.getRole().isGeneraladministrator()) {
            store = storeDao.getStore(idStore);

            if (store == null) {
                throw new NoStoresException();
            }
        }

        User existentUser = userDao.getUser(idUser);
        Role role = roleDao.getRole(idRole);
        if (existentUser != null && (role == null || existentUser.getRole().getIdrole()
                <= executor.getRole().getIdrole() || idRole <= executor.getRole().getIdrole())) {
            throw new RoleNotValidException();
        }

        String password = Utility.GeneratePassword();

        userDao.createUpdateUser(idUser, store, role, lowerCaseLogin, password, namesurname,
                email, pwexpiration, regexpiration, sessionduration);

        return true;
    }

    @Override
    public List<User> getUserList(User executor) throws OperationNotPermittedException,
            NoUsersException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Gestore_Credenziali.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        Store store = executor.getStore();

        if (executor.getRole().isGeneraladministrator()) {
            store = null;
        }

        List<User> users = userDao.getUsers(store);

        if (!users.isEmpty()) {
            return users;
        } else {
            throw new NoUsersException();
        }
    }

    @Override
    public boolean removeUser(User executor, int idUser) throws OperationNotPermittedException,
            RoleNotValidException, NoUsersException {

        String roleName = executor.getRole().getName().replace(" ", "_");

        if (roleName.equals(Roles.Amministratore_Generale.toString())
                || roleName.equals(Roles.Amministratore_Punto_Vendita.toString())
                || roleName.equals(Roles.Gestore_Credenziali.toString())) {
        } else {
            throw new OperationNotPermittedException();
        }

        User existentUser = userDao.getUser(idUser);
        if (existentUser != null) {
            if (existentUser.getRole().getIdrole() <= executor.getRole().getIdrole()) {
                throw new RoleNotValidException();
            }
        } else {
            throw new NoUsersException();
        }

        userDao.removeUser(idUser);

        return true;
    }
}
