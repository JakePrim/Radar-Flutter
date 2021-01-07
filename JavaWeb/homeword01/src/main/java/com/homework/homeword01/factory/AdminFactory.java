package com.homework.homeword01.factory;

import com.homework.homeword01.dao.AdminDao;
import com.homework.homeword01.dao.impl.AdminDaoImpl;

public class AdminFactory {
    public static AdminDao getAdminDao() {
        return new AdminDaoImpl();
    }
}
