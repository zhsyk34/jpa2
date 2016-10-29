package com.cat.dao;

import com.cat.entity.Build;
import com.cat.entity.Community;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CommunityDaoTest extends CommonDaoTest {

	@Resource
	private CommunityDao communityDao;

	@Resource
	private BuildDao buildDao;

	@Test
	public void saves() throws Exception {
		List<Community> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			list.add(new Community().setName("com" + i));
		}
		communityDao.saves(list);
	}

	@Test
	public void save1() throws Exception {
		Build build = new Build();
		build.setName("build");

		buildDao.save(build, 1L);
	}

	@Test
	public void save2() throws Exception {
		Build build = new Build();
		build.setName("build");

		buildDao.save2(build, 1L);
	}
}