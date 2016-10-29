package com.cat.dao;

import com.cat.entity.Build;

public interface BuildDao extends CommonDao<Build, Long> {

	void save(Build build, Long communityId);

	void save2(Build build, Long communityId);
}
