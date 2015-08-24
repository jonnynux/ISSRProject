package model.pojos;
// Generated 9-ago-2012 18.23.18 by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Store generated by hbm2java
 */
public class Store implements java.io.Serializable {

    private Integer idstore;
    private String name;
    private String address;
    private String piva;
    private Set<Product> products = new HashSet<Product>(0);
    private Set<Log> logs = new HashSet<Log>(0);
    private Set<User> users = new HashSet<User>(0);

    public Store() {
    }

    public Store(String name) {
        this.name = name;
    }

    public Store(String name, String address, String piva, Set<Product> products, Set<Log> logs, Set<User> users) {
        this.name = name;
        this.address = address;
        this.piva = piva;
        this.products = products;
        this.logs = logs;
        this.users = users;
    }

    public Integer getIdstore() {
        return this.idstore;
    }

    public void setIdstore(Integer idstore) {
        this.idstore = idstore;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPiva() {
        return this.piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    @XmlTransient
    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @XmlTransient
    public Set<Log> getLogs() {
        return this.logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    @XmlTransient
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name + " (" + address + ")";
    }
}
