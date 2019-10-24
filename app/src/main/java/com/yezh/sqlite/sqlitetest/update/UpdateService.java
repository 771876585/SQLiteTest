package com.yezh.sqlite.sqlitetest.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import static android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by yezh on 2019/10/24 10:15.
 * Description：
 */
public class UpdateService extends Service {

    DownloadManager manager;
    DownloadCompleteReceiver receiver;

    private void initDownManager(){
        manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse("http://wap.cmread.com/hybc/download/client?enterpriseId=57100019&version=Android_HY_57100019_V4.4_20190814")
        );
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
        | DownloadManager.Request.NETWORK_WIFI);
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "qiyeyuedu.apk");
        // 将下载请求放入队列
        manager.enqueue(down);
        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDownManager();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(receiver != null){
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    class DownloadCompleteReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //自动安装apk
                installAPK(manager.getUriForDownloadedFile(downId));

                //停止服务并关闭广播
                UpdateService.this.stopSelf();
            }
        }
    }

    private void installAPK(Uri apk){
        //自动安装待修改
       // startUpdateClient(getApplicationContext(), apk);
    }

    public static void startUpdateClient(Context appContext, Uri apkUri)
    {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            //兼容8.0
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                boolean hasInstallPermission = appContext.getPackageManager().canRequestPackageInstalls();
                if(!hasInstallPermission){
                    startInstallPermissionSettingActivity(appContext);
                }
            }
        }
        else{
//            installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");

            installIntent.setAction("android.intent.action.VIEW");
            installIntent.addCategory("android.intent.category.DEFAULT");
            installIntent.setType("application/vnd.android.package-archive");
            installIntent.setData(apkUri);
            installIntent.setDataAndType(apkUri,"application/vnd.android.package-archive");
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        appContext.startActivity(installIntent);
    }

    /**
     * 跳到设置--允许安装未知应用
     * @param context
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context){
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
