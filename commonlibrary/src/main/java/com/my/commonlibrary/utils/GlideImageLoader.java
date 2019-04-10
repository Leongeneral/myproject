package com.my.commonlibrary.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.my.commonlibrary.R;

import java.io.File;


public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        if(path.contains("http")){
            Glide.with(activity)
                    .load(path)
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }else{
            Glide.with(activity)
                    .load(Uri.fromFile(new File(path)))
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

    @Override
    public void clearMemoryCache() {
    }
}
