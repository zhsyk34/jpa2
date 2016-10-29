package com.cat.dao.impl;

import com.cat.dao.BuildDao;
import com.cat.dao.CommunityDao;
import com.cat.entity.Build;
import com.cat.entity.Community;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class BuildDaoImpl extends CommonDaoImpl<Build, Long> implements BuildDao {

	@Resource
	private CommunityDao communityDao;

	@Override
	public void save(Build build, Long communityId) {
		Community community = communityDao.findById(1L);
		if (community != null) {
			build.setCommunity(community);
		}
		super.save(build);
	}

	@Override
	public void save2(Build build, Long communityId) {
		Community community = new Community().setId(1L);
		super.manager().merge(community);
		build.setCommunity(community);

		super.save(build);
	}

}
