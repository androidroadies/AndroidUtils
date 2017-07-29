package com.common.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.service.LocationService;
import com.common.utils.R;


public class LocationActivity extends BaseActivity {

    TextView        tvAboutLocation;
    LocationService mLocationService;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_location;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        tvAboutLocation = (TextView) findViewById(R.id.tv_about_location);
        tvAboutLocation.setText("lastLatitude: unknown"
                + "\nlastLongitude: unknown"
                + "\nlatitude: unknown"
                + "\nlongitude: unknown"
                + "\ngetCountryName: unknown"
                + "\ngetLocality: unknown"
                + "\ngetStreet: unknown"
        );
    }

    @Override
    public void doBusiness(Context context) {
        bindService(new Intent(this, LocationService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationService = ((LocationService.LocationBinder) service).getService();
            mLocationService.setOnGetLocationListener(new LocationService.OnGetLocationListener() {
                @Override
                public void getLocation(final String lastLatitude, final String lastLongitude, final String latitude, final String longitude, final String country, final String locality, final String street) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvAboutLocation.setText("lastLatitude: " + lastLatitude +
                                    "\nlastLongitude: " + lastLongitude +
                                    "\nlatitude: " + latitude +
                                    "\nlongitude: " + longitude +
                                    "\ngetCountryName: " + country +
                                    "\ngetLocality: " + locality +
                                    "\ngetStreet: " + street
                            );
                        }
                    });
                }
            });
        }
    };
}