package com.data;

import entities.Ghostnet;
import entities.User;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class GhostnetDataStore {
    EntityManager entityManager;
    private static GhostnetDataStore instance;

    public GhostnetDataStore() {
        entityManager = BaseDataStore.getEntityManager();
    }

    public static GhostnetDataStore getInstance() {
        if (instance == null) {
            instance = new GhostnetDataStore();
        }
        return instance;
    }

    public void addGhostnet(Ghostnet ghostnet) {
        entityManager.getTransaction().begin();
        entityManager.persist(ghostnet);
        entityManager.getTransaction().commit();
    }

    public void updateGhostnet(Ghostnet ghostnet) {
        entityManager.getTransaction().begin();
        entityManager.merge(ghostnet);
        entityManager.getTransaction().commit();
    }

    public Ghostnet getGhostnet(int id) {
        String jpql = "SELECT p from Ghostnet p WHERE p.id = :id";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("id", id);
        List<Ghostnet> result = (List<Ghostnet>) query.getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public List<Ghostnet> readList(int first, int maxCount) {
        String jpql = "SELECT p from Ghostnet p";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(first).setMaxResults(maxCount);
        List<Ghostnet> result = (List<Ghostnet>) query.getResultList();
        return result;
    }

    public long readDataCount() {
        String jpql = "SELECT p FROM Ghostnet p";
        Query query = entityManager.createQuery(jpql);

        return query.getResultStream().count();
    }

    public List<Ghostnet> lazyRead(int first, int pageSize, Map<String, SortMeta> sortInfo, Map<String, FilterMeta> filterBy) {
        String jpql = "SELECT p from Ghostnet p order by p.CreatedAt desc";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(first).setMaxResults(pageSize);
        List<Ghostnet> result = (List<Ghostnet>) query.getResultList();
        // currently ignoring sort-fields and filter
        return result;
    }


}
