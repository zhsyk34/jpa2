package com.cat.dao.impl;

import com.cat.dao.BuildDao;
import com.cat.entity.Build;
import org.springframework.stereotype.Repository;

@Repository
public class BuildDaoImpl extends CommonDaoImpl<Build, Long> implements BuildDao {

	/*@Resource
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
		community = super.manager().merge(community);
		build.setCommunity(community);

		super.save(build);
	}*/

}
