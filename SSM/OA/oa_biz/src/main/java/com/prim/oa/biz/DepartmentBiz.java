package com.prim.oa.biz;

import com.prim.oa.pojo.Department;

import java.util.List;

/**
 * @author prim
 */
public interface DepartmentBiz {
    void add(Department department);

    void edit(Department department);

    void remove(String sn);

    Department get(String sn);

    List<Department> getAll();
}
