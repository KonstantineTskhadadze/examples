package com.example;


import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class App {
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
            app.whenCourseLikePersisted_thenCorrect();

            app = new App();
            app.whenCourseRatingPersisted_thenCorrect();

            app = new App();
            app.whenCourseRegistrationPersisted_thenCorrect();
        } finally {
            app.session.close();
            sessionFactory.close();
        }
    }

    private void whenCourseLikePersisted_thenCorrect() {
        Long studentId = 10L;
        Long courseId = 20L;

        Student student = new Student(studentId);
        Course course = new Course(courseId);

        student.getLikedCourses().add(course);
        course.getLikes().add(student); // optional add, because Student is relationship owner

        session.persist(student);
        session.persist(course);

        session.getTransaction().commit();
        session.close();
        session = sessionFactory.openSession();

        Student persistedStudent = session.find(Student.class, studentId);
        System.out.println(persistedStudent.getId()); // studentId
        System.out.println(persistedStudent.getLikedCourses().iterator().next().getId()); // courseId

        Course persistedCourse = session.find(Course.class, courseId);
        System.out.println(persistedCourse.getId()); // courseId
        System.out.println(persistedCourse.getLikes().iterator().next().getId()); // studentId
    }

    private void whenCourseRatingPersisted_thenCorrect() {
        Long studentId = 100L;
        Long courseId = 200L;

        Student student = new Student(studentId);
        session.persist(student);

        Course course = new Course(courseId);
        session.persist(course);

        CourseRating courseRating = new CourseRating();
        courseRating.setId(new CourseRatingKey());
        courseRating.setStudent(student);
        courseRating.setCourse(course);
        courseRating.setRating(77);
        session.persist(courseRating);

        session.getTransaction().commit();
        session.close();
        session = sessionFactory.openSession();

        CourseRating persistedCourseRating = session.find(CourseRating.class, new CourseRatingKey(studentId, courseId));

        System.out.println(persistedCourseRating.getStudent().getId()); // studentId
        System.out.println(persistedCourseRating.getCourse().getId()); // courseId
        System.out.println(courseRating.getRating()); // 77
    }

    private void whenCourseRegistrationPersisted_thenCorrect() {
        Long studentId = 1000L;
        Long courseId = 2000L;
        Long courseRegistrationId = 1L;

        Student student = new Student(studentId);
        session.persist(student);

        Course course = new Course(courseId);
        session.persist(course);

        CourseRegistration courseRegistration = new CourseRegistration();
        courseRegistration.setId(courseRegistrationId);
        courseRegistration.setStudent(student);
        courseRegistration.setCourse(course);
        courseRegistration.setRegisteredAt(LocalDateTime.now());
        courseRegistration.setGrade(87);
        session.persist(courseRegistration);

        session.getTransaction().commit();
        session.close();
        session = sessionFactory.openSession();

        CourseRegistration persistedCourseRegistration = session.find(CourseRegistration.class, courseRegistrationId);

        System.out.println(persistedCourseRegistration.getId()); // courseRegistrationId
        System.out.println(persistedCourseRegistration.getStudent().getId()); // studentId
        System.out.println(persistedCourseRegistration.getCourse().getId()); // courseId
        System.out.println(persistedCourseRegistration.getRegisteredAt()); // now
        System.out.println(persistedCourseRegistration.getGrade()); // 87
    }
}
