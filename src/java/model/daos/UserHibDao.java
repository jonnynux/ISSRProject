package model.daos;

import interfaces.daos.UserDao;
import java.util.Date;
import java.util.List;
import model.pojos.Role;
import model.pojos.Store;
import model.pojos.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate dao for user operations
 *
 * @author jonny
 */
public class UserHibDao extends HibernateDaoSupport implements UserDao {

    @Override
    @Transactional(readOnly = true)
    public User getUser(String login, String password) {

        List<User> users = getHibernateTemplate().find("from User where Login='"
                + login + "' and Password='" + password + "'");

        return users.isEmpty()
                ? null
                : users.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int idUser) {

        return getHibernateTemplate().get(User.class, idUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoles(int idRole) {

        return getHibernateTemplate().find("from Role as r where r.idrole>" + idRole);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean loginIsPresent(String login) {

        List<User> users = getHibernateTemplate().find("from User as u where u.login='"
                + login + "'");

        return users.isEmpty()
                ? false
                : true;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailIsPresent(String email) {

        List<User> users = getHibernateTemplate().find("from User as u where u.email='"
                + email + "'");

        return users.isEmpty()
                ? false
                : true;
    }

    @Override
    @Transactional
    public void createUpdateUser(int idUser, Store store, Role role, String login,
            String password, String namesurname, String email, Date pwexpiration,
            Date regexpiration, int sessionduration) {

        User user = new User();
        if (idUser > 0) {
            user.setIduser(idUser);
        }
        user.setStore(store);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        user.setNamesurname(namesurname);
        user.setEmail(email);
        user.setPwexpiration(pwexpiration);
        user.setRegexpiration(regexpiration);
        user.setSessionduration(sessionduration);

        getHibernateTemplate().saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(Store store) {

        String suffix = " order by u.store.idstore, u.role.idrole, u.login";
        return store == null
                ? getHibernateTemplate().find("from User as u" + suffix)
                : getHibernateTemplate().find("from User as u where u.store.idstore="
                + store.getIdstore() + suffix);
    }

    @Override
    public void removeUser(int idUser) {

        User u = getUser(idUser);
        getHibernateTemplate().delete(u);
    }
}
