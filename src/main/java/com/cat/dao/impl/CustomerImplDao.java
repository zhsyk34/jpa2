package com.cat.dao.impl;

import com.cat.dao.CustomerDao;
import com.cat.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerImplDao extends CommonDaoImpl<Customer, Long> implements CustomerDao {
}
