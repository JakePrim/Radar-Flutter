package com.homework.homeword01.factory;

import com.homework.homeword01.dao.SClassDao;
import com.homework.homeword01.dao.impl.SClassDaoImpl;

public class SClassFactory {
    public static SClassDao getSClassDao(){
        return new SClassDaoImpl();
    }
}
