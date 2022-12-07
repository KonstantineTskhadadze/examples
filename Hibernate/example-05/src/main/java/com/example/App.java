package com.example;

import com.example.entity.Address;
import com.example.entity.HibernateUtil;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static SessionFactory sessionFactory;
    private Session session;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public static void main(String[] args) {
        App app = null;
        try {
            app = new App();
            app.givenData_whenInsert_thenCreates1to1relationship();
        } finally {
            app.session.close();
            sessionFactory.close();
        }
    }

    private void givenData_whenInsert_thenCreates1to1relationship() {
        User user = new User();
        user.setUserName("alice@baeldung.com");

        Address address = new Address();
        address.setStreet("FK Street");
        address.setCity("FK City");

        address.setUser(user);
        user.setAddress(address);

        /*
         * Address entry will automatically be created by hibernate, since cascade type
         * is specified as ALL
         */
        session.persist(user);
        session.getTransaction().commit();

        assert1to1InsertedData();
    }

    private void assert1to1InsertedData() {
        @SuppressWarnings("unchecked")
        List<User> userList = session.createQuery("FROM User").list();

        System.out.println(userList.size()); // 1

        User user = userList.get(0);
        System.out.println(user.getUserName()); // alice@baeldung.com

        Address address = user.getAddress();
        System.out.println(address.getStreet()); // FK Street
        System.out.println(address.getCity()); // FK City
    }
}
