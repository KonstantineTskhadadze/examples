package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.entity.HibernateUtil;
import com.example.entity.Item;
import org.hibernate.Session;


public class ItemAppTester {
    private static final ItemApp app;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        app = new ItemApp();
    }

    public static void main(String[] args) {
        System.out.println(app.checkIfCriteriaTimeLower()); //true
        testGreaterThanCriteria();
        testLessThanCriteria();
        testLikeCriteria();
        testBetweenCriteria();
        testInCriteria();
        testNullCriteria();
        testNotNullCriteria();
        testTwoCriteria();
        testAndLogicalCriteria();
        testOrLogicalCriteria();
        testSortingCriteria();
        testRowCountProjection();
        testAverageProjection();
        testCombineTwoOrUsingAndCriteria();
        testCombineTwoAndUsingOrCriteria();
    }

    private static void testGreaterThanCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemPrice > 1000").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.greaterThanCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testLessThanCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemPrice < 1000").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.lessThanCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testLikeCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemName like 'Chair%'").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.likeCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testBetweenCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemPrice between 100 and 200").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.betweenCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testInCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemName in ('Skate Board', 'Paint', 'Glue')").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.inCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testNullCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemDescription is null").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.nullCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testNotNullCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemDescription is not null").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.notNullCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testTwoCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemDescription is null and itemName like 'chair%'").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.twoCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testAndLogicalCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemPrice>1000 and itemName like 'Chair%'").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.andLogicalCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testOrLogicalCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where itemPrice>1000 or itemName like 'Chair%'").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.orLogicalCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testSortingCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item order by itemName asc, itemPrice desc").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.sortingCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testRowCountProjection() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Long> expectedList = session.createQuery("Select count(*) from Item").list();
            Long[] expectedItems = new Long[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i);
            }

            Long[] returnedItems = app.projectionRowCount();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testAverageProjection() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Double> expectedList = session.createQuery("Select avg(itemPrice) from Item item").list();
            Double[] expectedItems = new Double[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i);
            }

            Double[] returnedItems = app.projectionAverage();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testCombineTwoOrUsingAndCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where (itemName = 'Glue' or itemName = 'Paint') and (itemPrice < 1200 or itemPrice > 1300)").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.combineTwoOrUsingAndCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

    private static void testCombineTwoAndUsingOrCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("unchecked")
            List<Item> expectedList = session.createQuery("From Item where (itemName like '%Paint%' and itemName like '%paint%') or (itemPrice > 1100 and itemPrice < 1400)").list();
            String[] expectedItems = new String[expectedList.size()];
            for (int i = 0; i < expectedList.size(); i++) {
                expectedItems[i] = expectedList.get(i).getItemName();
            }

            String[] returnedItems = app.combineTwoAndUsingOrCriteria();
            System.out.println(Arrays.equals(expectedItems, returnedItems)); //true
        }
    }

}
