package com.common.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.utilcode.util.ActivityUtils;
import com.common.Config;
import com.common.base.BaseActivity;
import com.common.utils.R;

public class ActivityActivity extends BaseActivity {

    private String imageActivityClassName;

    @Override
    public void initData(Bundle bundle) {
        imageActivityClassName = Config.PKG + ".activity.ImageActivity";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        findViewById(R.id.btn_launch_image_activity).setOnClickListener(this);
        TextView tvAboutActivity = (TextView) findViewById(R.id.tv_about_activity);
        tvAboutActivity.setText("Is ImageActivity Exists: " + ActivityUtils.isActivityExists(Config.PKG, imageActivityClassName)
                + "\ngetLauncherActivity: " + ActivityUtils.getLauncherActivity(Config.PKG)
                + "\ngetTopActivity: " + ActivityUtils.getTopActivity()
        );
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_launch_image_activity:
                ActivityUtils.startActivity(Config.PKG, imageActivityClassName);
                break;
            default:
                break;
        }
    }
}