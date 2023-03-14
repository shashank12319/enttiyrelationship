package com.example.demo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.example.demo.repository.ManRepository;

public class MainClass {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // create some data
        Man man1 = new Man("John");
        Address address1 = new Address("123 Main St", "Anytown", "CA", "12345", man1);
        man1.setAddress(address1);

        Man man2 = new Man("Jane");
        Address address2 = new Address("456 Oak St", "Sometown", "NY", "54321", man2);
        man2.setAddress(address2);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(man1);
        entityManager.persist(man2);

        transaction.commit();

        // test fetch strategies
        ManRepository manRepository = new ManRepository();
        manRepository.testFetchStrategies();

        // test CascadeType.ALL
        System.out.println("Testing CascadeType.ALL...");
        transaction = entityManager.getTransaction();
        transaction.begin();

        // update an existing man's address
        Man man3 = entityManager.find(Man.class, 1L);
        Address address3 = man3.getAddress();
        address3.setStreet("789 Elm St");
        address3.setCity("Anycity");
        address3.setState("TX");
        address3.setZipCode("54321");

        // delete a man and his address
        Man man4 = entityManager.find(Man.class, 2L);
        entityManager.remove(man4);

        transaction.commit();

        // verify changes
        System.out.println("Verifying CascadeType.ALL changes...");
        System.out.println("Remaining men: ");
        List<Man> men = entityManager.createQuery("SELECT m FROM Man m", Man.class).getResultList();
        for (Man man : men) {
            System.out.println(man.getName());
            Address address = man.getAddress();
            if (address != null) {
                System.out.println("  " + address.getStreet());
                System.out.println("  " + address.getCity() + ", " + address.getState() + " " + address.getZipCode());
            } else {
                System.out.println("  No address found");
            }
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
