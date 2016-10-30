package com.cat.kit;

import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

//construct:构筑物
public class Construct {

    private EntityManager manager;

    public static void main(String[] args) {
        Criteria criteria;//标准
        Selection selection;//选择
        Expression expression;//表达
        Predicate predicate;//断言
        Order order;
    }

    public void one() {
//        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        CriteriaQuery<Object> query = builder.createQuery();
//        builder.createCriteriaUpdate();
//        builder.createCriteriaDelete();
//        builder.createTupleQuery();
//
//        Metamodel meta = manager.getMetamodel();
//        EntityType<User> entity = meta.entity(User.class);
//        Root<User> from = query.from(User.class);
//        Root<User> root = query.from(entity);

    }

    public void root() {

    }
}
