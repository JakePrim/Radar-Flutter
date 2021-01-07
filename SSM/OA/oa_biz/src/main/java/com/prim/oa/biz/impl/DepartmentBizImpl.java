package com.prim.oa.biz.impl;

import com.prim.oa.biz.DepartmentBiz;
import com.prim.oa.dao.DepartmentDao;
import com.prim.oa.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务层的实现类
 *
 * @author prim
 */
@Service("departmentBiz")
public class DepartmentBizImpl implements DepartmentBiz {

    @Resource(name = "departmentDao")
    private DepartmentDao departmentDao;

    public void add(Department department) {
        departmentDao.insert(department);
    }

    public void edit(Department department) {
        departmentDao.update(department);
    }

    public void remove(String sn) {
        departmentDao.delete(sn);
    }

    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
