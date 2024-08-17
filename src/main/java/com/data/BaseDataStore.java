package com.data;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDataStore {
    private static EntityManager entityManager;

    // Private constructor to prevent external instantiation
    private BaseDataStore() {}

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("ghost-net-app");
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
}
