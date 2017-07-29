package com.common.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.common.utils.Common;
import com.common.utils.R;

public class ClientGetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_click);

        Button btnShare = (Button) findViewById(R.id.btnShare);
        WebView webview = (WebView) findViewById(R.id.webView);
            webview.loadUrl("file:///android_asset/get.html");
//        TextView tvCode = (TextView) findViewById(R.id.prevent_double_click_tv_code);
//        tvCode.setText(
//                " /**\n" +
//                        " * This is used for get data from API.\n" +
//                        " *\n" +
//                        " * @author Mayank\n" +
//                        " * @since 1.4\n" +
//                        " */\n" +
//                        "public class MyClientGet extends AsyncTask<String, String, String> {\n" +
//                        "\n" +
//                        "    private ProgressDialog dialog;\n" +
//                        "    private String message;\n" +
//                        "    private Context context;\n" +
//                        "    private OnGetCallComplete ongetcallcomplete;\n" +
//                        "\n" +
//                        "    public MyClientGet(Context ctx, String progressMessage, OnGetCallComplete onGetCallComplete) {\n" +
//                        "        message = progressMessage;\n" +
//                        "        this.context = ctx;\n" +
//                        "        this.ongetcallcomplete = onGetCallComplete;\n" +
//                        "        if (!(message.equals(\"\"))) {\n" +
//                        "            dialog = new ProgressDialog(ctx);\n" +
//                        "            dialog.setMessage(progressMessage);\n" +
//                        "        }\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    @Override\n" +
//                        "    protected void onPreExecute() {\n" +
//                        "        // TODO Auto-generated method stub\n" +
//                        "        super.onPreExecute();\n" +
//                        "        if (!(message.equals(\"\"))) {\n" +
//                        "            dialog.setCancelable(false);\n" +
//                        "            dialog.show();\n" +
//                        "        }\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    @Override\n" +
//                        "    protected String doInBackground(String... params) {\n" +
//                        "        // TODO Auto-generated method stub\n" +
//                        "        String result = null;\n" +
//                        "        if (!isCancelled()) {\n" +
//                        "            String serverurl = params[0];\n" +
//                        "            result = Utils.NetworkOperation(serverurl);\n" +
//                        "        }\n" +
//                        "        return result;\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    @Override\n" +
//                        "    protected void onPostExecute(String result) {\n" +
//                        "        // TODO Auto-generated method stub\n" +
//                        "        super.onPostExecute(result);\n" +
//                        "        if (!(message.equals(\"\"))) {\n" +
//                        "            if (dialog != null) {\n" +
//                        "                dialog.hide();\n" +
//                        "                dialog.dismiss();\n" +
//                        "            }\n" +
//                        "        }\n \n" +
//                        "        ongetcallcomplete.response(result);\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    protected void onProgressUpdate(String... progress) {\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    public interface OnGetCallComplete {\n" +
//                        "        public void response(String result);\n" +
//                        "    }\n" +
//                        "--------------Finish MyclientGet---------\n \n \n" +
//                        "}\n \n \n " +
//                        "How to use! \n \n \n" +
//                        "MyClientGet myclientget; \n \n \n " +
//                        "myclientget = new MyClientGet(context, \"Please Wait\", \"CallComplete\");\n" +
//                        "Note : CallComplete is a Method where you can get response\n" +
//                        "myclientget.execute(\"API_URL\", \"YOUR_KEY\")));\n" +
//                        "  /**\n" +
//                        "     * Get AccessKey call Done.\n" +
//                        "     */\n" +
//                        "    MyClientGet.OnGetCallComplete onAccessKeyCallComplete = new MyClientGet.OnGetCallComplete() {\n" +
//                        "        @Override\n" +
//                        "        public void response(String result) {\n" +
//                        "            try {\n" +
//                        "                JSONObject jobj = new JSONObject(result);\n" +
//                        "                String response_code = jobj.getString(\"response_code\");\n" +
//                        "                if (response_code.equals(getResources().getString(R.string.success_code))) {\n" +
//                        "                    // Success\n" +
//                        "                }\n" +
//                        "            } catch (JSONException e) {\n" +
//                        "                e.printStackTrace();\n" +
//                        "            }\n" +
//                        "        }\n" +
//                        "    };");

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.openShareDialog(ClientGetActivity.this, "Client Get", "", "http://www.paste.org/77943", "Get Data From API");

            }
        });
    }

}
