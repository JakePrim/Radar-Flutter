package com.prim.dao;

import com.prim.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository可以注入接口吗？Spring是如何实例化类的？
 * @author prim
 */
@Repository("departmentDao")
public interface DepartmentDao {
    void insert(Department department);

    void update(Department department);

    void delete(Integer id);

    Department selectOne(Integer id);

    List<Department> selectAll();
}
