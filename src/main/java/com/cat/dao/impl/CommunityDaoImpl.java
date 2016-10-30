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
            Predicate predicate = builder.greaterThanOrEqualTo(createTime, LocalDateTime.of(begin, LocalTime.MIN));
            predicates.add(predicate);
        }
        if (end != null) {
            Predicate predicate = builder.lessThanOrEqualTo(createTime, LocalDateTime.of(end, LocalTime.MIN));
            predicates.add(predicate);
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public long count(String name, LocalDate begin, LocalDate end) {
        EntityManager manager = super.manager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<Community> root = query.from(Community.class);
        query.select(builder.count(root));

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Predicate predicate = builder.like(root.get("name"), "%" + name + "%");
            predicates.add(predicate);
        }

        Path<LocalDateTime> createTime = root.get("createTime");
        if (begin != null) {
            Predicate predicate = builder.greaterThanOrEqualTo(createTime, LocalDateTime.of(begin, LocalTime.MIN));
            predicates.add(predicate);
        }
        if (end != null) {
            Predicate predicate = builder.lessThanOrEqualTo(createTime, LocalDateTime.of(end, LocalTime.MIN));
            predicates.add(predicate);
        }
        query.where(predicates.toArray(new Predicate[predicates.size()]));

        return manager.createQuery(query).getSingleResult();
    }

}
