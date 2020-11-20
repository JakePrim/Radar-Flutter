package com.prim.service.impl;

import com.prim.dao.StaffDao;
import com.prim.pojo.Staff;
import com.prim.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author prim
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {

    @Qualifier("staffDao")
    @Autowired
    private StaffDao staffDao;

    @Override
    public void add(Staff staff) {
        //添加的新的员工默认密码都是123456
        staff.setPassword("123456");
        staff.setWorkTime(new Date());
        staff.setStatus("正常");
        staffDao.insert(staff);
    }

    @Override
    public void remove(Integer id) {
        staffDao.delete(id);
    }

    @Override
    public void edit(Staff staff) {
        staffDao.update(staff);
    }

    @Override
    public Staff get(Integer id) {
        return staffDao.selectOne(id);
    }

    @Override
    public List<Staff> getAll() {
        return staffDao.selectAll();
    }
}
