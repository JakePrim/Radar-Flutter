package com.prim.oa.dao;

import com.prim.oa.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prim
 */
@Repository("departmentDao")
public interface DepartmentDao {
    void insert(Department department);

    void update(Department department);

    void delete(String sn);

    Department select(String sn);

    List<Department> selectAll();
}
