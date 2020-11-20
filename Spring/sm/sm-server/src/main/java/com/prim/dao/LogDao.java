package com.prim.dao;

import com.prim.pojo.Log;
import com.prim.pojo.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("logDao")
public interface LogDao {
    void insert(Log log);
    List<Log> selectByType(String type);
}
