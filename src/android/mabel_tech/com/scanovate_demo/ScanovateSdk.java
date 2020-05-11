package mabel_tech.com.scanovate_demo;

import android.app.Activity;
import android.content.Intent;
import java.util.UUID;
import mabel_tech.com.scanovate_demo.UI.GetConfig.GetConfigActivity;
import mabel_tech.com.scanovate_demo.UI.Log;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;

public class ScanovateSdk {

    private static ScanovateHandler sHandler;

    public static void start(final Activity activity,
                             String documentType,
                             Integer productId,
                             boolean showLevelerUI,
                             String projectName_Sdk,
                             String apiKey_Sdk,
                             String Url_Sdk,
                             String numberId,
                             boolean verification,
                             ScanovateHandler handler) {

        String uuidDevice = UUID.randomUUID().toString();
        Intent myIntent = new Intent(activity, GetConfigActivity.class);
        DocumentTypeUtils.storeLevelerUI(activity, showLevelerUI);
        DocumentTypeUtils.storeProductId(activity, productId);
        DocumentTypeUtils.storeUuidDevice(activity, uuidDevice);
        DocumentTypeUtils.storeDocumentType(activity, documentType);
        GetConfigActivity.setContext(activity);
        RetrofitClient.setContext(activity);
        RetrofitClient.storeConfigSdk(activity, Url_Sdk, projectName_Sdk, apiKey_Sdk);
        DocumentTypeUtils.storeNumberId(activity,numberId);
        DocumentTypeUtils.storeVerification(activity,verification);
        sHandler = handler;
        new Log().execute("Info", "Start_SKD", "SKD initialization", "", uuidDevice, "");
        activity.startActivity(myIntent);
    }

    public static ScanovateHandler getHandler() {
        return sHandler;
    }
}
