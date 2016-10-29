package com.cat.dao;

import com.cat.entity.User;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoTest extends CommonDaoTest {

	@Resource
	private UserDao userDao;
	@Resource
	private EntityManagerFactory entityManagerFactory;

	@Test
	public void init() throws Exception {
		System.out.println(userDao);
		System.out.println(entityManagerFactory.createEntityManager());
	}

	@Test
	public void save() throws Exception {
		User user = new User();
		user.setName("gm");
		//userDao.save(user);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(user);
		transaction.commit();
	}

	@Test
	public void save2() throws Exception {
		User user = new User();
		user.setName("gm2");
		userDao.save(user);
	}

	@Test
	public void saves() throws Exception {
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setName("player" + i);

			userDao.save(user);
		}
	}

	@Test
	public void saves2() throws Exception {
		List<User> users = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setName("player" + i);
			users.add(user);
		}
		userDao.saves(users);
	}

	@Test
	public void find() throws Exception {
		User user = userDao.findById(1L);
		print(user);
	}

	@Test
	public void findList() throws Exception {
		List<User> list = userDao.findList();
		print(list);
	}

	@Test
	public void update() throws Exception {
		User user = userDao.findById(1L);
		if (user != null) {
			user.setName("player");
			user.setUpdateTime(LocalDateTime.now());

			userDao.update(user);
		}
	}

	@Test
	public void update2() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setName("player2");
		user.setUpdateTime(LocalDateTime.now());

		userDao.update(user);
	}

	@Test
	public void update3() throws Exception {
		User user = new User();
		//user.setId(3L);//没有id或者不存在指定id的对象 都会报错
		user.setName("player2");
		user.setUpdateTime(LocalDateTime.now());

		userDao.update(user);
	}

	@Test
	public void merge() throws Exception {
		User user = new User();
		user.setId(1L);//update3()的情况均为Insert
		user.setName("super");
		user.setUpdateTime(LocalDateTime.now());

		userDao.merge(user);
	}

	@Test
	public void deleteById() throws Exception {
		System.out.println(userDao.deleteById(1L));
	}

	@Test
	public void deleteEntity() throws Exception {
		User user = new User().setId(5L);//detach exception
		userDao.deleteByEntity(user);
	}

	@Test
	public void deleteEntity2() throws Exception {
		User user = userDao.findById(5L);
		userDao.deleteByEntity(user);
	}

	@Test
	public void delete2() throws Exception {
		System.out.println(userDao.deleteByIds(Arrays.asList(new Long[]{1L, 2L})));
	}

	@Test
	public void delete3() throws Exception {
		System.out.println(userDao.deleteByIds(new Long[]{1L, 3L, 2L}));
	}

	@Test
	public void delete4() throws Exception {
		List<User> list = userDao.findList();
		userDao.deleteByEntities(list);
	}

	@Test
	public void count() throws Exception {
		userDao.count();
	}

	@Test
	public void contains() throws Exception {
		User user = new User().setId(6L);
		User user1 = userDao.findById(6L);
		System.out.println(userDao.contains(user));
		System.out.println(userDao.contains(user1));
	}

	@Test
	public void deleteAll() throws Exception {
		userDao.deleteAll();
	}
}