package com.common.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.common.utils.Common;
import com.common.utils.R;

public class ClientPostMultipartEntityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_click);

        Button btnShare = (Button) findViewById(R.id.btnShare);
        WebView webview = (WebView) findViewById(R.id.webView);
            webview.loadUrl("file:///android_asset/multi.html");
//        TextView tvCode = (TextView) findViewById(R.id.prevent_double_click_tv_code);
//        tvCode.setText(
//                " /**\n" +
//                        " * This is used for post data from API.\n" +
//                        " *\n" +
//                        " * @author Mayank\n" +
//                        " * @since 1.4\n" +
//                        " */\n" +
//                        "public class MyClientMultipartPost extends AsyncTask<Void, Void, Void> {\n" +
//                        "\n" +
//                        "    private final String ApiLink;\n" +
//                        "    private final MultipartEntity multipartentity;\n" +
//                        "    public ProgressDialog dialog;\n" +
//                        "    private String message;\n" +
//                        "    private Context context;\n" +
//                        "    private OnPostCallComplete onpostcallcomplete;\n" +
//                        "    private JSONObject jsonResult;\n" +
//                        "    private String json;\n" +
//                        "\n" +
//                        "    public MyClientMultipartPost(Context context, String progressMessage,\n" +
//                        "                                 OnPostCallComplete onPostCallComplete2, String ApiLink,\n" +
//                        "                                 MultipartEntity multipartentity) {\n" +
//                        "        message = progressMessage;\n" +
//                        "        this.context = context;\n" +
//                        "        this.ApiLink = ApiLink;\n" +
//                        "        this.multipartentity = multipartentity;\n" +
//                        "        this.onpostcallcomplete = onPostCallComplete2;\n" +
//                        "\n" +
//                        "        if (!(message.equals(\"\"))) {\n" +
//                        "            dialog = new ProgressDialog(context);\n" +
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
//                        "    protected Void doInBackground(Void... params) {\n" +
//                        "        String result = null;\n" +
//                        "\n" +
//                        "        if (!isCancelled()) {\n" +
//                        "\n" +
//                        "            InputStream is = null;\n" +
//                        "            JSONObject jObj = null;\n" +
//                        "\n" +
//                        "//            Utils.Log(getClass().getSimpleName(), \"Url : \" + ApiLink + \" Params : \" + multipartentity.toString());\n" +
//                        "            System.out.println(\"create poll data \" + ApiLink);\n" +
//                        "            try {\n" +
//                        "                HttpClient httpclient = new DefaultHttpClient();\n" +
//                        "                HttpContext localContext = new BasicHttpContext();\n" +
//                        "                HttpPost httppost = new HttpPost(ApiLink);\n" +
//                        "                httppost.setEntity(multipartentity);\n" +
//                        "\n" +
//                        "                HttpResponse response = httpclient.execute(httppost, localContext);\n" +
//                        "                if (response == null) {\n" +
//                        "\n" +
//                        "                } else {\n" +
//                        "                    HttpEntity entity = response.getEntity();\n" +
//                        "                    is = entity.getContent();\n" +
//                        "\n" +
//                        "                    // Log.e(\"log_tag\", \"\"+is.toString());\n" +
//                        "                }\n" +
//                        "            } catch (Exception e) {\n" +
//                        "                e.printStackTrace();\n" +
//                        "                // Log.e(\"log_tag\", \"Error in http connection \" + e.toString());\n" +
//                        "            }\n" +
//                        "\n" +
//                        "            // convert response to string\n" +
//                        "            try {\n" +
//                        "                BufferedReader reader = new BufferedReader(new InputStreamReader(is, \"iso-8859-1\"), 8);\n" +
//                        "                StringBuilder sb = new StringBuilder();\n" +
//                        "                String line = null;\n" +
//                        "                while ((line = reader.readLine()) != null) {\n" +
//                        "                    sb.append(line + \"\\n\");\n" +
//                        "                }\n" +
//                        "                is.close();\n" +
//                        "                json = sb.toString();\n" +
//                        "                Log.i(\"res\", json);\n" +
//                        "            } catch (Exception e) {\n" +
//                        "                // Log.e(\"log_tag\", \"Error converting result \" + e.toString());\n" +
//                        "                e.printStackTrace();\n" +
//                        "            }\n" +
//                        "        }\n" +
//                        "        //System.out.println(\"result in post: \"+result);\n" +
//                        "        return null;\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    @Override\n" +
//                        "    protected void onPostExecute(Void result) {\n" +
//                        "        // TODO Auto-generated method stub\n" +
//                        "        super.onPostExecute(result);\n" +
//                        "        if (!(message.equals(\"\"))) {\n" +
//                        "            if (dialog != null) {\n" +
//                        "                dialog.hide();\n" +
//                        "                dialog.dismiss();\n" +
//                        "            }\n" +
//                        "        }\n" +
//                        "        System.out.println(\"result in onPostExecute: \" + result);\n" +
//                        "        try {\n" +
//                        "            onpostcallcomplete.response(json);\n" +
//                        "        } catch (JSONException e) {\n" +
//                        "            e.printStackTrace();\n" +
//                        "        }\n" +
//                        "    }\n" +
//                        "\n" +
//                        "    public interface OnPostCallComplete {\n" +
//                        "        public void response(String result) throws JSONException;\n" +
//                        "    }\n" +
//                        "}");

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Common.openShareDialog(ClientPostMultipartEntityActivity.this, "Client Get", "", "http://www.paste.org/77935", "Get Data From API");

            }
        });
    }

}
