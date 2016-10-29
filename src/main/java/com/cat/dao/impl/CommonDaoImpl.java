package com.cat.dao.impl;

import com.cat.dao.CommonDao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CommonDaoImpl<E, K extends Serializable> implements CommonDao<E, K> {
//	@Resource
//	@PersistenceUnit
//	private EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<E> clazz;
	private final Class<E> key;
	private static String id;

	public CommonDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type != null && type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			clazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
			key = (Class<E>) parameterizedType.getActualTypeArguments()[1];
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public final EntityManager manager() {
//		return entityManagerFactory.createEntityManager();
		return entityManager;
	}

	@PostConstruct
	protected final void id() {
//		Metamodel metamodel = entityManagerFactory.getMetamodel();
		Metamodel metamodel = entityManager.getMetamodel();

		EntityType<E> entityType = metamodel.entity(clazz);

		Class<?> idType = entityType.getIdType().getJavaType();
		if (idType != key) {
			throw new RuntimeException("primary key type error in " + clazz.getSimpleName());
		}
		id = entityType.getId(key).getName();

//		System.out.println("init:" + clazz.getSimpleName() + ", primary key:" + id);
		if (id == null || id.isEmpty()) {
			throw new RuntimeException("can't find the key in " + clazz.getSimpleName());
		}
	}

	@Override
	public void save(E e) {
		assert e != null;
		manager().persist(e);
	}

	@Override
	public void saves(Collection<E> es) {
		assert es != null && !es.isEmpty();
		es.forEach(this::save);

		/*EntityManager manager = manager();
		if (es instanceof List) {
			List<E> list = (List<E>) es;
			for (int i = 0; i < es.size(); i++) {
				E e = list.get(i);
				manager.persist(e);
				if ((i % 3) == 2) {
					System.err.println("batch save.");
					manager.flush();
				}
			}
		}*/
	}

	@Override
	public int deleteById(K k) {
		/*E e = this.findById(k);
		if (e == null) {
			return 0;
		}

		manager().remove(e);
		return 1;*/

		assert k != null;
		EntityManager manager = manager();

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaDelete<E> query = builder.createCriteriaDelete(clazz);
		query.where(builder.equal(query.from(clazz).get(id), k));

		return manager.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByIds(K[] ks) {
		if (ks == null || ks.length == 0) {
			return 0;
		}
		EntityManager session = manager();
		CriteriaDelete<E> query = session.getCriteriaBuilder().createCriteriaDelete(clazz);
		query.where(query.from(clazz).get(id).in(ks));
		return session.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByIds(Collection<K> ks) {
		if (ks == null || ks.isEmpty()) {
			return 0;
		}
		EntityManager session = manager();
		CriteriaDelete<E> query = session.getCriteriaBuilder().createCriteriaDelete(clazz);
		query.where(query.from(clazz).get(id).in(ks));
		return session.createQuery(query).executeUpdate();
	}

	@Override
	public void deleteByEntity(E e) {
		/*boolean contains = manager().contains(e);
		if (contains) {
			manager().remove(e);
		} else {
			manager().remove(manager().merge(e));
		}*/
		assert e != null;
		EntityManagerFactory entityManagerFactory = entityManager.getEntityManagerFactory();
		K id = (K) entityManagerFactory.getPersistenceUnitUtil().getIdentifier(e);
		this.deleteById(id);
	}

	@Override
	public void deleteByEntities(Collection<E> es) {
		assert es != null && !es.isEmpty();
		es.forEach(this::deleteByEntity);
	}

	@Override
	public void update(E e) {
		assert e != null;
		this.merge(e);
	}

	@Override
	public void merge(E e) {
		assert e != null;
		manager().merge(e);
	}

	@Override
	public boolean contains(E e) {
		assert e != null;
		return manager().contains(e);
	}

	@Override
	public E findById(K k) {
		assert k != null;
		return manager().find(clazz, k);
	}

	@Override
	public List<E> findList() {
		EntityManager session = manager();

		CriteriaQuery<E> query = session.getCriteriaBuilder().createQuery(clazz);
		query.from(clazz);

		return session.createQuery(query).getResultList();
	}

	@Override
	public long count() {
		EntityManager session = manager();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<E> root = query.from(clazz);
		query.select(builder.count(root));

		return session.createQuery(query).getSingleResult();
	}
}
