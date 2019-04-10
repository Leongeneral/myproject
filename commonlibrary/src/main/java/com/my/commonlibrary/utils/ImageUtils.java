package com.my.commonlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Date: 2016/11/18
 * desc:图片处理工具类
 *
 * @author:DingZhixiang
 */
public class ImageUtils {

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    /*public static Bitmap getBitmap(String filePath) {
        if (TextUtils.isEmpty(filePath)) return null;
        return BitmapFactory.decodeFile(filePath);
    }*/


    public static Bitmap getBitmap(String filePath){
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;
        bfOptions.inPurgeable=true;
        bfOptions.inTempStorage=new byte[12 * 1024];
        // bfOptions.inJustDecodeBounds = true;
        File file = new File(filePath);
        FileInputStream fs=null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        if(fs != null)
            try {
                bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fs!=null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return bmp;
    }


    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static byte[] compressByQuality(Bitmap src, int quality, boolean recycle) {
        if (isEmptyBitmap(src) || quality < 0 || quality > 100) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        closeIO(baos);
        return bytes;
    }

    /**
     * 按质量压缩
     *
     * @param path     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static File compressByQuality(String path, int quality, boolean recycle) {
        Bitmap bitmap = getBitmap(path);
        if (isEmptyBitmap(bitmap) || quality < 0 || quality > 100) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        closeIO(baos);
        File file = new File(path);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIO(outputStream);
        }
        return file;
    }

    /**
     * 按最大字节数压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(Bitmap src, long maxByteSize, boolean recycle) {
        byte[] bytes =compressByQuality(src,maxByteSize,recycle,null);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static byte[] compressByQuality(Bitmap src, long maxByteSize, boolean recycle,String tag) {
        if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length > maxByteSize && quality >= 0) {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, quality -= 5, baos);
        }
        if (quality < 0) return null;
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        closeIO(baos);
        return bytes;
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
