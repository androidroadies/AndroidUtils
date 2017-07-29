package com.common.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.common.utils.Common;
import com.common.utils.R;

public class blurEffectsOnDrawableAct extends Activity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_effect_on_image);
        mContext = blurEffectsOnDrawableAct.this;

        init();

    }

    private void init() {
        //ImageView ivBlurImage = (ImageView) findViewById(R.id.ivBlurImage);

        Button btnCreateBlurImage = (Button) findViewById(R.id.btnCreateBlurImage);
        btnCreateBlurImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Common.blurEffectsOnDrawable(mContext, R.drawable.airdroid, 25);
            }
        });

    }
}