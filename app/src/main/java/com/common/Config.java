package com.common;


import com.common.utilcode.util.Utils;

import java.io.File;

public class Config {
    public static final String PKG      = "com.common";
    public static final String TEST_PKG = "com.blankj.testinstall";
    private static String testApkPath;

    public static String getTestApkPath() {
        if (testApkPath == null)
            testApkPath = Utils.getContext().getCacheDir().getAbsolutePath() + File.separatorChar + "apk" + File.separatorChar + "test_install.apk";
        return testApkPath;
    }
}
