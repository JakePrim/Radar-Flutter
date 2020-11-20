package com.prim.service.impl;

import com.prim.dao.DepartmentDao;
import com.prim.pojo.Department;
import com.prim.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务类 @Service注入交由Spring实例化DepartmentServiceImpl类
 * @author prim
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Qualifier("departmentDao")
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void add(Department department) {
        departmentDao.insert(department);
    }

    @Override
    public void remove(Integer id) {
        departmentDao.delete(id);
    }

    @Override
    public void edit(Department department) {
        departmentDao.update(department);
    }

    @Override
    public Department get(Integer id) {
        return departmentDao.selectOne(id);
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
