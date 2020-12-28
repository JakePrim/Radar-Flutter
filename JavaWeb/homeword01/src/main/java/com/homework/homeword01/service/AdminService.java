package com.homework.homeword01.service;

import com.homework.homeword01.dao.AdminDao;
import com.homework.homeword01.factory.AdminFactory;
import com.homework.homeword01.pojo.Admin;

public class AdminService {
    private AdminDao adminDao;

    public AdminService() {
        this.adminDao = AdminFactory.getAdminDao();
    }

    public Admin adminLogin(Admin admin) {
        return adminDao.login(admin);
    }
}
