package com.prim.factory;

import com.prim.factory.i18n.I18n;
import com.prim.factory.i18n.I18nFactory;

import java.util.Arrays;

public class Software {
    public static void main(String[] args) {
        I18n i18n = I18nFactory.getI18n("Italy");
        System.out.println("args = " + i18n.getTitle());
    }
}
