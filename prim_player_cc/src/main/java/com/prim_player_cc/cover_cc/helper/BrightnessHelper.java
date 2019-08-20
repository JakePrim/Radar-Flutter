package com.prim_player_cc.cover_cc.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.prim_player_cc.utils.Tools;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/9 0009
 * 描    述：调节屏幕亮度的辅助类
 * 修订历史：
 * ================================================
 */
public class BrightnessHelper {

    private ContentResolver resolver;

    private int maxBrightness = 255;

    private static final String TAG = "BrightnessHelper";

    public BrightnessHelper(Context context) {
        resolver = context.getContentResolver();
    }

    /*
    * 调整亮度范围
    */
    private int adjustBrightnessNumber(int brightness) {
        if (brightness < 0) {
            brightness = 0;
        } else if (brightness > 255) {
            brightness = 255;
        }
        return brightness;
    }

    /*
     * 关闭自动调节亮度
     */
    public void offAutoBrightness() {
        try {
            if (Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(resolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** 获取系统亮度 */
    public int getSystemBrightness() {
        return Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    /** 获取APP内的亮度 */
    public float getAppBrightness(Context context) {
        float screenBrightness = Tools.scanForActivity(context).getWindow().getAttributes().screenBrightness;
        if (screenBrightness == -1) {
            //一开始是默认亮度的时候，获取系统亮度，计算比例值
            screenBrightness = getSystemBrightness() / 255f;
        }
        return screenBrightness;
    }

    /*
     * 设置系统亮度，如果有设置了自动调节，请先调用offAutoBrightness()方法关闭自动调节，否则会设置失败
     */
    public void setSystemBrightness(int newBrightness) {
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS
                , adjustBrightnessNumber(newBrightness));
    }

    public int getMaxBrightness() {
        return maxBrightness;
    }

    //设置当前APP的亮度
    public void setAppBrightness(float brightnessPercent, Context context) {
        Window window = Tools.scanForActivity(context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightnessPercent;
        window.setAttributes(layoutParams);
    }
}
