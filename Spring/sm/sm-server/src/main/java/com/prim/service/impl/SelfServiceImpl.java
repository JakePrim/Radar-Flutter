package com.prim.service.impl;

import com.prim.dao.SelfDao;
import com.prim.dao.StaffDao;
import com.prim.pojo.Staff;
import com.prim.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("selfService")
public class SelfServiceImpl implements SelfService {
    @Autowired
    @Qualifier("selfDao")
    private SelfDao selfDao;

    @Qualifier("staffDao")
    @Autowired
    private StaffDao staffDao;

    @Override
    public Staff login(String account, String password) {
        Staff staff = selfDao.login(account);
        if (staff != null) {
            if (staff.getPassword().equals(password)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public void changePassword(Integer id, String password) {
        Staff staff = staffDao.selectOne(id);
        staff.setPassword(password);
        staffDao.update(staff);
    }
}
