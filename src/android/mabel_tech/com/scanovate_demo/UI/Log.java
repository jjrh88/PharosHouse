package mabel_tech.com.scanovate_demo.UI;


import android.os.AsyncTask;

import mabel_tech.com.scanovate_demo.model.LogRequest;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;
import okhttp3.ResponseBody;

public class Log extends AsyncTask<String, Integer, Integer> {

    protected Integer doInBackground(String... parameters) {

        try {
            String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
            int sdkVersion = android.os.Build.VERSION.SDK_INT;

            RetrofitClient retrofitClient = new RetrofitClient();
            retrofitClient.RegisterLog(new LogRequest(parameters[0], parameters[1], parameters[2], android.os.Build.MODEL, Integer.toString(sdkVersion) + "-" + myVersion, RetrofitClient.getNameProject_Sdk(), parameters[3],parameters[4], parameters[5]), new ApiHelper.RegisterLogHandler() {
                @Override
                public void onSuccess(int IdLog) {

                }

                @Override
                public void onSuccess(int statusCode, ResponseBody response) {

                }

                @Override
                public void onConnectionFailed() {

                }

                @Override
                public void onFailure(int statusCode, String error) {

                }
            });
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }
}