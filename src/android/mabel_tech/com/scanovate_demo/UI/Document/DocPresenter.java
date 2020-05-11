package mabel_tech.com.scanovate_demo.UI.Document;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import mabel_tech.com.scanovate_demo.Utils;
import mabel_tech.com.scanovate_demo.model.CloseRegistroRequest;
import mabel_tech.com.scanovate_demo.model.CloseRequest;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.model.DocRequest;
import mabel_tech.com.scanovate_demo.model.DocRequestFront;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;
import okhttp3.ResponseBody;

public class DocPresenter implements DocContract.Presenter {

    private DocContract.View view;
    private String uid;
    private int frontPostStatusCode;
    private int backPostStatusCode;
    private Bitmap backDocBmp;
    private Context context;


    DocPresenter(DocContract.View view, String uid, Context context) {

        this.view = view;
        this.uid = uid;
        this.context = context;
    }

    @Override
    public void onFrontImageCaptured(Bitmap bitmap, String documentType, String uuidDevice) {
        view.onWebServiceStart();
        new RetrofitClient().postDocFront(
                new DocRequestFront(Utils.BitMapToString(bitmap, 50), uid, documentType),
                new ApiHelper.CompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {
                        Log.d("ScanovateSdk", "onFrontImageCaptured: onSuccess");
                        frontPostStatusCode = statusCode;
                        view.onWebServiceStop();
                        if (backDocBmp != null) {
                            sendBackDoc(backDocBmp, uuidDevice);
                        }
                    }

                    @Override
                    public void onConnectionFailed() {
                        view.onNoConnection();
                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        Log.d("ScanovateSdk", "onFrontImageCaptured: onFailure: " + error);
                        view.onWebServiceStop();
                        frontPostStatusCode = statusCode;
                        view.finishFlow(false, null, statusCode);
                    }
                }, documentType, context
        );
    }

    @Override
    public void onBackImageCaptured(Bitmap bitmap, String uuidDevice) {

        if (frontPostStatusCode == 200) {
            sendBackDoc(bitmap, uuidDevice);
        } else {
            backDocBmp = bitmap;
        }
    }

    private void sendBackDoc(Bitmap bitmap, String uuidDevice) {
        view.onWebServiceStart();
        new RetrofitClient().postDocBack(
                new DocRequest(Utils.BitMapToString(bitmap, 50), uid),
                new ApiHelper.CloseCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        view.onWebServiceStop();
                        if (statusCode == 201) {
                            Log.d("ScanovateSdk", "close: onSuccess");
                            view.finishFlow(true, response, statusCode);
                        } else {
                            Log.d("ScanovateSdk", "onBackImageCaptured: onSuccess");
                            backPostStatusCode = statusCode;
                            view.continueFlow(true,response,statusCode);
                            //close();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {

                    }

                    @Override
                    public void onConnectionFailed() {
                        view.onNoConnection();
                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        Log.d("ScanovateSdk", "onBackImageCaptured: onFailure: " + error);
                        view.onWebServiceStop();
                        backPostStatusCode = statusCode;
                        view.finishFlow(false, null, statusCode);
                    }
                }, context
        );
    }

    @Override
    public void onViewCreated(Intent intent) {

    }


    @Override
    public void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber) {
        view.onWebServiceStart();
        new RetrofitClient().CloseRegistro(
                new CloseRegistroRequest(uid, IdentificationNumber, IndividualCodeFinger),
                new ApiHelper.CloseCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        android.util.Log.d("ScanovateSdk", "close: onSuccess");
                        view.onWebServiceStop();
                        view.continueFlow(true, response, statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {

                    }

                    @Override
                    public void onConnectionFailed() {
                        view.onNoConnection();
                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        android.util.Log.d("ScanovateSdk", "close: onFailure: " + error);
                        view.onWebServiceStop();
                        view.finishFlow(false, null, statusCode);
                    }
                }, context
        );

    }

    @Override
    public void close(String uid) {
        view.onWebServiceStart();
        new RetrofitClient().close(
                new CloseRequest(uid),
                new ApiHelper.CloseCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        Log.d("ScanovateSdk", "close: onSuccess");
                        view.onWebServiceStop();
                        view.finishFlow(true, response, statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, ResponseBody response) {

                    }

                    @Override
                    public void onConnectionFailed() {
                        view.onNoConnection();
                    }

                    @Override
                    public void onFailure(int statusCode, String error) {
                        Log.d("ScanovateSdk", "close: onFailure: " + error);
                        view.onWebServiceStop();
                        view.finishFlow(false, null, statusCode);
                    }
                }, context
        );
    }



}
