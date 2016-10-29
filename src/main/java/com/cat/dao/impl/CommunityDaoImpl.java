package com.cat.dao.impl;

import com.cat.dao.CommunityDao;
import com.cat.entity.Community;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommunityDaoImpl extends CommonDaoImpl<Community, Long> implements CommunityDao {

    @Override
    public List<Community> findList(String name, LocalDate begin, LocalDate end) {
        EntityManager manager = super.manager();

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Community> query = builder.createQuery(Community.class);

        Root<Community> root = query.from(Community.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Predicate predicate = builder.like(root.get("name"), "%" + name + "%");
            predicates.add(predicate);
        }

        Path<LocalDateTime> createTime = root.get("createTime");
        if (begin != null) {
            Predicate predicate = builder.greaterThan(createTime, LocalDateTime.of(begin, LocalTime.NOON));
            predicates.add(predicate);
        }
        if (end != null) {
            Predicate predicate = builder.lessThan(createTime, LocalDateTime.of(end, LocalTime.NOON));
            predicates.add(predicate);
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return manager.createQuery(query).getResultList();
    }

    /*public <SK> List<Build> findAllByAnyAttributes(AttributesHashMap<Build> attributes, SingularAttribute<Build, SK> order) {

        final CriteriaBuilder builder = super.manager().getCriteriaBuilder();

        final CriteriaQuery<Build> criteria = builder.createQuery(Build.class);

        final Root<Build> root = criteria.from(Build.class);
        criteria.select(root);

        Predicate where = builder.conjunction();
        for (Entry<SingularAttribute<Build, Object>, Object> attr : attributes.entrySet()) {
            where = builder.or(where, builder.equal(root.get(attr.getKey()), attr.getValue()));
        }
        criteria.where(where);

        if (order != null) {
            criteria.orderBy(builder.asc(root.get(order)));
        }
        List<E> results = getEntityManager().createQuery(criteria).getResultList();

        return results;
    }*/
}
