package com.prim.dao;

import com.prim.pojo.Staff;
import org.springframework.stereotype.Repository;

/**
 * @author prim
 */
@Repository("selfDao")
public interface SelfDao {
    Staff login(String account);
}
