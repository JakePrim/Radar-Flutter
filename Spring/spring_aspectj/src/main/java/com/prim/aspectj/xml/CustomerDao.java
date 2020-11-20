package com.prim.aspectj.xml;

/**
 * @author prim
 */
public interface CustomerDao {
    void save();

    String update();

    void delete();

    void findOne();

    void findAll();
}
