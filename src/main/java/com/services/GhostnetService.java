package com.services;

import dtos.AuthCookieDto;
import entities.Ghostnet;
import entities.Pojo;
import entities.User;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@ManagedBean
@SessionScoped
@Named("ghostnetService")
public class GhostnetService {
    EntityManager entityManager;

    public GhostnetService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ghost-net-app");
        entityManager = factory.createEntityManager();
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
        String jpql = "SELECT p from Ghostnet p";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(first).setMaxResults(pageSize);
        List<Ghostnet> result = (List<Ghostnet>) query.getResultList();
        // currently ignoring sort-fields and filter
        return result;
    }


}
