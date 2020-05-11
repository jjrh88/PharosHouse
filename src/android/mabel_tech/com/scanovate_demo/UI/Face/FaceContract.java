package mabel_tech.com.scanovate_demo.UI.Face;

import android.graphics.Bitmap;

import mabel_tech.com.scanovate_demo.UI.base.BasePresenter;
import mabel_tech.com.scanovate_demo.UI.base.BaseView;
import mabel_tech.com.scanovate_demo.model.CloseResponse;

public interface FaceContract {

    interface Presenter extends BasePresenter {

        void onLivenessSuccess(Bitmap bitmap, String latitude, String longitude, String uuidDevice);
        void verificationCustomer(String docType, String numberId, Bitmap face, Bitmap fingerPrint, String latitude, String longitude);
        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice);
        void onLivenessFailed();

    }

    interface View extends BaseView<Presenter> {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error, int statuscode);
        void onWebServiceStart();
        void finishFlow(boolean result, CloseResponse response, Integer code);
        void continueFlow(boolean result, CloseResponse response, int code);


    }
}
