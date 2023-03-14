package com.example.demo.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.example.demo.repository.StudentRepository;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Student student1 = new Student("John");
        Student student2 = new Student("Jane");

        Laptop laptop1 = new Laptop("Dell", "Inspiron", student1);
        Laptop laptop2 = new Laptop("HP", "Pavilion", student1);
        Laptop laptop3 = new Laptop("Lenovo", "ThinkPad", student1);
        Laptop laptop4 = new Laptop("Dell", "XPS", student2);
        Laptop laptop5 = new Laptop("MacBook", "Pro", student2);
        Laptop laptop6 = new Laptop("HP", "EliteBook", student2);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(student1);
        entityManager.persist(student2);

        entityManager.persist(laptop1);
        entityManager.persist(laptop2);
        entityManager.persist(laptop3);
        entityManager.persist(laptop4);
        entityManager.persist(laptop5);
        entityManager.persist(laptop6);

        transaction.commit();

        StudentRepository studentRepository = new StudentRepository();
        studentRepository.testFetchStrategies();

        entityManager.close();
        entityManagerFactory.close();
    }
}
