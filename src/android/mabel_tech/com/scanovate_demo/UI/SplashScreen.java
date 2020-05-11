package mabel_tech.com.scanovate_demo.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateHandler;
import mabel_tech.com.scanovate_demo.ScanovateSdk;
import mabel_tech.com.scanovate_demo.kyc.util.DataContext;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;
import okhttp3.ResponseBody;


public class SplashScreen extends AppCompatActivity {

    private static Integer Code;
    private static String Uid;
    private static String UuidDevice;
    private static String TransactionId;
    private static String IdState;
    private static String NameState;
    private static String FirstName;
    private static String SecondName;
    private static String FirstSurname;
    private static String SecondSurname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScanovateSdk.start(
                        SplashScreen.this,
                        "1",
                        1,
                        false,
                        "credibancoqa",
                        "db92efc69991",
                        "https://adocolumbia.ado-tech.com/credibancoqa/api/",
                        "",
                        false,
                        new ScanovateHandler() {
                            @Override
                            public void onSuccess(CloseResponse response, int code, String uuidDevice) {
                                //CloseResponse myReponse = response;
                                Code = code;
                                Uid = response.getUid();
                                UuidDevice = uuidDevice;
                                IdState = response.getExtras().getIdState();
                                NameState = response.getExtras().getStateName();
                                FirstName = response.getFirstName();
                                SecondName = response.getSecondName();
                                FirstSurname = response.getFirstSurname();
                                SecondSurname = response.getSecondSurname();
                                TransactionId = response.getTransactionId();
                                String responseExtras = response.getExtras().getStateName() + response.getExtras().getIdState();
                            }

                            @Override
                            public void onFailure(CloseResponse response) {
                                //Toast.makeText(SplashScreen.this, "Resultado: failure" + "-- Front" + DataContext.getInstance().getResponseServers("FrontSide") + "Back--" + DataContext.getInstance().getResponseServers("BackSide"), Toast.LENGTH_LONG).show();
                            }

                        });
            }
        }, 1500);
    }
}
