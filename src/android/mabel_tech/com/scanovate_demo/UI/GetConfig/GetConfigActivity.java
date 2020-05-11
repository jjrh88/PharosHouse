package mabel_tech.com.scanovate_demo.UI.GetConfig;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import mabel_tech.com.scanovate_demo.DocumentTypeUtils;
import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.ScanovateSdk;
import mabel_tech.com.scanovate_demo.UI.Face.FaceActivity;
import mabel_tech.com.scanovate_demo.UI.Face.tip1Face;
import mabel_tech.com.scanovate_demo.UI.Log;
import mabel_tech.com.scanovate_demo.UI.NoConnection;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.model.GetConfigResponse;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;

public class GetConfigActivity extends BaseActivity implements GetConfigContract.View {


    static Context context;
    public static final int REQUEST_CODE = 27;
    //private static final int CAMERA_REQUEST_CODE = 1;
    Dialog dialog;
    ProgressBar progressBar;
    GetConfigContract.Presenter presenter;

    //ProgressBar progressBar;


    public static void setContext(Context context) {
        GetConfigActivity.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        dialog = new Dialog(this);
        setPresenter(new GetConfigPresenter(this, this));
        progressBar = findViewById(R.id.prb_afe);
        presenter.getConfig(RetrofitClient.getApiKey_Sdk());

    }


    @Override
    public void onWebServiceSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onWebServiceFailed(String error) {
        //progressBar.setVisibility(View.INVISIBLE);
        new AlertDialog.Builder(this).setTitle(R.string.service_failed).setMessage(error).show();
    }

    @Override
    public void onWebServiceStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishFlow(int statuscode, String error) {
        new Log().execute("Error", "finishFlow", "Error in get Config services", "", DocumentTypeUtils.getUuiddevice(this),"");
        CloseResponse responseError = new CloseResponse();
        CloseResponse.Extras extrasError = new CloseResponse.Extras();
        responseError.setExtras(extrasError);
        if(statuscode == 401) {
            responseError.getExtras().setIdState("15");
            responseError.getExtras().setStateName("Error");
            responseError.getExtras().setAdditionalProp1("El proceso de autorización no fue exitoso. Valide el codigo de proyecto y/o el API Key");
        }else if(statuscode == 404){
            responseError.getExtras().setIdState("15");
            responseError.getExtras().setStateName("Error");
            responseError.getExtras().setAdditionalProp1("El codigo de proyecto no existe");
        }else{
            responseError.getExtras().setIdState("15");
            responseError.getExtras().setStateName("Error");
            responseError.getExtras().setAdditionalProp1("Ha ocurrido un error, valide el número de id entregado para obtener más detalles");
        }
        ScanovateSdk.getHandler().onFailure(responseError);
        this.finish();
    }


    @Override
    public void continueFlow(GetConfigResponse response) {
        new Log().execute("Info", "continueFlow", "Success in get Config services", "", DocumentTypeUtils.getUuiddevice(this), "");

        DocumentTypeUtils.storeGetConfig(this,response);
        RetrofitClient.storeConfigServer(context,response.getBase_Url(),response.getProjectName_Server(),response.getApiKey_Server());
        //Intent intent = new Intent(this, UserAuthenticationActivity.class);
        //startActivityForResult(intent, UserAuthenticationActivity.REQUEST_CODE);
        DocumentTypeUtils.storeTryLiveness(this, response.getTryLiveness());
        DocumentTypeUtils.storeUrlLiveness(this,response.getUrlServiceLiveness());
        DocumentTypeUtils.storeTokenKyc(this,response.getToken_KYC());
        //Intent intent = new Intent(this, PersonalDataActivity.class);
        Intent intent = new Intent(this, tip1Face.class);
        startActivityForResult(intent, tip1Face.REQUEST_CODE);
        finish();
    }


    @Override
    public void setPresenter(GetConfigContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNoConnection() {
        Button btnContinue;
        dialog.setContentView(R.layout.no_connection);
        btnContinue = (Button) dialog.findViewById(R.id.tv_retry);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenter.getConfig(RetrofitClient.getApiKey_Sdk());
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

}
