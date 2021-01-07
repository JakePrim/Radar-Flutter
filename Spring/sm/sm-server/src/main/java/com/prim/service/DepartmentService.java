package com.prim.service;

import com.prim.pojo.Department;

import java.util.List;

/**
 * @author prim
 */
public interface DepartmentService {
    void add(Department department);

    void remove(Integer id);

    void edit(Department department);

    Department get(Integer id);

    List<Department> getAll();
}
