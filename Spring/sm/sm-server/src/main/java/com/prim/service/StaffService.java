package com.prim.service;

import com.prim.pojo.Department;
import com.prim.pojo.Staff;

import java.util.List;

/**
 * @author prim
 */
public interface StaffService {
    void add(Staff staff);

    void remove(Integer id);

    void edit(Staff staff);

    Staff get(Integer id);

    List<Staff> getAll();
}
