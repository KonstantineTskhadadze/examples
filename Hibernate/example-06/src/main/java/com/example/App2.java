package com.example;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.entity.Cart;
import com.example.entity.HibernateUtil;
import com.example.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class App2 {

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();

        Item item1 = new Item(cart1);
        Item item2 = new Item(cart2);

        Set<Item> itemsSet = new HashSet<>();
        itemsSet.add(item1);
        itemsSet.add(item2);
        cart1.setItems(itemsSet);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        System.out.println("Session created");

        try {
            // start transaction
            Transaction tx = session.beginTransaction();

            // Save the Model object
            session.save(cart1);
            session.save(cart2);
            session.save(item1);
            session.save(item2);

            // Commit transaction
            tx.commit();

            session.close();
            session = sessionFactory.openSession();

            item1 = session.get(Item.class, item1.getId());
            item2 = session.get(Item.class, item2.getId());

            System.out.printf("item1 ID=%d, Foreign Key Cart ID=%d%n", item1.getId(), item1.getCart().getId());
            System.out.printf("item2 ID=%d, Foreign Key Cart ID=%d%n", item2.getId(), item2.getCart().getId());

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
