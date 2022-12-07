package com.example;

import com.example.entity.Cart;
import com.example.entity.HibernateUtil;
import com.example.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App1
{
    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        Cart cart = new Cart();
        Item item1 = new Item(cart);
        Item item2 = new Item(cart);

        Set<Item> itemsSet = new HashSet<>();
        itemsSet.add(item1);
        itemsSet.add(item2);
        cart.setItems(itemsSet);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        System.out.println("Session created");

        try {
            // start transaction
            Transaction tx = session.beginTransaction();

            // Save the Model object
            session.save(cart);
            session.save(item1);
            session.save(item2);

            // Commit transaction
            tx.commit();

            System.out.println("Cart ID=" + cart.getId());
            System.out.println("item1 ID=" + item1.getId() + ", Foreign Key Cart ID=" + item1.getCart().getId());
            System.out.println("item2 ID=" + item2.getId() + ", Foreign Key Cart ID=" + item2.getCart().getId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                System.out.println("Closing SessionFactory");
                sessionFactory.close();
            }
        }
    }

}
