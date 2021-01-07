package com.prim.dao;

import com.prim.pojo.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prim
 */
@Repository("staffDao")
public interface StaffDao {
    void insert(Staff staff);

    void update(Staff staff);

    void delete(Integer id);

    Staff selectOne(Integer id);

    List<Staff> selectAll();
}
