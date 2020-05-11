package mabel_tech.com.scanovate_demo.UI.GetConfig;

import mabel_tech.com.scanovate_demo.UI.base.BasePresenter;
import mabel_tech.com.scanovate_demo.UI.base.BaseView;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.model.GetConfigResponse;

public interface GetConfigContract {

    interface Presenter extends BasePresenter {

        void getConfig(String message);
        void finishFlow(boolean result, CloseResponse response, Integer code, String uuidDevice);

    }

    interface View extends BaseView<mabel_tech.com.scanovate_demo.UI.GetConfig.GetConfigContract.Presenter>
    {

        void onWebServiceSuccess();
        void onWebServiceFailed(String error);
        void onWebServiceStart();
        void finishFlow(int statuscode, String error);
        void continueFlow(GetConfigResponse response);

    }
}

