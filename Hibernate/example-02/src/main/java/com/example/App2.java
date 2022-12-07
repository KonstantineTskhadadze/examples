package com.example;


import com.example.entity.Car;
import com.example.entity.HibernateUtil;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App2 {
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
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = builder.createQuery(Car.class);
            Root<Car> root = criteria.from(Car.class);
            Predicate predicate = builder.equal(root.get("id"), id);
            criteria.where(predicate);
            return session.createQuery(criteria).uniqueResult();
        }
    }

    private static List<Car> getCars() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteria = builder.createQuery(Car.class);
            criteria.select(criteria.from(Car.class));
            return session.createQuery(criteria).list();
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
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Car> criteriaUpdate = builder.createCriteriaUpdate(Car.class);
            Root<Car> root = criteriaUpdate.from(Car.class);
            criteriaUpdate.set("name", car.getName()).set("price", car.getPrice());
            criteriaUpdate.where(builder.equal(root.get("id"), car.getId()));

            session.beginTransaction();
            session.createQuery(criteriaUpdate).executeUpdate();
            session.getTransaction().commit();
        }
    }

    private static void deleteCar(Car car) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<Car> criteriaDelete = builder.createCriteriaDelete(Car.class);
            Root<Car> root = criteriaDelete.from(Car.class);
            criteriaDelete.where(builder.equal(root.get("id"), car.getId()));

            session.beginTransaction();
            session.createQuery(criteriaDelete).executeUpdate();
            session.getTransaction().commit();
        }
    }
}

