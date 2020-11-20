package com.prim.autoproxy;

/**
 * @author prim
 */
public class StudentDaoImpl implements StudentDao {
    @Override
    public void save() {
        System.out.println("StudentDaoImpl.save");
    }

    @Override
    public void find() {
        System.out.println("StudentDaoImpl.find");
    }

    @Override
    public void delete() {
        System.out.println("StudentDaoImpl.delete");
    }

    @Override
    public void update() {
        System.out.println("StudentDaoImpl.update");
    }
}
