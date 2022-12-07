package com.example;

import com.example.entity.app1.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Hello world!
 */
public class App1 {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private static final EntityManager ENTITY_MANAGER;
    private static final List<Student> students;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("h2DB");
        ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();
        students = new ArrayList<>();
        students.add(new Student("Oliver", "Oliver@gmail.com"));
        students.add(new Student("Lucas", "Lucas@gmail.com"));
        students.add(new Student("Henry", "Henry@yahoo.com"));
        students.add(new Student("Theodore", "Th_dr@live.com"));
        students.add(new Student("Sophia", "sophia.sh@mail.com"));
        students.add(new Student("Isabella", "Isabel@missioncollege.edu"));
        students.add(new Student("Mia", "miamia@citruscollege.edu"));
        students.add(new Student("William", "William@yahoo.com"));
    }
    public static void main(final String[] args) {

        try {
            students.forEach(App1::createStudent);
            System.out.println();

            findStudent("Lucas");
            System.out.println();

            searchStudent("ia");
            System.out.println();

            updateStudent(students.get(4).getId(), "sophia.sh@gmail.com");
            System.out.println();

            deleteStudent(students.get(6).getId());
            System.out.println();

            listStudents();
            System.out.println();

            truncateStudent();
        } finally {
            if (ENTITY_MANAGER != null) {
                ENTITY_MANAGER.close();
            }
            if (ENTITY_MANAGER_FACTORY != null) {
                ENTITY_MANAGER_FACTORY.close();
            }
        }
    }

    private static void createStudent(final Student student) {
        student.setCreationDate(new Date());
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.persist(student);
        ENTITY_MANAGER.getTransaction().commit();
        System.out.println("Added Student: " + student);
    }

    private static void findStudent(final String name) {
        try {
            final TypedQuery<Student> query = ENTITY_MANAGER.createQuery("select s from " + Student.class.getName() + " s where s.name = :name", Student.class);
            query.setParameter("name", name);
            final Student student = query.getSingleResult();
            System.out.println("Your result---Find student:");
            System.out.println(student);
        } catch (final Exception ex) {
            System.err.println("Some exception occured: " + ex.getMessage());
        }
    }

    private static void searchStudent(final String name) {
        try {
            final TypedQuery<Student> query = ENTITY_MANAGER.createQuery(
                    "select s from " + Student.class.getName() + " s where lower(s.name) like :search", Student.class);
            query.setParameter("search", "%" + name.toLowerCase() + "%");

            final List<Student> list = query.getResultList();
            if (list.size() == 0) {
                System.out.println("Nothing found where name like " + name);
                return;
            }

            System.out.println("Your result---Indexed students:");
            list.forEach(System.out::println);
        } catch (final Exception ex) {
            System.err.println("Some exception occured: " + ex.getMessage());
        }
    }

    private static void updateStudent(final int studentId, final String newEmail) {
        final Student student = ENTITY_MANAGER.find(Student.class, studentId);
        if (student == null) {
            System.out.println("No student was found with id " + studentId);
        } else {
            System.out.println("Updating student " + student);
            student.setEmail(newEmail);
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.persist(student);
            ENTITY_MANAGER.getTransaction().commit();
            System.out.println("The student was updated successfully");
        }
    }

    private static void deleteStudent(final int studentId) {
        final Student student = ENTITY_MANAGER.getReference(Student.class, studentId); //or find
        if (student == null) {
            System.out.println("No student was found with id " + studentId);
        } else {
            System.out.println("Deleting student " + student);
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(student);
            ENTITY_MANAGER.getTransaction().commit();
            System.out.println("The student was deleted successfully");
        }
    }

    private static void listStudents() {
        final Query query = ENTITY_MANAGER.createQuery("select s from " + Student.class.getName() + " s order by s.id");

        final List<?> students = query.getResultList();
        System.out.println("Your result---List students:");
        System.out.println(students);
        System.out.println(String.valueOf(students.size()) + " row(s) selected");
    }

    private static void truncateStudent() {
        try {
            final Query query = ENTITY_MANAGER.createQuery("select s from " + Student.class.getName() + " s");

            @SuppressWarnings("unchecked") final List<Student> list = query.getResultList();
            if (list.size() == 0) {
                System.out.println("Your list is already empty!");
                return;
            }

            ENTITY_MANAGER.getTransaction().begin();
            list.forEach(ENTITY_MANAGER::remove);
            ENTITY_MANAGER.getTransaction().commit();
            System.out.println("Your list is now successfully cleared");
        } catch (final Exception ex) {
            System.err.println("Some exception occured: " + ex.getMessage());
        }
    }
}
