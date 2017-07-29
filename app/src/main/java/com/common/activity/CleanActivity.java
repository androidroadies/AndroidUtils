package com.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.common.utilcode.util.CleanUtils;
import com.common.utilcode.util.ToastUtils;
import com.common.base.BaseActivity;
import com.common.utils.R;

import java.io.File;
public class CleanActivity extends BaseActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_clean;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        Button btnCleanInternalCache = (Button) findViewById(R.id.btn_clean_internal_cache);
        Button btnCleanInternalFiles = (Button) findViewById(R.id.btn_clean_internal_files);
        Button btnCleanInternalDbs = (Button) findViewById(R.id.btn_clean_internal_databases);
        Button btnCleanInternalSP = (Button) findViewById(R.id.btn_clean_internal_sp);
        Button btnCleanExternalCache = (Button) findViewById(R.id.btn_clean_external_cache);
        btnCleanInternalCache.setOnClickListener(this);
        btnCleanInternalFiles.setOnClickListener(this);
        btnCleanInternalDbs.setOnClickListener(this);
        btnCleanInternalSP.setOnClickListener(this);
        btnCleanExternalCache.setOnClickListener(this);

        btnCleanInternalCache.setText(getCacheDir().getPath());
        btnCleanInternalFiles.setText(getFilesDir().getPath());
        btnCleanInternalDbs.setText(getFilesDir().getParent() + File.separator + "databases");
        btnCleanInternalSP.setText(getFilesDir().getParent() + File.separator + "shared_prefs");

        if (getExternalCacheDir() != null) {
            btnCleanExternalCache.setText(getExternalCacheDir().getAbsolutePath());
        }
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clean_internal_cache:
                ToastUtils.showShort("cleanInternalCache" + CleanUtils.cleanInternalCache());
                break;
            case R.id.btn_clean_internal_files:
                ToastUtils.showShort("cleanInternalFiles" + CleanUtils.cleanInternalFiles());
                break;
            case R.id.btn_clean_internal_databases:
                ToastUtils.showShort("cleanInternalDbs" + CleanUtils.cleanInternalDbs());
                break;
            case R.id.btn_clean_internal_sp:
                ToastUtils.showShort("cleanInternalSP" + CleanUtils.cleanInternalSP());
                break;
            case R.id.btn_clean_external_cache:
                ToastUtils.showShort("cleanExternalCache" + CleanUtils.cleanExternalCache());
                break;
        }
    }
}
