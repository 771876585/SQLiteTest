package com.yezh.sqlite.sqlitetest.bitmapcache;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;

import com.yezh.sqlite.sqlitetest.R;

import java.lang.ref.WeakReference;

/**
 * Created by yezh on 2020/6/29 09:34.
 * Description：
 */
public class BitmapCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmapcache);
        ImageView ivCache = findViewById(R.id.iv_cache);
        ivCache.setImageBitmap(decodeSampledBitmapFromResource(
                getResources(), R.drawable.face, 300, 300));

        //获取内存最大值
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //重写此方法来衡量每张图片的大小，默认返回图片的数量
                return value.getByteCount() / 1024;
            }
        };

        ((TextView)findViewById(R.id.tv_cache)).setText(maxMemory + " KB");
        loadBitmap(R.drawable.face,(ImageView) findViewById(R.id.iv_cache2));

    }

    private Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        //第一次将解析inJustDecodeBounds设置为true，获取图片的实际大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        //源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth){
            //计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float)height/(float)reqHeight);
            final int widthRatio = Math.round((float)width/(float)reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    private LruCache<String, Bitmap> mMemoryCache;

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromMemCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key){
        return mMemoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView){
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.face);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(resId);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{
        private final WeakReference<ImageView> imageViewWeakReference;
        BitmapWorkerTask(ImageView imageView) {
            imageViewWeakReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            final Bitmap bitmap = decodeSampledBitmapFromResource(getResources(),
                    integers[0], 300, 300);
            addBitmapToMemoryCache(String.valueOf(integers[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                final ImageView imageView = imageViewWeakReference.get();
                if(imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
