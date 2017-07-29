package com.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import pub.devrel.easypermissions.EasyPermissions;


public class HomeActivity extends Activity {

    private Context mContext;
    ListView sdkFunctionalityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        //Activity mActivity = HomeActivity.this;

        TextView txtDemo = (TextView) findViewById(R.id.txtDemo);
        TextView txtCodeSnip = (TextView) findViewById(R.id.txtCodeSnip);
        TextView txtMaterialLibrary = (TextView) findViewById(R.id.txtMaterialLibrary);

        txtDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intdemo = new Intent(mContext, MainActivity.class);
                startActivity(intdemo);
            }
        });

        txtCodeSnip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intcode = new Intent(mContext, CodeSnippestHomeActivity.class);
                startActivity(intcode);
            }
        });

        txtMaterialLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intcode = new Intent(mContext, MaterialLibrariesListActivity.class);
                startActivity(intcode);
            }
        });

        updatePemisssion();
    }

    private void updatePemisssion() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have storage permission, open image picker bottom sheet
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage), 100, perms);
        }
    }

}
