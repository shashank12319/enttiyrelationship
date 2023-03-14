package com.example.demo.repository;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Address;
import com.example.demo.model.Man;

@Repository
public class ManRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveManWithAddress(Man man, Address address) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(address); // persist address first
        man.setAddress(address); // set address on man entity
        entityManager.persist(man); // persist man entity with cascading to address
        transaction.commit();
    }

    public Man findManById(Long id) {
        return entityManager.find(Man.class, id);
    }

    public List<Man> findAllMen() {
        TypedQuery<Man> query = entityManager.createQuery("SELECT m FROM Man m", Man.class);
        return query.getResultList();
    }

    public void updateMan(Man man) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(man); // merge updated man entity
        transaction.commit();
    }

    public void deleteManById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Man man = entityManager.find(Man.class, id);
        if (man != null) {
            entityManager.remove(man); // remove man entity and associated address entity
        }
        transaction.commit();
    }
    public void testFetchStrategies() {
        // test fetch strategies
        System.out.println("Testing fetch strategies...");
        System.out.println("FetchType.LAZY:");
        Query query = (Query) entityManager.createQuery("SELECT m FROM Man m JOIN FETCH m.address WHERE m.id = :id");
        ((javax.persistence.Query) query).setParameter("id", 1L);
        Man man1 = (Man) ((javax.persistence.Query) query).getSingleResult();
        System.out.println(man1.getName() + " - " + man1.getAddress().getStreet());

        System.out.println("FetchType.EAGER:");
        Man man2 = entityManager.find(Man.class, 2L);
        System.out.println(man2.getName() + " - " + man2.getAddress().getStreet());
    }

}
