package mabel_tech.com.scanovate_demo.UI.GetData;

import android.content.Context;
import android.content.Intent;

import android.app.Activity;

import mabel_tech.com.scanovate_demo.ScanovateSdk;
import mabel_tech.com.scanovate_demo.model.CloseRegistroRequest;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;
import okhttp3.ResponseBody;

public class GetDataPresenter extends Activity implements GetDataContract.Presenter {

    private GetDataContract.View view;
    private String myUid;
    private Context context;

    GetDataPresenter(GetDataContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onViewCreated(Intent intent) {

    }



    @Override
    public void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice) {
        view.onWebServiceStart();
        myUid = uid;
        new RetrofitClient().CloseRegistro(
                new CloseRegistroRequest(uid,IdentificationNumber,IndividualCodeFinger),
                new ApiHelper.CloseCompletionHandler() {
                    @Override
                    public void onSuccess(int statusCode, CloseResponse response) {
                        view.onWebServiceSuccess();
                        android.util.Log.d("ScanovateSdk", "close: onSuccess");
                        view.validateRequest(true, response, statusCode);
                        //finishFlow(true, response);
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
                        finishFlow(false, null, statusCode, uuidDevice);
                    }
                }, context
        );

    }



    @Override
    public void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice) {

        if (result) {
            ScanovateSdk.getHandler().onSuccess(response, code, uuidDevice);
        } else {
            ScanovateSdk.getHandler().onFailure(response);

        }
        view.finishFlow();
    }


}
