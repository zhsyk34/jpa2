package com.cat.service.impl;

import com.cat.dao.BuildDao;
import com.cat.dao.CommunityDao;
import com.cat.entity.Build;
import com.cat.entity.Community;
import com.cat.service.BuildService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BuildServiceImpl implements BuildService {

    @Resource
    private CommunityDao communityDao;
    @Resource
    private BuildDao buildDao;

    @Override
    public void save(Build build, Long communityId) {
        Community community = communityDao.findById(communityId);
        if (community == null) {
            throw new RuntimeException();
        }
        build.setCommunity(community);
        buildDao.save(build);
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int delete(Long[] ids) {
        return 0;
    }

    @Override
    public void update(Build build, Long communityId) {

    }

    @Override
    public Build find(Long id) {
        return null;
    }

    @Override
    public List<Build> findList(String name, Long communityId, LocalDate begin, LocalDate end) {
        return null;
    }

    @Override
    public List<Map> findMap(String name, Long communityId, LocalDate begin, LocalDate end) {
        EntityManager manager = buildDao.manager();

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Map> query = builder.createQuery(Map.class);

        Root<Build> buildRoot = query.from(Build.class);
        Root<Community> communityRoot = query.from(Community.class);

        query.from(Build.class);

        manager.createQuery(query).getResultList();
        return null;
    }

    @Override
    public int count(String name, Long communityId, LocalDate begin, LocalDate end) {
        return 0;
    }

}
