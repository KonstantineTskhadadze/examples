package com.example;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.entity.HibernateUtil;
import jakarta.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.example.entity.Person;

public class SaveMethodsTest {
    private static final SessionFactory sessionFactory;
    private Session session;
    private final boolean doNotCommit;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public SaveMethodsTest() {
        this.doNotCommit = false;
        this.session = sessionFactory.openSession();
        this.session.beginTransaction();
    }


    public static void main(final String[] args) {
        SaveMethodsTest test = null;
        try {
            test = new SaveMethodsTest();
            test.whenPersistTransient_thenSavedToDatabaseOnCommit();

            test = new SaveMethodsTest();
            test.whenPersistPersistent_thenNothingHappens();

            test = new SaveMethodsTest();
            test.whenPersistDetached_thenThrowsException();

            test = new SaveMethodsTest();
            test.whenMergeDetached_thenEntityUpdatedFromDatabase();

            test = new SaveMethodsTest();
            test.whenSaveTransient_thenIdGeneratedImmediately();

            test = new SaveMethodsTest();
            test.whenSavePersistent_thenNothingHappens();

            test = new SaveMethodsTest();
            test.whenSaveDetached_thenNewInstancePersisted();

            test = new SaveMethodsTest();
            test.whenMergeTransient_thenNewEntitySavedToDatabase();

            test = new SaveMethodsTest();
            test.whenMergePersistent_thenReturnsSameObject();

            test = new SaveMethodsTest();
            test.whenUpdateDetached_thenEntityUpdatedFromDatabase();

            test = new SaveMethodsTest();
            test.whenUpdateTransient_thenThrowsException();

            test = new SaveMethodsTest();
            test.whenUpdatePersistent_thenNothingHappens();

            test = new SaveMethodsTest();
            test.whenSaveOrUpdateDetached_thenEntityUpdatedFromDatabase();

            test = new SaveMethodsTest();
            test.whenSaveOrUpdateTransient_thenSavedToDatabaseOnCommit();

            test = new SaveMethodsTest();
            test.whenSaveOrUpdatePersistent_thenNothingHappens();
        } finally {
            if (!test.doNotCommit) {
                test.session.getTransaction().commit();
            }
            test.session.close();
            sessionFactory.close();
        }
    }

    private void whenPersistTransient_thenSavedToDatabaseOnCommit() {
        final Person person = new Person();
        person.setName("John");
        this.session.persist(person);

        this.session.getTransaction().commit();
        this.session.close();

        this.session = sessionFactory.openSession();
        this.session.beginTransaction();

        System.out.println(this.session.get(Person.class, person.getId())); // person not null
    }

    private void whenPersistPersistent_thenNothingHappens() {
        final Person person = new Person();
        person.setName("John");

        this.session.persist(person);
        final Long id1 = person.getId();

        this.session.persist(person);
        final Long id2 = person.getId();

        System.out.println(id1 + " - " + id2); // should be the same
    }

    private void whenPersistDetached_thenThrowsException() {
        // doNotCommit = true;

        final Person person = new Person();
        person.setName("John");
        this.session.persist(person);
        this.session.evict(person);

        try {
            this.session.persist(person);
        } catch (final PersistenceException e) {
            System.out.println(e.getMessage()); // detached entity passed to persist
        }
    }

    private void whenMergeDetached_thenEntityUpdatedFromDatabase() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);
        this.session.flush();
        this.session.evict(person);

        person.setName("Mary");
        final Person mergedPerson = (Person) this.session.merge(person);

        System.out.println(person != mergedPerson); // should be true, person and mergedPerson are not the same
        System.out.println(mergedPerson.getName()); // Mary
    }

    private void whenSaveTransient_thenIdGeneratedImmediately() {
        final Person person = new Person();
        person.setName("John");

        System.out.println(person.getId()); // Should be null

        final Long id = (Long) this.session.save(person);
        System.out.println(person.getId()); // Should be not null

        this.session.getTransaction().commit();
        this.session.close();

        System.out.println(id == person.getId()); // Should be the same

        this.session = sessionFactory.openSession();
        this.session.beginTransaction();

        System.out.println(this.session.get(Person.class, person.getId()));
    }

    private void whenSavePersistent_thenNothingHappens() {
        final Person person = new Person();
        person.setName("John");
        final Long id1 = (Long) this.session.save(person);
        final Long id2 = (Long) this.session.save(person);
        System.out.println(id1 + " - " + id2); // Should be the same
    }

    private void whenSaveDetached_thenNewInstancePersisted() {
        final Person person = new Person();
        person.setName("John");
        final Long id1 = (Long) this.session.save(person);
        this.session.evict(person);

        final Long id2 = (Long) this.session.save(person);
        System.out.println(id1 + " - " + id2); // Should not be the same: id2 = id1 + 1
    }

    private void whenMergeTransient_thenNewEntitySavedToDatabase() {
        final Person person = new Person();
        person.setName("John");
        final Person mergedPerson = (Person) this.session.merge(person);

        this.session.getTransaction().commit();
        this.session.beginTransaction();

        System.out.println(person.getId()); //Should be null
        System.out.println(mergedPerson.getId()); //Should not be null
    }

    private void whenMergePersistent_thenReturnsSameObject() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);

        final Person mergedPerson = (Person) this.session.merge(person);

        System.out.println(person == mergedPerson); //Should be true
    }

    private void whenUpdateDetached_thenEntityUpdatedFromDatabase() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);
        this.session.evict(person);

        person.setName("Mary");
        this.session.update(person);
        System.out.println(person.getName()); //Mary
    }

    private void whenUpdateTransient_thenThrowsException() {
        final Person person = new Person();
        person.setName("John");

        try {
            this.session.update(person);
        } catch (final HibernateException e) {
            System.out.println(e.getMessage()); //The given object has a null identifier
        }
    }

    private void whenUpdatePersistent_thenNothingHappens() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);
        this.session.update(person);
    }

    private void whenSaveOrUpdateDetached_thenEntityUpdatedFromDatabase() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);
        this.session.evict(person);

        person.setName("Mary");
        this.session.saveOrUpdate(person);
        System.out.println(person.getName()); //Mary
    }

    private void whenSaveOrUpdateTransient_thenSavedToDatabaseOnCommit() {
        final Person person = new Person();
        person.setName("John");
        this.session.saveOrUpdate(person);

        this.session.getTransaction().commit();
        this.session.close();

        this.session = sessionFactory.openSession();
        this.session.beginTransaction();

        System.out.println(this.session.get(Person.class, person.getId()));
    }

    private void whenSaveOrUpdatePersistent_thenNothingHappens() {
        final Person person = new Person();
        person.setName("John");
        this.session.save(person);
        this.session.saveOrUpdate(person);
    }
}

