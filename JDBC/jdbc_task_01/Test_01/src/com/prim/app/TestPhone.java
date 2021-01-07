package com.prim.app;

import com.prim.dao.PhoneDao;
import com.prim.entity.Phone;

import java.sql.SQLException;
import java.util.List;

public class TestPhone {


    public static void main(String[] args) throws SQLException {
        PhoneDao phoneDao = new PhoneDao();
        List<Phone> phones = phoneDao.findByPhonesPrice(2000, "2019-1-1");
        for (Phone phone : phones) {
            System.out.println(phone);
        }

        System.out.println("颜色为白色的所有手机");
        List<Phone> phoneList = phoneDao.findByPhonesColor("白色");
        for (Phone phone : phoneList) {
            System.out.println(phone);
        }
    }

}
