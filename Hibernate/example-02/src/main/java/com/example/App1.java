package com.example;

/**
 * Hello world!
 */


import com.example.entity.Car;
import com.example.entity.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App1 {
    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public static void main(String[] args) {
        System.out.println(getCarById(2));
        System.out.println(getCars());

        Car car = new Car();
        car.setName("Toyota");
        car.setPrice(34500);

        addCar(car);
        System.out.println(getCarById(car.getId()));

        car.setName("Toyota RAV 4");
        car.setPrice(37000);
        updateCar(car);
        System.out.println(getCarById(car.getId()));

        deleteCar(car);
        System.out.println(getCarById(car.getId()));
    }

    private static Car getCarById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Car.class, id);
        }
    }

    private static List<Car> getCars() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Car", Car.class).list();
        }
    }

    private static void addCar(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(car);
            session.getTransaction().commit();
        }
    }

    private static void updateCar(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }
    }

    private static void deleteCar(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(car);
            session.getTransaction().commit();
        }
    }
}