package com.cat.kit;

import com.cat.dao.CommonDaoTest;
import com.cat.dao.CustomerDao;
import com.cat.entity.Build;
import com.cat.entity.Community;
import com.cat.entity.Customer;
import com.cat.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SetAttribute;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"JpaQlInspection", "Duplicates"})
public class APITest extends CommonDaoTest {
    @PersistenceContext
    private EntityManager manager;
    @Resource
    private CustomerDao customerDao;

    @Test
    public void init() throws Exception {
        System.out.println(manager);
    }

    @Test
    public void init2() throws Exception {
        for (int i = 0; i < 8; i++) {
            Customer customer = new Customer().setName("a").setDate(LocalDate.now());
            customer.setPrice(BigDecimal.valueOf(Math.random() * 1000));
            customerDao.save(customer);
        }
    }

    @Test
    public void query() throws Exception {
        TypedQuery<User> query = manager.createQuery("select user from User user", User.class);
        List<User> list = query.getResultList();
        list.forEach(user -> System.out.println(user.getId() + " " + user.getName()));
    }

    @Test
    public void query1() throws Exception {
        String sql = "select user from User user where name = :name";
        TypedQuery<User> query = manager.createQuery(sql, User.class);
        query.setParameter("name", "a");
        List<User> list = query.getResultList();
        list.forEach(user -> System.out.println(user.getId() + " " + user.getName()));
        User user = manager.createQuery(sql, User.class).setParameter("name", "a").getSingleResult();
        System.out.println(user);
    }

    @Test
    public void query1_1() throws Exception {
        String sql = "select user from User user where name = :name and createTime > :time";
        TypedQuery<User> query = manager.createQuery(sql, User.class);
        query.setParameter("name", "a");
        query.setParameter("time", new Date(), TemporalType.DATE);//type
        List<User> list = query.getResultList();
        list.forEach(user -> System.out.println(user.getId() + " " + user.getName()));
    }

    @Test
    public void query2() throws Exception {
        String sql = "select user from User user where name = :name";
        //javax.persistence.NoResultException: No entity found for query
        //User user = manager.createQuery(sql, User.class).setParameter("name", "sm").getSingleResult();
        TypedQuery<User> query = manager.createQuery(sql, User.class).setParameter("name", "sm");
        User user = null;
        //1.
       /* try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }*/
        //2.
        user = query.getResultList().stream().findFirst().orElse(null);

        System.out.println(user);
    }

    @Test
    public void query3() throws Exception {
        String sql = "select user from User user where name = ?1";//?index
        //javax.persistence.NoResultException: No entity found for query
        //User user = manager.createQuery(sql, User.class).setParameter("name", "sm").getSingleResult();
        TypedQuery<User> query = manager.createQuery(sql, User.class).setParameter(1, "a");
        User user = query.getResultList().stream().findFirst().orElse(null);

        System.out.println(user.getId() + " " + user.getName());
    }

    @Test
    public void query4() throws Exception {
        String sql = "select user from User user where name = '" + "a" + "'";//?index
        //javax.persistence.NoResultException: No entity found for query
        //User user = manager.createQuery(sql, User.class).setParameter("name", "sm").getSingleResult();
        TypedQuery<User> query = manager.createQuery(sql, User.class);
        User user = query.getResultList().stream().findFirst().orElse(null);

        System.out.println(user.getId() + " " + user.getName());
    }

    @Test
    public void criteria() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        //sql?
        CriteriaQuery<User> search = builder.createQuery(User.class);
        Root<User> root = search.from(User.class);
        search.select(root);
        //
        TypedQuery<User> query = manager.createQuery(search);
        List<User> list = query.getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void criteria1() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        //sql?
        CriteriaQuery<User> search = builder.createQuery(User.class);
        //search api?
//        search.select();
//        search.from();
//        search.where();
//        search.distinct();
//        search.groupBy();
//        search.having();
//        search.multiselect();
//        search.subquery();
//        //
        Root<User> root = search.from(User.class);
//        EntityType<User> entityType = root.getModel();
//        System.out.println(entityType.getName());
        search.select(root);

        ParameterExpression<String> parameter = builder.parameter(String.class);
//        Path<Object> name = root.get("name");
        Expression<Object> name = root.get("name");
        Predicate predicate = builder.equal(name, parameter);
        search.where(predicate);
        //
        TypedQuery<User> query = manager.createQuery(search);
        query.setParameter(parameter, "a");
        List<User> list = query.getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void criteria1_2() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Object> query = builder.createQuery();
        Root<User> root = query.from(User.class);
        query.select(root);

        Predicate predicate1 = builder.like(root.get("name"), "%a%");
        Predicate predicate2 = builder.like(root.get("name"), "%b%");
//        Predicate predicate = builder.and(predicate1, predicate2);
        Predicate predicate = builder.or(predicate1, predicate2);
        query.where(predicate);

        List<Object> list = manager.createQuery(query).getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void criteria2() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Object> query = builder.createQuery();
        Root<Build> root = query.from(Build.class);
        query.select(root);

        Join<Object, Object> join = root.join("community");

        List<Object> list = manager.createQuery(query).getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void criteria3() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Community> query = builder.createQuery(Community.class);
        Root<Community> root = query.from(Community.class);
        query.select(root);
        SetAttribute<? super Community, ?> build = root.getModel().getSet("builds");
        SetJoin<Community, ?> join = root.join(build, JoinType.LEFT);
//        root.join("builds", JoinType.INNER);

        List<Community> list = manager.createQuery(query).getResultList();
        list.forEach(community -> System.out.println(community.getId() + " " + community.getName()));
    }

    //order by
    @Test
    public void criteria4() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Community> query = builder.createQuery(Community.class);
        Root<Community> root = query.from(Community.class);
        query.select(root);
        query.orderBy(builder.asc(root.get("name")));

        List<Community> list = manager.createQuery(query).getResultList();
        list.forEach(community -> System.out.println(community.getId() + " " + community.getName()));
    }

    //group by
    @Test
    public void criteria5() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Customer> root = query.from(Customer.class);
        Path<String> name = root.get("name");
        Expression<Long> count = builder.count(name);
        query.groupBy(name);
        //query.having(builder.or(builder.like(name, "%a%"), builder.like(name, "%c%")));
        //TODO

        query.multiselect(builder.tuple(name), count);

        List<Object[]> list = manager.createQuery(query).getResultList();
        list.forEach(objects -> {
            for (int i = 0; i < objects.length; i++) {
                System.out.println(objects[i]);
            }
        });
    }

    @Test
    public void criteria6() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Object> query = builder.createQuery();
        Root<Community> root = query.from(Community.class);
        query.select(root);
        root.fetch("builds");

        List<Object> list = manager.createQuery(query).getResultList();
        list.forEach(o -> {
            try {
                super.print(o);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void criteria8() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<Customer> root = query.from(Customer.class);
        //get entity property name
        query.multiselect(root.get("price"), root.get("name"), root.get("id"));

        List<Tuple> list = manager.createQuery(query).getResultList();
        for (Tuple tuple : list) {
            Object[] array = tuple.toArray();
            for (int i = 0; i < array.length; i++) {
                System.out.print(tuple.get(i) + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void criteria9() throws Exception {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
//        builder.construct();
//        builder.array();

    }
}