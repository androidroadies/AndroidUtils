package com.common.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.common.base.BaseActivity;
import com.common.utils.R;


public class MainActivity extends BaseActivity {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main_code;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void activityClick(View view) {
        startActivity(new Intent(this, ActivityActivity.class));
    }

    public void appClick(View view) {
        startActivity(new Intent(this, AppActivity.class));
    }

    public void cleanClick(View view) {
        startActivity(new Intent(this, CleanActivity.class));
    }

    public void crashClick(View view) {
        throw new NullPointerException("crash test");
    }

    public void deviceClick(View view) {
        startActivity(new Intent(this, DeviceActivity.class));
    }

    public void fragmentClick(View view) {
        startActivity(new Intent(this, FragmentActivity.class));
    }

//    public void flashlightClick(View view) {
//        startActivity(new Intent(this, FlashlightActivity.class));
//    }

    public void handlerClick(View view) {
        startActivity(new Intent(this, HandlerActivity.class));
    }

    public void imageClick(View view) {
        startActivity(new Intent(this, ImageActivity.class));
    }

    public void keyboardClick(View view) {
        startActivity(new Intent(this, KeyboardActivity.class));
    }

    public void locationClick(View view) {
        startActivity(new Intent(this, LocationActivity.class));
    }

    public void logClick(View view) {
        startActivity(new Intent(this, LogActivity.class));
    }

    public void networkClick(View view) {
        startActivity(new Intent(this, NetworkActivity.class));
    }

//    public void permissionClick(View view) {
//        startActivity(new Intent(this, PermissionActivity.class));
//    }

    public void phoneClick(View view) {
        startActivity(new Intent(this, PhoneActivity.class));
    }

    public void pinyinClick(View view) {
        startActivity(new Intent(this, PinyinActivity.class));
    }

    public void processClick(View view) {
        startActivity(new Intent(this, ProcessActivity.class));
    }

    public void sdcardClick(View view) {
        startActivity(new Intent(this, SDCardActivity.class));
    }

    public void snackbarClick(View view) {
        startActivity(new Intent(this, SnackbarActivity.class));
    }

    public void spannableClick(View view) {
        startActivity(new Intent(this, SpannableActivity.class));
    }

    public void toastClick(View view) {
        startActivity(new Intent(this, ToastActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {

                case R.id.action_git_hub:

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/androidroadies/AndroidUtils")));
                    break;
                case R.id.action_blog:
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://androidroadies.blogspot.in/")));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }
}
