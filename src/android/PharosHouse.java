package cordova.plugin.pharoshouse;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import mabel_tech.com.scanovate_demo.ScanovateHandler;
import mabel_tech.com.scanovate_demo.ScanovateSdk;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.network.ApiHelper;
import mabel_tech.com.scanovate_demo.network.RetrofitClient;
import okhttp3.ResponseBody;

/**
 * This class echoes a string called from JavaScript.
 */
public class PharosHouse extends CordovaPlugin {

    Context contect;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("capturar")) {
            this.capturar(args, callbackContext);
            return true;
        }
        return false;
    }

    private void capturar(JSONArray args, CallbackContext callbackContext) {
        if (args != null) {
        try
        {
            string documentType = args.getJSONObject(0).getString("documentType");
            int productId = Integer.parseInt(args.getJSONObject(0).getString("productId"));
            boolean showLevelerUI = args.getJSONObject(0).getString("showLevelerUI");
            string projectName_Sdk = args.getJSONObject(0).getString("projectName_Sdk");
            string apiKey_Sdk = args.getJSONObject(0).getString("apiKey_Sdk");
            string Url_Sdk = args.getJSONObject(0).getString("Url_Sdk");
            string numberId = args.getJSONObject(0).getString("numberId");
            boolean verification = args.getJSONObject(0).getString("productId");
              
            ScanovateSdk.start(this,
            documentType,
            1,
            false,
            projectName_Sdk,
            apiKey_Sdk,
            Url_Sdk,
            numberId,
            verification,
            new ScanovateHandler() {
                @Override
                public void onSuccess(final CloseResponse response, final int code, final String uuidDevice) {
                    //CloseResponse myReponse = response;
                    //String responseExtras = response.getExtras().getStateName() + response.getExtras().getIdState();
                    final String calificacion = response.getExtras().getStateName();
                    evaluateTransaction(response.getTransactionId());
                }

                @Override
                public void onFailure(final CloseResponse closeResponse) {
                    final String calificacion = closeResponse.getExtras().getStateName() +" "+ closeResponse.getExtras().getAdditionalProp1();
                }
             });

            } catch (Exception e) {
                callback.success("Something went wrong ", e);
            }
           
        } else {
            callbackContext.error("Please donot plass null value");
        }
    }

    private void evaluateTransaction(String transactionId) {

        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.validateTransaction("credibancoqa", transactionId, new ApiHelper.ValidateTransactionHandler() {
            @Override
            public void onSuccess(String stateName) {
                progress.dismiss();
                alert("Resultado de Transacción: " + stateName);
            }

            @Override
            public void onSuccess(int i, ResponseBody responseBody) {
                String algo = "";

            }

            @Override
            public void onConnectionFailed() {
                String algo = "";
            }

            @Override
            public void onFailure(int i, String s) {
                //evaluateTransaction(transactionId, contect);
            }
        }, contect);
    }
}
