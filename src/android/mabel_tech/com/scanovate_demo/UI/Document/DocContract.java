package mabel_tech.com.scanovate_demo.UI.Document;

import android.graphics.Bitmap;
import android.view.View;

import mabel_tech.com.scanovate_demo.UI.base.BasePresenter;
import mabel_tech.com.scanovate_demo.UI.base.BaseView;
import mabel_tech.com.scanovate_demo.model.CloseResponse;

public interface DocContract {

    interface Presenter extends BasePresenter {

        void onFrontImageCaptured(Bitmap bitmap, String documentType, String uuidDevice);
        void onBackImageCaptured(Bitmap bitmap, String uuidDevice);
        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber);
        void close(String uid);
    }

    interface View extends BaseView<DocContract.Presenter> {

        void onWebServiceStart();
        void finishFlow(boolean result, CloseResponse response, Integer code);
        void onWebServiceStop();
        void onWebServiceResume();
        void continueFlow(boolean result, CloseResponse response, Integer code);
    }
}
