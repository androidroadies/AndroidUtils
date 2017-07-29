package com.common.example;
/**
 * @author Y@$!n
 *
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.common.utils.Common;
import com.common.utils.R;

public class DateFormateAct extends Activity {
    private TextView date1;
    private TextView date2;
    private TextView date3;
    private TextView date4;
    private TextView date5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_format_act);
        //Context mContext = DateFormateAct.this;
        init();

    }

    private void init() {
        // TODO Auto-generated method stub
        date1 = (TextView) findViewById(R.id.tvDateFormate_yyyyMMdd);
        date2 = (TextView) findViewById(R.id.tvDateFormate_yyyyMMddHHmm);
        date3 = (TextView) findViewById(R.id.tvDateFormate_yyyyMMddHHmmZ);
        date4 = (TextView) findViewById(R.id.tvDateFormate_yyyyMMddHHmmssSSSZ);
        date5 = (TextView) findViewById(R.id.tvDateFormate_yyyyMMddTHHmmssSSSZ);

        Button btnShowDate = (Button) findViewById(R.id.btnShowDate);
        btnShowDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                date1.setText(Common.getCurrentDate("yyyy-MM-dd"));
                date2.setText(Common.getCurrentDate("yyyy-MM-dd HH:mm"));
                date3.setText(Common.getCurrentDate("yyyy-MM-dd HH:mmZ"));
                date4.setText(Common.getCurrentDate("yyyy-MM-dd HH:mm:ss.SSSZ"));
                date5.setText(Common
                        .getCurrentDate("yyyy-MM-dd 'T' HH:mm:ss.SSSZ"));

                date1.setVisibility(View.VISIBLE);
                date2.setVisibility(View.VISIBLE);
                date3.setVisibility(View.VISIBLE);
                date4.setVisibility(View.VISIBLE);
                date5.setVisibility(View.VISIBLE);
            }
        });
    }
}
