package com.cat.kit;

import com.cat.entity.Build;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//http://www.objectdb.com/java/jpa/persistence/metamodel
public class API {

    @SuppressWarnings("JpaQlInspection")
    @Entity
    @ExcludeSuperclassListeners
    @EntityListeners({})
    @NamedQuery(name = "findList", query = "select user from User user")
    public static class MyEntityWithCallbacks {
        @PrePersist
        void onPrePersist() {
        }

        @PostPersist
        void onPostPersist() {
        }

        @PostLoad
        void onPostLoad() {
        }

        @PreUpdate
        void onPreUpdate() {
        }

        @PostUpdate
        void onPostUpdate() {
        }

        @PreRemove
        void onPreRemove() {
        }

        @PostRemove
        void onPostRemove() {
        }
    }

    @Resource
    private EntityManager manager;

    //Criteria Query Structure
    public void api() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery();
        //select
//        query.select().distinct().multiselect();
//        builder.array();
//        builder.tuple();
//        builder.construct();
        //
//        query.from();
//        query.where();
        //
//        query.groupBy();
//        query.having();
//        builder.count();
//        builder.sum();
//        builder.avg();
//        builder.min();
//        builder.max();

//        builder.isMember();
        builder.function("", Long.class, builder.literal(""));

        //http://www.objectdb.com/java/jpa/query/jpql/literal#Criteria_Query_Literals_
        builder.literal(true);
    }

    public void query() {
        //1.createQuery
//        String sql = "select name from build";
//        Query query = manager.createQuery(sql);
//        query.getResultList();
//        query = manager.createQuery("select count(*) from build", Long.class);
//        query.getSingleResult();
//        query = manager.createQuery("update ...");
//        int i = query.executeUpdate();
//
//        query = manager.createQuery("select name from build where name = :name", Build.class);
//        query.setParameter("name", "");
//        TypedQuery<Build> query2 = manager.createQuery("select name from build where name = ?1", Build.class);
//        query2.setParameter(1, "");
//        //2.
//        manager.createNamedQuery("findList");
    }

    public void model() {
        Metamodel meta = manager.getMetamodel();

        Set<ManagedType<?>> managedTypes = meta.getManagedTypes();
        ManagedType<Build> managedType = meta.managedType(Build.class);
        managedType.getJavaType();
        managedType.getPersistenceType();
        //include inherited
        Set<Attribute<? super Build, ?>> attributes = managedType.getAttributes();
        managedType.getAttribute("");
        //exclude inherited
        managedType.getDeclaredAttributes();
        managedType.getDeclaredAttribute("");

        Set<EntityType<?>> entityTypes = meta.getEntities();
        EntityType<Build> entityType = meta.entity(Build.class);
        IdentifiableType<? super Build> supertype = entityType.getSupertype();
        boolean hasSingleIdAttribute = entityType.hasSingleIdAttribute();
        entityType.getId(Long.class);//明确知道？
        SingularAttribute<? super Build, ?> singularAttribute = entityType.getId(entityType.getIdType().getJavaType());
        singularAttribute.getType();
        singularAttribute.getName();
        singularAttribute.getJavaType();
        singularAttribute.getJavaMember();
        singularAttribute.getDeclaringType();
        entityType.getDeclaredId(Long.class);
        boolean hasVersionAttribute = entityType.hasVersionAttribute();
        SingularAttribute<? super Build, Long> entityTypeVersion = entityType.getVersion(Long.class);
        entityType.getName();

        Set<EmbeddableType<?>> embeddableTypes = meta.getEmbeddables();
        meta.embeddable(Build.class);

    }

    public void advance(Build build) {
        //detach
        manager.detach(build);
        CascadeType detach = CascadeType.DETACH;
        manager.getTransaction().rollback();
        manager.clear();
        manager.close();

        //merge
        manager.merge(build);
        CascadeType merge = CascadeType.MERGE;

        //lock
        //optimistic乐观锁
        //pessimistic悲观锁
        manager.lock(build, LockModeType.OPTIMISTIC);
        manager.lock(build, LockModeType.PESSIMISTIC_READ);
        Map<String, Object> properties = new HashMap();
        manager.find(Build.class, 1, LockModeType.NONE, properties);

        //cache
        EntityManagerFactory factory = manager.getEntityManagerFactory();
        Cache cache = factory.getCache();
        cache.contains(Build.class, 1L);
        cache.evict(Build.class);
        cache.evictAll();

    }

    public void base() {
        Build build = new Build();
        //store
        manager.persist(build);
        //retrieval
        manager.find(Build.class, 1);
        manager.getReference(Build.class, 1);
        FetchType fetchType = FetchType.EAGER;

        PersistenceUtil util = Persistence.getPersistenceUtil();
        util.isLoaded(build);
        util.isLoaded(build, "");
        manager.refresh(build);

        //update
        manager.getTransaction().commit();
        //delete
        manager.remove(build);
        CascadeType cascadeType = CascadeType.REMOVE;
        //orphanRemoval=true
    }

}
