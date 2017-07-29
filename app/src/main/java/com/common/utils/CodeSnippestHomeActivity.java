package com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.common.example.ClientGetActivity;
import com.common.example.ClientPostActivity;
import com.common.example.ClientPostMultipartEntityActivity;

public class CodeSnippestHomeActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = CodeSnippestHomeActivity.this;
        //Activity mActivity = CodeSnippestHomeActivity.this;

        String[] sdkFunctionalityListValue = new String[]{
                "Executing a HTTP Get Request",/* 0 */
                "Executing a HTTP Post Request",/* 1 */
                "Executing a HTTP post Request for Send Audio,Video and Image",/* 2 */
        };

        ListView sdkFunctionalityList = (ListView) findViewById(R.id.Md_list_company);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sdkFunctionalityListValue);
        sdkFunctionalityList.setAdapter(stringArrayAdapter);

        sdkFunctionalityList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedListItem(position);
            }

        });

    }

    private void selectedListItem(int position) {
        switch (position) {
            case 0:
                Intent intget = new Intent(mContext, ClientGetActivity.class);
                startActivity(intget);
                break;
            case 1:
                Intent intpost = new Intent(mContext, ClientPostActivity.class);
                startActivity(intpost);
                break;
            case 2:
                Intent intmulti = new Intent(mContext, ClientPostMultipartEntityActivity.class);
                startActivity(intmulti);
                break;

        }
    }
}
