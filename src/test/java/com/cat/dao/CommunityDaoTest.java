package com.cat.dao;

import com.cat.entity.Build;
import com.cat.entity.Community;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommunityDaoTest extends CommonDaoTest {

    @Resource
    private CommunityDao communityDao;

    @Test
    public void save() throws Exception {
        Community community = new Community().setName("community");
        Set<Build> builds = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            builds.add(new Build().setCommunity(community).setName("b" + i));
        }
        community.setBuilds(builds);
        communityDao.save(community);
    }

    @Test
    public void saves() throws Exception {
        List<Community> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Community().setName("com" + i));
        }
        communityDao.saves(list);
    }

    @Test
    public void update() throws Exception {
        Community community = new Community().setId(1L);
        communityDao.update(community);
    }

    @Test
    public void delete() throws Exception {
        System.out.println(communityDao.deleteById(1L));
    }

    @Test
    public void findList() throws Exception {
        List<Community> list = communityDao.findList("z", null, null);
        list.forEach(community -> {
            System.out.println(community.getName() + " " + community.getCreateTime());
        });
    }

    @Test
    public void findList2() throws Exception {
        List<Community> list = communityDao.findList("t", LocalDate.of(2013, 12, 13), LocalDate.of(2015, 11, 12));
        list.forEach(community -> {
            System.out.println(community.getId() + " " + community.getName() + " " + community.getCreateTime());
        });
    }
}