package com.cat.dao.impl;

import com.cat.dao.CommunityDao;
import com.cat.entity.Community;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityDaoImpl extends CommonDaoImpl<Community, Long> implements CommunityDao {
}
