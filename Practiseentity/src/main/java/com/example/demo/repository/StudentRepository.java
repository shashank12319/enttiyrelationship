package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Laptop;
import com.example.demo.model.Student;

@Repository
public class StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void testFetchStrategies() {
        // Test FetchType.EAGER
        System.out.println("Testing FetchType.EAGER...");
        Student studentEager = entityManager.find(Student.class, 1L);
        List<Laptop> laptopsEager = studentEager.getLaptops();
        System.out.println("Laptops fetched eagerly: " + laptopsEager.size());

        // Test FetchType.LAZY
        System.out.println("Testing FetchType.LAZY...");
        Student studentLazy = entityManager.getReference(Student.class, 2L);
        System.out.println("Student fetched lazily: " + studentLazy.getName());
        List<Laptop> laptopsLazy = studentLazy.getLaptops();
        System.out.println("Laptops fetched lazily: " + laptopsLazy.size());
    }
}
