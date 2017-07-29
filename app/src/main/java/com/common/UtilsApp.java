package com.common;

import com.common.utilcode.BuildConfig;
import com.common.utilcode.util.CrashUtils;
import com.common.utilcode.util.FileUtils;
import com.common.utilcode.util.LogUtils;
import com.common.utilcode.util.Utils;
import com.common.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;

public class UtilsApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 内存泄露检查工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Utils.init(this);
        initLog();
        initCrash();
        initAssets();
    }

    public static void initLog() {
        LogUtils.Builder builder = new LogUtils.Builder()
                .setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag(null)
                .setLogHeadSwitch(true)
                .setLog2FileSwitch(false)
                .setDir("")
                .setBorderSwitch(true)
                .setLogFilter(LogUtils.V);
        LogUtils.d(builder.toString());
    }

    private void initCrash() {
        CrashUtils.getInstance().init();
    }

    private void initAssets() {
        if (!FileUtils.isFileExists(Config.getTestApkPath())) {
            try {
                FileUtils.writeFileFromIS(Config.getTestApkPath(), getAssets().open("test_install.apk"), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
