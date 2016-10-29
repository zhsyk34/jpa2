package com.cat.dao.impl;

import com.cat.dao.UserDao;
import com.cat.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User, Long> implements UserDao {
}
