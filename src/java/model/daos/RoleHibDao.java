package model.daos;

import interfaces.daos.RoleDao;
import java.util.List;
import model.pojos.Role;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate dao for role operations
 *
 * @author jonny
 */
public class RoleHibDao extends HibernateDaoSupport implements RoleDao {

    public static String roles[] = {
        "Amministratore Generale",
        "Amministratore Punto Vendita",
        "Gestore Credenziali",
        "Analista Indici",
        "Addetto Reclami",
        "Magazziniere",
        "Cassiere"};

    @Override
    @Transactional(readOnly = true)
    public List<Role> getRoleList() {

        return getHibernateTemplate().find("from Role");
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(int idRole) {

        return getHibernateTemplate().get(Role.class, idRole);
    }
}
