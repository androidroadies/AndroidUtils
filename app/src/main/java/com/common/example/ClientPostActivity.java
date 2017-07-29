package com.common.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.common.utils.Common;
import com.common.utils.R;

public class ClientPostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_click);

        Button btnShare = (Button) findViewById(R.id.btnShare);
        WebView webview = (WebView) findViewById(R.id.webView);
            webview.loadUrl("file:///android_asset/post.html");

//        TextView tvCode = (TextView) findViewById(R.id.prevent_double_click_tv_code);
//        tvCode.setText(
//                " /**\n" +
//                        " * This is used for post data from API.\n" +
//                        " *\n" +
//                        " * @author Mayank\n" +
//                        " * @since 1.4\n" +
//                        " */\n" +
//                        "public class MyClientPost extends AsyncTask<Map<String, Object>, String, String> {\n" +
//                        "\n" +
//                        "    public ProgressDialog dialog;\n" +
//                        "    private String message;\n" +
//                        "    private Context context;\n" +
//                        "    private OnPostCallComplete onpostcallcomplete;\n" +
//                        "    private JSONObject jsonResult;\n" +
//                        "\n" +
//                        "    public MyClientPost(Context context, String progressMessage, OnPostCallComplete onPostCallComplete2) {\n" +
//                        "        message = progressMessage;\n" +
//                        "        this.context = context;\n" +
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
//                        "    protected String doInBackground(Map<String, Object>... params) {\n" +
//                        "        String result = null;\n" +
//                        "\n" +
//                        "        if (!isCancelled()) {\n" +
//                        "\n" +
//                        "            Map<String, Object> passed_params = params[0];\n" +
//                        "            // API call URL\n" +
//                        "            String serverUrl = (String) passed_params.get(\"url\");\n" +
//                        "            Log.v(Utils.TAG, \"API url: \" + serverUrl);\n" +
//                        "            // parameter data to send\n" +
//                        "            @SuppressWarnings(\"unchecked\")\n" +
//                        "            Map<String, String> methodParameter = (Map<String, String>) passed_params.get(\"method_parameters\");\n" +
//                        "            try {\n" +
//                        "                HttpClient client = new DefaultHttpClient();\n" +
//                        "                HttpPost post = new HttpPost(serverUrl);\n" +
//                        "\n" +
//                        "                Iterator<Entry<String, String>> iterator = methodParameter.entrySet().iterator();\n" +
//                        "                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(methodParameter.size());\n" +
//                        "                while (iterator.hasNext()) {\n" +
//                        "                    Entry<String, String> param = iterator.next();\n" +
//                        "                    nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));\n" +
//                        "                }\n" +
//                        "                Log.v(Utils.TAG, \"post latlng \" + nameValuePairs.toString());\n" +
//                        "                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);\n" +
//                        "                post.setEntity(entity);\n" +
//                        "                HttpResponse response = client.execute(post);\n" +
//                        "                HttpEntity resp_entity = response.getEntity();\n" +
//                        "                result = EntityUtils.toString(resp_entity);\n" +
//                        "                // System.out.println(\"result in post 80: \"+result);\n" +
//                        "                if (response.getStatusLine().getStatusCode() != 200) {\n" +
//                        "                    Log.v(Utils.TAG, \"post  status code \" + response.getStatusLine().getStatusCode());\n" +
//                        "                    jsonResult = new JSONObject();\n" +
//                        "                    jsonResult.put(\"response_code\", \"9999\");\n" +
//                        "                    jsonResult.put(\"response_message\", \"85 Server error occurred while processing request. Please try again.\");\n" +
//                        "                    result = jsonResult.toString();\n" +
//                        "                    return result;\n" +
//                        "                }\n" +
//                        "            } catch (Exception e) {\n" +
//                        "                Log.v(Utils.TAG, \"post exception \" + e.getMessage());\n" +
//                        "                try {\n" +
//                        "                    jsonResult = new JSONObject();\n" +
//                        "                    jsonResult.put(\"response_code\", \"9999\");\n" +
//                        "                    jsonResult.put(\"response_message\", \"94 Server error occurred while processing request. Please try again.\");\n" +
//                        "                    result = jsonResult.toString();\n" +
//                        "                    return result;\n" +
//                        "                } catch (JSONException jsone) {\n" +
//                        "                    jsone.printStackTrace();\n" +
//                        "                }\n" +
//                        "            }\n" +
//                        "        }\n" +
//                        "        //System.out.println(\"result in post: \"+result);\n" +
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
//                        "        }\n" +
//                        "        System.out.println(\"result in onPostExecute: \" + result);\n" +
//                        "        try {\n" +
//                        "            onpostcallcomplete.response(result);\n" +
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

                Common.openShareDialog(ClientPostActivity.this, "Client Get", "", "http://www.paste.org/77942", "Get Data From API");

            }
        });
    }

}
