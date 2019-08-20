package com.prim_player_cc.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/9 - 10:01 AM
 */
public class BitmapRotating {
    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (bitmap == null || angle == 0) return bitmap;
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }

    public static Bitmap createPhotos(Bitmap bitmap){
        if(bitmap!=null){
            Matrix m=new Matrix();
            try{
                m.setRotate(90, bitmap.getWidth()/2, bitmap.getHeight()/2);//90就是我们需要选择的90度
                Bitmap bmp2=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap=bmp2;
            }catch(Exception ex){
                System.out.print("创建图片失败！"+ex);
            }
        }
        return bitmap;
    }

}
