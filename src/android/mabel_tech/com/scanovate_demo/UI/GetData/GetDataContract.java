package mabel_tech.com.scanovate_demo.UI.GetData;

import mabel_tech.com.scanovate_demo.UI.base.BasePresenter;
import mabel_tech.com.scanovate_demo.UI.base.BaseView;
import mabel_tech.com.scanovate_demo.model.CloseResponse;

public interface GetDataContract {

    interface Presenter extends BasePresenter {


        void closeRegistro(String uid, String IndividualCodeFinger, String IdentificationNumber, String uuidDevice);
        void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice);
        //void validateRequest(boolean result, CloseResponse response, int statusCode);

    }

    interface View extends BaseView<mabel_tech.com.scanovate_demo.UI.GetData.GetDataContract.Presenter> {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error);
        void onWebServiceStart();
        void finishFlow();
        void validateRequest(boolean result, CloseResponse response, int statusCode);
        void continueFlow(String uid);
        void continueFlowRegistro(String uid);

    }
}

