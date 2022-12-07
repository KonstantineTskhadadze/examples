package com.example;

import com.example.entity.HibernateUtil;
import com.example.entity.Item;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.List;

public class ItemApp {
    public boolean checkIfCriteriaTimeLower() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.like(root.get("itemName"), "%item One%"));
            long startTimeCriteria = System.nanoTime();
            session.createQuery(criteria).list();
            long endTimeCriteria = System.nanoTime();
            long durationCriteria = endTimeCriteria - startTimeCriteria;
            System.out.println("durationCriteria: " + durationCriteria);

            long startTimeHQL = System.nanoTime();
            session.createQuery("FROM Item where itemName like '%item One%'").list();
            long endTimeHQL = System.nanoTime();
            long durationHQL = endTimeHQL - startTimeHQL;
            System.out.println("durationHQL: " + durationHQL);

            return durationCriteria > durationHQL;
        }
    }

    // To get items having price more than 1000
    public String[] greaterThanCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.gt(root.get("itemPrice"), 1000)); //gt - greaterThan

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // To get items having price less than 1000
    public String[] lessThanCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.lt(root.get("itemPrice"), 1000)); //lt - lessThan

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // To get items whose Name start with Chair
    public String[] likeCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.like(root.get("itemName"), "Chair%"));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    //To get records having itemPrice in between 100 and 200
    public String[] betweenCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.between(root.get("itemPrice"), 100, 200));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    //To get records having itemName in 'Skate Board', 'Paint' and 'Glue'
    public String[] inCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(root.get("itemName").in("Skate Board", "Paint", "Glue"));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // To check if the given property is null
    public String[] nullCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.isNull(root.get("itemDescription")));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // To check if the given property is not null
    public String[] notNullCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);
            criteria.select(root).where(builder.isNotNull(root.get("itemDescription")));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // Adding more than one expression in one cr
    public String[] twoCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            Predicate[] predicates = new Predicate[2];
            predicates[0] = builder.isNull(root.get("itemDescription"));
            predicates[1] = builder.like(root.get("itemName"), "chair%");
            criteria.select(root).where(predicates);

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    //To get items matching with the above defined conditions joined with Logical AND
    public String[] andLogicalCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            Predicate greaterThanPrice = builder.gt(root.get("itemPrice"), 1000);
            Predicate chairItems = builder.like(root.get("itemName"), "Chair%");
            criteria.select(root).where(builder.and(greaterThanPrice, chairItems));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // To get items matching with the above defined conditions joined with Logical OR
    public String[] orLogicalCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            Predicate greaterThanPrice = builder.gt(root.get("itemPrice"), 1000);
            Predicate chairItems = builder.like(root.get("itemName"), "Chair%");
            criteria.select(root).where(builder.or(greaterThanPrice, chairItems));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // Sorting example
    public String[] sortingCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            criteria.select(root);
            criteria.orderBy(builder.asc(root.get("itemName")), builder.desc(root.get("itemPrice")));

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    // Set projections Row Count
    public Long[] projectionRowCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            Root<Item> root = criteria.from(Item.class);

            criteria.select(builder.count(root));

            Query query = session.createQuery(criteria);
            List<Long> itemProjected = query.getResultList();
            Long projectedRowCount[] = new Long[itemProjected.size()];
            for (int i = 0; i < itemProjected.size(); i++) {
                projectedRowCount[i] = itemProjected.get(i);
            }
            return projectedRowCount;
        }
    }

    // Set projections average of itemPrice
    public Double[] projectionAverage() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
            Root<Item> root = criteria.from(Item.class);

            criteria.select(builder.avg(root.get("itemPrice")));

            Query query = session.createQuery(criteria);
            List<Double> avgItemPriceList = query.getResultList();
            Double avgItemPrice[] = new Double[avgItemPriceList.size()];
            for (int i = 0; i < avgItemPriceList.size(); i++) {
                avgItemPrice[i] = avgItemPriceList.get(i);
            }
            return avgItemPrice;
        }
    }

    public String[] combineTwoOrUsingAndCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            Predicate glueItem = builder.equal(root.get("itemName"), "Glue");
            Predicate paintItem = builder.equal(root.get("itemName"), "Paint");
            Predicate glueOrPaintItem = builder.or(glueItem, paintItem);

            Predicate price1Item = builder.lt(root.get("itemPrice"), 1200);
            Predicate price2Item = builder.gt(root.get("itemPrice"), 1300);
            Predicate priceItem = builder.or(price1Item, price2Item);

            Predicate finalPredicate = builder.and(glueOrPaintItem, priceItem);
            criteria.select(root).where(finalPredicate);

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

    public String[] combineTwoAndUsingOrCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery(Item.class);
            Root<Item> root = criteria.from(Item.class);

            Predicate paintItem1 = builder.like(root.get("itemName"), "%Paint%");
            Predicate paintItem2 = builder.like(root.get("itemName"), "%paint%");
            Predicate paintItem = builder.and(paintItem1, paintItem2);

            Predicate price1Item = builder.gt(root.get("itemPrice"), 1100);
            Predicate price2Item = builder.lt(root.get("itemPrice"), 1400);
            Predicate priceItem = builder.and(price1Item, price2Item);

            Predicate finalPredicate = builder.or(paintItem, priceItem);
            criteria.select(root).where(finalPredicate);

            Query query = session.createQuery(criteria);
            List<Item> greaterThanItemsList = query.getResultList();
            String greaterThanItems[] = new String[greaterThanItemsList.size()];
            for (int i = 0; i < greaterThanItemsList.size(); i++) {
                greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
            }
            return greaterThanItems;
        }
    }

}

