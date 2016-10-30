package com.cat.dao;

import com.cat.entity.Community;

import java.time.LocalDate;
import java.util.List;

public interface CommunityDao extends CommonDao<Community, Long> {

    List<Community> findList(String name, LocalDate begin, LocalDate end);

    long count(String name, LocalDate begin, LocalDate end);
}
