package com.common.example;
/**
 * @author Y@$!n
 *
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.common.utils.Common;
import com.common.utils.R;

public class PinchZoomImageViewAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinch_zoom_image_view);
        //Context mContext = PinchZoomImageViewAct.this;

        init();

    }

    private void init() {
        ImageView ivImagePich = (ImageView) findViewById(R.id.ivImagePich);
        Common.applyPinchZoomImage(ivImagePich);
    }
}