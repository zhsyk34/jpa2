package com.cat.kit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

public class QueryKit {

    private final EntityManager manager;

    public QueryKit(EntityManager entityManager) {
        this.manager = entityManager;
    }

    private CriteriaBuilder builder() {
        return manager.getCriteriaBuilder();
    }

    public void init() {
        CriteriaBuilder builder = builder();

//        Predicate predicate = builder.equal();
    }
}
