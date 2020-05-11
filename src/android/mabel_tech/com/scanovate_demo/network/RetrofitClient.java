package mabel_tech.com.scanovate_demo.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import mabel_tech.com.scanovate_demo.Log;
import mabel_tech.com.scanovate_demo.R;
import mabel_tech.com.scanovate_demo.ScanovateApp;
import mabel_tech.com.scanovate_demo.model.CloseRegistroRequest;
import mabel_tech.com.scanovate_demo.model.CloseRequest;
import mabel_tech.com.scanovate_demo.model.CloseResponse;
import mabel_tech.com.scanovate_demo.model.DocRequest;
import mabel_tech.com.scanovate_demo.model.DocRequestFront;
import mabel_tech.com.scanovate_demo.model.ErrorResponse;
import mabel_tech.com.scanovate_demo.model.FaceRequest;
import mabel_tech.com.scanovate_demo.model.GetConfigRequest;
import mabel_tech.com.scanovate_demo.model.GetConfigResponse;
import mabel_tech.com.scanovate_demo.model.LogRequest;
import mabel_tech.com.scanovate_demo.model.LogResponse;
import mabel_tech.com.scanovate_demo.model.Score;
import mabel_tech.com.scanovate_demo.model.ScoreResponse;
import mabel_tech.com.scanovate_demo.model.UserAuthenticationRequest;
import mabel_tech.com.scanovate_demo.model.UserAuthenticationResponse;
import mabel_tech.com.scanovate_demo.model.VerificationIdentityRequest;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scanovate.hybridliveness.BuildConfig;

public class RetrofitClient implements ApiHelper {


    static Context context;

    final String PROJECT_NAME = RetrofitClient.getNameProject_Sdk();


    private static final String TAG = "okhttp";

    private static RetrofitApi instance = null;

