package com.prim.factory.i18n;

public class I18nFactory {

    //静态工厂
    public static I18n getI18n(String area) {
        if (area.equals("chain")) {
            return new Chinese();
        } else if (area.equals("spain")) {
            return new Spainish();
        } else if (area.equals("Italy")) {
            return new Italian();
        } else {
            return null;
        }
    }
}
