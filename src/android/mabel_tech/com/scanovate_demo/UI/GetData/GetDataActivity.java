package mabel_tech.com.scanovate_demo.UI.GetData;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acuant.mobilesdk.Permission;

import mabel_tech.com.scanovate_demo.DocumentTypeUtils;
import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.UI.Document.DocActivity;
import mabel_tech.com.scanovate_demo.UI.Log;
import mabel_tech.com.scanovate_demo.UI.base.BaseActivity;
import mabel_tech.com.scanovate_demo.kyc.util.Util;
import mabel_tech.com.scanovate_demo.model.CloseResponse;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;

public class GetDataActivity extends BaseActivity implements GetDataContract.View {

    public static final int REQUEST_CODE = 26;
    private static final int CAMERA_REQUEST_CODE = 1;
    Dialog myDialog;
    GetDataContract.Presenter presenter;
    ImageView captureData;
    TextView title_captureData;
    TextView text_cedula;
    TextView IdentificationNumber;
    TextView text_codDactilar;
    TextView IndividualFingerCode;
    Button capture_data_btn;
    Button fix_data;
    ProgressBar progressBar;
    private static String uid;
    private static String uuidDevice;
    private static String cedulaNumber;
    private static String dactilarCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Original
        ScanovateApp.SetContext(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_data_activity);
        myDialog = new Dialog(this);
        uid = getIntent().getStringExtra("uid");
        uuidDevice = getIntent().getStringExtra("uuidDevice");
        cedulaNumber = getIntent().getStringExtra("cedulaNumber");
        dactilarCode = getIntent().getStringExtra("dactilarCode");
        setPresenter(new GetDataPresenter(this, this));
        initViews();
    }


    void initViews() {
        title_captureData = findViewById(R.id.text_tittle);
        IdentificationNumber = findViewById(R.id.text_IdentificationNumber);
        IdentificationNumber.setText(cedulaNumber);
        IndividualFingerCode = findViewById(R.id.text_IndividualFingerCode);
        IndividualFingerCode.setText(dactilarCode);
        progressBar = findViewById(R.id.prb_ka);
        capture_data_btn = findViewById(R.id.btn_capture_data);
        capture_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = IdentificationNumber.getText().toString();
                String codDactilar = IndividualFingerCode.getText().toString();
                if (codDactilar.length()>6 && !codDactilar.isEmpty())
                    codDactilar = codDactilar.substring(0, 6);
                presenter.closeRegistro(uid, codDactilar,cedula,uuidDevice);

            }
        });
    }
    ;

    @Override
    public void onWebServiceSuccess() {

        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onWebServiceFailed(String error) {

        progressBar.setVisibility(View.INVISIBLE);
        new AlertDialog.Builder(this).setTitle(R.string.service_failed).setMessage(error).show();
    }

    @Override
    public void onWebServiceStart() {
        //text_cedula.setVisibility(View.INVISIBLE);
        IdentificationNumber.setVisibility(View.INVISIBLE);
        //text_codDactilar.setVisibility(View.INVISIBLE);
        IndividualFingerCode.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishFlow() {
        //presenter.onFinishFlow(true);
        progressBar.setVisibility(View.GONE);
        this.finish();
    }

    @Override
    public void validateRequest(boolean result, CloseResponse response, int statusCode) {
        String stateName = response.getExtras().getStateName();
        if ( stateName.equals("N.U.I. O INDIVIDUAL DACTILAR INCORRECTOS.")){
            showPopup();
        }else{
            presenter.finishFlow(result, response, statusCode, uuidDevice);
        }
    }

    @Override
    public void continueFlow(String uid) {

        Intent intent = new Intent(this, DocActivity.class);
        intent.putExtra("uid", uid);
        startActivityForResult(intent, DocActivity.REQUEST_CODE);
    }

    @Override
    public void continueFlowRegistro(String uidresponse){
        uid = uidresponse;
        //configureView();

    }
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // ask permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.VIBRATE}, CAMERA_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //if (requestCode == CAMERA_REQUEST_CODE) {
        //  if (grantResults.length > 0
        //        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //  new Log().execute("Info", "onRequestPermissionsResult", " Front camera access granted", "");
        //capture(null);
        //} else {
        //new Log().execute("Warning", "onRequestPermissionsResult", " Front camera access not granted", "");
        //}
        // }

        switch (requestCode) {
            case Permission.PERMISSION_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Log().execute("Info", "onRequestPermissionsResult", " Front camera access granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    //capture(null);

                } else {
                    // permission denied
                    new Log().execute("Warning", "onRequestPermissionsResult", " Front camera access not granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    //Util.showDialog(this, "Denied permission.Please give camera permission to proceed.");
                }

            }

            case Permission.PERMISSION_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Log().execute("Info", "onRequestPermissionsResult", "Location access granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                } else {
                    // permission denied
                    new Log().execute("Warning", "onRequestPermissionsResult", "Location access not granted", uid, DocumentTypeUtils.getUuiddevice(this), "");
                    Util.showDialog(this, "Denied permission.Please give location permission to proceed.");
                }
            }
        }

    }


    @Override
    public void setPresenter(GetDataContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNoConnection() {

        progressBar.setVisibility(View.GONE);
        //statusTv.setText(R.string.no_connection);
    }


    public void showPopup() {
        Button btnContinue;
        myDialog.setContentView(R.layout.custompopup);
        btnContinue = (Button) myDialog.findViewById(R.id.btnfollow);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text_cedula.setVisibility(View.VISIBLE);
                IdentificationNumber.setVisibility(View.VISIBLE);
                //text_codDactilar.setVisibility(View.VISIBLE);
                IndividualFingerCode.setVisibility(View.VISIBLE);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