    private static RetrofitApi getClientValidation() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("apiKey", RetrofitClient.getApiKey_Server())
                        .addHeader("projectName", RetrofitClient.getNameProject_Server())
                        .build();
                return chain.proceed(request);
            }
        });

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.getUrlSite_Server()+"/Integration/"+RetrofitClient.getNameProject_Server()+"/Validation/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .build())
                .build()
                .create(RetrofitApi.class);
    }

    private static RetrofitApi getClientValidationTransaction() {

        //if (instance == null) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("apiKey", RetrofitClient.getApiKey_Server())
                        .addHeader("projectName", RetrofitClient.getNameProject_Server())
                        .build();
                return chain.proceed(request);
            }
        });

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.getUrlSite_Server()+"/"+RetrofitClient.getNameProject_Server()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .build())
                .build()
                .create(RetrofitApi.class);
        //}

    }


    private static RetrofitApi getClientFront(final String documentType) {

        //if (instance == null) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("apiKey", RetrofitClient.getApiKey_Server())
                        .addHeader("projectName", RetrofitClient.getNameProject_Server())
                        //.addHeader("DocumentType", documentType)
                        .build();
                return chain.proceed(request);
            }
        });

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.getUrlSite_Server()+"/Integration/"+RetrofitClient.getNameProject_Server()+"/Validation/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .build())
                .build()
                .create(RetrofitApi.class);
        //}

    }

    private static RetrofitApi getClientConfig() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor =
                    new HttpLoggingInterceptor(message -> Log.d(TAG, message));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(httpLoggingInterceptor);
        }

        httpClient.addInterceptor(chain -> {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("apiKey", RetrofitClient.getApiKey_Sdk())
                    .addHeader("projectName", RetrofitClient.getNameProject_Sdk())
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.getUrlSite_Sdk()+RetrofitClient.getNameProject_Sdk()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .build())
                .build()
                .create(RetrofitApi.class);
    }
    private static RetrofitApi getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor =
                    new HttpLoggingInterceptor(message -> Log.d(TAG, message));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(httpLoggingInterceptor);
        }

        httpClient.addInterceptor(chain -> {
            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("apiKey", RetrofitClient.getApiKey_Sdk())
                    .addHeader("projectName", RetrofitClient.getNameProject_Sdk())
                    .build();
            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .baseUrl(RetrofitClient.getUrlSite_Sdk()+RetrofitClient.getNameProject_Sdk()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .build())
                .build()
                .create(RetrofitApi.class);
    }


    private boolean checkConnection(Context mContext) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) ScanovateApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        return Objects.requireNonNull(connectivityManager).getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void postFace(FaceRequest request, final FaceCompletionHandler handler, Context mContext) {

        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientValidation().postFace(request).enqueue(new Callback<CloseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseResponse> call, @NonNull Response<CloseResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse mError;
                    try {
                        mError = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        handler.onFailure(response.code(), mError.getError1());
                    } catch (IOException e) {
                        handler.onFailure(response.code(), ScanovateApp.getAppContext().getResources().getString(R.string.unknown_error));
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    @Override
    public void postDocFront(DocRequestFront request, final CompletionHandler handler, String documentType, Context mContext) {

        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientFront(documentType).postDocFront(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    @Override
    public void postDocBack(DocRequest request, final CloseCompletionHandler handler, Context mContext) {

        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientValidation().postDocBack(request).enqueue(new Callback<CloseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseResponse> call, @NonNull Response<CloseResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });
    }

    @Override
    public void close(final CloseRequest request, final CloseCompletionHandler handler, Context mContext) {

        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientValidation().close(request).enqueue(new Callback<CloseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseResponse> call, @NonNull Response<CloseResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    @Override
    public void validateTransaction(final String project, final String txId, final ValidateTransactionHandler handler, Context mContext) {
        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
        } else {
            getClientValidationTransaction().validateTransaction(txId, false).enqueue(new Callback<ScoreResponse>() {
                public void onResponse(Call<ScoreResponse> call, Response<ScoreResponse> response) {
                    ScoreResponse responseBody = (ScoreResponse) response.body();
                    if (responseBody != null) {
                        ArrayList<Score> scoreArrayList = responseBody.getScores();
                        if (scoreArrayList == null) {
                            handler.onFailure(400, "");
                        } else if (((Score) scoreArrayList.get(0)).getId() != 1L) {
                            handler.onSuccess(((Score) scoreArrayList.get(0)).getStateName());
                        } else {
                            RetrofitClient.this.validateTransaction(project, txId, handler, mContext);
                        }
                    }

                }

                public void onFailure(Call<ScoreResponse> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public void customerVerification(VerificationIdentityRequest request, VerificationCustomerHandler handler, Context mContext) {
        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClient().customerVerification(request).enqueue(new Callback<CloseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseResponse> call, @NonNull Response<CloseResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }


    @Override
    public void RegisterLog(LogRequest request, RegisterLogHandler handler) {
        request.Project = PROJECT_NAME;
        getClient().RegisterLog(request).enqueue(new Callback<LogResponse>() {
            @Override
            public void onResponse(Call<LogResponse> call, Response<LogResponse> response) {
                LogResponse responseBody = response.body();
                if (responseBody != null) {
                    int idResponse = responseBody.getResponse();
                    if (idResponse > 0) {
                        handler.onFailure(400, "");
                    } else {
                        handler.onSuccess(idResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<LogResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void CloseRegistro(final CloseRegistroRequest request, final CloseCompletionHandler handler, Context mContext) {

        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientValidation().CloseRegistro(request).enqueue(new Callback<CloseResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloseResponse> call, @NonNull Response<CloseResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CloseResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    @Override
    public void UserAuthentication(UserAuthenticationRequest userAuthentication, UserAuthenticationHandler handler, Context mContext) {
        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClient().UserAuthentication(userAuthentication).enqueue(new Callback<UserAuthenticationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserAuthenticationResponse> call, @NonNull Response<UserAuthenticationResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserAuthenticationResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    @Override
    public void GetConfig(GetConfigRequest getConfig, GetConfigHandler handler, Context mContext) {
        if (!checkConnection(mContext)) {
            handler.onConnectionFailed();
            return;
        }

        getClientConfig().GetConfig(getConfig.Message).enqueue(new Callback<GetConfigResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetConfigResponse> call, @NonNull Response<GetConfigResponse> response) {

                if (response.isSuccessful()) {
                    handler.onSuccess(response.code(), response.body());
                } else {
                    handler.onFailure(response.code(), response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetConfigResponse> call, @NonNull Throwable t) {
                handler.onFailure(-1, t.toString());
            }
        });

    }

    public static void storeConfigSdk(Activity activity, String UrlSite_Sdk, String nameProject_Sdk, String apiKey_Sdk) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("urlSite_Sdk", UrlSite_Sdk);
        editor.putString("nameProject_Sdk", nameProject_Sdk);
        editor.putString("apiKey_Sdk", apiKey_Sdk);
        editor.commit();
    }

    public static void storeConfigServer(Context activity, String UrlSite_Server, String nameProject_Server, String apiKey_Server) {
        SharedPreferences prefs = activity.getSharedPreferences("shared_login_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("urlSite_Server", UrlSite_Server);
        editor.putString("nameProject_Server", nameProject_Server);
        editor.putString("apiKey_Server", apiKey_Server);
        editor.commit();
    }


    public static String getUrlSite_Sdk() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("urlSite_Sdk", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }

    public static String getNameProject_Sdk() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("nameProject_Sdk", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }
    public static String getApiKey_Sdk() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("apiKey_Sdk", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }


    public static String getUrlSite_Server() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("urlSite_Server", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }

    public static String getNameProject_Server() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("nameProject_Server", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }

    public static String getApiKey_Server() {
        String value = "1";
        SharedPreferences data = context.getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        if (data != null) {
            value = data.getString("apiKey_Server", "");
        }
        assert value != null;
        if (value.isEmpty()) {
            value = "1";
        }

        return value;
    }



    public static void setContext(Context context) {
        RetrofitClient.context = context;
    }
}
