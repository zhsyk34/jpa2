package com.cat.service;

import com.cat.entity.Build;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BuildService {

    void save(Build build, Long communityId);

    int delete(Long id);

    int delete(Long[] ids);

    void update(Build build, Long communityId);

    Build find(Long id);

    List<Build> findList(String name, Long communityId, LocalDate begin, LocalDate end);

    List<Map> findMap(String name, Long communityId, LocalDate begin, LocalDate end);

    int count(String name, Long communityId, LocalDate begin, LocalDate end);
}
