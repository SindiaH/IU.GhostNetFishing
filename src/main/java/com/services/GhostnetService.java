package com.services;

import entities.Ghostnet;
import entities.Pojo;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Query;
import java.util.List;

@RequestScoped
@ManagedBean
public class GhostnetService {
//    private DataStore dataStore;
//
//    private void addData(Ghostnet ghostnet) {
//        dataStore.entityManager.getTransaction().begin();
//        dataStore.entityManager.persist(ghostnet);
//        dataStore.entityManager.getTransaction().commit();
//    }
//
//    private void updateData(Ghostnet ghostnet) {
//        dataStore.entityManager.getTransaction().begin();
//        dataStore.entityManager.merge(ghostnet);
//        dataStore.entityManager.getTransaction().commit();
//    }
//
//    private List<Ghostnet> readData(int first, int maxCount) {
//        String jpql = "SELECT p from Ghostnet p";
//        Query query = dataStore.entityManager.createQuery(jpql);
//        query.setFirstResult(first).setMaxResults(maxCount);
//        List<Ghostnet> result = (List<Ghostnet>) query.getResultList();
//        return result;
//    }
}
